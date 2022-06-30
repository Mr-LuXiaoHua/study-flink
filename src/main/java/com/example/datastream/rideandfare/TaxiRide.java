package com.example.datastream.rideandfare;

import java.time.Instant;

/**
 * 出租车车程(taxi ride)事件结构
 * @author luxiaohua
 * @create 2022-06-30 10:52
 */
public class TaxiRide {

    private	Long   	rideId         ;   // 每次车程的唯一id
    private	Long   	taxiId         ;   // 每一辆出租车的唯一id
    private	Long   	driverId       ;   // 每一位司机的唯一id
    private	Boolean	isStart        ;   // 行程开始事件为 TRUE， 行程结束事件为 FALSE
    private	Long	eventTime      ;   // 事件的时间戳
    private	Float  	startLon       ;   // 车程开始位置的经度
    private	Float  	startLat       ;   // 车程开始位置的维度
    private	Float  	endLon         ;   // 车程结束位置的经度
    private	Float  	endLat         ;   // 车程结束位置的维度
    private	Short  	passengerCnt   ;   // 乘车人数

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

    public Boolean getStart() {
        return isStart;
    }

    public void setStart(Boolean start) {
        isStart = start;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public Float getStartLon() {
        return startLon;
    }

    public void setStartLon(Float startLon) {
        this.startLon = startLon;
    }

    public Float getStartLat() {
        return startLat;
    }

    public void setStartLat(Float startLat) {
        this.startLat = startLat;
    }

    public Float getEndLon() {
        return endLon;
    }

    public void setEndLon(Float endLon) {
        this.endLon = endLon;
    }

    public Float getEndLat() {
        return endLat;
    }

    public void setEndLat(Float endLat) {
        this.endLat = endLat;
    }

    public Short getPassengerCnt() {
        return passengerCnt;
    }

    public void setPassengerCnt(Short passengerCnt) {
        this.passengerCnt = passengerCnt;
    }

    @Override
    public String toString() {
        return "TaxiRide{" +
                "rideId=" + rideId +
                ", taxiId=" + taxiId +
                ", driverId=" + driverId +
                ", isStart=" + isStart +
                ", eventTime=" + eventTime +
                ", startLon=" + startLon +
                ", startLat=" + startLat +
                ", endLon=" + endLon +
                ", endLat=" + endLat +
                ", passengerCnt=" + passengerCnt +
                '}';
    }
}
