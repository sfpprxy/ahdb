package org.ahdb.model;


import java.util.Map;


public class Cache {

    public Map<String, IpStats> statsByIp;

    public Map<String, IpStats> getStatsByIp() {
        return statsByIp;
    }

    public Cache setStatsByIp(Map<String, IpStats> statsByIp) {
        this.statsByIp = statsByIp;
        return this;
    }
}
