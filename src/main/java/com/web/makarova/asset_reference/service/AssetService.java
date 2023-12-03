package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Asset;
import com.web.makarova.asset_reference.repository.AssetCrudRepository;
import com.web.makarova.asset_reference.repository.AssetPagingAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetService {
    @Value("${page.size}")
    private int size;
    private final AssetCrudRepository assetCrudRepository;
    private final AssetPagingAndSortingRepository assetPagingAndSortingRepository;
    private final CurrencyService currencyService;
    private final ExchangeService exchangeService;

    @Autowired
    public AssetService(AssetCrudRepository assetCrudRepository, CurrencyService currencyService,
                        ExchangeService exchangeService,
                        AssetPagingAndSortingRepository assetPagingAndSortingRepository) {
        this.assetCrudRepository = assetCrudRepository;
        this.assetPagingAndSortingRepository = assetPagingAndSortingRepository;
        this.exchangeService = exchangeService;
        this.currencyService = currencyService;
    }

    public Page<Asset> getAllAssets(int page) {
        return assetPagingAndSortingRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Asset> getAllAssetsSorted(String sortBy, int page) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return assetPagingAndSortingRepository.findAll(pageable);
    }

    public Page<Asset> getAssetsByField(String field, String value, int page) {
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
