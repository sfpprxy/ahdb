package org.ahdb.model;


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

    public ItemDesc setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemDesc setName(String name) {
        this.name = name;
        return this;
    }

    public String getItemClass() {
        return itemClass;
    }

    public ItemDesc setItemClass(String itemClass) {
        this.itemClass = itemClass;
        return this;
    }

    public String getSubClass() {
        return subClass;
    }

    public ItemDesc setSubClass(String subClass) {
        this.subClass = subClass;
        return this;
    }

    public Integer getItemLv() {
        return itemLv;
    }

    public ItemDesc setItemLv(Integer itemLv) {
        this.itemLv = itemLv;
        return this;
    }

    public Integer getRequireLv() {
        return requireLv;
    }

    public ItemDesc setRequireLv(Integer requireLv) {
        this.requireLv = requireLv;
        return this;
    }

    public Integer getVendorBuy() {
        return vendorBuy;
    }

    public ItemDesc setVendorBuy(Integer vendorBuy) {
        this.vendorBuy = vendorBuy;
        return this;
    }

    public Integer getVendorSell() {
        return vendorSell;
    }

    public ItemDesc setVendorSell(Integer vendorSell) {
        this.vendorSell = vendorSell;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public ItemDesc setIcon(String icon) {
        this.icon = icon;
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
                " " + (vendorSell / 10000) + "g" +
                " " + (vendorSell / 100) % 100 + "s" +
                " " + (vendorSell / 1) % 100 + "c" + " ";
    }
}
