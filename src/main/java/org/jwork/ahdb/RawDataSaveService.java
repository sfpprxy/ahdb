package org.jwork.ahdb;

import org.jwork.ahdb.model.RawData;
import org.jwork.ahdb.model.ValuableDataByAccount;
import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class RawDataSaveService {
    private static final Logger log = LoggerFactory.getLogger(RawDataSaveService.class);

    @Autowired
    RawDataRepository rawDataRepository;

    public void save(ValuableDataByAccount dataByA, Timestamp createTime) {
        try {
            RawData rawData = new RawData()
                    .setTime(createTime)
                    .setAccountId(dataByA.accountId)
                    .setType(dataByA.type)
                    .setRawStr(U.gson.toJson(dataByA.valuableData));
            rawDataRepository.save(rawData);
        } catch (Exception ex) {
            throw new AhdbException("save raw data fail: " + dataByA, ex);
        }
    }
}
