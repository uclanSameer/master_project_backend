package com.example.neighbour.dto.order;

import com.example.neighbour.data.OrderItem;
import com.example.neighbour.dto.MenuItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class OrderItemResponse {

    private MenuItemDto menuItem;

    private Integer quantity;

    private BigDecimal price;


    public static OrderItemResponse fromOrderItem(OrderItem orderItem) {
        return new OrderItemResponse(
                new MenuItemDto(orderItem.getItem()),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }
}
