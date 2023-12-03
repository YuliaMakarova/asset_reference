package com.web.makarova.asset_reference.repository;

import com.web.makarova.asset_reference.entity.Asset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetCrudRepository extends CrudRepository<Asset, Long> {
}
