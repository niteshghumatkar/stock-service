package com.code4info.stockservice.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Table(name="stocks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @Column(name = "currentprice")
    BigDecimal currentPrice;

}
