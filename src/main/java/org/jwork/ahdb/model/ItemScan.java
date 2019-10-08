package org.jwork.ahdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@IdClass(ItemScan.IdClass.class)
public class ItemScan {
    // itemString,minBuyout,marketValue,numAuctions,quantity,lastScan
    @Id
    public String itemId;
    public Integer minBuyout;
    public Double marketValue;
    public Integer numAuctions;
    public Integer quantity;
    @Id
    public Timestamp scanTime;
    public String realm;
    public Timestamp addTime;

    public String getItemId() {
        return itemId;
    }

    public ItemScan setItemId(String itemIdcc) {
        itemId = itemIdcc;
        return this;
    }

    public Integer getMinBuyout() {
        return minBuyout;
    }

    public ItemScan setMinBuyout(Integer minBuyoutcc) {
        minBuyout = minBuyoutcc;
        return this;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public ItemScan setMarketValue(Double marketValuecc) {
        marketValue = marketValuecc;
        return this;
    }

    public Integer getNumAuctions() {
        return numAuctions;
    }

    public ItemScan setNumAuctions(Integer numAuctionscc) {
        numAuctions = numAuctionscc;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemScan setQuantity(Integer quantitycc) {
        quantity = quantitycc;
        return this;
    }

    public Timestamp getScanTime() {
        return scanTime;
    }

    public ItemScan setScanTime(Timestamp scanTimecc) {
        scanTime = scanTimecc;
        return this;
    }

    public String getRealm() {
        return realm;
    }

    public ItemScan setRealm(String realmcc) {
        realm = realmcc;
        return this;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public ItemScan setAddTime(Timestamp addTimecc) {
        addTime = addTimecc;
        return this;
    }

    static class IdClass implements Serializable {
        public String itemId;
        public Timestamp scanTime;

        public String getItemId() {
            return itemId;
        }

        public IdClass setItemId(String itemIdcc) {
            itemId = itemIdcc;
            return this;
        }

        public Timestamp getScanTime() {
            return scanTime;
        }

        public IdClass setScanTime(Timestamp scanTimecc) {
            scanTime = scanTimecc;
            return this;
        }
    }

    @Override
    public String toString() {
        return "ItemScan "+
                itemId + " " +
                minBuyout.toString() + " " +
                marketValue.toString() + " " +
                numAuctions.toString() + " " +
                quantity.toString() + " " +
                scanTime.toString();
//        return "ItemScan{" +
//                "itemInteger='" + itemInteger + '\'' +
//                ", minBuyout='" + minBuyout + '\'' +
//                ", marketValue='" + marketValue + '\'' +
//                ", numAuctions='" + numAuctions + '\'' +
//                ", quantity='" + quantity + '\'' +
//                ", scanTime='" + scanTime + '\'' +
//                '}';
    }
}
