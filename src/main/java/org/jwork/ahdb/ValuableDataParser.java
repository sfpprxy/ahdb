package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.model.ValuableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Objects;

public class ValuableDataParser {
    private static final Logger log = LoggerFactory.getLogger(ValuableDataParser.class);

    public static List<ItemScan> getItemScanList(ValuableData dataByA) {
        String raw = dataByA.auctionDBScan;
        List<String> l = List.of(raw.split("\\\\n"));
        if (!Objects.equals("itemString,minBuyout,marketValue,numAuctions,quantity,lastScan", l.head())) {
            throw new AhdbException("getItemScanList fail - TSM AuctionDBScan format changed: " + raw);
        }
        l = l.pop();

        List<ItemScan> itemScanL = l.map(s -> {
            try {
                List<String> sl = List.of(s.split(","));
                return new ItemScan()
                        .setItemId(sl.get(0).substring(2))
                        .setMinBuyout(Integer.valueOf(sl.get(1)))
                        .setMarketValue(Double.valueOf(sl.get(2)))
                        .setNumAuctions(Integer.valueOf(sl.get(3)))
                        .setQuantity(Integer.valueOf(sl.get(4)))
                        .setScanTime(new Timestamp(Long.valueOf(sl.get(5))*1000));
            } catch (Exception ex) {
                log.error("parse to ItemScan fail: " + s, ex);
                return null;
            }
        }).filter(Objects::nonNull);

        return itemScanL;
    }

}
