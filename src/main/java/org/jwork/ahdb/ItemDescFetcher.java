package org.jwork.ahdb;

import io.vavr.collection.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jwork.ahdb.model.ItemDesc;
import org.jwork.ahdb.util.U;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

import static io.vavr.API.Try;

public class ItemDescFetcher {
    private static final Logger log = LoggerFactory.getLogger(ItemDescFetcher.class);

    public static ItemDesc getDesc(String id) {
        // TODO fetch item icon as well
        Document doc = null;
        try {
            doc = Jsoup.connect("https://cn.classic.wowhead.com/item=" + id).get();
        } catch (IOException e) {
            log.error("getDesc fail item id: " + id, e);
        }

        Element p2 = Objects.requireNonNull(doc).getElementById("main-contents");
        Element p3 = p2.getElementsByClass("text").first();
        Element eName = p3.select("h1").first();
        Element eDesc = p3.getElementsByTag("noscript").first();

        Node eLv = eDesc.select("span.q").first().childNodes().get(3);
        Element eg = eDesc.select("span.moneygold").first();
        Element es = eDesc.select("span.moneysilver").first();
        Element ec = eDesc.select("span.moneycopper").first();
        Element eIcon = doc.selectFirst("link[rel=\"image_src\"]");
        ItemDesc tmpItemDesc = getItemClass(doc.toString());

        String name = Try(() -> eName.text()).getOrElse("unknowName");
        int ilv = Try(() -> Integer.valueOf(eLv.toString())).getOrElse(0);
        int g = Try(() -> Integer.valueOf(eg.text())).getOrElse(0);
        int s = Try(() -> Integer.valueOf(es.text())).getOrElse(0);
        int c = Try(() -> Integer.valueOf(ec.text())).getOrElse(0);
        String icon = eIcon.attr("href");

        return new ItemDesc()
                .setId(id)
                .setName(name)
                .setItemClass(tmpItemDesc.itemClass)
                .setSubClass(tmpItemDesc.subClass)
                .setItemLv(ilv)
                .setRequireLv(null)
                .setVendorBuy(null)
                .setVendorSell(c + 100*s + 10000*g)
                .setIcon(icon);
    }

    public static ItemDesc getItemClass(String docStr) {
        java.util.List<java.util.List> itemCata = RawMeta.getItemCata();

        // PageTemplate.set({ breadcrumb: [0,0,2,1]});
        String breadcrumb = "PageTemplate.set({ breadcrumb: ";
        int index = docStr.indexOf(breadcrumb);
        String sub = docStr.substring(index + breadcrumb.length()+1, index + breadcrumb.length()+20);
        // 0,0,2,1
        String commaClass = sub.substring(0, sub.indexOf("]"));
        String[] sp = commaClass.split(",");
        Integer itemClassIndex = Integer.valueOf(sp[2]);
        Integer subClassIndex = Integer.valueOf(sp[3]);

        List itemClassList = List.ofAll(itemCata).map(e -> List.ofAll(e)).find(e -> {
            Double d = (Double) e.get(0);
            return U.match(itemClassIndex, d.intValue());
        }).get();

        String itemClass = itemClassList.get(1).toString();

        java.util.List subClassListJ = (java.util.List) itemClassList.get(3);
        Object subClassObj = List.ofAll(subClassListJ).find(e -> {
            java.util.List subClassJ = (java.util.List) e;
            Double d = (Double) subClassJ.get(0);
            return U.match(subClassIndex, d.intValue());
        }).get();
        Object subClass = ((java.util.List) subClassObj).get(1);

        return new ItemDesc().setItemClass(itemClass).setSubClass(subClass.toString());
    }
}
