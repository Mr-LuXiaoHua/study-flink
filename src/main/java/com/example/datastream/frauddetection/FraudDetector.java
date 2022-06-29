package com.example.datastream.frauddetection;

import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;

import java.io.IOException;
import java.util.Objects;

/**
 * @author luxiaohua
 * @create 2022-06-29 14:58
 */
public class FraudDetector extends KeyedProcessFunction<Long, Transaction, Alert> {

    /**
     * 定义小金额边界
     */
    private static final double SMALL_AMOUNT = 1.00;

    /**
     * 定义大金额边界
     */
    private static final double LARGE_AMOUNT = 500.00;

    /**
     * 1分钟时间
     */
    private static final long ONE_MINUTE = 60 * 1000;

    /**
     * 保存是否有消费小金额的状态
     */
    private transient ValueState<Boolean> smallAmountState;

    /**
     * 定时器状态
     */
    private transient ValueState<Long> timerState;

    @Override
    public void open(Configuration parameters) throws Exception {
        // 初始化ValueState

        ValueStateDescriptor<Boolean> smallAmountStateDescriptor = new ValueStateDescriptor<Boolean>("small-amount-state", Types.BOOLEAN);
        smallAmountState = getRuntimeContext().getState(smallAmountStateDescriptor);

        ValueStateDescriptor<Long> timerStateDescriptor = new ValueStateDescriptor<Long>("timer-state", Types.LONG);
        timerState = getRuntimeContext().getState(timerStateDescriptor);

    }

    @Override
    public void processElement(Transaction transaction, Context context, Collector<Alert> collector) throws Exception {
        if (Objects.isNull(transaction)) {
            return;
        }
        // Get the current state for the current key
        Boolean lastTransactionWasSmall = smallAmountState.value();

        // Check if the flag is set
        if (Objects.nonNull(lastTransactionWasSmall)) {
            if (transaction.getAmount() > LARGE_AMOUNT) {
                Alert alert = new Alert();
                alert.setAccountId(transaction.getAccountId());
                alert.setAmount(transaction.getAmount());

                collector.collect(alert);
            }
            clearUp(context);
        }

        if (transaction.getAmount() < SMALL_AMOUNT) {
            // set the flag to true
            smallAmountState.update(true);

            // 注册定时器，设置一个当前时间一分钟后触发的定时器，同时，将触发时间保存到 timerState 状态中。
            long timer = context.timerService().currentProcessingTime() + ONE_MINUTE;
            context.timerService().registerProcessingTimeTimer(timer);
            timerState.update(timer);
        }

    }

    @Override
    public void onTimer(long timestamp, OnTimerContext ctx, Collector<Alert> out) throws Exception {
        // remove flag after 1 minute
        timerState.clear();
        smallAmountState.clear();
    }

    private void clearUp(Context ctx) {
        try {
            // delete timer
            Long timer = timerState.value();
            ctx.timerService().deleteProcessingTimeTimer(timer);

            timerState.clear();
            smallAmountState.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
