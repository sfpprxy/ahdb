select item_desc.id,
       item_desc.name,
       item_scan.scan_time,
       item_scan.min_buyout,
       item_scan.market_value,
       item_scan.num_auctions,
       item_scan.quantity,
       item_desc.item_class,
       item_desc.sub_class
from item_desc
         join item_scan
              on item_desc.id = item_scan.item_id
where item_desc.name != '!奥术水晶'
order by scan_time desc;

-- queryItemDailyStats
SELECT daily_time_bucket, cast(daily_avg_market_value AS INTEGER)
FROM view_daily
WHERE item_id = '2592'
  AND daily_time_bucket > now() - INTERVAL '14 day'
ORDER BY daily_time_bucket DESC;

-- queryAllItemStats
WITH bounds AS (
    SELECT item_id                                                        id0,
           time_bucket('9973 day', daily_time_bucket)                       time_bucket0,
           (avg(daily_avg_market_value) - stddev(daily_avg_market_value)) low,
           (avg(daily_avg_market_value) + stddev(daily_avg_market_value)) high
    FROM view_daily
    WHERE daily_time_bucket > cast(now() AS date) - INTERVAL '14 day'
    GROUP BY id0, time_bucket0
)
SELECT id,
       name,
       vindex,
       greatest(round(
                        CASE WHEN vindex > 6 THEN
                                 least((((vindex * 18 + quantity * 7 + auctions * 5 + market * 8) * 0.07)) / 2 - 100, quantity*0.7)
                             ELSE quantity * 0.1
                            END
                    ), 1)AS max_stock,
       vendor_sell,
       market,
       marketd,
       auctions,
       quantity,
       item_lv,
       item_class,
       sub_class,
       time_buctet_14day
FROM (SELECT id,
             count(id),
             name,
             round(avg(daily_avg_market_value) /
                   (CASE WHEN (vendor_sell = 0) THEN greatest(item_lv * 5, 20)
                         ELSE vendor_sell + 15
                       END))    vindex,
             vendor_sell,
             round(avg(daily_avg_market_value))                                           market,
             round(stddev(daily_avg_market_value))                                        marketd,
             round(avg(daily_avg_num_auctions))                                           auctions,
             round(avg(daily_avg_quantity))                                               quantity,
             item_lv,
             item_class,
             sub_class,
             time_bucket('9973 day', daily_time_bucket)                                     time_buctet_14day
      FROM view_daily,
           bounds
               JOIN item_desc ON item_desc.id = id0
      WHERE daily_time_bucket > cast(now() AS date) - INTERVAL '14 day'
        AND item_id = id0
        AND time_bucket('9973 day', daily_time_bucket) = time_bucket0
        AND daily_avg_market_value BETWEEN low AND high
      GROUP BY id, name, vendor_sell, time_buctet_14day, item_lv, item_class, sub_class) AS a
WHERE 1 = 1
--   AND item_class IN ('材料', '消耗品', '杂项')
--   AND sub_class NOT IN ('食物和饮料', '!垃圾')
--   AND auctions > 10
--   AND quantity BETWEEN 20 AND 7000
--   AND vindex > 5
ORDER BY max_stock DESC;
