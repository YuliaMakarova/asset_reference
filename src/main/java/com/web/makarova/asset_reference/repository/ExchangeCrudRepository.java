package com.web.makarova.asset_reference.repository;

import com.web.makarova.asset_reference.entity.Exchange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeCrudRepository extends CrudRepository<Exchange, Long> {
}
