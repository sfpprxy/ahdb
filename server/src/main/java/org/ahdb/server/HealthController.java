package org.ahdb.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping()
@RestController
public class HealthController {

    @GetMapping(value = "/mem")
    public String mem() {
        Runtime rt = Runtime.getRuntime();
        long max = rt.maxMemory();
        long free = rt.freeMemory();
        long total = rt.totalMemory();
        String mem = String.format("max %s free %s total %s", max, free, total);
        log.info(mem);
        log.debug(mem);
        return mem;
    }

}
