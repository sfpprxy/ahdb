package org.jwork.ahdb.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
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

    public String getId() {
        return id;
    }

    public ItemDesc setId(String idcc) {
        id = idcc;
        return this;
    }

    public String getItemClass() {
        return itemClass;
    }

    public ItemDesc setItemClass(String itemClasscc) {
        itemClass = itemClasscc;
        return this;
    }

    public String getSubClass() {
        return subClass;
    }

    public ItemDesc setSubClass(String subClasscc) {
        subClass = subClasscc;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemDesc setName(String namecc) {
        name = namecc;
        return this;
    }

    public Integer getItemLv() {
        return itemLv;
    }

    public ItemDesc setItemLv(Integer itemLvcc) {
        itemLv = itemLvcc;
        return this;
    }

    public Integer getRequireLv() {
        return requireLv;
    }

    public ItemDesc setRequireLv(Integer requireLvcc) {
        requireLv = requireLvcc;
        return this;
    }

    public Integer getVendorBuy() {
        return vendorBuy;
    }

    public ItemDesc setVendorBuy(Integer vendorBuycc) {
        vendorBuy = vendorBuycc;
        return this;
    }

    public Integer getVendorSell() {
        return vendorSell;
    }

    public ItemDesc setVendorSell(Integer vendorSellcc) {
        vendorSell = vendorSellcc;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public ItemDesc setIcon(String iconcc) {
        icon = iconcc;
        return this;
    }

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
