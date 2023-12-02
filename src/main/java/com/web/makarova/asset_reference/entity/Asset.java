package com.web.makarova.asset_reference.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "asset")
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exchange_id", nullable = false)
    private Exchange exchange;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(nullable = false, length = 20)
    private String isin;

    @Column(name = "bloomberg_ticker", nullable = false, length = 20)
    private String bloombergTicker;

    @Column(nullable = false)
    private String name;

}
