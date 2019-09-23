package org.jwork.ahdb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jwork.ahdb.model.ItemDesc;
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

        String name = eName.text();
        int lv = Integer.valueOf(eLv.toString());
        int g = Try(() -> Integer.valueOf(eg.text())).getOrElse(0);
        int s = Try(() -> Integer.valueOf(es.text())).getOrElse(0);
        int c = Try(() -> Integer.valueOf(ec.text())).getOrElse(0);

        return new ItemDesc()
                .setId(id)
                .setName(name)
                .setLv(lv)
                .setG(g).setS(s).setC(c);
    }
}
