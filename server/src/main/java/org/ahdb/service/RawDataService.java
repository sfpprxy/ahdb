package org.ahdb.service;

import org.ahdb.model.RawData;
import org.ahdb.model.ValuableData;
import org.ahdb.model.ValuableDataByAccount;
import org.ahdb.repo.RawDataRepository;
import org.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class RawDataService {

    private static final Logger log = LoggerFactory.getLogger(RawDataService.class);

    @Inject
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

    public ValuableDataByAccount getOne() {
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now().minusDays(1));

        U.Timer timer = U.newTimer();
        io.vavr.collection.List<RawData> lr = io.vavr.collection.List.ofAll(rawDataRepository.findByTimeGreaterThan(ts));

        ValuableData dataByA = U.gson.fromJson(lr.last().rawStr, ValuableData.class);

        log.debug("parseRawFromDB time: {}", timer.getSeconds());

        ValuableDataByAccount vd = new ValuableDataByAccount()
                .setAccountId("debug-account#1")
                .setTime(new Timestamp(System.currentTimeMillis()))
                .setType("debug")
                .setValuableData(dataByA);
        return vd;
    }

    public List<RawData> getAll() {
        return rawDataRepository.findAll();
    }

}
