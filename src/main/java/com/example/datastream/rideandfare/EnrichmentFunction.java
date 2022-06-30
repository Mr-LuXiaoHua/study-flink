package com.example.datastream.rideandfare;


import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.Objects;

/**
 * @author luxiaohua
 * @create 2022-06-30 14:08
 */
public class EnrichmentFunction extends RichCoFlatMapFunction<TaxiRide, TaxiFare, RideAndFare> {

    private ValueState<TaxiRide> taxiRideValueState;
    private ValueState<TaxiFare> taxiFareValueState;


    @Override
    public void open(Configuration parameters) throws Exception {
        ValueStateDescriptor<TaxiRide> taxiRideDescriptor = new ValueStateDescriptor<TaxiRide>("save-ride", TaxiRide.class);
        ValueStateDescriptor<TaxiFare> taxiFareDescriptor = new ValueStateDescriptor<TaxiFare>("save-fare", TaxiFare.class);

        taxiRideValueState = getRuntimeContext().getState(taxiRideDescriptor);
        taxiFareValueState = getRuntimeContext().getState(taxiFareDescriptor);

    }


    /**
     * 当车程事件到来，检查车费的taxiFareValueState是否保存有对应行程付费记录
     * 如果有，则匹配输出，清空状态
     * 如果没有，则将车程事件保存起来
     */
    @Override
    public void flatMap1(TaxiRide taxiRide, Collector<RideAndFare> collector) throws Exception {
        TaxiFare taxiFare = taxiFareValueState.value();
        if (Objects.isNull(taxiFare)) {
            taxiRideValueState.update(taxiRide);
        } else {
            taxiFareValueState.clear();

            RideAndFare rideAndFare = new RideAndFare();
            rideAndFare.setRide(taxiRide);
            rideAndFare.setFare(taxiFare);

            collector.collect(rideAndFare);
        }
    }


    /**
     * 当付费事件到来，检查车程的taxiRideValueState是否保存有对应行程车程记录
     * 如果有，则匹配输出，清空状态
     * 如果没有，则将付费事件保存起来
     */
    @Override
    public void flatMap2(TaxiFare taxiFare, Collector<RideAndFare> collector) throws Exception {
        TaxiRide taxiRide = taxiRideValueState.value();
        if (Objects.isNull(taxiRide)) {
            taxiFareValueState.update(taxiFare);
        } else {
            taxiRideValueState.clear();

            RideAndFare rideAndFare = new RideAndFare();
            rideAndFare.setRide(taxiRide);
            rideAndFare.setFare(taxiFare);

            collector.collect(rideAndFare);

        }
    }
}
