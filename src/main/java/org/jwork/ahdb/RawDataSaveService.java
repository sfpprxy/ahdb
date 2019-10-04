package org.jwork.ahdb;

import com.google.gson.Gson;
import org.jwork.ahdb.model.RawData;
import org.jwork.ahdb.model.ValuableDataByAccount;
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

    Gson gson = new Gson();

    public void save(Timestamp createTime, ValuableDataByAccount e) {
        try {
            RawData rawData = new RawData()
                    .setTime(createTime)
                    .setAccountId(e.accountId)
                    .setType(e.type)
                    .setRawStr(gson.toJson(e.valuableData));
            rawDataRepository.save(rawData);
        } catch (Exception ex) {
            throw new AhdbException("save raw data fail: " + e, ex);
        }
    }
}
