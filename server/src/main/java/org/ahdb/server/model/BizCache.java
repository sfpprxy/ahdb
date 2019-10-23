package org.ahdb.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BizCache {

    @Id
    public String id;
    public String cacheStr;

    public String getId() {
        return id;
    }

    public BizCache setId(String id) {
        this.id = id;
        return this;
    }

    public String getCacheStr() {
        return cacheStr;
    }

    public BizCache setCacheStr(String rawStrcc) {
        cacheStr = rawStrcc;
        return this;
    }
}
