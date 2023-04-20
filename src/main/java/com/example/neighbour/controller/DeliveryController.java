package com.example.neighbour.controller;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.dto.delivery.DeliveryRequest;
import com.example.neighbour.dto.delivery.DeliveryResponse;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.service.DeliveryService;
import com.example.neighbour.service.UserService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(ApiConstants.API_VERSION_1 + "delivery")
public class DeliveryController {

    private final UserService userService;

    private final DeliveryService deliveryService;

    @PostMapping("/register")
    public ResponseDto<String> registerDeliveryUser(@RequestBody UserDto userDto) {
        log.info("Registering delivery user: {}", userDto.getEmail());
        return userService.addDeliveryUser(userDto);
    }


    @GetMapping("/toDeliver")
    public ResponseDto<List<DeliveryResponse>> toDeliver() {
        log.info("Getting deliveries to deliver");
        return deliveryService.assignedDeliveries();
    }

    @GetMapping("/delivered")
    public ResponseDto<List<DeliveryResponse>> delivered() {
        log.info("Getting deliveries delivered");
        return deliveryService.deliveredDeliveries();
    }


    @GetMapping("/notDelivered")
    public ResponseDto<List<DeliveryResponse>> notDelivered() {
        log.info("Getting deliveries not delivered");
        return deliveryService.notYetDeliveredDeliveries();
    }

    @GetMapping("/unassigned")
    public ResponseDto<List<DeliveryResponse>> unassigned() {
        log.info("Getting deliveries unassigned");
        return deliveryService.unassignedDeliveries();
    }

    @PostMapping("/assign")
    public ResponseDto<String> assignDelivery(@RequestBody DeliveryRequest request) {
        log.info("Assigning delivery to user: {}", request.emailOfDeliveryPerson());
        deliveryService.assignDeliveryPerson(request);
        return ResponseDto.success(null,  "Delivery assigned");
    }

    @PutMapping("/delivered/{orderNumber}")
    public ResponseDto<String> deliver(@PathVariable() int  orderNumber) {
        log.info("Delivering delivery: {}", orderNumber);
        deliveryService.updateDeliveryStatus(orderNumber, Delivery.DeliveryStatus.DELIVERED);
        return ResponseDto.success(null,  "Item delivered");
    }
}
