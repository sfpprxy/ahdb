package org.jwork.ahdb;

import org.jwork.ahdb.model.ValuableDataByAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/ahdb")
@RestController
public class DataController {
    private static final Logger log = LoggerFactory.getLogger(DataController.class);

    @Autowired
    DataService dataService;

    @PostMapping(value="/push")
    public String receive(@RequestBody List<ValuableDataByAccount> valuableDataByAccount) {
        Boolean success = dataService.process(io.vavr.collection.List.ofAll(valuableDataByAccount));
        if (success) {
            return "OK";
        }
        return "ERR";
    }

}