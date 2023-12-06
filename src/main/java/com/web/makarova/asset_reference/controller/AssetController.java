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

    @GetMapping("/list")
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/{page}")
    public ResponseEntity<List<Asset>> getAssets(
            @PathVariable int page,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        List<Asset> assets = assetService.getAssets(page, sortBy, sortOrder, filteringField, filteringValue)
                .getContent();
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }

    @GetMapping("/countPage")
    public ResponseEntity<Integer> getCountPageForAssets(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false) String filteringField,
            @RequestParam(required = false) String filteringValue) {
        int countPage = assetService.getAssets(0, sortBy, sortOrder, filteringField, filteringValue)
                .getTotalPages();
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
