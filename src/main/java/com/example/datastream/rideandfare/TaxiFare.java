package com.example.datastream.rideandfare;

import java.time.Instant;

/**
 * 出租车车费(taxi fare)事件结构
 * @author luxiaohua
 * @create 2022-06-30 10:57
 */
public class TaxiFare {

    private	Long   	rideId         ;   // 每次车程的唯一id
    private	Long   	taxiId         ;   // 每一辆出租车的唯一id
    private	Long   	driverId       ;   // 每一位司机的唯一id
    private Long startTime      ;   // 车程开始时间
    private	String 	paymentType    ;   // 现金(CASH)或刷卡(CARD)
    private	Float  	tip            ;   // 小费
    private	Float  	tolls          ;   // 过路费
    private	Float  	totalFare      ;   // 总计车费


    public Long getRideId() {
        return rideId;
    }

    public void setRideId(Long rideId) {
        this.rideId = rideId;
    }

    public Long getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(Long taxiId) {
        this.taxiId = taxiId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Float getTip() {
        return tip;
    }

    public void setTip(Float tip) {
        this.tip = tip;
    }

    public Float getTolls() {
        return tolls;
    }

    public void setTolls(Float tolls) {
        this.tolls = tolls;
    }

    public Float getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(Float totalFare) {
        this.totalFare = totalFare;
    }

    @Override
    public String toString() {
        return "TaxiFare{" +
                "rideId=" + rideId +
                ", taxiId=" + taxiId +
                ", driverId=" + driverId +
                ", startTime=" + startTime +
                ", paymentType='" + paymentType + '\'' +
                ", tip=" + tip +
                ", tolls=" + tolls +
                ", totalFare=" + totalFare +
                '}';
    }
}
