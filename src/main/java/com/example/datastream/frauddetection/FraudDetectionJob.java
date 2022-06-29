package com.example.datastream.frauddetection;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


/**
 * @author luxiaohua
 * @create 2022-06-29 11:29
 */
public class FraudDetectionJob {
    public static void main(String[] args) throws Exception {
        // 初始化环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // kafka消息格式: {"accountId":1001, "timestamp":1656490723171, "amount":0.12}

        // 定义Kafka数据源
        KafkaSource<Transaction> source = KafkaSource.<Transaction>builder()
                .setBootstrapServers("192.168.0.192:9092")
                .setTopics("TOPIC_FRAUD_DETECTION")
                .setGroupId("TEST_GROUP")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new TransactionDeserialization())
                .build();

        // 加载数据源
        DataStreamSource<Transaction> fraudDetectionSource
                = env.fromSource(source, WatermarkStrategy.noWatermarks(), "FraudDetection-Source");

        // 处理数据
        SingleOutputStreamOperator<Alert> alertStreamOperator = fraudDetectionSource.keyBy(Transaction::getAccountId)
                .process(new FraudDetector())
                .name("Fraud-Detector");

        // 输出告警结果
        alertStreamOperator.addSink(new AlertSink())
                .name("Send-Alerts");

        env.execute("Fraud Detection");

    }
}
