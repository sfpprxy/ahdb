package org.ahdb.server;

import io.vavr.collection.List;
import org.ahdb.server.model.AccountStats;
import org.ahdb.server.model.ItemScan;
import org.ahdb.server.model.ValuableDataByAccount;
import org.ahdb.server.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ReceiveService {
    private static final Logger log = LoggerFactory.getLogger(ReceiveService.class);

    @Autowired
    RawDataService rawDataService;
    @Autowired
    ItemDescService itemDescService;
    @Autowired
    ItemScanService itemScanService;
    @Autowired
    AccountService accountService;


    public Boolean receive(List<ValuableDataByAccount> lvaluableDataByAccount) {
        for (ValuableDataByAccount dataByA : lvaluableDataByAccount) {
            log.info("raw data received from account {}", dataByA.accountId);

            Timestamp createTime = new Timestamp(System.currentTimeMillis());

            rawDataService.save(dataByA, createTime);

            processRaw(dataByA, createTime);
        }

        return true;
    }

    public void processRaw(ValuableDataByAccount dataByA, Timestamp createTime) {
        if (U.match("debug", dataByA.type)) {
            log.debug("debug rawData received -> drop");
            return;
        }
        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA.valuableData);

        AccountStats as = ValuableDataParser.getAccountStats(dataByA);

        Boolean shouldSave = itemScanService.save(lis, createTime);

        accountService.chargeByPush(as);

        if (shouldSave) {
            itemDescService.save(lis);
        }
    }

}
