package com.example.datastream.wordcount;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author luxiaohua
 * @create 2022-06-28 14:42
 */
public class DataStreamApiWordCountBatch {

    public static void main(String[] args) throws Exception {


        ParameterTool parameterFromArgs = ParameterTool.fromArgs(args);
        String input = parameterFromArgs.getRequired("input");

        // 初始化环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        // 加载数据源
        DataStreamSource<String> wordSource = env.readTextFile(input, "UTF-8");

        // 数据转换
        SingleOutputStreamOperator<Word> wordStreamOperator = wordSource.flatMap(new TokenizerFunction());

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

        env.execute("WordCountBatch");
    }



}
