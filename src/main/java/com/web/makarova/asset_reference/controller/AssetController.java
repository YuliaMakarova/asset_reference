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
        List<Asset> assets = assetService.getAllAssets(page).getContent();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Asset>> getAllAssetsSorted(@RequestParam String sortBy, @RequestParam int page) {
        List<Asset> assets = assetService.getAllAssetsSorted(sortBy, page).getContent();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Asset>> getAssetsByField(@RequestParam String field, @RequestParam String name,
                                                        @RequestParam(defaultValue = "0") int page) {
        List<Asset> assets = assetService.getAssetsByField(field, name, page).getContent();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/countPage")
    public ResponseEntity<Integer> countPageForAllAssets() {
        int countPage = assetService.getAllAssets(0).getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
    }

    @GetMapping("/countPage/filter")
    public ResponseEntity<Integer> countPageForAssetsByField(@RequestParam String field, @RequestParam String name) {
        int countPage = assetService.getAssetsByField(field, name, 0).getTotalPages();
        return new ResponseEntity<>(countPage, HttpStatus.OK);
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
