package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Getter
@Setter
@Accessors(chain = true)
public class Day14Stat {

    public String id;
    public String name;
    public Integer vIndex;
    public Integer maxStock;
    public Integer vendorSell;
    public Integer market;
    public Integer marketD;
    public Integer auctions;
    public Integer quantity;
    public Integer itemLv;
    public String itemClass;
    public String subClass;
    public Timestamp time_bucket;

}
