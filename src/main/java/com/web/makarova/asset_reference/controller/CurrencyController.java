package com.web.makarova.asset_reference.controller;

import com.web.makarova.asset_reference.entity.Currency;
import com.web.makarova.asset_reference.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset_reference/currencies")
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/{page}")
    public ResponseEntity<List<Currency>> getCurrencies(
            @PathVariable int page,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        List<Currency> currencies = currencyService.getCurrencies(page, sortBy, sortOrder, filteringField, filteringValue)
                .getContent();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/countPage")
    public ResponseEntity<Integer> getCountPageForCurrencies(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        int countPage = currencyService.getCurrencies(0, sortBy, sortOrder, filteringField, filteringValue)
                .getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Currency> createCurrency(@RequestParam String code, @RequestParam String name) {
        Currency newCurrency = currencyService.createCurrency(code, name);
        return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable Long id, @RequestParam String code,
                                                   @RequestParam String name) {
        Currency updatedCurrency = currencyService.updateCurrency(id, code, name);
        return updatedCurrency != null
                ? new ResponseEntity<>(updatedCurrency, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurrency(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
