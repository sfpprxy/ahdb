package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "biz_cache")
@Getter
@Setter
@Accessors(chain = true)
public class CacheWrapper {

    @Id
    public String id;
    public String cacheStr;

}
