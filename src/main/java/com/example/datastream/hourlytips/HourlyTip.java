package com.example.datastream.hourlytips;

/**
 * @author luxiaohua
 * @create 2022-07-01 14:19
 */
public class HourlyTip {

    /**
     * 小时结束时的时间戳
     */
    private Long eventTime;

    /**
     * 司机id driverId
     */
    private Long driverId;

    /**
     * 获得的小费总数
     */
    private Float tips;

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Float getTips() {
        return tips;
    }

    public void setTips(Float tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "HourlyTip{" +
                "eventTime=" + eventTime +
                ", driverId=" + driverId +
                ", tips=" + tips +
                '}';
    }
}
