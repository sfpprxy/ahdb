package org.ahdb.server;

import io.vavr.collection.List;
import org.ahdb.server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ValuableDataParser {
    private static final Logger log = LoggerFactory.getLogger(ValuableDataParser.class);

    public static List<ItemScan> getItemScanList(ValuableData dataByA) {
        String raw = dataByA.auctionDBScan;
        List<String> l = List.of(raw.split("\\\\n"));
        if (!Objects.equals("itemString,minBuyout,marketValue,numAuctions,quantity,lastScan", l.head())) {
            throw new AhdbException("getItemScanList fail - TSM AuctionDBScan format changed: " + raw);
        }
        l = l.pop();

        AtomicReference<Timestamp> headScanTime = new AtomicReference<>();

        List<ItemScan> itemScanL = l.map(s -> {
            try {
                List<String> sl = List.of(s.split(","));
                Timestamp scanTime = new Timestamp(Long.valueOf(sl.get(5)) * 1000);

                if (headScanTime.get() == null) {
                    headScanTime.set(scanTime);
                }
                int sec = 600;
                int gapAllowed = sec * 1000 * 5;
                Timestamp mustAfter = new Timestamp(headScanTime.get().getTime() - gapAllowed);
                Timestamp mustBefore = new Timestamp(headScanTime.get().getTime() + gapAllowed);
                if (scanTime.after(mustAfter) && scanTime.before(mustBefore)) {
                    scanTime = headScanTime.get();
                } else {
                    throw new AhdbException("getItemScanList fail - scanTime gap too long: " + raw);
                }

                return new ItemScan()
                        .setItemId(sl.get(0).substring(2))
                        .setMinBuyout(Integer.valueOf(sl.get(1)))
                        .setMarketValue(Double.valueOf(sl.get(2)))
                        .setNumAuctions(Integer.valueOf(sl.get(3)))
                        .setQuantity(Integer.valueOf(sl.get(4)))
                        .setScanTime(scanTime);
            } catch (Exception ex) {
                log.error("parse to ItemScan fail: " + s, ex);
                return null;
            }
        }).filter(Objects::nonNull);

        return itemScanL;
    }

    public static AccountStats getAccountStats(ValuableDataByAccount vd) {
        String accountId = vd.accountId.substring(0, vd.accountId.indexOf("#"));
        String chars = ValuableDataParser.getChars(vd.valuableData);
        AccountStats as = new AccountStats().setAccountId(accountId).setChars(chars);
        log.debug("AccountStats {} {}", accountId, chars);
        return as;
    }

    public static String getChars(ValuableData dataByA) {
        String raw = dataByA.chars;
        int i1 = raw.indexOf("\"") + 1;
        int i2 = raw.indexOf("-") - 1;
        String s = raw.substring(i1, i2);
        return s;
    }

}
