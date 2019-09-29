package org.jwork.ahdb;

import io.vavr.collection.HashMap;
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
//        List<ItemScan> isl = ValuableDataParser.parseAuctionDBScan(RawDataReaderMock.readAuctionDBScan());
//        Table table = new Table(List.of("id", "name", "vendorSell", "minBuyout", "marketValue",
//                "numAuctions", "quantity", "lastScan").asJava());
//
//        ItemDesc i1 = ItemDescFetcher.getDesc("2447");
//        ItemDesc i2 = ItemDescFetcher.getDesc("3356");
//        ItemDesc i3 = ItemDescFetcher.getDesc("2771");
//        ItemDesc i4 = ItemDescFetcher.getDesc("2291");
//        List<ItemDesc> ml = List.of(i1, i2, i3, i4);
//
//        java.util.List<Map<String, Object>> tableRows = ml.map(i -> {
//            ItemScan scan = isl.filter(is -> Objects.equals(is.itemString, i.id)).head();
//            HashMap<String, Object> row = HashMap.of(
//                    "id", i.id,
//                    "name", i.name,
//                    "vendorSell", (i.vendorSell),
//                    "minBuyout", scan.minBuyout,
//                    "marketValue", scan.marketValue,
//                    "numAuctions", scan.numAuctions,
//                    "quantity", scan.quantity,
//                    "lastScan", scan.lastScan
//            );
//
//            return (Map<String, Object>) row.toJavaMap();
//        }).asJava();
//
//        table.putAll(tableRows);
//        table.prettyPrint();
    }
}
