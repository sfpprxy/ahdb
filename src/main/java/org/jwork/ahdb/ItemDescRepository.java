package org.jwork.ahdb;

import org.jwork.ahdb.model.ItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ItemDescRepository extends JpaRepository<ItemDesc, Timestamp> {
    List<ItemDesc> findAllById(String id);
}
