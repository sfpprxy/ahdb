package org.ahdb.model;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "biz_cache")

public class CacheWrapper {

    @Id
    public String id;
    public String cacheStr;

    public String getId() {
        return id;
    }

    public CacheWrapper setId(String id) {
        this.id = id;
        return this;
    }

    public String getCacheStr() {
        return cacheStr;
    }

    public CacheWrapper setCacheStr(String cacheStr) {
        this.cacheStr = cacheStr;
        return this;
    }
}
