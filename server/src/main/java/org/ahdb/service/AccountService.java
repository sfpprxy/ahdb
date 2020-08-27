package org.ahdb.service;

import org.ahdb.model.AccountStats;
import org.ahdb.model.AccountStatsVO;
import org.ahdb.repo.AccountStatsRepository;
import org.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.QueryParam;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@ApplicationScoped
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Inject
    AccountStatsRepository accountStatsRepository;

    @Transactional
    public AccountStatsVO getStats(@QueryParam("account") String accountId) {
        log.trace("getStats for: {}", accountId);
        AccountStats as = accountStatsRepository.findFirstByAccountId(accountId);
        AccountStatsVO vo = new AccountStatsVO();
        if (as == null) {
            log.warn("cannot find account {}", accountId);
            return vo;
        }

        vo.setAccountId(accountId)
                .setChars(as.chars)
                .setPower(Integer.toString(as.power));
        return vo;
    }


    private void charge(AccountStats accountStats, Integer quantity) {
        log.debug("into charge");
        AccountStats as = accountStatsRepository.findById(accountStats.accountId).orElse(new AccountStats());
        if (as.accountId == null) {
            as.setAccountId(accountStats.accountId)
                    .setChars(accountStats.chars)
                    .setPower(quantity)
                    .setLastPush(accountStats.lastPush);
        } else {
            int t = as.power + quantity;
            log.debug("power {}", t);
            if (t > 1000) {
                t = 1000;
            }
            as.setPower(t);
        }
        log.debug("charge = {} {} {}", as.accountId, as.chars, as.power);
        accountStatsRepository.save(as);
    }

    private boolean consume(String accountId, Integer quantity) {
        AccountStats as = accountStatsRepository.findById(accountId).orElse(new AccountStats());
        if (as.accountId == null) {
            log.warn("account {} does not exist", accountId);
        } else {
            int t = as.power - quantity;
            if (t < 0) {
                return false;
            }
            as.setPower(t);
        }
        log.debug("consume = {} {} {}", as.accountId, as.chars, as.power);
        accountStatsRepository.save(as);
        return true;
    }

    @Transactional
    public void chargeByPush(AccountStats accountStats) {
        String id = accountStats.accountId;
        AccountStats as = accountStatsRepository.findFirstByAccountId(id);
        if (as == null) {
            as = accountStats;
            as.setLastPush(Timestamp.valueOf(U.dateTimeNow().minusDays(1)));
        }
        log.debug("before charge accountId {} chars {} lastPush {}", id, as.chars, as.lastPush);
        if (as.lastPush.toLocalDateTime().isBefore(LocalDateTime.now().minusMinutes(5))) {
            as.setLastPush(Timestamp.valueOf(LocalDateTime.now()));
            charge(as, 20);
        }
        String msg = U.fstr("after  charge accountId {} chars {} lastPush {}", id, as.chars, as.lastPush);
        log.info(msg);
    }

    @Transactional
    public boolean consumeByQuery(String accountId) {
        AccountStats as = accountStatsRepository.findFirstByAccountId(accountId);
        if (as == null) {
            log.error("cannot find account {} to consume", accountId);
            return false;
        }
        LocalDateTime lp = as.lastPush.toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime b30m = now.minusMinutes(30);
        LocalDateTime b1h = now.minusHours(1);
        LocalDateTime b2h = now.minusHours(2);
        LocalDateTime b4h = now.minusHours(4);
        LocalDateTime b12h = now.minusHours(8);
        int cp = 0;
        if (lp.isAfter(b30m)) {
            log.debug("free query for {}", accountId);
        } else if (lp.isBefore(b30m) && lp.isAfter(b1h)) {
            cp = 1;
        } else if (lp.isBefore(b1h) && lp.isAfter(b2h)) {
            cp = 2;
        } else if (lp.isBefore(b2h) && lp.isAfter(b4h)) {
            cp = 4;
        } else if (lp.isBefore(b4h) && lp.isAfter(b12h)) {
            cp = 10;
        } else {
            cp = 20;
        }
        return consume(accountId, cp);
    }

    @Transactional
    public boolean consumeByQueryAll(String accountId) {
        AccountStats as = accountStatsRepository.findFirstByAccountId(accountId);
        if (as == null) {
            log.error("cannot find account {} to consume", accountId);
            return false;
        }

        return consume(accountId, 100);
    }

}
