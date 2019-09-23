package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;


public class RawDataParser {
    private static final Logger log = LoggerFactory.getLogger(RawDataParser.class);

    public static List<String> parseItemsId(String raw) {
        List<String> ids = List.of(raw.split("[spliter]")).pop();
        ids = ids.map(e -> e.trim().substring(1));

        return ids;
    }

    public static List<ItemScan> parseAuctionDBScan(String raw) {
        List<String> l = List.of(raw.split("\\\\n"));
        if (!Objects.equals("itemString,minBuyout,marketValue,numAuctions,quantity,lastScan", l.head())) {
            throw new ParseException("parseAuctionDBScan fail - TSM format changed: " + raw);
        }
        try {
            l = l.pop();
        } catch (Throwable e) {
            throw new ParseException("parseAuctionDBScan fail - no AuctionDBScan Data: " + raw, e);
        }

        List<ItemScan> itemScanL = l.map(s -> {
            try {
                List<String> sl = List.of(s.split(","));
                return new ItemScan()
                        .setItemString(sl.get(0).substring(2))
                        .setMinBuyout(Integer.valueOf(sl.get(1)))
                        .setMarketValue(Double.valueOf(sl.get(2)))
                        .setNumAuctions(Integer.valueOf(sl.get(3)))
                        .setQuantity(Integer.valueOf(sl.get(4)))
                        .setLastScan(Integer.valueOf(sl.get(5)));
            } catch (Throwable e) {
                log.error("parse ItemScan fail: " + s, e);
            }
            return new ItemScan();
        });

        return itemScanL;
    }

}
