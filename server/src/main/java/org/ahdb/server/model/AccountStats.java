package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class AccountStats {
    @Id
    public String accountId;
    public String chars;
    public Integer power;
    public Timestamp lastPush;
}
