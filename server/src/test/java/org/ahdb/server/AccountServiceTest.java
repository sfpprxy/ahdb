package org.ahdb.server;

import org.ahdb.server.model.AccountStats;
import org.junit.Test;

public class AccountServiceTest {

    @Test
    public void losePowerByTime() {
        AccountService s = new AccountService();
        for (int i = 0; i < 100; i++) {

            AccountStats as = new AccountStats();
            System.out.println(new AccountStats().setPower(i));
        }
    }
}
