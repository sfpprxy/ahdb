package org.ahdb.server;

import org.ahdb.server.model.RawData;
import org.ahdb.server.model.ValuableDataByAccount;
import org.ahdb.server.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RawDataService {
    private static final Logger log = LoggerFactory.getLogger(RawDataService.class);

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
            log.error("save raw data fail: ", ex);
            log.error("raw data: {}", dataByA);
        }
    }

    public List<RawData> getAll() {
        return rawDataRepository.findAll();
    }

}
