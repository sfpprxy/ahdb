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

select count(scan_time)
from item_desc
         join item_scan
              on item_desc.id = item_scan.item_id
where item_desc.name != '!奥术水晶';
