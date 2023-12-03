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
    public ResponseEntity<List<Currency>> getAllCurrencies(@PathVariable int page) {
        List<Currency> currencies = currencyService.getAllCurrencies(page);
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Currency>> getCurrenciesByName(@RequestParam String name,
                                                             @RequestParam(defaultValue = "0") int page) {
        List<Currency> currencies = currencyService.getCurrenciesByName(name, page);
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/byCode")
    public ResponseEntity<List<Currency>> getCurrenciesByCode(@RequestParam String code,
                                                             @RequestParam(defaultValue = "0") int page) {
        List<Currency> currencies = currencyService.getCurrenciesByCode(code, page);
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Currency> createCurrency(@RequestParam String code, @RequestParam String name) {
        Currency newCurrency = currencyService.createCurrency(code, name);
        return new ResponseEntity<>(newCurrency, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> updateCurrency(@PathVariable Long id, @RequestParam String code, @RequestParam String name) {
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
