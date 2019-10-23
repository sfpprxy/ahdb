package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    final RawLogService rawLogService;

    public ItemStats queryItemStats(String accountId, String item) {
        String chars = accountService.getStats(accountId).chars;
        String msg = U.fstr("{} queryItemStats {}", chars, item);
        log.info(msg);
        rawLogService.log("queryItem", msg);

        ItemStats itemStats = new ItemStats();

        ItemDesc desc = itemDescService.getItem(item);
        // TODO: get item market stats
        itemStats.setItemDesc(desc)
                .setCurrentMarket(11)
                .setAvgMarketToday(13)
                .setAvgMarket14Day(15);

        boolean powerEnough = accountService.consumeByQuery(accountId);
        if (!powerEnough) {
            throw new AhdbUserException("not enough power");
        }
        return itemStats;
    }

}
