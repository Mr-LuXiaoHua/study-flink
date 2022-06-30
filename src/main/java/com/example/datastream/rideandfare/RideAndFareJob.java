package com.example.datastream.rideandfare;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;

/**
 * @author luxiaohua
 * @create 2022-06-30 11:05
 */
public class RideAndFareJob {

    public static void main(String[] args) throws Exception {

        // 初始化环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(5000L);
        env.setStateBackend(new FsStateBackend("file:///mnt/data/checkpoints"));

        env.setParallelism(2);

        // 定义出租车-车程数据源
        KafkaSource<TaxiRide> rideSource = KafkaSource.<TaxiRide>builder()
                .setBootstrapServers("192.168.0.192:9092")
                .setTopics("TOPIC_RIDE")
                .setGroupId("TEST_GROUP")
                .setClientIdPrefix("ride") // 避免kafka clientId重复
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new TaxiRideDeserialization())
                .build();

        // 定义出租车-车费数据源
        KafkaSource<TaxiFare> fareSource = KafkaSource.<TaxiFare>builder()
                .setBootstrapServers("192.168.0.192:9092")
                .setTopics("TOPIC_FARE")
                .setGroupId("TEST_GROUP")
                .setClientIdPrefix("fare") // 避免kafka clientId重复
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new TaxiFareDeserialization())
                .build();


        // 从车程事件中过滤中车程开始时间，并按车程标识 rideId 分组
        KeyedStream<TaxiRide, Long> rideStream = env.fromSource(rideSource, WatermarkStrategy.noWatermarks(), "ride source")
                .filter(ride -> ride.getStart()).keyBy(TaxiRide::getRideId);

        // 付车费事件按行程标识 rideId 分组
        KeyedStream<TaxiFare, Long> fareStream = env.fromSource(fareSource, WatermarkStrategy.noWatermarks(), "fare source")
                .keyBy(TaxiFare::getRideId);

        rideStream.connect(fareStream).flatMap(new EnrichmentFunction())
                .uid("enrichment") // uid for this operator's state
                .name("enrichment") // name for this operator in the web UI
                .addSink(new PrintSinkFunction<>());


        env.execute("Join Rides with Fares");
    }
}
