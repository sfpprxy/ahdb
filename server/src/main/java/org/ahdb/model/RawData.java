package org.ahdb.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity

public class RawData {

    @Id
    public Timestamp time;
    public String type;
    public String accountId;
    public String rawStr;

    public Timestamp getTime() {
        return time;
    }

    public RawData setTime(Timestamp time) {
        this.time = time;
        return this;
    }

    public String getType() {
        return type;
    }

    public RawData setType(String type) {
        this.type = type;
        return this;
    }

    public String getAccountId() {
        return accountId;
    }

    public RawData setAccountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public String getRawStr() {
        return rawStr;
    }

    public RawData setRawStr(String rawStr) {
        this.rawStr = rawStr;
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
