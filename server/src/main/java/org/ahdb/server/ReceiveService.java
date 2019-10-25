package org.ahdb.server;

import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.AccountStats;
import org.ahdb.server.model.ItemScan;
import org.ahdb.server.model.ValuableDataByAccount;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReceiveService {

    final RawDataService rawDataService;
    final ItemDescService itemDescService;
    final ItemScanService itemScanService;
    final AccountService accountService;
    final RawLogService rawLogService;

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
            log.error("receive fail {}", U.stackTrace(ex));
            rawLogService.log("receive fail", U.stackTrace(ex));
            return false;
        }
    }

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

}
