package com.code4info.stockservice;

import com.code4info.stockservice.model.Stock;
import com.code4info.stockservice.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
@EnableEurekaClient
public class StockServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(StockRepository repository){
        CommandLineRunner commandLineRunner = args -> {
            List<Stock> stocks = new ArrayList<>();

            stocks.add(Stock.builder().name("Tesla Incl").currentPrice(BigDecimal.valueOf(650.28)).build());
            stocks.add(Stock.builder().name("Apple Incl").currentPrice(BigDecimal.valueOf(130.56)).build());
            stocks.add(Stock.builder().name("Alphabet Inc Class A").currentPrice(BigDecimal.valueOf(2142.87)).build());
            stocks.add(Stock.builder().name("Amazon.com, Inc.").currentPrice(BigDecimal.valueOf(144.34)).build());
            stocks.add(Stock.builder().name("Spotify Technology SA").currentPrice(BigDecimal.valueOf(99.27)).build());

            repository.saveAll(stocks);
        };
        return commandLineRunner;
    }

}
