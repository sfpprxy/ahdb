package org.ahdb.server;

import org.ahdb.server.model.ValuableDataByAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ahdb")
@RestController
public class ReceiveController {
    private static final Logger log = LoggerFactory.getLogger(ReceiveController.class);

    @Autowired
    ReceiveService receiveService;
    @Autowired
    RawDataService rawDataService;

    @PostMapping(value = "/push")
    public String receive(@RequestBody List<ValuableDataByAccount> valuableDataByAccount) {
        Boolean success = receiveService.receive(io.vavr.collection.List.ofAll(valuableDataByAccount));
        if (success) {
            return "OK";
        }
        return "ERR";
    }

    @GetMapping(value = "/saveAllFromRaw")
    public String saveAllFromRaw() {
        return "ERR";
    }
}
