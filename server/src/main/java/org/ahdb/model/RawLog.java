package org.ahdb.model;


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

    public RawLog setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    public String getType() {
        return type;
    }

    public RawLog setType(String type) {
        this.type = type;
        return this;
    }

    public String getRawStr() {
        return rawStr;
    }

    public RawLog setRawStr(String rawStr) {
        this.rawStr = rawStr;
        return this;
    }
}
