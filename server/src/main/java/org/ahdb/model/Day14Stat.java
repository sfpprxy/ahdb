package org.ahdb.model;


import io.quarkus.runtime.annotations.RegisterForReflection;

import java.sql.Timestamp;


@RegisterForReflection
public class Day14Stat {

    public Day14Stat() {
    }

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

    public String getId() {
        return id;
    }

    public Day14Stat setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Day14Stat setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getvIndex() {
        return vIndex;
    }

    public Day14Stat setvIndex(Integer vIndex) {
        this.vIndex = vIndex;
        return this;
    }

    public Integer getMaxStock() {
        return maxStock;
    }

    public Day14Stat setMaxStock(Integer maxStock) {
        this.maxStock = maxStock;
        return this;
    }

    public Integer getVendorSell() {
        return vendorSell;
    }

    public Day14Stat setVendorSell(Integer vendorSell) {
        this.vendorSell = vendorSell;
        return this;
    }

    public Integer getMarket() {
        return market;
    }

    public Day14Stat setMarket(Integer market) {
        this.market = market;
        return this;
    }

    public Integer getMarketD() {
        return marketD;
    }

    public Day14Stat setMarketD(Integer marketD) {
        this.marketD = marketD;
        return this;
    }

    public Integer getAuctions() {
        return auctions;
    }

    public Day14Stat setAuctions(Integer auctions) {
        this.auctions = auctions;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Day14Stat setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Integer getItemLv() {
        return itemLv;
    }

    public Day14Stat setItemLv(Integer itemLv) {
        this.itemLv = itemLv;
        return this;
    }

    public String getItemClass() {
        return itemClass;
    }

    public Day14Stat setItemClass(String itemClass) {
        this.itemClass = itemClass;
        return this;
    }

    public String getSubClass() {
        return subClass;
    }

    public Day14Stat setSubClass(String subClass) {
        this.subClass = subClass;
        return this;
    }

    public Timestamp getTime_bucket() {
        return time_bucket;
    }

    public Day14Stat setTime_bucket(Timestamp time_bucket) {
        this.time_bucket = time_bucket;
        return this;
    }
}
