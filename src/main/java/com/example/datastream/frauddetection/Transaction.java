package com.example.datastream.frauddetection;

/**
 * @author luxiaohua
 * @create 2022-06-29 14:21
 */
public class Transaction {
    private long accountId;
    private long timestamp;
    private double amount;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
