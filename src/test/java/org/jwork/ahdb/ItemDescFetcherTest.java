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
public class ItemDescFetcherTest {
    private static final Logger log = LoggerFactory.getLogger(ItemDescFetcherTest.class);

    @Autowired
    RawDataRepository rawDataRepository;

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
    public void getInfo() {
        U.Timer t = U.newTimer();
        parseRawFromDB().map(rd -> rd.itemString).drop(3800)
                .forEach(id -> {
                    ItemDesc desc = ItemDescFetcher.getDesc(id);
                    log.debug(desc.toString());
                });
        log.debug("time: {}", t.getTime());
    }

    @Test
    public void getInfoParallel() {
        U.Timer t = U.newTimer();
        ForkJoinPool poll = new ForkJoinPool(100);
        try {
            poll.submit(
                    () -> {
                        parseRawFromDB().map(rd -> rd.itemString).drop(3800)
                                .toJavaParallelStream()
                                .forEach(id -> {
                                    System.out.println("fetching one...");
                                    ItemDesc desc = ItemDescFetcher.getDesc(id);
                                    log.debug(desc.toString());
                                });
                    }
            );
        } catch (Exception ex) {
            log.error("fetch fail: ", ex);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ecc) {
            ecc.printStackTrace();
        }
        log.debug("time: {}", t.getTime());
    }
}
