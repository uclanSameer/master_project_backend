package com.example.neighbour.service.impl;

import com.example.neighbour.data.*;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.delivery.DeliveryRequest;
import com.example.neighbour.dto.delivery.DeliveryResponse;
import com.example.neighbour.enums.PaymentStatus;
import com.example.neighbour.enums.Role;
import com.example.neighbour.repositories.DeliveryRepository;
import com.example.neighbour.service.UserRetrievalService;
import com.example.neighbour.utils.UserUtilsTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceImplTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private UserRetrievalService userService;

    @InjectMocks
    private DeliveryServiceImpl deliveryService;

    @Test
    void createDelivery() {

        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(i -> i.getArgument(0));

        Order order = OrderTest.getOrder();

        deliveryService.createDelivery(order, "123 Fake Street");

        verify(deliveryRepository, times(1)).save(any(Delivery.class));
    }

    @Test
    void unassignedDeliveries() {
        Delivery delivery = DeliveryTest.getDelivery();
        delivery.setDeliveryPerson(null);
        when(deliveryRepository.findAllByDeliveryPersonNull()).thenReturn(
                List.of(
                        delivery
                )
        );
        ResponseDto<List<DeliveryResponse>> listResponseDto = deliveryService.unassignedDeliveries();

        verify(deliveryRepository, times(1)).findAllByDeliveryPersonNull();

        assert listResponseDto.getData().size() == 1;

        DeliveryResponse deliveryResponse = listResponseDto.getData().get(0);

        assert deliveryResponse.orderNumber() == 1;

        assertNull(deliveryResponse.deliveryPerson());

    }

    @Test
    void assignedDelivery() {
        // set user to security context
        UserUtilsTest.SET_NORMAL_USER_AUTHENTICATION();
        Delivery delivery = DeliveryTest.getDelivery();
        when(deliveryRepository.findAllByDeliveryPerson(any())).thenReturn(
                List.of(
                        delivery
                )
        );

        ResponseDto<List<DeliveryResponse>> listResponseDto = deliveryService.assignedDeliveries();

        verify(deliveryRepository, times(1)).findAllByDeliveryPerson(any());

        assertNotNull(listResponseDto.getData().get(0).deliveryPerson());
    }

    @Test
    void notYetDeliveredDeliveries(){
        Delivery delivery = DeliveryTest.getDelivery();
        when(deliveryRepository.findAllByStatus(eq(Delivery.DeliveryStatus.PENDING))).thenReturn(
                List.of(
                        delivery
                )
        );
        ResponseDto<List<DeliveryResponse>> listResponseDto = deliveryService.notYetDeliveredDeliveries();

        verify(deliveryRepository, times(1)).findAllByStatus(eq(Delivery.DeliveryStatus.PENDING));

        assert listResponseDto.getData().size() == 1;

        DeliveryResponse deliveryResponse = listResponseDto.getData().get(0);

        assert deliveryResponse.orderNumber() == 1;

        assertEquals(deliveryResponse.status(), Delivery.DeliveryStatus.PENDING);

    }


    @Test
    void deliveredDeliveries(){
        Delivery delivery = DeliveryTest.getDelivery();
        delivery.setStatus(Delivery.DeliveryStatus.DELIVERED);
        when(deliveryRepository.findAllByStatus(eq(Delivery.DeliveryStatus.DELIVERED))).thenReturn(
                List.of(
                        delivery
                )
        );
        ResponseDto<List<DeliveryResponse>> listResponseDto = deliveryService.deliveredDeliveries();

        verify(deliveryRepository, times(1)).findAllByStatus(eq(Delivery.DeliveryStatus.DELIVERED));

        assert listResponseDto.getData().size() == 1;

        DeliveryResponse deliveryResponse = listResponseDto.getData().get(0);

        assert deliveryResponse.orderNumber() == 1;

        assertEquals(deliveryResponse.status(), Delivery.DeliveryStatus.DELIVERED);
    }


    @Test
    void updateDeliveryStatus(){
        when(deliveryRepository.findByOrderId(anyInt()))
                .thenReturn(
                        Optional.of(DeliveryTest.getDelivery())
                );

        when(deliveryRepository.save(any()))
                .thenAnswer(i -> {
                    Delivery delivery= i.getArgument(0);

                    assert delivery.getStatus() == Delivery.DeliveryStatus.DELIVERED;
                    return delivery;
                });

        deliveryService.updateDeliveryStatus(1, Delivery.DeliveryStatus.DELIVERED);
    }

    @Test
    void assignDeliveryPerson(){
        when(deliveryRepository.findByOrderId(anyInt()))
                .thenReturn(
                        Optional.of(DeliveryTest.getDelivery())
                );

        when(userService.getUserByEmail(anyString()))
                .thenReturn(
                        UserTest.getDeliveryUser()
                );
        when(deliveryRepository.save(any()))
                .thenAnswer(i -> {
                    Delivery delivery= i.getArgument(0);

                    assert delivery.getDeliveryPerson().getRole() == Role.DELIVERY;
                    return delivery;
                });

        deliveryService.assignDeliveryPerson(
                new DeliveryRequest(
                        1,
                        "email@delivery.com"
                )
        );
    }
}
