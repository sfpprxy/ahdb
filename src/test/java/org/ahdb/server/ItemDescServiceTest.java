package org.ahdb.server;

import io.vavr.collection.List;
import org.ahdb.server.model.*;
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
import java.util.concurrent.ForkJoinPool;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemDescServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ItemDescServiceTest.class);

    @Autowired
    RawDataRepository rawDataRepository;
    @Autowired
    ItemDescRepository itemDescRepository;
    @Autowired
    ItemDescService itemDescServiceaa;

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
                        boolean exist = itemDescRepository.findById(is.itemId).isPresent();
                        if (!exist) {
                            log.debug("BUCUNZAI");
                            ItemDesc desc = ItemDescFetcher.getDesc(is.itemId);
                            itemDescRepository.save(desc);
                        } else {
                            log.debug("GOOD");
                        }
                    } catch (Exception ex) {
//                        log.debug("save ItemDesc itemId: {} fail: ", is.itemId, ex);
                    }
                })
        ).get();
        log.debug("time: {}", t.getTime());
    }

    @Test
    public void realSave() {
        U.Timer t = U.newTimer();
        List<ItemScan> lis = parseRawFromDB();
        itemDescServiceaa.save(lis);
        log.debug("time: {}", t.getTime());
    }
}
