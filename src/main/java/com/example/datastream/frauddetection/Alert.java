package com.example.datastream.frauddetection;

/**
 * @author luxiaohua
 * @create 2022-06-29 14:59
 */
public class Alert {
    private long accountId;
    private Double amount;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "accountId=" + accountId +
                ", amount=" + amount +
                '}';
    }
}
