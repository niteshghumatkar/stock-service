package com.code4info.stockservice.mapper;

import com.code4info.stockservice.dto.StockRequest;
import com.code4info.stockservice.dto.StockResponse;
import com.code4info.stockservice.model.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface StockMapper {
    Stock mapDTOToEntity(StockRequest stockRequest);
    StockResponse mapEntityToDTO(Stock stock);
    List<StockResponse> mapIterableToDto(Iterable<Stock> stockList);
}
