package com.code4info.stockservice.repository;

import com.code4info.stockservice.model.Stock;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface StockRepository extends PagingAndSortingRepository<Stock,Long> {
}
