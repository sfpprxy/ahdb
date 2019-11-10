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
           time_bucket('31 day', daily_time_bucket)                       time_bucket0,
           (avg(daily_avg_market_value) - stddev(daily_avg_market_value)) low,
           (avg(daily_avg_market_value) + stddev(daily_avg_market_value)) high
    FROM view_daily
    WHERE daily_time_bucket > cast(now() AS date) - INTERVAL '14 day'
    GROUP BY id0, time_bucket0
)
SELECT id,
       name,
       vindex,
       round(vindex * quantity * 0.05) AS max_stock,
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
                   (CASE vendor_sell WHEN 0 THEN item_lv + 1000 ELSE vendor_sell END))    vindex,
             vendor_sell,
             round(avg(daily_avg_market_value))                                           market,
             round(stddev(daily_avg_market_value))                                        marketd,
             round(avg(daily_avg_num_auctions))                                           auctions,
             round(avg(daily_avg_quantity))                                               quantity,
             item_lv,
             item_class,
             sub_class,
             time_bucket('31 day', daily_time_bucket)                                     time_buctet_14day
      FROM view_daily,
           bounds
               JOIN item_desc ON item_desc.id = id0
      WHERE daily_time_bucket > cast(now() AS date) - INTERVAL '14 day'
        AND item_id = id0
        AND time_bucket('31 day', daily_time_bucket) = time_bucket0
        AND daily_avg_market_value BETWEEN low AND high
      GROUP BY id, name, vendor_sell, time_buctet_14day, item_lv, item_class, sub_class) AS a
WHERE 1 = 1
--   AND item_class IN ('材料', '消耗品', '杂项')
--   AND sub_class NOT IN ('食物和饮料', '肉类')
--   --AND sub_class IN ('垃圾')
--   --AND name IN ('轻羽毛','皇血草','银矿石','毛料','铁矿石','坚固的石头','粗糙的石头','铜矿石','火焰精华')
--   AND market NOTNULL
--   AND market > 400
--   AND vendor_sell NOT BETWEEN 1 AND 3
--   AND quantity > 100
--   AND vindex > 4
ORDER BY vindex DESC;
