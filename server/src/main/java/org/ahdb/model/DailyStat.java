package org.ahdb.model;


import java.sql.Timestamp;


public class DailyStat {

    public Timestamp day;
    public Integer avgMarket;

    public Timestamp getDay() {
        return day;
    }

    public DailyStat setDay(Timestamp day) {
        this.day = day;
        return this;
    }

    public Integer getAvgMarket() {
        return avgMarket;
    }

    public DailyStat setAvgMarket(Integer avgMarket) {
        this.avgMarket = avgMarket;
        return this;
    }
}
