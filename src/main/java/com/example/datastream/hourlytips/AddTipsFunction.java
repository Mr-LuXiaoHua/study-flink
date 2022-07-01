package com.example.datastream.hourlytips;

import com.example.datastream.rideandfare.TaxiFare;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

/**
 * @author luxiaohua
 * @create 2022-07-01 14:18
 */
public class AddTipsFunction extends ProcessWindowFunction<TaxiFare, HourlyTip, Long, TimeWindow> {


    /**
     * 汇总时间段内某个司机的总的消费数量
     * @param key
     * @param context
     * @param taxiFares
     * @param collector
     * @throws Exception
     */
    @Override
    public void process(Long key, Context context, Iterable<TaxiFare> taxiFares, Collector<HourlyTip> collector) throws Exception {
        float sumOfTips = 0F;
        for (TaxiFare taxiFare : taxiFares) {
            sumOfTips += taxiFare.getTip();
        }
        HourlyTip hourlyTip = new HourlyTip();
        hourlyTip.setEventTime(context.window().getEnd());
        hourlyTip.setDriverId(key);
        hourlyTip.setTips(sumOfTips);

        collector.collect(hourlyTip);
    }
}
