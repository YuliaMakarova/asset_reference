package com.web.makarova.asset_reference.repository;

import com.web.makarova.asset_reference.entity.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangePagingAndSortingRepository extends PagingAndSortingRepository<Exchange, Long> {
    Page<Exchange> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    Page<Exchange> findByCodeContainingIgnoreCase(@Param("code") String code, Pageable pageable);
}
