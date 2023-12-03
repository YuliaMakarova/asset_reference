package com.web.makarova.asset_reference.repository;

import com.web.makarova.asset_reference.entity.Currency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPagingAndSortingRepository extends PagingAndSortingRepository<Currency, Long> {
    Page<Currency> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    Page<Currency> findByCodeContainingIgnoreCase(@Param("code") String code, Pageable pageable);
}
