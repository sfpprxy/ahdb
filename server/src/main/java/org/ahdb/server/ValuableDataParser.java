package org.ahdb.server;

import io.vavr.collection.List;
import org.ahdb.server.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

        final LocalDateTime[] headScanTime = {null};

        List<ItemScan> itemScanL = l.map(s -> {
            try {
                List<String> sl = List.of(s.split(","));
                Timestamp scanTime = new Timestamp(Long.valueOf(sl.get(5)) * 1000);
                LocalDateTime ld = scanTime.toLocalDateTime();

                if (headScanTime[0] == null) {
                    headScanTime[0] = ld;
                }
                int gapAllowed = 1; // minutes
                LocalDateTime before = headScanTime[0].minusMinutes(gapAllowed);
                LocalDateTime after = headScanTime[0].plusMinutes(gapAllowed);

                if (before.isBefore(ld) && after.isAfter(ld)) {
                    scanTime = Timestamp.valueOf(headScanTime[0]);
                } else {
                    throw new AhdbException("getItemScanList fail - scanTime gap too long: " + raw);
                }

                return new ItemScan()
                        .setItemId(sl.get(0).substring(2))
                        .setMinBuyout(Integer.valueOf(sl.get(1)))
                        .setMarketValue(Double.valueOf(sl.get(2)).intValue())
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
