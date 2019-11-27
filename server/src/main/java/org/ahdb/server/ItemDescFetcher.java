package org.ahdb.server;

import io.vavr.collection.List;
import lombok.extern.slf4j.Slf4j;
import org.ahdb.server.model.ItemDesc;
import org.ahdb.server.util.U;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.util.Objects;

import static io.vavr.API.Try;

@Slf4j
public class ItemDescFetcher {

    public static ItemDesc getItemClass(String docStr) {
        java.util.List<java.util.List> itemCata = RawMeta.getItemCata();

        String breadcrumb = "";
        String commaClass = "";
        try {
            // WH.Layout.set({breadcrumb: [0, 0, 7,5]});
            breadcrumb = "breadcrumb: ";
            int index = docStr.indexOf(breadcrumb);
            String sub = docStr.substring(index + breadcrumb.length() + 1, index + breadcrumb.length() + 20);
            // 0,0,2,1
            commaClass = sub.substring(0, sub.indexOf("]"));
            String[] sp = commaClass.replace(" ", "").split(",");
            Integer itemClassIndex = Integer.valueOf(sp[2]);
            Integer subClassIndex = Integer.valueOf(sp[3]);

            List<java.util.List> itemClassLJava = List.ofAll(itemCata);
            List<List> itemClassLVavr = itemClassLJava.map(e -> List.ofAll(e));

            List itemClassList = itemClassLVavr.find(e -> {
                Double d = (Double) e.get(0);
                return U.match(itemClassIndex, d.intValue());
            }).get();

            String itemClass = itemClassList.get(1).toString();

            String subClass = Try(() -> {
                java.util.List subClassListJ = (java.util.List) itemClassList.get(3);
                Object subClassObj = List.ofAll(subClassListJ).find(e -> {
                    java.util.List subClassJ = (java.util.List) e;
                    Double d = Try(() -> Double.valueOf(subClassJ.get(0).toString())).getOrElse(0d);
                    return U.match(subClassIndex, d.intValue());
                }).get();
                Object subClassStr = ((java.util.List) subClassObj).get(1);
                return subClassStr.toString();
            }).getOrElse("");

            return new ItemDesc().setItemClass(itemClass).setSubClass(subClass);
        } catch (Exception ex) {
            throw new AhdbException(U.fstr("getItemClass fail breadcrumb: {} commaClass: {} ", breadcrumb, commaClass), ex);
        }
    }

    public static ItemDesc getDesc(String id) {
        // TODO later fetch item icon as well
        Document doc;
        String name = "";
        try {
            doc = Jsoup.connect("https://cn.classic.wowhead.com/item=" + id).get();

            Element p2 = Objects.requireNonNull(doc).getElementById("main-contents");
            Element p3 = p2.getElementsByClass("text").first();
            Element eName = p3.select("h1").first();
            Element eDesc = p3.getElementsByTag("noscript").first();

            Node eLv = Try(() -> eDesc.select("span.q").first().childNodes().get(3)).getOrNull();
            Element eg = eDesc.select("span.moneygold").first();
            Element es = eDesc.select("span.moneysilver").first();
            Element ec = eDesc.select("span.moneycopper").first();
            Element eIcon = doc.selectFirst("link[rel=\"image_src\"]");

            name = Try(() -> eName.text()).getOrElse("unknowName");
            int ilv = Try(() -> Integer.valueOf(eLv.toString())).getOrElse(0);
            int g = Try(() -> Integer.valueOf(eg.text())).getOrElse(0);
            int s = Try(() -> Integer.valueOf(es.text())).getOrElse(0);
            int c = Try(() -> Integer.valueOf(ec.text())).getOrElse(0);
            String icon = eIcon.attr("href");

            ItemDesc tmpItemDesc = getItemClass(doc.toString());

            return new ItemDesc()
                    .setId(id)
                    .setName(name)
                    .setItemClass(tmpItemDesc.itemClass)
                    .setSubClass(tmpItemDesc.subClass)
                    .setItemLv(ilv)
                    .setRequireLv(null)
                    .setVendorBuy(null)
                    .setVendorSell(c + 100 * s + 10000 * g)
                    .setIcon(icon);

        } catch (Exception ex) {
            log.error("getDesc fail item itemId {} name {} ", id, name, ex);
            return null;
        }
    }

}
