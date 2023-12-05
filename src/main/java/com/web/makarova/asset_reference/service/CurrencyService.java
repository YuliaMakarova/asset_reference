package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Currency;
import com.web.makarova.asset_reference.repository.CurrencyCrudRepository;
import com.web.makarova.asset_reference.repository.CurrencyPagingAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurrencyService {
    @Value("${page.size}")
    private int size;
    private final CurrencyCrudRepository currencyCrudRepository;
    private final CurrencyPagingAndSortingRepository currencyPagingAndSortingRepository;

    @Autowired
    public CurrencyService(CurrencyCrudRepository currencyCrudRepository,
                           CurrencyPagingAndSortingRepository currencyPagingAndSortingRepository) {
        this.currencyCrudRepository = currencyCrudRepository;
        this.currencyPagingAndSortingRepository = currencyPagingAndSortingRepository;
    }

    public Page<Currency> getCurrencies(int page, String sortBy, String sortOrder, String field, String value) {
        Pageable pageable;
        if (sortBy != null) {
            pageable = sortOrder != null && sortOrder.equalsIgnoreCase("desc") ?
                    PageRequest.of(page, size, Sort.by(sortBy).descending()) :
                    PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size);
        }
        if (field != null)
            return switch (field.toLowerCase()) {
                case "name" -> currencyPagingAndSortingRepository.findByNameContainingIgnoreCase(value,
                        pageable);
                case "code" -> currencyPagingAndSortingRepository.findByCodeContainingIgnoreCase(value,
                        pageable);
                default -> currencyPagingAndSortingRepository.findAll(pageable);
            };
        else
            return currencyPagingAndSortingRepository.findAll(pageable);
    }

    public Currency getCurrencyById(Long id) {
        Optional<Currency> optionalCurrency = currencyCrudRepository.findById(id);
        return optionalCurrency.orElse(null);
    }

    public Currency createCurrency(String code, String name) {
        Currency currency = new Currency();
        currency.setCode(code);
        currency.setName(name);
        return currencyCrudRepository.save(currency);
    }

    public Currency updateCurrency(Long id, String code, String name) {
        Optional<Currency> existingCurrency = currencyCrudRepository.findById(id);

        if (existingCurrency.isPresent()) {
            Currency currencyToUpdate = existingCurrency.get();
            currencyToUpdate.setCode(code);
            currencyToUpdate.setName(name);
            return currencyCrudRepository.save(currencyToUpdate);
        } else {
            return null;
        }
    }

    public void deleteCurrency(Long id) {
        currencyCrudRepository.deleteById(id);
    }
}

