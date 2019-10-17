package org.ahdb.server.model;

public class ValuableData {

    public String chars;
    public String auctionDBScan;

    public String getChars() {
        return chars;
    }

    public ValuableData setChars(String charscc) {
        chars = charscc;
        return this;
    }

    public String getAuctionDBScan() {
        return auctionDBScan;
    }

    public ValuableData setAuctionDBScan(String auctionDBScancc) {
        auctionDBScan = auctionDBScancc;
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
