package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.AccountStatsVO;
import org.ahdb.server.model.ItemStats;
import org.ahdb.server.model.ValuableDataByAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping()
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AhdbController {

    final ReceiveService receiveService;
    final AccountService accountService;
    final QueryService queryService;

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

    @GetMapping(value = "/item-stats")
    public ItemStats queryItemStats(@RequestParam String account, @RequestParam String item, HttpServletRequest request) {
        return queryService.queryItemStats(account, item, request.getRemoteAddr());
    }

    @GetMapping(value = "/test-get")
    public ItemStats testGet(HttpServletRequest request) {
        ItemStats is = queryService.queryItemStats("178635643", "毛料", request.getRemoteAddr());
        return is;
    }
}
