package org.jwork.ahdb;

import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jwork.ahdb.model.*;
import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ForkJoinPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemDescSaveServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ItemDescSaveServiceTest.class);

    @Autowired
    RawDataRepository rawDataRepository;
    @Autowired
    ItemDescRepository itemDescRepository;

    public List<ItemScan> parseRawFromDB() {
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now().minusDays(1));

        U.Timer timer = U.newTimer();
        List<RawData> lr = List.ofAll(rawDataRepository.findByTimeGreaterThan(ts));

        ValuableData dataByA = U.gson.fromJson(lr.last().rawStr, ValuableData.class);
        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA);

        log.debug("time: {}", timer.getTime());
        log.debug("lis.length: {}", lis.length());

        return lis;
    }

    @Test
    public void save() throws Exception {
        U.Timer t = U.newTimer();
        List<ItemScan> lis = parseRawFromDB();
        ForkJoinPool poll = new ForkJoinPool(20);
        poll.submit(() ->
                lis.toJavaParallelStream().forEach(is -> {
                    try {
                        int size = itemDescRepository.findAllById(is.itemString).size();
                        if (size < 1) {
                            ItemDesc desc = ItemDescFetcher.getDesc(is.itemString);
                            itemDescRepository.save(desc);
                        }
                    } catch (Exception ex) {
                        log.debug("save ItemDesc id: {} fail: ", is.itemString, ex);
                    }
                })
        ).get();
        log.debug("time: {}", t.getTime());
    }
}