package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.*;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryService {

    final QueryRepository queryRepository;
    final ItemScanRepository itemScanRepository;
    final AccountService accountService;
    final ItemDescService itemDescService;
    final CacheService cacheService;

    public void ipFilter(String ip) {
        log.info("empty accountId IP {}", ip);

        IpStats ipst = cacheService.getIpStats(ip);
        log.debug("timesToday {} last query {}", ipst.timesToday, ipst.lastQuery);

        if (ipst.lastQuery.isBefore(U.dateTimeNow().minusDays(1))) {
            ipst.setTimesToday(0);
        }
        if (ipst.timesToday > 10) {
            throw new AhdbUserException(AhdbUserException.NO_FREE);
        }

        ipst.setTimesToday(ipst.timesToday + 2);
        ipst.setLastQuery(U.dateTimeNow());
        log.debug("timesToday {} last query {}", ipst.timesToday, ipst.lastQuery);

        cacheService.setIpStats(ip, ipst);
    }

    public ItemStats queryItemStats(String accountId, String item, String ip) {
        String chars = accountService.getStats(accountId).chars;
        String msg = U.fstr("{} queryItemStats {}", chars, item);
        log.info(msg);

        ItemStats itemStats = new ItemStats();

        ItemDesc desc = itemDescService.getItem(item);
        ItemScan scan = itemScanRepository.findFirstByItemIdOrderByScanTimeDesc(desc.id);
        if (scan == null) {
            throw new AhdbUserException(AhdbUserException.NO_ITEM);
        }
        log.debug("scan market: {}", scan.marketValue);

        List<List<Object>> raw = queryRepository.queryItemDailyStats(desc.id);

        List<DailyStat> dailyStats = raw.stream().map(tuple -> {
            DailyStat stat = U.tupleToBean(tuple, DailyStat.class);
            return stat;
        }).collect(Collectors.toList());

        itemStats.setItemDesc(desc)
                .setCurrentMarket(scan.marketValue)
                .setAt(scan.scanTime)
                .setAvgMarket3Day(null)
                .setAvgMarket14Day(null)
                .setDailyStats(dailyStats);

        if (U.empty(accountId) || U.match(accountId, "null")) {
            ipFilter(ip);
        } else {
            boolean powerEnough = accountService.consumeByQuery(accountId);
            if (!powerEnough) {
                throw new AhdbUserException(AhdbUserException.NO_POWER);
            }
        }

        return itemStats;
    }

    public String queryAllItemStats(String accountId) {
        List<List<Object>> raw = queryRepository.queryAllItemStats();
        List<List<Object>> rawEarly = queryRepository.queryAllItemStatsEarly7();
        List<List<Object>> rawLater = queryRepository.queryAllItemStatsLater7();
        List<Day14Stat> all = raw.stream().map(tuple -> U.tupleToBean(tuple, Day14Stat.class))
                .collect(Collectors.toList());
        List<Day14Stat> early = rawEarly.stream()
                .map(tuple -> U.tupleToBean(tuple, Day14Stat.class))
                .collect(Collectors.toList());
        List<Day14Stat> later = rawLater.stream()
                .map(tuple -> U.tupleToBean(tuple, Day14Stat.class))
                .collect(Collectors.toList());

        String content = io.vavr.collection.List.ofAll(all)
                .map(s -> {
                    Day14Stat mayE = io.vavr.collection.List.ofAll(early)
                            .find(e -> U.match(e.id, s.id)).getOrElse(new Day14Stat().setMarket(0));
                    Day14Stat mayL = io.vavr.collection.List.ofAll(later)
                            .find(l -> U.match(l.id, s.id)).getOrElse(new Day14Stat().setMarket(0));

                    if (mayL.market == 0 || mayE.market == 0) {
                        Day14Stat stat = io.vavr.collection.List.ofAll(all).find(a -> U.match(a.id, s.id)).get();
                        if (stat.quantity > 60 && stat.vIndex > 6 && stat.auctions > 10) {
                            log.warn("Exception Item : ID {} NAME {}", s.id, stat.getName());
                        }
                    }

                    Integer change = mayL.market - mayE.market;

                    List<String> s0 = U.list(
                            s.vIndex.toString(),
                            s.maxStock.toString(),
                            s.market.toString(),
                            change.toString(),
                            s.marketD.toString(),
                            s.auctions.toString(),
                            s.quantity.toString(),
                            s.itemClass,
                            s.subClass);
                    String stats = String.join(",", s0);
                    String item = U.fstr("        [\"{}\"]=\"{}\",", s.id, stats);
                    return item;
                })
                .collect(Collectors.joining("\n"));

        String itemStats = "[\"itemStats\"] = {\n" +
                      U.fstr("{}", content) +
                      "\n    },";

        if (U.empty(accountId) || U.match(accountId, "null")) {
            throw new AhdbUserException(AhdbUserException.NO_POWER);
        }
        boolean powerEnough = accountService.consumeByQueryAll(accountId);
        if (!powerEnough) {
            throw new AhdbUserException(AhdbUserException.NO_POWER);
        }

        return itemStats;
    }

}
