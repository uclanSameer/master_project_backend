package com.example.neighbour.data.cart;

import com.example.neighbour.data.MenuItem;
import com.example.neighbour.data.Order;
import com.example.neighbour.data.OrderItem;
import com.example.neighbour.dto.MenuItemDto;
import com.example.neighbour.dto.cart.CartItemDto;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private MenuItem item;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public CartItem() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public CartItem(Cart cart, MenuItem item, Integer quantity) {
        this.cart = cart;
        this.item = item;
        this.quantity = quantity;
    }

    public OrderItem toOrderItem(Order order) {
        return new OrderItem(
                item,
                order,
                quantity,
                item.getPrice(),
                item.getNutritionalInfo()
        );
    }

    @Override
    public String toString() {
        return "CartItem{" +
                ", cart=" + cart +
                ", item=" + item +
                ", quantity=" + quantity +
                '}';
    }

    public CartItemDto toDto() {
        return new CartItemDto(
                new MenuItemDto(item),
                quantity
        );
    }
}