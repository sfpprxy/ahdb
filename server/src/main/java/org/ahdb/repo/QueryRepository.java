package org.ahdb.repo;

import org.ahdb.common.AhdbException;
import org.ahdb.util.U;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class QueryRepository {

    private static final Logger log = LoggerFactory.getLogger(QueryRepository.class);

    @PersistenceContext
    EntityManager entityManager;
    SessionFactory sessionFactory;

    @Transactional
    public <T> T nativeQuery(String sql,Class<T> type) {
        Session session = entityManager.unwrap(Session.class);
        if (sessionFactory == null) {
            sessionFactory = session.getSessionFactory();
        }
        T res = null;
        try (Session cs = sessionFactory.openSession()) {
            NativeQuery nq = cs.createNativeQuery(sql);
            if (type == List.class) {
                List list = nq.getResultList();
                res= type.cast(list);
            } else if (type == Integer.class) {
                Integer nqRes = nq.executeUpdate();
                res = type.cast(nqRes);
            } else {
                Object result = nq.getSingleResult();
                res= type.cast(result);
            }
        } catch (Exception ex) {
            log.error("nativeQuery fail", ex);
            throw ex;
        }
        return res;
    }

    public void validateItemId(String itemId) {
        try {
            Integer id = Integer.valueOf(itemId);
            log.debug("validateItemId {}", itemId);
        } catch (NumberFormatException e) {
            throw new AhdbException(U.fstr("potential sql injection {}", itemId));
        }
    }

    public List<Object[]> queryItemDailyStats(String itemId) {
        validateItemId(itemId);
        List<Object[]> lists = nativeQuery(U.fstr(
                "SELECT daily_time_bucket, CAST(daily_avg_market_value AS INTEGER)\n" +
                "FROM view_daily\n" +
                "WHERE item_id = '{}'\n" +
                "  AND daily_time_bucket > now() - INTERVAL '14 day'\n" +
                "ORDER BY daily_time_bucket DESC", itemId),
                List.class);
        return lists;
    }

    public List<Object[]> queryAllItemStats() {
        List<Object[]> lists = nativeQuery(
                "WITH bounds AS (\n" +
                "    SELECT item_id                                                        id0,\n" +
                "           time_bucket('9973 day', daily_time_bucket)                     time_bucket0,\n" +
                "           (avg(daily_avg_market_value) - stddev(daily_avg_market_value)) low,\n" +
                "           (avg(daily_avg_market_value) + stddev(daily_avg_market_value)) high\n" +
                "    FROM view_daily\n" +
                "    WHERE daily_time_bucket BETWEEN cast(now() AS date) - INTERVAL '14 day' AND cast(now() AS date) - INTERVAL '0 day'\n" +
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
                "                   (CASE WHEN (vendor_sell = 0) THEN greatest(item_lv * 9, 20)\n" +
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
                "             time_bucket('9973 day', daily_time_bucket)                                     time_buctet_14day\n" +
                "      FROM view_daily,\n" +
                "           bounds\n" +
                "               JOIN item_desc ON item_desc.id = id0\n" +
                "      WHERE daily_time_bucket BETWEEN cast(now() AS date) - INTERVAL '14 day' AND cast(now() AS date) - INTERVAL '0 day'\n" +
                "        AND item_id = id0\n" +
                "        AND time_bucket('9973 day', daily_time_bucket) = time_bucket0\n" +
                "        AND daily_avg_market_value BETWEEN low AND high\n" +
                "      GROUP BY id, name, vendor_sell, time_buctet_14day, item_lv, item_class, sub_class) AS a\n" +
                "WHERE 1 = 1\n" +
                "--   AND item_class IN ('材料', '消耗品', '杂项')\n" +
                "--   AND sub_class NOT IN ('食物和饮料', '!垃圾')\n" +
                "--   AND auctions > 10\n" +
                "--   AND quantity BETWEEN 20 AND 7000\n" +
                "--   AND vindex > 5\n" +
                "ORDER BY max_stock DESC",
                List.class);
        return lists;
    }

    public List<Object[]> queryAllItemStatsEarly7() {
        List<Object[]> lists = nativeQuery(
                "WITH bounds AS (\n" +
                "    SELECT item_id                                                        id0,\n" +
                "           time_bucket('9973 day', daily_time_bucket)                     time_bucket0,\n" +
                "           (avg(daily_avg_market_value) - stddev(daily_avg_market_value)) low,\n" +
                "           (avg(daily_avg_market_value) + stddev(daily_avg_market_value)) high\n" +
                "    FROM view_daily\n" +
                "    WHERE daily_time_bucket BETWEEN cast(now() AS date) - INTERVAL '14 day' AND cast(now() AS date) - INTERVAL '7 day'\n" +
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
                "                   (CASE WHEN (vendor_sell = 0) THEN greatest(item_lv * 9, 20)\n" +
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
                "             time_bucket('9973 day', daily_time_bucket)                                     time_buctet_14day\n" +
                "      FROM view_daily,\n" +
                "           bounds\n" +
                "               JOIN item_desc ON item_desc.id = id0\n" +
                "      WHERE daily_time_bucket BETWEEN cast(now() AS date) - INTERVAL '14 day' AND cast(now() AS date) - INTERVAL '7 day'\n" +
                "        AND item_id = id0\n" +
                "        AND time_bucket('9973 day', daily_time_bucket) = time_bucket0\n" +
                "        AND daily_avg_market_value BETWEEN low AND high\n" +
                "      GROUP BY id, name, vendor_sell, time_buctet_14day, item_lv, item_class, sub_class) AS a\n" +
                "WHERE 1 = 1\n" +
                "--   AND item_class IN ('材料', '消耗品', '杂项')\n" +
                "--   AND sub_class NOT IN ('食物和饮料', '!垃圾')\n" +
                "--   AND auctions > 10\n" +
                "--   AND quantity BETWEEN 20 AND 7000\n" +
                "--   AND vindex > 5\n" +
                "ORDER BY max_stock DESC",
                List.class);
        return lists;
    }

    public List<Object[]> queryAllItemStatsLater7() {
        List<Object[]> lists = nativeQuery(
                "WITH bounds AS (\n" +
                "    SELECT item_id                                                        id0,\n" +
                "           time_bucket('9973 day', daily_time_bucket)                     time_bucket0,\n" +
                "           (avg(daily_avg_market_value) - stddev(daily_avg_market_value)) low,\n" +
                "           (avg(daily_avg_market_value) + stddev(daily_avg_market_value)) high\n" +
                "    FROM view_daily\n" +
                "    WHERE daily_time_bucket BETWEEN cast(now() AS date) - INTERVAL '7 day' AND cast(now() AS date) - INTERVAL '0 day'\n" +
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
                "                   (CASE WHEN (vendor_sell = 0) THEN greatest(item_lv * 9, 20)\n" +
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
                "             time_bucket('9973 day', daily_time_bucket)                                     time_buctet_14day\n" +
                "      FROM view_daily,\n" +
                "           bounds\n" +
                "               JOIN item_desc ON item_desc.id = id0\n" +
                "      WHERE daily_time_bucket BETWEEN cast(now() AS date) - INTERVAL '7 day' AND cast(now() AS date) - INTERVAL '0 day'\n" +
                "        AND item_id = id0\n" +
                "        AND time_bucket('9973 day', daily_time_bucket) = time_bucket0\n" +
                "        AND daily_avg_market_value BETWEEN low AND high\n" +
                "      GROUP BY id, name, vendor_sell, time_buctet_14day, item_lv, item_class, sub_class) AS a\n" +
                "WHERE 1 = 1\n" +
                "--   AND item_class IN ('材料', '消耗品', '杂项')\n" +
                "--   AND sub_class NOT IN ('食物和饮料', '!垃圾')\n" +
                "--   AND auctions > 10\n" +
                "--   AND quantity BETWEEN 20 AND 7000\n" +
                "--   AND vindex > 5\n" +
                "ORDER BY max_stock DESC",
                List.class);
        return lists;
    }

    public void refreshDay14ItemStats() {
        try {
            nativeQuery("REFRESH MATERIALIZED VIEW view_daily", Integer.class);
        } catch (Exception ex) {
            log.error("refreshDay14ItemStats fail", ex);
        }
    }

}
