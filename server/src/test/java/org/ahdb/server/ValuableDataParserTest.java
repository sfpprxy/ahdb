package org.ahdb.server;

import io.vavr.collection.List;
import org.ahdb.server.model.ItemScan;
import org.ahdb.server.model.RawData;
import org.ahdb.server.model.ValuableData;
import org.ahdb.server.util.U;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValuableDataParserTest {
    private static final Logger log = LoggerFactory.getLogger(ValuableDataParserTest.class);

    @Autowired
    RawDataRepository rawDataRepository;

    @Test
    public void parseAuctionDBScan() {
        List<ItemScan> lis = ValuableDataParser.getItemScanList(getRawFromDB());
    }

    @Test
    public void parseChars() {
        String chars = ValuableDataParser.getChars(getRawFromDB());
        System.out.println(chars);
    }

    public ValuableData getRawFromDB() {
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now().minusDays(1));

        U.Timer timer = U.newTimer();
        List<RawData> lr = List.ofAll(rawDataRepository.findByTimeGreaterThan(ts));

        ValuableData dataByA = U.gson.fromJson(lr.last().rawStr, ValuableData.class);

        log.debug("time: {}", timer.getTime());

        return dataByA;
    }
}
