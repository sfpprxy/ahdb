package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.model.ValuableDataByAccount;
import org.jwork.ahdb.util.U;
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

    public Boolean receive(List<ValuableDataByAccount> lvaluableDataByAccount) {
        lvaluableDataByAccount.forEach(dataByA -> {
            Timestamp createTime = new Timestamp(System.currentTimeMillis());

            rawDataService.save(dataByA, createTime);

            processRaw(dataByA, createTime);
        });
        return true;
    }

    public void processRaw(ValuableDataByAccount dataByA, Timestamp createTimecc) {
        if (U.match("debug", dataByA.type)) {
            log.debug("debug rawData received");
            return;
        }
        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA.valuableData);

        Boolean shouldSave = itemScanService.save(lis, createTimecc);

        if (shouldSave) {
            itemDescService.save(lis);
        }
    }

}
