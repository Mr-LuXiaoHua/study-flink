package com.example.datastream.hourlytips;

import com.example.datastream.rideandfare.TaxiFare;
import com.example.datastream.rideandfare.TaxiFareDeserialization;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @author luxiaohua
 * @create 2022-07-01 14:09
 */
public class HourlyTipsJob {
    public static void main(String[] args) throws Exception {

        // 初始化环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 定义出租车-车费数据源
        KafkaSource<TaxiFare> fareSource = KafkaSource.<TaxiFare>builder()
                .setBootstrapServers("192.168.0.192:9092")
                .setTopics("TOPIC_FARE")
                .setGroupId("TEST_GROUP")
                .setClientIdPrefix("fare") // 避免kafka clientId重复
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new TaxiFareDeserialization())
                .build();

        DataStreamSource<TaxiFare> fareStream = env.fromSource(fareSource, WatermarkStrategy.<TaxiFare>forMonotonousTimestamps().withTimestampAssigner((fare, t) -> fare.getStartTime()), "fare source");

        // 按司机分组，对每小时内的数据进行统计，求出每个司机每小时的总小费
        SingleOutputStreamOperator<HourlyTip> hourlyTipsStream = fareStream.keyBy(TaxiFare::getDriverId)
                .window(TumblingEventTimeWindows.of(Time.hours(1)))
                .process(new AddTipsFunction());


        /**
         *  window和windowAll的区别
         *
         *  keyBy后数据分流，window是把不同的key分开聚合成窗口
         *      而windowAll是把所有的key都聚合起来，所以windowAll的并行度只能为1，而window可以有多个并行度
         *
         */


        // 把所有key汇总起来，找出每个小时总消费最多的司机
        SingleOutputStreamOperator<HourlyTip> hourlyMaxStream = hourlyTipsStream.windowAll(TumblingEventTimeWindows.of(Time.hours(1))).max("tips");


        hourlyMaxStream.addSink(new PrintSinkFunction<>());

        env.execute("Hourly Tips");

    }
}
