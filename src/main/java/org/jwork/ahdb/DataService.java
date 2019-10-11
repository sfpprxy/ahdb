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
public class DataService {
    private static final Logger log = LoggerFactory.getLogger(DataService.class);

    @Autowired
    RawDataSaveService rawDataSaveService;
    @Autowired
    ItemDescSaveService itemDescSaveService;
    @Autowired
    ItemScanService itemScanService;

    public Boolean receive(List<ValuableDataByAccount> valuableDataByAccount) {
        valuableDataByAccount.forEach(dataByA -> {
            Timestamp createTime = new Timestamp(System.currentTimeMillis());

            rawDataSaveService.save(dataByA, createTime);

            if (U.match("debug", dataByA.type)) {
                log.debug("received debug rawData");
                return;
            }
            List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA.valuableData);

            new Thread(() -> {
                itemDescSaveService.save(lis);
            }).start();

            itemScanService.save(lis, createTime);
        });
        return true;
    }

}
