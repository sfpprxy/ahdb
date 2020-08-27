package org.ahdb.service;

import org.ahdb.model.Cache;
import org.ahdb.model.CacheWrapper;
import org.ahdb.model.IpStats;
import org.ahdb.repo.CacheRepository;
import org.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CacheService {

    private static final Logger log = LoggerFactory.getLogger(CacheService.class);

    @Inject
    CacheRepository cacheRepository;

    public Cache get() {
        CacheWrapper cw = cacheRepository.findAll().get(0);
        log.debug("cacheStr: {}", cw.cacheStr);
        Cache cache = U.gson.fromJson(cw.getCacheStr(), Cache.class);
        if (U.empty(cache)) {
            cache = new Cache();
        }
        if (U.empty(cache.getStatsByIp())) {
            cache.setStatsByIp(U.map());
        }
        set(cache);
        return cache;
    }

    public Cache set(Cache cache) {
        String rawStr = U.gson.toJson(cache);
        CacheWrapper cw = new CacheWrapper().setId("1").setCacheStr(rawStr);
        cacheRepository.save(cw);
        return cache;
    }

    public IpStats getIpStats(String ip) {
        Cache cache = get();
        IpStats is = cache.getStatsByIp().getOrDefault(ip, new IpStats());

        if (is.lastQuery == null) {
            is.setLastQuery(U.dateTimeNow().minusDays(2));
        }
        if (is.timesToday == null) {
            is.setTimesToday(0);
        }

        return is;
    }

    public void setIpStats(String ip, IpStats ipStats) {
        Cache cache = get();
        cache.statsByIp.put(ip, ipStats);
        set(cache);
    }

}
