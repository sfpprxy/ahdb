package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemDesc;
import org.jwork.ahdb.model.ItemScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataCollector {
    private static final Logger log = LoggerFactory.getLogger(DataCollector.class);

    public void consume(String raw) {
        // TODO replace with REST receive
        raw = RawDataReaderMock.readAuctionDBScan();

        List<ItemScan> scanned = RawDataParser.parseAuctionDBScan(raw);

        // TODO 1. update items desc for AHDB
        scanned.forEach(is -> {
            boolean exist = false; // query db, check is.itemString exist
            if (!exist) {
                ItemDesc iDesc = ItemDescFetcher.getDesc(is.itemString);
                // save to db
            }
        });

        // TODO 2. save new metric scan data to AHDB
        scanned.forEach(is -> {

        });

    }
}
