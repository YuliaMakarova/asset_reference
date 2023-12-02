package com.web.makarova.asset_reference.repository;

import com.web.makarova.asset_reference.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {
}
