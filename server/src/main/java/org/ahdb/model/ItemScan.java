package org.ahdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class ItemScan {
    // itemString,minBuyout,marketValue,numAuctions,quantity,lastScan
    @Id
    public String id;
    public String itemId;
    public Integer minBuyout;
    public Integer marketValue;
    public Integer numAuctions;
    public Integer quantity;
    public Timestamp scanTime;
    public String realm;
    public Timestamp addTime;

    public String getId() {
        return id;
    }

    public ItemScan setId(String id) {
        this.id = id;
        return this;
    }

    public String getItemId() {
        return itemId;
    }

    public ItemScan setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public Integer getMinBuyout() {
        return minBuyout;
    }

    public ItemScan setMinBuyout(Integer minBuyout) {
        this.minBuyout = minBuyout;
        return this;
    }

    public Integer getMarketValue() {
        return marketValue;
    }

    public ItemScan setMarketValue(Integer marketValue) {
        this.marketValue = marketValue;
        return this;
    }

    public Integer getNumAuctions() {
        return numAuctions;
    }

    public ItemScan setNumAuctions(Integer numAuctions) {
        this.numAuctions = numAuctions;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemScan setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Timestamp getScanTime() {
        return scanTime;
    }

    public ItemScan setScanTime(Timestamp scanTime) {
        this.scanTime = scanTime;
        return this;
    }

    public String getRealm() {
        return realm;
    }

    public ItemScan setRealm(String realm) {
        this.realm = realm;
        return this;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public ItemScan setAddTime(Timestamp addTime) {
        this.addTime = addTime;
        return this;
    }

    @Override
    public String toString() {
        return "ItemScan " +
                id + " " +
                itemId + " " +
                minBuyout.toString() + " " +
                marketValue.toString() + " " +
                numAuctions.toString() + " " +
                quantity.toString() + " " +
                scanTime.toString();
    }

}
