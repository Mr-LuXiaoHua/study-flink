package com.example.datastream.rideandfare;

/**
 * @author luxiaohua
 * @create 2022-06-30 14:09
 */
public class RideAndFare {

    public TaxiRide ride;
    public TaxiFare fare;

    public TaxiRide getRide() {
        return ride;
    }

    public void setRide(TaxiRide ride) {
        this.ride = ride;
    }

    public TaxiFare getFare() {
        return fare;
    }

    public void setFare(TaxiFare fare) {
        this.fare = fare;
    }

    @Override
    public String toString() {
        return "RideAndFare{" +
                "ride=" + ride +
                ", fare=" + fare +
                '}';
    }
}
