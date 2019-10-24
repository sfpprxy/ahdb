package org.ahdb.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.Cache;
import org.ahdb.server.model.CacheWrapper;
import org.ahdb.server.model.IpStats;
import org.ahdb.server.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CacheService {

    final CacheRepository cacheRepository;

    public IpStats getIpStats(String ip) {
        Cache cache = get();
        IpStats is = cache.getStatsByIp().get(ip);
        if (is.lastQuery == null) {
            is.setLastQuery(U.dateTimeNow().minusDays(2));
        }
        log.debug("fuck get 1 {}", is.timesToday);
        if (is.timesToday == null) {
            is.setTimesToday(0);
        }
        log.debug("fuck get 2 {}", is.timesToday);
        System.out.println(2222222);
        return is;
    }

    public void setIpStats(String ip, IpStats ipStats) {
        Cache cache = get();
        cache.statsByIp.put(ip, ipStats);
        log.debug("fuck set {}", ipStats.timesToday);
        set(cache);
    }

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
}
