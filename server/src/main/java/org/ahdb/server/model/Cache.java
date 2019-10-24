package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Setter
@Accessors(chain = true)
public class Cache {

    public Map<String, IpStats> statsByIp;

}
