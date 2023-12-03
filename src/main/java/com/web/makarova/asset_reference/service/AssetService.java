package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Asset;
import com.web.makarova.asset_reference.repository.AssetCrudRepository;
import com.web.makarova.asset_reference.repository.AssetPagingAndSortingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Asset> getAllAssets(int page) {
        Page<Asset> assetPage = assetPagingAndSortingRepository.findAll(PageRequest.of(page, size));
        return assetPage.getContent();
    }

    public List<Asset> getAssetsByName(String name, int page) {
        Page<Asset> assetPage = assetPagingAndSortingRepository.findByNameContainingIgnoreCase(name,
                PageRequest.of(page, size));
        return assetPage.getContent();
    }

    public List<Asset> getAssetsByIsin(String isin, int page) {
        Page<Asset> assetPage = assetPagingAndSortingRepository.findByIsinContainingIgnoreCase(isin,
                PageRequest.of(page, size));
        return assetPage.getContent();
    }

    public List<Asset> getAssetsBloombergTicker(String bloombergTicker, int page) {
        Page<Asset> assetPage = assetPagingAndSortingRepository.
                findByBloombergTickerContainingIgnoreCase(bloombergTicker, PageRequest.of(page, size));
        return assetPage.getContent();
    }

    public List<Asset> getAssetsCurrencyName(String currencyName, int page) {
        Page<Asset> assetPage = assetPagingAndSortingRepository.findByCurrency_NameContainingIgnoreCase(currencyName,
                PageRequest.of(page, size));
        return assetPage.getContent();
    }

    public List<Asset> getAssetsExchangeName(String exchangeName, int page) {
        Page<Asset> assetPage = assetPagingAndSortingRepository.findByExchange_NameContainingIgnoreCase(exchangeName,
                PageRequest.of(page, size));
        return assetPage.getContent();
    }

    public Asset getAssetById(Long id) {
        Optional<Asset> optionalAsset = assetCrudRepository.findById(id);
        return optionalAsset.orElse(null);
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
