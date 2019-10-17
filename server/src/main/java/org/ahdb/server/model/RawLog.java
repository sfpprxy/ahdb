package org.ahdb.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class RawLog {

    @Id
    public Timestamp time;
    public String type;
    public String rawStr;

    public Timestamp getTime() {
        return time;
    }

    public RawLog setTime(Timestamp timecc) {
        time = timecc;
        return this;
    }

    public String getType() {
        return type;
    }

    public RawLog setType(String typecc) {
        type = typecc;
        return this;
    }

    public String getRawStr() {
        return rawStr;
    }

    public RawLog setRawStr(String rawStrcc) {
        rawStr = rawStrcc;
        return this;
    }
}
