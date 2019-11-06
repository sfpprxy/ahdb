package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class ValuableData {

    public String chars;
    public String auctionDBScan;

    @Override
    public String toString() {
        return "ValuableData{" +
                "\nchars='" + chars + '\'' +
                "\nauctionDBScan='" + auctionDBScan + '\'' +
                '}';
    }
}
