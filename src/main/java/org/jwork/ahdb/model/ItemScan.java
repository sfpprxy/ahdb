package org.jwork.ahdb.model;

public class ItemScan {
    // itemInteger,minBuyout,marketValue,numAuctions,quantity,lastScan
    public String itemString;
    public Integer minBuyout;
    public Double marketValue;
    public Integer numAuctions;
    public Integer quantity;
    public Integer lastScan;

    public String getItemString() {
        return itemString;
    }

    public ItemScan setItemString(String itemStringcc) {
        itemString = itemStringcc;
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

    public Integer getLastScan() {
        return lastScan;
    }

    public ItemScan setLastScan(Integer lastScancc) {
        lastScan = lastScancc;
        return this;
    }

    @Override
    public String toString() {
        return "ItemScan "+
                itemString + " " +
                minBuyout.toString() + " " +
                marketValue.toString() + " " +
                numAuctions.toString() + " " +
                quantity.toString() + " " +
                lastScan.toString();
//        return "ItemScan{" +
//                "itemInteger='" + itemInteger + '\'' +
//                ", minBuyout='" + minBuyout + '\'' +
//                ", marketValue='" + marketValue + '\'' +
//                ", numAuctions='" + numAuctions + '\'' +
//                ", quantity='" + quantity + '\'' +
//                ", lastScan='" + lastScan + '\'' +
//                '}';
    }
}
