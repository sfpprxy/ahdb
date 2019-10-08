package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.model.ValuableDataByAccount;
import org.jwork.ahdb.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DataService {

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
                return;
            }
            List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA.valuableData);

            itemDescSaveService.save(lis);

            itemScanService.save(lis, createTime);
        });
        return true;
    }

}
