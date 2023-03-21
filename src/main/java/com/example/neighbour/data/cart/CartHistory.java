package com.example.neighbour.data.cart;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cart_history")
public class CartHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "items_added", nullable = false)
    private String itemsAdded;

    @Column(name = "items_removed", nullable = false)
    private String itemsRemoved;

    @Column(name = "changes_time", nullable = false)
    private Instant changesTime;

    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost;

}