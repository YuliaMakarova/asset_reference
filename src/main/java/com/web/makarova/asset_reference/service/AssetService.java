package com.web.makarova.asset_reference.service;

import com.web.makarova.asset_reference.entity.Asset;
import com.web.makarova.asset_reference.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final CurrencyService currencyService;
    private final ExchangeService exchangeService;

    @Autowired
    public AssetService(AssetRepository assetRepository, CurrencyService currencyService,
                        ExchangeService exchangeService) {
        this.assetRepository = assetRepository;
        this.exchangeService = exchangeService;
        this.currencyService = currencyService;
    }

    public List<Asset> getAllAssets() {
        Iterable<Asset> assetsIterable = assetRepository.findAll();
        return StreamSupport
                .stream(assetsIterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    public Asset getAssetById(Long id) {
        Optional<Asset> optionalAsset = assetRepository.findById(id);
        return optionalAsset.orElse(null);
    }

    public Asset createAsset(Long exchangeId, Long currencyId, String isin, String bloombergTicker, String name) {
        Asset asset = new Asset();
        asset.setExchange(exchangeService.getExchangeById(exchangeId));
        asset.setCurrency(currencyService.getCurrencyById(currencyId));
        asset.setIsin(isin);
        asset.setBloombergTicker(bloombergTicker);
        asset.setName(name);
        return assetRepository.save(asset);
    }

    public Asset updateAsset(Long id, Long exchangeId, Long currencyId, String isin, String bloombergTicker, String name) {
        Optional<Asset> existingAsset = assetRepository.findById(id);

        if (existingAsset.isPresent()) {
            Asset assetToUpdate = existingAsset.get();
            assetToUpdate.setExchange(exchangeService.getExchangeById(exchangeId));
            assetToUpdate.setCurrency(currencyService.getCurrencyById(currencyId));
            assetToUpdate.setIsin(isin);
            assetToUpdate.setBloombergTicker(bloombergTicker);
            assetToUpdate.setName(name);
            return assetRepository.save(assetToUpdate);
        } else {
            return null;
        }
    }

    public void deleteAsset(Long id) {
        assetRepository.deleteById(id);
    }
}
