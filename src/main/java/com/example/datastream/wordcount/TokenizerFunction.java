package com.example.datastream.wordcount;


import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @author luxiaohua
 * @create 2022-06-28 14:58
 */
public class TokenizerFunction implements FlatMapFunction<String, Word> {

    @Override
    public void flatMap(String value, Collector<Word> collector) throws Exception {
        if (value == null) {
            return;
        }
        String[] tokens = value.toLowerCase().split("\\W+");
        for (String token : tokens) {
            if (token != null && token.length() > 0) {
                collector.collect(new Word(token, 1L));
            }
        }
    }
}
