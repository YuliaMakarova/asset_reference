package com.web.makarova.asset_reference.controller;

import com.web.makarova.asset_reference.entity.Asset;
import com.web.makarova.asset_reference.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset_reference/assets")
public class AssetController {

    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/{page}")
    public ResponseEntity<List<Asset>> getAllAssets(@PathVariable int page) {
        List<Asset> assets = assetService.getAllAssets(page);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/byName")
    public ResponseEntity<List<Asset>> getAssetsByName(@RequestParam String name,
                                                       @RequestParam(defaultValue = "0") int page) {
        List<Asset> assets = assetService.getAssetsByName(name, page);
        return new ResponseEntity<>(assets, HttpStatus.OK);

    }

    @GetMapping("/byIsin")
    public ResponseEntity<List<Asset>> getAssetsByIsin(@RequestParam String isin,
                                                       @RequestParam(defaultValue = "0") int page) {
        List<Asset> assets = assetService.getAssetsByIsin(isin, page);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/byBloombergTicker")
    public ResponseEntity<List<Asset>> getAssetsByBloombergTicker(@RequestParam String bloombergTicker,
                                                  @RequestParam(defaultValue = "0") int page) {
        List<Asset> assets = assetService.getAssetsBloombergTicker(bloombergTicker, page);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/byCurrencyName")
    public ResponseEntity<List<Asset>> getAssetsByCurrencyName(@RequestParam String currencyName,
                                               @RequestParam(defaultValue = "0") int page) {
        List<Asset> assets = assetService.getAssetsCurrencyName(currencyName, page);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/byExchangeName")
    public ResponseEntity<List<Asset>> getAssetsByExchangeName(@RequestParam String exchangeName,
                                               @RequestParam(defaultValue = "0") int page) {
        List<Asset> assets = assetService.getAssetsExchangeName(exchangeName, page);
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Asset> createAsset(@RequestParam Long exchangeId, @RequestParam Long currencyId,
                                             @RequestParam String isin, @RequestParam String bloombergTicker,
                                             @RequestParam String name) {
        Asset newAsset = assetService.createAsset(exchangeId, currencyId, isin, bloombergTicker, name);
        return new ResponseEntity<>(newAsset, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Asset> updateAsset(@PathVariable Long id, @RequestParam Long exchangeId,
                                             @RequestParam Long currencyId, @RequestParam String isin,
                                             @RequestParam String bloombergTicker, @RequestParam String name) {
        Asset updatedAsset = assetService.updateAsset(id, exchangeId, currencyId, isin, bloombergTicker, name);
        return updatedAsset != null
                ? new ResponseEntity<>(updatedAsset, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAsset(@PathVariable Long id) {
        assetService.deleteAsset(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
