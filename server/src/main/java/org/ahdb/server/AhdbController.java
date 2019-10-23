package org.ahdb.server;

import org.ahdb.server.model.AccountStatsVO;
import org.ahdb.server.model.ValuableDataByAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ahdb")
@RestController
public class AhdbController {
    private static final Logger log = LoggerFactory.getLogger(AhdbController.class);

    @Autowired
    ReceiveService receiveService;
    @Autowired
    AccountService accountService;

    @PostMapping(value = "/push")
    public String receive(@RequestBody List<ValuableDataByAccount> valuableDataByAccount) {
        Boolean success = receiveService.receive(io.vavr.collection.List.ofAll(valuableDataByAccount));
        if (success) {
            return "OK";
        }
        return "ERR";
    }

    @GetMapping(value = "/account-stats")
    public AccountStatsVO getAccountStats(@RequestParam String account) {
        return accountService.getStats(account);
    }

    @GetMapping(value = "/saveAllFromRaw")
    public String saveAllFromRaw() {
        return "ERR";
    }
}
