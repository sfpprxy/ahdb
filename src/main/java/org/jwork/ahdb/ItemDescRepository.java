package org.jwork.ahdb;

import org.jwork.ahdb.model.ItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDescRepository extends JpaRepository<ItemDesc, String> {

    @Query(value = "select id from item_desc", nativeQuery = true)
    List<String> findAllItemId();
}
