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
public class RawData {

    @Id
    public Timestamp time;
    public String type;
    public String accountId;
    public String rawStr;

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
