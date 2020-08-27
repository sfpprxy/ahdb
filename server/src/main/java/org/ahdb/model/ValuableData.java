package org.ahdb.model;


public class ValuableData {

    public String chars;
    public String auctionDBScan;

    public String getChars() {
        return chars;
    }

    public ValuableData setChars(String chars) {
        this.chars = chars;
        return this;
    }

    public String getAuctionDBScan() {
        return auctionDBScan;
    }

    public ValuableData setAuctionDBScan(String auctionDBScan) {
        this.auctionDBScan = auctionDBScan;
        return this;
    }

    @Override
    public String toString() {
        return "ValuableData{" +
                "\nchars='" + chars + '\'' +
                "\nauctionDBScan='" + auctionDBScan + '\'' +
                '}';
    }
}
