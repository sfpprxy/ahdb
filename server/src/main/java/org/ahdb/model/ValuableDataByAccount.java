package org.ahdb.model;


import java.sql.Timestamp;


public class ValuableDataByAccount {

    public Timestamp time;
    public String type;
    public String accountId;
    public ValuableData valuableData;

    public Timestamp getTime() {
        return time;
    }

    public ValuableDataByAccount setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    public String getType() {
        return type;
    }

    public ValuableDataByAccount setType(String type) {
        this.type = type;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public ValuableDataByAccount setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public ValuableData getValuableData() {
        return valuableData;
    }

    public ValuableDataByAccount setValuableData(ValuableData valuableData) {
        this.valuableData = valuableData;
        return this;
    }

    @Override
    public String toString() {
        return "ValuableDataByAccount{" +
                "\ntime=" + time +
                "\ntype='" + type + '\'' +
                "\naccountId='" + accountId + '\'' +
                "\nvaluableData=" + valuableData +
                '}';
    }
}
