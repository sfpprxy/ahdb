package org.jwork.ahdb;

import io.vavr.collection.HashSet;
import io.vavr.collection.List;
import io.vavr.collection.SortedSet;
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
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemScanServiceTest {
    private static final Logger log = LoggerFactory.getLogger(ItemScanServiceTest.class);

    @Autowired
    RawDataRepository rawDataRepository;

    @Autowired
    ItemScanRepository itemScanRepository;

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
        rawDataRepository.findAll()
                .forEach(rawData -> {
                    try {
                        ValuableData dataByA = U.gson.fromJson(rawData.rawStr, ValuableData.class);
                        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA);
                        U.Timer t = U.newTimer();
                        itemDescSaveService.save(lis);
                        log.debug("itemDescSaveService.save time: {}", t.getTime());
//                        itemScanService.save(lis, new Timestamp(System.currentTimeMillis()));
//                        log.debug("itemScanService.save time: {}", t.getTime());
                    } catch (Exception ex) {
                        log.error("saveAll one fail", ex);
                    }
                });
        log.debug("Submit finish");
        U.sleep(9999999);
    }

    @Test
    public void checkDiffScanTime() {
        rawDataRepository.findAll()
                .forEach(rawData -> {
                    System.out.println("===================================================");
                    ValuableData dataByA = U.gson.fromJson(rawData.rawStr, ValuableData.class);
                    List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA);
                    java.util.HashSet<Timestamp> set = new java.util.HashSet<>();
                    for (ItemScan is : lis) {
                        if (set.contains(is.scanTime)) {

                        } else {
                            set.add(is.scanTime);
                            System.out.println(is.scanTime);
                        }
                    }
                });
    }

    @Transactional
    @Test
    public void removeDuplicate() {

        int total = 770817;
        U.Counter c = U.newCounter(total);
        java.util.List<Timestamp> all = itemScanRepository.streamAll();

        java.util.HashSet<Timestamp> set = new java.util.HashSet<>();
        java.util.HashSet<Timestamp> delSet = new java.util.HashSet<>();
        log.debug("all size: {}", all.size());

        all.forEach(scanTime -> {
//            log.debug("scanTime: {}", scanTime);
            Timestamp should = Timestamp.valueOf(scanTime.toLocalDateTime().minusMinutes(5));

            boolean exist = false;
            Timestamp found = null;

            for (Timestamp t : set) {
                if (t.after(should) && !t.equals(scanTime)) {
                    found = t;
                    exist = true;
                }
            }

            if (exist) {
                delSet.add(scanTime);
            } else {
                set.add(scanTime);
            }

            c.addOne();
            c.check().ifPresent(System.out::println);
        });
        log.debug("setSize: {}", set.size());
        SortedSet<Timestamp> ss = HashSet.ofAll(set).toSortedSet();
        for (Timestamp t : ss) {
            System.out.println(t);
        }
        log.debug("delSetSize: {}", delSet.size());
        SortedSet<Timestamp> delss = HashSet.ofAll(delSet).toSortedSet();
        for (Timestamp t : delss) {
            System.out.println(t);
        }
    }

    @Test
    public void testPer() {
        int total = 7787;
        U.Counter c = U.newCounter(total);
        for (int j = 0; j < total; j++) {
            c.addOne();
            c.check().ifPresent(System.out::println);
            U.sleep(1);
        }
    }
}
