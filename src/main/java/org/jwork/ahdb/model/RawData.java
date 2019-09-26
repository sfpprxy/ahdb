package org.jwork.ahdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class RawData {

    @Id
    public java.sql.Timestamp time;
    public String type;
    public String accountId;
    public String rawStr;

    public Timestamp getTime() {
        return time;
    }

    public RawData setTime(Timestamp timecc) {
        time = timecc;
        return this;
    }

    public String getType() {
        return type;
    }

    public RawData setType(String typecc) {
        type = typecc;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public RawData setAccountId(String accountIdcc) {
        accountId = accountIdcc;
        return this;
    }

    public String getRawStr() {
        return rawStr;
    }

    public RawData setRawStr(String rawStrcc) {
        rawStr = rawStrcc;
        return this;
    }

    @Override
    public String toString() {
        return "RawData{" +
                "time=" + time +
                "\ntype='" + type + '\'' +
                "\naccountId='" + accountId + '\'' +
                "\nrawStr='" + rawStr + '\'' +
                '}';
    }
}
