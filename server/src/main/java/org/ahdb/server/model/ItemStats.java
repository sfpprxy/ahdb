package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ItemStats {

    public ItemDesc itemDesc;
    public Integer currentMarket;
    public Timestamp at;
    public Integer avgMarket3Day;
    public Integer avgMarket14Day;
    public List<DailyStat> dailyStats;

}
