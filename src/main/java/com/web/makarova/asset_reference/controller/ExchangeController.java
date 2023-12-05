package com.web.makarova.asset_reference.controller;

import com.web.makarova.asset_reference.entity.Exchange;
import com.web.makarova.asset_reference.service.ExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset_reference/exchanges")
public class ExchangeController {

    private final ExchangeService exchangeService;

    @Autowired
    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{page}")
    public ResponseEntity<List<Exchange>> getExchanges(
            @PathVariable int page,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        List<Exchange> exchanges = exchangeService.getExchanges(page, sortBy, sortOrder, filteringField, filteringValue)
                .getContent();
        return new ResponseEntity<>(exchanges, HttpStatus.OK);
    }

    @GetMapping("/countPage")
    public ResponseEntity<Integer> getCountPageForExchanges(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        int countPage = exchangeService.getExchanges(0, sortBy, sortOrder, filteringField, filteringValue)
                .getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Exchange> createExchange(@RequestParam String name, @RequestParam String code) {
        Exchange newExchange = exchangeService.createExchange(name, code);
        return new ResponseEntity<>(newExchange, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exchange> updateExchange(@PathVariable Long id, @RequestParam String name, @RequestParam String code) {
        Exchange updatedExchange = exchangeService.updateExchange(id, name, code);
        return updatedExchange != null
                ? new ResponseEntity<>(updatedExchange, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExchange(@PathVariable Long id) {
        exchangeService.deleteExchange(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
