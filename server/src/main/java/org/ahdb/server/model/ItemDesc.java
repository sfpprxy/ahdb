package org.ahdb.server.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class ItemDesc {

    @Id
    public String id;
    public String name;
    public String itemClass;
    public String subClass;
    public Integer itemLv;
    public Integer requireLv;
    public Integer vendorBuy;
    public Integer vendorSell;
    public String icon;

    @Override
    public String toString() {
        Integer vdBuy = 0;
        if (vendorBuy == null) {
            vdBuy = 0;
        }
        return "ItemDesc" +
                " " + id +
                " " + name +
                " " + itemClass +
                " " + subClass +
                " " + itemLv +
                " " + requireLv +
//                " " + vdBuy % 1 +
//                " " + vdBuy % 1 +
//                " " + vdBuy % 1 +
                " " + (vendorSell/10000) +"g"+
                " " + (vendorSell/100) % 100 +"s"+
                " " + (vendorSell/1) % 100 + "c"+" ";
    }
}
