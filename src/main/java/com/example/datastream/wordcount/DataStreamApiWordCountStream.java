package com.example.datastream.wordcount;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author luxiaohua
 * @create 2022-06-28 14:42
 */
public class DataStreamApiWordCountStream {

    public static void main(String[] args) throws Exception {

        // 初始化环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 定义kafka数据源
        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("192.168.0.192:9092")
                .setTopics("TOPIC_WORD")
                .setGroupId("TEST_GROUP")
                .setStartingOffsets(OffsetsInitializer.latest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        // 加载数据源
        DataStreamSource<String> kafkaWordSource = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Word Source");

        // 数据转换
        SingleOutputStreamOperator<Word> wordStreamOperator = kafkaWordSource.flatMap(new TokenizerFunction());

        // 按单词分组
        KeyedStream<Word, String> wordKeyedStream = wordStreamOperator.keyBy(new KeySelector<Word, String>() {
            @Override
            public String getKey(Word word) throws Exception {
                return word.getWord();
            }
        });

        // 求和
        SingleOutputStreamOperator<Word> sumStream = wordKeyedStream.sum("frequency");
        sumStream.print();


        env.execute("WordCountStream");

    }



}
