package org.ahdb.server;

import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.ValuableDataByAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ReceiveServiceTest {

    @Autowired
    ReceiveService receiveService;
    @Autowired
    RawDataService rawDataService;

    @Test
    public void mock() {
        ValuableDataByAccount one = rawDataService.getOne();
        one.setType("drop scan");
        Boolean received = receiveService.receive(List.of(one));
        log.info("received: {}" ,received);
    }

}
