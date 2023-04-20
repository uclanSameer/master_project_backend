package com.example.neighbour.service;

import com.example.neighbour.data.Delivery;
import com.example.neighbour.data.Order;
import com.example.neighbour.dto.delivery.DeliveryRequest;
import com.example.neighbour.dto.delivery.DeliveryResponse;
import com.example.neighbour.dto.ResponseDto;

import java.util.List;

public interface DeliveryService {


    void createDelivery(Order order, String formattedAddress);

    ResponseDto<List<DeliveryResponse>> unassignedDeliveries();

    ResponseDto<List<DeliveryResponse>> notYetDeliveredDeliveries();

    ResponseDto<List<DeliveryResponse>> assignedDeliveries();

    ResponseDto<List<DeliveryResponse>> deliveredDeliveries();

    void updateDeliveryStatus(int orderId, Delivery.DeliveryStatus status);

    void assignDeliveryPerson(DeliveryRequest deliveryRequest);

}
