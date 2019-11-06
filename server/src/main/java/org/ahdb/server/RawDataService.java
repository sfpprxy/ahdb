package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.RawData;
import org.ahdb.server.model.ValuableData;
import org.ahdb.server.model.ValuableDataByAccount;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RawDataService {

    final RawDataRepository rawDataRepository;

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

        log.debug("parseRawFromDB time: {}", timer.getTime());

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
