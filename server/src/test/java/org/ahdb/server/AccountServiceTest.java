package org.ahdb.server;

import org.ahdb.server.model.AccountStatsVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void losePowerByTime() {
        AccountStatsVO stats = accountService.getStats("123123");
        System.out.println(stats.accountId);
    }
}
