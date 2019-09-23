package org.jwork.ahdb;

import io.vavr.collection.List;
import org.junit.Test;
import org.jwork.ahdb.model.ItemDesc;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.util.Table;

import java.util.Map;
import java.util.Objects;

public class TSMScanMatcherTest {

    @Test
    public void match() {
        List<ItemScan> isl = RawDataParser.parseAuctionDBScan(RawDataReaderMock.readAuctionDBScan());
        Table table = new Table(List.of("id", "name", "vendorSell", "minBuyout", "marketValue",
                "numAuctions", "quantity", "lastScan").asJavaMutable());

        ItemDesc i1 = ItemDescFetcher.getDesc("2447");
        ItemDesc i2 = ItemDescFetcher.getDesc("3356");
        ItemDesc i3 = ItemDescFetcher.getDesc("2771");
        ItemDesc i4 = ItemDescFetcher.getDesc("4479");
        List<ItemDesc> ml = List.of(i1, i2, i3, i4);

        var tc = ml.map(i -> {
            ItemScan scan = isl.filter(is -> Objects.equals(is.itemString, i.id)).head();
            Map<String, Object> row = Map.of(
                    "id", i.id,
                    "name", i.name,
                    "vendorSell", (i.g + "g " + i.s + "s " + i.c + "c "),
                    "minBuyout", scan.minBuyout,
                    "marketValue", scan.marketValue,
                    "numAuctions", scan.numAuctions,
                    "quantity", scan.quantity,
                    "lastScan", scan.lastScan
            );
            return row;
        }).asJavaMutable();

        table.putAll(tc);
        table.prettyPrint();

    }
}
