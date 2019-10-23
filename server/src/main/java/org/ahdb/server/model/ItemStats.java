package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ItemStats {
    public ItemDesc itemDesc;
    public Integer currentMarket;
    public Integer avgMarketToday;
    public Integer avgMarket14Day;
}
