package org.ahdb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ahdb")
@RestController
public class HealthController {
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);

    @GetMapping(value = "/mem")
    public String mem() {
        Runtime rt = Runtime.getRuntime();
        long max = rt.maxMemory();
        long free = rt.freeMemory();
        long total = rt.totalMemory();
        String mem = String.format("max %s free %s total %s", max, free, total);
        log.debug(mem);
        return mem;
    }
}
