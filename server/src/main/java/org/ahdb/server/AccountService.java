package org.ahdb.server;

import org.ahdb.server.model.AccountStats;
import org.ahdb.server.model.AccountStatsVO;
import org.ahdb.server.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Map;

@Service
public class AccountService {
    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountStatsRepository accountStatsRepository;

    private static Map<String, Timestamp> uploaders = U.map();
    private int powerUnit = 10;

    @Transactional
    public AccountStatsVO getStats(String accountId) {
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

    @Transactional
    public void chargeByPush(AccountStats as) {
        String id = as.accountId;
        log.debug("accountId {} chars {}", id, as.chars);

        charge(id, as.chars, 20 * powerUnit);
    }

    @Transactional
    public void consumeByQuery(String accountId) {
        AccountStats as = accountStatsRepository.findFirstByAccountId(accountId);
        if (as == null) {
            log.error("cannot find account {} to consume", accountId);
            return;
        }
        // TODO: impl
        consume(accountId, powerUnit);
    }

    private void charge(String accountId, String chars, Integer quantity) {
        log.debug("into charge");
        AccountStats as = accountStatsRepository.findById(accountId).orElse(new AccountStats());
        if (as.accountId == null) {
            as.setAccountId(accountId)
                    .setChars(chars)
                    .setPower(0);
        } else {
            int t = as.power + quantity;
            log.debug("power {}", t);
            if (t > 100 * powerUnit) {
                t = 100 * powerUnit;
            }
            as.setPower(t);
        }
        log.debug("charge = {} {} {}", as.accountId, as.chars, as.power);
        accountStatsRepository.save(as);
    }

    private void consume(String accountId, Integer quantity) {
        AccountStats as = accountStatsRepository.findById(accountId).orElse(new AccountStats());
        if (as.accountId == null) {
            log.warn("account {} does not exist", accountId);
        } else {
            int t = as.power - quantity;
            if (t < 0) {
                t = 0;
            }
            as.setPower(t);
        }
        log.debug("consume = {} {} {}", as.accountId, as.chars, as.power);
        accountStatsRepository.save(as);
    }
}
