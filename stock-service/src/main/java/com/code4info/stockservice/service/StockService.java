package com.code4info.stockservice.service;

import com.code4info.stockservice.dto.StockRequest;
import com.code4info.stockservice.dto.StockResponse;
import com.code4info.stockservice.exception.StockNotFoundException;
import com.code4info.stockservice.mapper.StockMapper;
import com.code4info.stockservice.model.Stock;
import com.code4info.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private StockMapper stockMapper= Mappers.getMapper(StockMapper.class);


    public void createStock(StockRequest stockRequest) {
        Stock stock=stockMapper.mapDTOToEntity(stockRequest);
        stockRepository.save(stock);
        log.info("Stock {} is saved",stock.getId());
    }

    public List<StockResponse> getAllStocks(Integer page,Integer size) {
        Iterable<Stock> stocks=null;
        if(page!=null && size!=null){
            Pageable pageable= PageRequest.of(page,size);
            stocks=stockRepository.findAll(pageable);
        }else{
            stocks=stockRepository.findAll();
        }
        List<StockResponse> stockResponses=stockMapper.mapIterableToDto(stocks);
       return stockResponses;
    }

    @SneakyThrows
    public void patchStock(Long id, Map<String,String> stockFields) {
        Optional<Stock> stock=stockRepository.findById(id);
        if(stock.isPresent()){
            stockFields.forEach((key,value)-> {
                Field field=ReflectionUtils.findField(Stock.class,key);
                field.setAccessible(true);
                ReflectionUtils.setField(field,stock.get(), new BigDecimal(value));
            });
            stockRepository.save(stock.get());
            log.info("Stock {} is updted",stock.get().getId());
        }else{
            throw new StockNotFoundException("Stock With Id "+id+" Not Found");
        }
    }

    @SneakyThrows
    public StockResponse getStock(Long id){
        Optional<Stock> stock=stockRepository.findById(id);
        if(stock.isEmpty()){
            throw new StockNotFoundException("Stock Not Found");
        }
        StockResponse stockResponse=stock.isPresent()?stockMapper.mapEntityToDTO(stock.get()):null;
        return stockResponse;
    }

    @SneakyThrows
    public void deleteStock(Long id) {
        try {
            stockRepository.deleteById(id);
            log.info("Stock {} is deleted",id);
        }catch(Exception ex){
            throw new StockNotFoundException("Stock With Id "+id+" Not Found");
        }
    }
}
