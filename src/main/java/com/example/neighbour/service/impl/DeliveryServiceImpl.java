package com.example.neighbour.service.impl;

import com.example.neighbour.configuration.security.permissions.ROLE_ADMIN;
import com.example.neighbour.configuration.security.permissions.ROLE_DELIVERY;
import com.example.neighbour.configuration.security.permissions.ROLE_DELIVERY_OR_ADMIN;
import com.example.neighbour.data.Delivery;
import com.example.neighbour.data.Order;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.delivery.DeliveryRequest;
import com.example.neighbour.dto.delivery.DeliveryResponse;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.repositories.DeliveryRepository;
import com.example.neighbour.service.DeliveryService;
import com.example.neighbour.service.UserRetrievalService;
import com.example.neighbour.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final UserRetrievalService userService;

    public void createDelivery(Order order, String formattedAddress) {
        try {
            var delivery = new Delivery();
            delivery.setOrder(order);
            delivery.setStatus(Delivery.DeliveryStatus.PENDING);
            delivery.setDeliveryPerson(null);
            delivery.setAddress(formattedAddress);

            deliveryRepository.save(delivery);
            log.info("Delivery created for order: {}", order.getId());
        } catch (Exception e) {
            log.error("Error creating delivery for order: {}", order.getId(), e);
            throw new ErrorResponseException(500, "Error creating delivery");
        }
    }

    @Override
    @ROLE_ADMIN
    public ResponseDto<List<DeliveryResponse>> unassignedDeliveries() {
        try {
            List<DeliveryResponse> deliveryResponses = mapListOfDeliveriesToDto(deliveryRepository.findAllByDeliveryPersonNull());
            log.info("Unassigned deliveries: {}", deliveryResponses.size());
            return new ResponseDto<>(deliveryResponses);
        } catch (Exception e) {
            log.error("Error unassigned deliveries", e);
            throw new ErrorResponseException(500, "Error unassigned deliveries");
        }
    }

    @ROLE_DELIVERY
    public ResponseDto<List<DeliveryResponse>> assignedDeliveries() {
        User user = UserUtils.getAuthenticatedUser();
        try {
            List<Delivery> deliveries = deliveryRepository.findAllByDeliveryPerson(user);
            List<DeliveryResponse> deliveryResponses = mapListOfDeliveriesToDto(deliveries);
            log.info("Assigned deliveries: {}", deliveryResponses.size());
            return new ResponseDto<>(deliveryResponses);
        } catch (Exception e) {
            log.error("Error getting assigned deliveries", e);
            throw new ErrorResponseException(500, "Error getting assigned deliveries");
        }
    }

    @Override
    @ROLE_ADMIN
    public ResponseDto<List<DeliveryResponse>> notYetDeliveredDeliveries() {
        try {
            List<Delivery> allByStatus = deliveryRepository.findAllByStatus(Delivery.DeliveryStatus.PENDING);
            List<DeliveryResponse> deliveryResponses = mapListOfDeliveriesToDto(allByStatus);
            log.info("Not yet delivered deliveries: {}", deliveryResponses.size());
            return new ResponseDto<>(deliveryResponses);
        } catch (Exception e) {
            log.error("Error not yet delivered deliveries", e);
            throw new ErrorResponseException(500, "Error not yet delivered deliveries");
        }
    }

    @Override
    @ROLE_ADMIN
    public ResponseDto<List<DeliveryResponse>> deliveredDeliveries() {
        try {
            List<Delivery> allByStatus = deliveryRepository.findAllByStatus(Delivery.DeliveryStatus.DELIVERED);
            List<DeliveryResponse> deliveryResponses = mapListOfDeliveriesToDto(allByStatus);
            log.info("Not yet delivered deliveries: {}", deliveryResponses.size());
            return new ResponseDto<>(deliveryResponses);
        } catch (Exception e) {
            log.error("Error occurred while getting delivered deliveries", e);
            throw new ErrorResponseException(500, "Error not yet delivered deliveries");
        }
    }

    @Override
    @ROLE_DELIVERY
    public void updateDeliveryStatus(int orderId, Delivery.DeliveryStatus status) {
        try {
            var delivery = deliveryRepository.findByOrderId(orderId)
                    .orElseThrow(() -> new ErrorResponseException(404, "Delivery not found"));
            delivery.setStatus(status);
            deliveryRepository.save(delivery);
            log.info("Delivery status updated to: {}", status);
        } catch (Exception e) {
            log.error("Error updating delivery status", e);
            throw new ErrorResponseException(500, "Error updating delivery status");
        }
    }

    @Override
    @ROLE_ADMIN
    public void assignDeliveryPerson(DeliveryRequest deliveryRequest) {
        try {
            var deliveryPerson = userService.getUserByEmail(deliveryRequest.emailOfDeliveryPerson());

            var delivery = deliveryRepository.findByOrderId(deliveryRequest.orderNumber())
                    .orElseThrow(() -> new ErrorResponseException(404, "Delivery not found"));
            delivery.setDeliveryPerson(deliveryPerson);
            deliveryRepository.save(delivery);
            log.info("Delivery assigned to: {}", deliveryPerson);
        } catch (Exception e) {
            log.error("Error assigning delivery person", e);
            throw new ErrorResponseException(500, "Error assigning delivery person");
        }
    }

    @NotNull
    private static List<DeliveryResponse> mapListOfDeliveriesToDto(List<Delivery> allByStatus) {
        return allByStatus
                .stream()
                .map(DeliveryResponse::new)
                .collect(Collectors.toList());
    }


}
