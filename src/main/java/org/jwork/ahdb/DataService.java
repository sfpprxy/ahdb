package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jwork.ahdb.model.ValuableDataByAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DataService {
    private static final Logger log = LoggerFactory.getLogger(DataService.class);

    public Boolean process(List<ValuableDataByAccount> valuableDataByAccount) {
        valuableDataByAccount.forEach(e -> {
            log.debug(String.valueOf(e.id));
            log.debug(e.type);
            log.debug(e.accountId);
            log.debug(e.valuableData.chars);
            log.debug(String.valueOf(e.valuableData.auctionDBScan.length()));

            // TODO 0. save raw to db


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
