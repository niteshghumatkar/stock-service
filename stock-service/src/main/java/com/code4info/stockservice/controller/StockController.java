package com.code4info.stockservice.controller;


import com.code4info.stockservice.service.StockService;
import com.code4info.stockservice.dto.StockRequest;
import com.code4info.stockservice.dto.StockResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class StockController {

    private final StockService stockService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createStock(@RequestBody StockRequest stockRequest){
        stockService.createStock(stockRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> getAllStocks(@RequestParam(required = false)Integer page, @RequestParam(required = false) Integer size) {
            List<StockResponse> stockResponses=stockService.getAllStocks(page,size);
        return stockResponses;
    }

    @GetMapping("/{id}")
    public StockResponse getStock(@PathVariable Long id){
        StockResponse stockResponse = stockService.getStock(id);
        return stockResponse;
    }

    @PatchMapping("/{id}")
    public void patchStock(@PathVariable Long id, @RequestBody Map<String,String> stockFields){
        stockService.patchStock(id,stockFields);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStock(@PathVariable Long id){
        stockService.deleteStock(id);
    }

}
