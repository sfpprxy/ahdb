package org.ahdb.model;


import java.sql.Timestamp;
import java.util.List;


public class ItemStats {

    public ItemDesc itemDesc;
    public Integer currentMarket;
    public Timestamp at;
    public Integer avgMarket3Day;
    public Integer avgMarket14Day;
    public List<DailyStat> dailyStats;

    public ItemDesc getItemDesc() {
        return itemDesc;
    }

    public ItemStats setItemDesc(ItemDesc itemDesc) {
        this.itemDesc = itemDesc;
        return this;
    }

    public Integer getCurrentMarket() {
        return currentMarket;
    }

    public ItemStats setCurrentMarket(Integer currentMarket) {
        this.currentMarket = currentMarket;
        return this;
    }

    public Timestamp getAt() {
        return at;
    }

    public ItemStats setAt(Timestamp at) {
        this.at = at;
        return this;
    }

    public Integer getAvgMarket3Day() {
        return avgMarket3Day;
    }

    public ItemStats setAvgMarket3Day(Integer avgMarket3Day) {
        this.avgMarket3Day = avgMarket3Day;
        return this;
    }

    public Integer getAvgMarket14Day() {
        return avgMarket14Day;
    }

    public ItemStats setAvgMarket14Day(Integer avgMarket14Day) {
        this.avgMarket14Day = avgMarket14Day;
        return this;
    }

    public List<DailyStat> getDailyStats() {
        return dailyStats;
    }

    public ItemStats setDailyStats(List<DailyStat> dailyStats) {
        this.dailyStats = dailyStats;
        return this;
    }
}
