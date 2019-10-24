package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@IdClass(ItemScan.IdClass.class)
public class ItemScan {
    // itemString,minBuyout,marketValue,numAuctions,quantity,lastScan
    @Id
    public String itemId;
    public Integer minBuyout;
    public Integer marketValue;
    public Integer numAuctions;
    public Integer quantity;
    @Id
    public Timestamp scanTime;
    public String realm;
    public Timestamp addTime;

    @Getter
    @Setter
    @Accessors(chain = true)
    static class IdClass implements Serializable {
        public String itemId;
        public Timestamp scanTime;
    }

    @Override
    public String toString() {
        return "ItemScan "+
                itemId + " " +
                minBuyout.toString() + " " +
                marketValue.toString() + " " +
                numAuctions.toString() + " " +
                quantity.toString() + " " +
                scanTime.toString();
//        return "ItemScan{" +
//                "itemInteger='" + itemInteger + '\'' +
//                ", minBuyout='" + minBuyout + '\'' +
//                ", marketValue='" + marketValue + '\'' +
//                ", numAuctions='" + numAuctions + '\'' +
//                ", quantity='" + quantity + '\'' +
//                ", scanTime='" + scanTime + '\'' +
//                '}';
    }
}
