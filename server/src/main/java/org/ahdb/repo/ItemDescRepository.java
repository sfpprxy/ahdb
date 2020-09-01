package org.ahdb.repo;

import org.ahdb.model.ItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDescRepository extends JpaRepository<ItemDesc, String> {

    @Query("SELECT id FROM ItemDesc")
    List<Object> findIdsByIdNotNull();

    List<ItemDesc> findTop10ByIdOrNameContains(String id, String name);

    List<ItemDesc> findByIdOrName(String id, String name);

}
