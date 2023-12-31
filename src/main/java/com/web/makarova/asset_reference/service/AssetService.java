package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Asset;
import com.web.makarova.asset_reference.repository.AssetCrudRepository;
import com.web.makarova.asset_reference.repository.AssetPagingAndSortingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AssetService {
    @Value("${page.size}")
    private int size;
    private final AssetCrudRepository assetCrudRepository;
    private final AssetPagingAndSortingRepository assetPagingAndSortingRepository;
    private final CurrencyService currencyService;
    private final ExchangeService exchangeService;

    public List<Asset> getAllAssets() {
        Iterable<Asset> assetsIterable = assetCrudRepository.findAll();
        return StreamSupport
                .stream(assetsIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Page<Asset> getAssets(int page, String sortBy, String sortOrder, String field, String value) {
        Pageable pageable;
        if (sortBy != null) {
            pageable = sortOrder != null && sortOrder.equalsIgnoreCase("desc") ?
                    PageRequest.of(page, size, Sort.by(sortBy).descending()) :
                    PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        if (field != null) {
            return switch (field.toLowerCase()) {
                case "name" -> assetPagingAndSortingRepository.findByNameContainingIgnoreCase(value,
                        PageRequest.of(page, size));
                case "isin" -> assetPagingAndSortingRepository.findByIsinContainingIgnoreCase(value,
                        PageRequest.of(page, size));
                case "bloombergticker" -> assetPagingAndSortingRepository.findByBloombergTickerContainingIgnoreCase(value,
                        PageRequest.of(page, size));
                case "currency.name" -> assetPagingAndSortingRepository.findByCurrency_NameContainingIgnoreCase(value,
                        PageRequest.of(page, size));
                case "exchange.name" -> assetPagingAndSortingRepository.findByExchange_NameContainingIgnoreCase(value,
                        PageRequest.of(page, size));
                default -> assetPagingAndSortingRepository.findAll(PageRequest.of(page, size));
            };
        } else {
            return assetPagingAndSortingRepository.findAll(pageable);
        }
    }

    public Asset createAsset(Long exchangeId, Long currencyId, String isin, String bloombergTicker, String name) {
        Asset asset = new Asset();
        asset.setExchange(exchangeService.getExchangeById(exchangeId));
        asset.setCurrency(currencyService.getCurrencyById(currencyId));
        asset.setIsin(isin);
        asset.setBloombergTicker(bloombergTicker);
        asset.setName(name);
        return assetCrudRepository.save(asset);
    }

    public Asset updateAsset(Long id, Long exchangeId, Long currencyId, String isin, String bloombergTicker, String name) {
        Optional<Asset> existingAsset = assetCrudRepository.findById(id);

        if (existingAsset.isPresent()) {
            Asset assetToUpdate = existingAsset.get();
            assetToUpdate.setExchange(exchangeService.getExchangeById(exchangeId));
            assetToUpdate.setCurrency(currencyService.getCurrencyById(currencyId));
            assetToUpdate.setIsin(isin);
            assetToUpdate.setBloombergTicker(bloombergTicker);
            assetToUpdate.setName(name);
            return assetCrudRepository.save(assetToUpdate);
        } else {
            return null;
        }
    }

    public void deleteAsset(Long id) {
        assetCrudRepository.deleteById(id);
    }
}
