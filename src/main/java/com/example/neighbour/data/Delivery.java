package com.example.neighbour.data;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "delivery")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @ToString.Exclude
    private Order order;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "delivery_person", nullable = false)
    private String deliveryPerson;

    @Column(name = "status", nullable = false)
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Delivery delivery = (Delivery) o;
        return id != null && Objects.equals(id, delivery.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}