package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Exchange;
import com.web.makarova.asset_reference.repository.ExchangeCrudRepository;
import com.web.makarova.asset_reference.repository.ExchangePagingAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Exchange> getAllExchanges(int page) {
        Page<Exchange> exchangePage = exchangePagingAndSortingRepository.findAll(PageRequest.of(page, size));
        return exchangePage.getContent();
    }

    public List<Exchange> getExchangesByName(String name, int page) {
        Page<Exchange> exchangePage = exchangePagingAndSortingRepository.findByNameContainingIgnoreCase(name,
                PageRequest.of(page, size));
        return exchangePage.getContent();
    }

    public List<Exchange> getExchangesByCode(String code, int page) {
        Page<Exchange> exchangePage = exchangePagingAndSortingRepository.findByCodeContainingIgnoreCase(code,
                PageRequest.of(page, size));
        return exchangePage.getContent();
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
