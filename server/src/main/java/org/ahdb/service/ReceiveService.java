package org.ahdb.service;

import io.vavr.collection.List;
import org.ahdb.model.AccountStats;
import org.ahdb.model.ItemScan;
import org.ahdb.model.ValuableDataByAccount;
import org.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Timestamp;

@ApplicationScoped
public class ReceiveService {

    private static final Logger log = LoggerFactory.getLogger(ReceiveService.class);

    @Inject
    RawDataService rawDataService;
    @Inject ItemDescService itemDescService;
    @Inject ItemScanService itemScanService;
    @Inject AccountService accountService;
    @Inject RawLogService rawLogService;

    public void processRaw(ValuableDataByAccount dataByA, Timestamp createTime) {
        if (U.match("debug", dataByA.type)) {
            log.debug("debug rawData received -> drop");
            return;
        }

        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA.valuableData);

        AccountStats as = ValuableDataParser.getAccountStats(dataByA);

        Boolean shouldFetchDesc = false;
        if (dataByA.type.contains("drop scan")) {
            log.debug("itemScanService.save -> drop");
        } else {
            shouldFetchDesc = itemScanService.save(lis, createTime);
        }

        if (dataByA.type.contains("drop charge")) {
            log.debug("accountService.chargeByPush -> drop");
        } else {
            accountService.chargeByPush(as);
        }

        if (shouldFetchDesc) {
            itemDescService.save(lis);
        }
    }

    public Boolean receive(List<ValuableDataByAccount> lvaluableDataByAccount) {
        try {
            for (ValuableDataByAccount dataByA : lvaluableDataByAccount) {
                log.info("raw data received from account {}", dataByA.accountId);
                rawLogService.log("receive", dataByA.accountId);

                Timestamp createTime = new Timestamp(System.currentTimeMillis());

                rawDataService.save(dataByA, createTime);

                processRaw(dataByA, createTime);
            }
            return true;
        } catch (Exception ex) {
            log.error("receive fail ", ex);
            rawLogService.log("receive fail", U.stackTrace(ex));
            return false;
        }
    }

}
