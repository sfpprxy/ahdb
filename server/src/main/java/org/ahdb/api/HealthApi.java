package org.ahdb.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Path("/")
public class HealthApi {

    private static final Logger log = LoggerFactory.getLogger(HealthApi.class);

    @GET
    @Path(value = "mem")
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
