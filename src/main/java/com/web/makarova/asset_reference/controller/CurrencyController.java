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
        List<Currency> currencies = currencyService.getAllCurrencies(page).getContent();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Currency>> getAllCurrenciesSorted(@RequestParam String sortBy, @RequestParam int page) {
        List<Currency> currencies = currencyService.getAllCurrenciesSorted(sortBy, page).getContent();
        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Currency>> getCurrenciesByField(@RequestParam String field, @RequestParam String name,
                                                       @RequestParam(defaultValue = "0") int page) {
        List<Currency> currencies = currencyService.getCurrenciesByField(field, name, page).getContent();
        return new ResponseEntity<>(currencies, HttpStatus.OK);

    }

    @GetMapping("/countPage")
    public ResponseEntity<Integer> countPageForAllCurrencies() {
        int countPage = currencyService.getAllCurrencies(0).getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
    }

    @GetMapping("/countPage/filter")
    public ResponseEntity<Integer> countPageForCurrenciesByField(@RequestParam String field,
                                                                 @RequestParam String name) {
        int countPage = currencyService.getCurrenciesByField(field, name, 0).getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
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
