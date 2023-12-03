package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Exchange;
import com.web.makarova.asset_reference.repository.ExchangeCrudRepository;
import com.web.makarova.asset_reference.repository.ExchangePagingAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExchangeService {
    @Value("${page.size}")
    private int size;
    private final ExchangeCrudRepository exchangeCrudRepository;
    private final ExchangePagingAndSortingRepository exchangePagingAndSortingRepository;

    @Autowired
    public ExchangeService(ExchangeCrudRepository exchangeCrudRepository,
                           ExchangePagingAndSortingRepository exchangePagingAndSortingRepository) {
        this.exchangeCrudRepository = exchangeCrudRepository;
        this.exchangePagingAndSortingRepository = exchangePagingAndSortingRepository;
    }

    public Page<Exchange> getAllExchanges(int page) {
        return exchangePagingAndSortingRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Exchange> getAllExchangesSorted(String sortBy, int page) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return exchangePagingAndSortingRepository.findAll(pageable);
    }

    public Page<Exchange> getExchangesByField(String field, String value, int page) {
        return switch (field.toLowerCase()) {
            case "name" -> exchangePagingAndSortingRepository.findByNameContainingIgnoreCase(value,
                    PageRequest.of(page, size));
            case "code" -> exchangePagingAndSortingRepository.findByCodeContainingIgnoreCase(value,
                    PageRequest.of(page, size));
            default -> exchangePagingAndSortingRepository.findAll(PageRequest.of(page, size));
        };
    }

    public Exchange getExchangeById(Long id) {
        Optional<Exchange> optionalExchange = exchangeCrudRepository.findById(id);
        return optionalExchange.orElse(null);
    }

    public Exchange createExchange(String name, String code) {
        Exchange exchange = new Exchange();
        exchange.setName(name);
        exchange.setCode(code);
        return exchangeCrudRepository.save(exchange);
    }

    public Exchange updateExchange(Long id, String name, String code) {
        Optional<Exchange> existingExchange = exchangeCrudRepository.findById(id);

        if (existingExchange.isPresent()) {
            Exchange exchangeToUpdate = existingExchange.get();
            exchangeToUpdate.setName(name);
            exchangeToUpdate.setCode(code);
            return exchangeCrudRepository.save(exchangeToUpdate);
        } else {
            return null;
        }
    }

    public void deleteExchange(Long id) {
        exchangeCrudRepository.deleteById(id);
    }
}
