package com.web.makarova.asset_reference.repository;

import com.web.makarova.asset_reference.entity.Asset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetPagingAndSortingRepository extends PagingAndSortingRepository<Asset, Long> {
    Page<Asset> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    Page<Asset> findByIsinContainingIgnoreCase(@Param("isin") String isin, Pageable pageable);

    Page<Asset> findByBloombergTickerContainingIgnoreCase(@Param("bloombergTicker") String bloombergTicker,
                                                          Pageable pageable);

    Page<Asset> findByCurrency_NameContainingIgnoreCase(@Param("currencyName") String currencyName, Pageable pageable);

    Page<Asset> findByExchange_NameContainingIgnoreCase(@Param("exchangeName") String exchangeName, Pageable pageable);
}
