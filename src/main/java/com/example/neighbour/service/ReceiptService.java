package com.example.neighbour.service;

import com.example.neighbour.data.cart.CartItem;
import com.example.neighbour.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptService {

    private final EmailService  emailService;

    public void sendReceipt(List<CartItem> cartItems, String email) {
        var html = createHtml(cartItems);

        MessageDto messageDto = MessageDto.builder()
                .to(email)
                .subject("Order Receipt")
                .message(html)
                .isHtml(true)
                .build();

        emailService.sendEmail(messageDto);
    }


    //    create html for cartItems
    public String createHtml(List<CartItem> cartItems) {
        StringBuilder sb = new StringBuilder();

        sb.append("<h2>Order Receipt</h2>");
        sb.append("<table border='1'>");
        sb.append("<tr><th>Item</th><th>Quantity</th><th>Price</th></tr>");
        for (CartItem cartItem : cartItems) {
            sb.append("<tr>");
            sb.append("<td>").append(cartItem.getItem().getName()).append("</td>");
            sb.append("<td>").append(cartItem.getQuantity()).append("</td>");
            sb.append("<td>").append(cartItem.getItem().getPrice()).append("</td>");
            sb.append("</tr>");
        }

        var total = cartItems.stream()
                .mapToDouble(item -> item.getItem().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
                        .doubleValue()).sum();

        sb.append("<tr><td colspan='2'>Total: ").append(total).append("</td></tr>");
        sb.append("</table>");
        return sb.toString();
    }

}
