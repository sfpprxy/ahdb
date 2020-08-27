package org.ahdb.repo;

import org.ahdb.model.IdsOnly;
import org.ahdb.model.ItemDesc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDescRepository extends JpaRepository<ItemDesc, String> {

    List<IdsOnly> findIdsByIdNotNull();

    List<ItemDesc> findTop10ByIdOrNameContains(String id, String name);

    List<ItemDesc> findByIdOrName(String id, String name);

}
