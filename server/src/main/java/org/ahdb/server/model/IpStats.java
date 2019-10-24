package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class IpStats {

    public LocalDateTime lastQuery;
    public Integer timesToday;

}
