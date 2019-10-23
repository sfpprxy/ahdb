package org.ahdb.server;

import org.ahdb.server.model.BizCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private static final Logger log = LoggerFactory.getLogger(CacheService.class);

    @Autowired
    CacheRepository cacheRepository;

    public void get() {
        BizCache bc = cacheRepository.findAll().get(0);

        log.debug("cacheStr: {}", bc.cacheStr);
    }
}
