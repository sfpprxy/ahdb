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


SELECT daily_time_bucket, CAST(daily_avg_market_value AS INTEGER)
FROM view_daily
WHERE item_id = '2592'
  AND daily_time_bucket > now() - INTERVAL '14 day'
ORDER BY daily_time_bucket DESC;
