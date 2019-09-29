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

    public Boolean process(List<ValuableDataByAccount> valuableDataByAccount) {
        valuableDataByAccount.forEach(dataByA -> {
            Timestamp createTime = new Timestamp(System.currentTimeMillis());

            rawDataSaveService.save(createTime, dataByA);

            if (U.match("debug", dataByA.type)) {
                return;
            }
            List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA.valuableData);

            // TODO 2. update items desc for AHDB
            boolean exist = false; // query db, check is.itemString exist
            if (!exist) {
                // save to db
            }

            // TODO 3. save new metric scan data to AHDB

        });
        return true;
    }

}
