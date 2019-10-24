package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.IpStats;
import org.ahdb.server.model.ItemDesc;
import org.ahdb.server.model.ItemStats;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QueryService {

    final QueryRepository queryRepository;
    final AccountService accountService;
    final ItemDescService itemDescService;
    final CacheService cacheService;

    public ItemStats queryItemStats(String accountId, String item, String ip) {
        if (U.empty(accountId)) {
            ipFilter(ip);
        }

        String chars = accountService.getStats(accountId).chars;
        String msg = U.fstr("{} queryItemStats {}", chars, item);
        log.info(msg);

        ItemStats itemStats = new ItemStats();

        ItemDesc desc = itemDescService.getItem(item);
        // TODO: get item market stats
        itemStats.setItemDesc(desc)
                .setCurrentMarket(11)
                .setAvgMarketToday(13)
                .setAvgMarket14Day(15);

        boolean powerEnough = accountService.consumeByQuery(accountId);
        if (!powerEnough) {
            // TODO: AOP catch Ex and return
            throw new AhdbUserException(AhdbUserException.NO_POWER);
        }
        return itemStats;
    }

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

}
