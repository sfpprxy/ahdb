package org.jwork.ahdb;

import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.model.RawData;
import org.jwork.ahdb.model.ValuableData;
import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemScanServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ItemScanServiceTest.class);

    @Autowired
    RawDataRepository rawDataRepository;

    @Autowired
    ItemScanService itemScanService;

    @Autowired
    ItemDescSaveService itemDescSaveService;

    public List<ItemScan> parseRawFromDB() {
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now().minusDays(1));

        U.Timer timer = U.newTimer();
        List<RawData> lr = List.ofAll(rawDataRepository.findByTimeGreaterThan(ts));

        ValuableData dataByA = U.gson.fromJson(lr.last().rawStr, ValuableData.class);
        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA);

        log.debug("parseRawFromDB time: {}", timer.getTime());
        log.debug("lis.length: {}", lis.length());

        return lis;
    }

    @Test
    public void save() {
        itemScanService.save(parseRawFromDB(), new Timestamp(System.currentTimeMillis()));
    }

    @Test
    public void saveAllFromRaw() {
        // TODO: save all
        rawDataRepository.findAll()
                .forEach(rawData -> {
                    try {
                        ValuableData dataByA = U.gson.fromJson(rawData.rawStr, ValuableData.class);
                        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA);
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
