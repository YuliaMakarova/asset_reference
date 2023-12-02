package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Currency;
import com.web.makarova.asset_reference.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public List<Currency> getAllCurrencies() {
        Iterable<Currency> currenciesIterable = currencyRepository.findAll();
        return StreamSupport
                .stream(currenciesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Currency getCurrencyById(Long id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        return optionalCurrency.orElse(null);
    }

    public Currency createCurrency(String code, String name) {
        Currency currency = new Currency();
        currency.setCode(code);
        currency.setName(name);
        return currencyRepository.save(currency);
    }

    public Currency updateCurrency(Long id, String code, String name) {
        Optional<Currency> existingCurrency = currencyRepository.findById(id);

        if (existingCurrency.isPresent()) {
            Currency currencyToUpdate = existingCurrency.get();
            currencyToUpdate.setCode(code);
            currencyToUpdate.setName(name);
            return currencyRepository.save(currencyToUpdate);
        } else {
            return null;
        }
    }

    public void deleteCurrency(Long id) {
        currencyRepository.deleteById(id);
    }
}

