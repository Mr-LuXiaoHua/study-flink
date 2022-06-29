package com.example.datastream.frauddetection;

import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author luxiaohua
 * @create 2022-06-29 16:13
 */
public class AlertSink implements SinkFunction<Alert> {

    private static final Logger LOG = LoggerFactory.getLogger(AlertSink.class);

    public AlertSink() {
    }

    @Override
    public void invoke(Alert value, Context context) throws Exception {
        LOG.info(value.toString());
    }
}
