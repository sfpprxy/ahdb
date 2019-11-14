package org.ahdb.server;

import org.ahdb.server.model.ItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QueryRepository extends JpaRepository<ItemDesc, String> {

    @Query(value =
                "select item_desc.id, " +
                "       item_desc.name, " +
                "       item_scan.scan_time, " +
                "       item_scan.min_buyout, " +
                "       item_scan.market_value, " +
                "       item_scan.num_auctions, " +
                "       item_scan.quantity, " +
                "       item_desc.item_class, " +
                "       item_desc.sub_class " +
                "from item_desc " +
                "         join item_scan " +
                "              on item_desc.id = item_scan.item_id " +
                "where item_desc.name != '!奥术水晶' " +
                "order by scan_time desc;"
            , nativeQuery = true)
    List<Object> view();

    @Query(value =
            "SELECT daily_time_bucket, CAST(daily_avg_market_value AS INTEGER)\n" +
            "FROM view_daily\n" +
            "WHERE item_id = ?\n" +
            "  AND daily_time_bucket > now() - INTERVAL '14 day'\n" +
            "ORDER BY daily_time_bucket DESC"
    ,nativeQuery = true)
    List<List<Object>> queryItemDailyStats(String id);


    @Query(value =
            "WITH bounds AS (\n" +
            "    SELECT item_id                                                        id0,\n" +
            "           time_bucket('31 day', daily_time_bucket)                       time_bucket0,\n" +
            "           (avg(daily_avg_market_value) - stddev(daily_avg_market_value)) low,\n" +
            "           (avg(daily_avg_market_value) + stddev(daily_avg_market_value)) high\n" +
            "    FROM view_daily\n" +
            "    WHERE daily_time_bucket > cast(now() AS date) - INTERVAL '14 day'\n" +
            "    GROUP BY id0, time_bucket0\n" +
            ")\n" +
            "SELECT id,\n" +
            "       name,\n" +
            "       vindex,\n" +
            "       greatest(round(\n" +
            "                        CASE WHEN vindex > 6 THEN\n" +
            "                                 least((((vindex * 18 + quantity * 7 + auctions * 5 + market * 8) * 0.07)) / 2 - 100, quantity*0.7)\n" +
            "                             ELSE quantity * 0.1\n" +
            "                            END\n" +
            "                    ), 1)AS max_stock,\n" +
            "       vendor_sell,\n" +
            "       market,\n" +
            "       marketd,\n" +
            "       auctions,\n" +
            "       quantity,\n" +
            "       item_lv,\n" +
            "       item_class,\n" +
            "       sub_class,\n" +
            "       time_buctet_14day\n" +
            "FROM (SELECT id,\n" +
            "             count(id),\n" +
            "             name,\n" +
            "             round(avg(daily_avg_market_value) /\n" +
            "                   (CASE WHEN (vendor_sell = 0) THEN greatest(item_lv * 5, 20)\n" +
            "                         ELSE vendor_sell + 15\n" +
            "                       END))    vindex,\n" +
            "             vendor_sell,\n" +
            "             round(avg(daily_avg_market_value))                                           market,\n" +
            "             round(stddev(daily_avg_market_value))                                        marketd,\n" +
            "             round(avg(daily_avg_num_auctions))                                           auctions,\n" +
            "             round(avg(daily_avg_quantity))                                               quantity,\n" +
            "             item_lv,\n" +
            "             item_class,\n" +
            "             sub_class,\n" +
            "             time_bucket('31 day', daily_time_bucket)                                     time_buctet_14day\n" +
            "      FROM view_daily,\n" +
            "           bounds\n" +
            "               JOIN item_desc ON item_desc.id = id0\n" +
            "      WHERE daily_time_bucket > cast(now() AS date) - INTERVAL '14 day'\n" +
            "        AND item_id = id0\n" +
            "        AND time_bucket('31 day', daily_time_bucket) = time_bucket0\n" +
            "        AND daily_avg_market_value BETWEEN low AND high\n" +
            "      GROUP BY id, name, vendor_sell, time_buctet_14day, item_lv, item_class, sub_class) AS a\n" +
            "WHERE 1 = 1\n" +
            "--   AND item_class IN ('材料', '消耗品', '杂项')\n" +
            "--   AND sub_class NOT IN ('食物和饮料', '!垃圾')\n" +
            "--   AND auctions > 10\n" +
            "--   AND quantity BETWEEN 20 AND 7000\n" +
            "--   AND vindex > 5\n" +
            "ORDER BY max_stock DESC",
            nativeQuery = true)
    List<List<Object>> queryAllItemStats();

}
