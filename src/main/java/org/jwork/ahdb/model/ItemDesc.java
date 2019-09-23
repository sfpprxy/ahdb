package org.jwork.ahdb.model;

public class ItemDesc {
    public String id;
    public String name;
    public Integer lv;
    public Integer g;
    public Integer s;
    public Integer c;

    public String getId() {
        return id;
    }

    public ItemDesc setId(String idcc) {
        id = idcc;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemDesc setName(String namecc) {
        name = namecc;
        return this;
    }

    public Integer getLv() {
        return lv;
    }

    public ItemDesc setLv(Integer lvcc) {
        lv = lvcc;
        return this;
    }

    public Integer getG() {
        return g;
    }

    public ItemDesc setG(Integer gcc) {
        g = gcc;
        return this;
    }

    public Integer getS() {
        return s;
    }

    public ItemDesc setS(Integer scc) {
        s = scc;
        return this;
    }

    public Integer getC() {
        return c;
    }

    public ItemDesc setC(Integer ccc) {
        c = ccc;
        return this;
    }

    @Override
    public String toString() {
        return "ItemDesc" +
                " " + id +
                " " + name +
                " " + lv +
                " " + g +
                " " + s +
                " " + c + " ";
    }
}
