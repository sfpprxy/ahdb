package org.jwork.ahdb;

import org.jwork.ahdb.model.ItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViewRepository extends JpaRepository<ItemDesc, String> {

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

    @Query(value = "select count(*)\n" +
            "from item_desc\n" +
            "         join item_scan\n" +
            "              on item_desc.id = item_scan.item_id\n" +
            "where item_desc.name != '!奥术水晶'\n"
    ,nativeQuery = true)
    long countNum();
}
