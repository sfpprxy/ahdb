package org.jwork.ahdb;

import io.vavr.collection.List;
import org.junit.Test;
import org.jwork.ahdb.model.ItemScan;

public class RawDataParserTest {

    @Test
    public void parseAuctionDBScan() {
        String raw = RawDataReaderMock.readAuctionDBScan();
        List<ItemScan> l = RawDataParser.parseAuctionDBScan(raw);
        System.out.println(l);
    }
}
