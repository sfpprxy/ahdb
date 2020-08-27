package org.ahdb.model;

import java.time.LocalDateTime;

public class IpStats {

    public LocalDateTime lastQuery;
    public Integer timesToday;

    public LocalDateTime getLastQuery() {
        return lastQuery;
    }

    public IpStats setLastQuery(LocalDateTime lastQuery) {
        this.lastQuery = lastQuery;
        return this;
    }

    public Integer getTimesToday() {
        return timesToday;
    }

    public IpStats setTimesToday(Integer timesToday) {
        this.timesToday = timesToday;
        return this;
    }
}
