package org.jwork.ahdb.model;

import java.sql.Timestamp;

public class ValuableDataByAccount {

    public java.sql.Timestamp time;
    public String type;
    public String accountId;
    public ValuableData valuableData;

    public Timestamp getTime() {
        return time;
    }

    public ValuableDataByAccount setTime(Timestamp timecc) {
        time = timecc;
        return this;
    }

    public String getType() {
        return type;
    }

    public ValuableDataByAccount setType(String typecc) {
        type = typecc;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public ValuableDataByAccount setAccountId(String accountIdcc) {
        accountId = accountIdcc;
        return this;
    }

    public ValuableData getValuableData() {
        return valuableData;
    }

    public ValuableDataByAccount setValuableData(ValuableData valuableDatacc) {
        valuableData = valuableDatacc;
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
