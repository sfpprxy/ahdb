package org.jwork.ahdb;

import com.google.gson.Gson;
import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jwork.ahdb.model.ItemScan;
import org.jwork.ahdb.model.RawData;
import org.jwork.ahdb.model.ValuableData;
import org.jwork.ahdb.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ValuableDataParserTest {

    @Autowired
    RawDataRepository rawDataRepository;

    @Test
    public void parseAuctionDBScan() {
//        List<RawData> lrd = rawDataRepository.findAll();
//        long ts = lrd.get(89).time.getTime();
//        System.out.println("AAA");
//        System.out.println(ts);

        Pageable firstPageWithTwoElements = PageRequest.of(0, 90);
        Page<RawData> page = rawDataRepository.findAll(firstPageWithTwoElements);
        RawData raw = page.get().collect(Collectors.toList()).get(89);
        System.out.println("raw:");
        System.out.println(raw.time);
        System.out.println(raw.rawStr.length());

        ValuableData dataByA = U.gson.fromJson(raw.rawStr, ValuableData.class);
        List<ItemScan> lis = ValuableDataParser.getItemScanList(dataByA);
        System.out.println(lis.length());

    }
}
