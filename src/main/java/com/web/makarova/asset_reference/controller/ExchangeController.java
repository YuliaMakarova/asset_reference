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
    public ResponseEntity<List<Exchange>> getAllExchanges(@PathVariable int page) {
        List<Exchange> exchanges = exchangeService.getAllExchanges(page).getContent();
        return new ResponseEntity<>(exchanges, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Exchange>> getAllExchangesSorted(@RequestParam String sortBy, @RequestParam int page) {
        List<Exchange> exchanges = exchangeService.getAllExchangesSorted(sortBy, page).getContent();
        return new ResponseEntity<>(exchanges, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Exchange>> getExchangesByField(@RequestParam String field, @RequestParam String name,
                                                               @RequestParam(defaultValue = "0") int page) {
        List<Exchange> exchanges = exchangeService.getExchangesByField(field, name, page).getContent();
        return new ResponseEntity<>(exchanges, HttpStatus.OK);

    }

    @GetMapping("/countPage")
    public ResponseEntity<Integer> countPageForAllExchanges() {
        int countPage = exchangeService.getAllExchanges(0).getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
    }

    @GetMapping("/countPage/filter")
    public ResponseEntity<Integer> countPageForExchangesByField(@RequestParam String field,
                                                                @RequestParam String name) {
        int countPage = exchangeService.getExchangesByField(field, name, 0).getTotalPages();
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
