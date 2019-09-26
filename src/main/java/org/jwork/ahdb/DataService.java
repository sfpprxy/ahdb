package org.jwork.ahdb;

import com.google.gson.Gson;
import io.vavr.collection.List;
import org.jwork.ahdb.model.RawData;
import org.jwork.ahdb.model.ValuableDataByAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class DataService {
    private static final Logger log = LoggerFactory.getLogger(DataService.class);

    @Autowired
    RawDataRepository rawDataRepository;

    Gson gson = new Gson();

    public Boolean process(List<ValuableDataByAccount> valuableDataByAccount) {
        valuableDataByAccount.forEach(e -> {
            log.debug(e.toString());

            // TODO 0. save raw to db
            try {
                RawData rawData = new RawData()
                        .setTime(new Timestamp(System.currentTimeMillis()))
                        .setAccountId(e.accountId)
                        .setType(e.type)
                        .setRawStr(gson.toJson(e.valuableData));
                log.debug(rawData.toString());
                rawDataRepository.save(rawData);
            } catch (Throwable ex) {
                log.error("save raw data fail: "+ rawDataRepository.toString(), ex);
            }

            // TODO 1. parse ValuableDataByAccount

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
