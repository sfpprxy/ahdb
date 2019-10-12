package org.jwork.ahdb;

import org.jwork.ahdb.model.*;
import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RawDataService {
    private static final Logger log = LoggerFactory.getLogger(RawDataService.class);

    @Autowired
    RawDataRepository rawDataRepository;

    @Autowired
    ItemScanService itemScanService;

    @Autowired
    ItemDescSaveService itemDescSaveService;

    public void save(ValuableDataByAccount dataByA, Timestamp createTime) {
        try {
            RawData rawData = new RawData()
                    .setTime(createTime)
                    .setAccountId(dataByA.accountId)
                    .setType(dataByA.type)
                    .setRawStr(U.gson.toJson(dataByA.valuableData));
            rawDataRepository.save(rawData);
        } catch (Exception ex) {
            log.error("save raw data fail: ", ex);
            log.error("raw data: {}", dataByA);
        }
    }

    public void saveAllFromRaw() {
        rawDataRepository.findAll()
                .forEach(rawData -> {
                    try {
                        ValuableData dataByA = U.gson.fromJson(rawData.rawStr, ValuableData.class);
                        io.vavr.collection.List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA);
                        U.Timer t = U.newTimer();
                        itemDescSaveService.save(lis);
                        log.debug("itemDescSaveService.save time: {}", t.getTime());
                        itemScanService.save(lis, new Timestamp(System.currentTimeMillis()));
                        log.debug("itemScanService.save time: {}", t.getTime());
                    } catch (Exception ex) {
                        log.error("saveAll one fail", ex);
                    }
                });
    }
}
