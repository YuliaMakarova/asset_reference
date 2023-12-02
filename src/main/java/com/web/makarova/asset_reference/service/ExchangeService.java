package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Exchange;
import com.web.makarova.asset_reference.repository.ExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ExchangeService {

    private final ExchangeRepository exchangeRepository;

    @Autowired
    public ExchangeService(ExchangeRepository exchangeRepository) {
        this.exchangeRepository = exchangeRepository;
    }

    public List<Exchange> getAllExchanges() {
        Iterable<Exchange> exchangesIterable = exchangeRepository.findAll();
        return StreamSupport
                .stream(exchangesIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Exchange getExchangeById(Long id) {
        Optional<Exchange> optionalExchange = exchangeRepository.findById(id);
        return optionalExchange.orElse(null);
    }

    public Exchange createExchange(String name, String code) {
        Exchange exchange = new Exchange();
        exchange.setName(name);
        exchange.setCode(code);
        return exchangeRepository.save(exchange);
    }

    public Exchange updateExchange(Long id, String name, String code) {
        Optional<Exchange> existingExchange = exchangeRepository.findById(id);

        if (existingExchange.isPresent()) {
            Exchange exchangeToUpdate = existingExchange.get();
            exchangeToUpdate.setName(name);
            exchangeToUpdate.setCode(code);
            return exchangeRepository.save(exchangeToUpdate);
        } else {
            return null;
        }
    }

    public void deleteExchange(Long id) {
        exchangeRepository.deleteById(id);
    }
}
