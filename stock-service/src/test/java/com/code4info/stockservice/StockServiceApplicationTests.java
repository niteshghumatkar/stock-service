package com.code4info.stockservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.code4info.stockservice.dto.StockRequest;
import com.code4info.stockservice.model.Stock;
import com.code4info.stockservice.repository.StockRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = StockServiceApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class StockServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    StockRepository stockRepository;

    @Test
    void testGetStock() throws Exception {
        mockMvc.perform(get("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("Tesla Incl")))
                .andExpect(jsonPath("$[3].name", CoreMatchers.is("Amazon.com, Inc.")));
    }

    @Test
    void testCreateStock() throws Exception {
        StockRequest stockRequest=getStockRequest();
        String stockRequestString=objectMapper.writeValueAsString(stockRequest);

        mockMvc.perform(post("/api/stocks").contentType(MediaType.APPLICATION_JSON)
        .content(stockRequestString)).andExpect(status().isCreated());

        Iterable<Stock> stocks=stockRepository.findAll();
        assertThat(stocks).extracting(Stock::getName).contains("Adobe Inc");
    }

    @Test
    void patchStock() throws Exception{
        Map<String,String> updateFields=new HashMap<>();
        updateFields.put("currentPrice","130");
        String updateFieldString=objectMapper.writeValueAsString(updateFields);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/stocks/4").contentType(MediaType.APPLICATION_JSON)
        .content(updateFieldString)).andExpect(status().isOk());

        mockMvc.perform(get("/api/stocks/4")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPrice", CoreMatchers.is(130.0)));
    }

    @Test
    void testStockNotFoundException() throws Exception{
        mockMvc.perform(get("/api/stocks/15")
                .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message",CoreMatchers.is("Stock Not Found")));
    }

    StockRequest getStockRequest(){
        return StockRequest.builder().name("Adobe Inc").currentPrice(BigDecimal.valueOf(670)).build();
    }

}
