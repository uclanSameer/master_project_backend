package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.delivery.DeliveryRequest;
import com.example.neighbour.dto.delivery.DeliveryResponse;
import com.example.neighbour.dto.delivery.DeliveryResponseTest;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.users.UserDtoTest;
import com.example.neighbour.service.DeliveryService;
import com.example.neighbour.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryControllerTest {

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private DeliveryController deliveryController;

    @Test
    void testRegisterDelivery() {
        when(userService.addDeliveryUser(any())).thenReturn(
                ResponseDto.success("Delivery user registered successfully", null)
        );
        UserDto userDto = UserDtoTest.getUserDto();

        ResponseDto<String> responseDto = deliveryController.registerDeliveryUser(userDto);

        assert (responseDto.getData().equals("Delivery user registered successfully"));
    }

    @Test
    void testToDeliver() {

        DeliveryResponse deliveryResponse = DeliveryResponseTest.getDeliveryResponse();
        List<DeliveryResponse> deliveryResponseList = List.of(deliveryResponse);
        when(deliveryService.assignedDeliveries()).thenReturn(
                ResponseDto.success(deliveryResponseList, "Delivery list retrieved successfully")
        );

        ResponseDto<List<DeliveryResponse>> toDeliver = deliveryController.toDeliver();

        assert (toDeliver.getData().equals(deliveryResponseList));
    }


    @Test
    void testDeliveredDeliveries() {

        DeliveryResponse deliveryResponse = DeliveryResponseTest.getDeliveryResponse();
        List<DeliveryResponse> deliveryResponseList = List.of(deliveryResponse);
        when(deliveryService.deliveredDeliveries()).thenReturn(
                ResponseDto.success(deliveryResponseList, "Delivery list retrieved successfully")
        );

        ResponseDto<List<DeliveryResponse>> deliveredDeliveries = deliveryController.delivered();

        assert (deliveredDeliveries.getData().equals(deliveryResponseList));
    }

    @Test
    void testFetchingOfUnassignedDeliveries() {
        DeliveryResponse deliveryResponse = DeliveryResponseTest.getDeliveryResponse();
        List<DeliveryResponse> deliveryResponseList = List.of(deliveryResponse);
        when(deliveryService.unassignedDeliveries()).thenReturn(
                ResponseDto.success(deliveryResponseList, "Delivery list retrieved successfully")
        );

        ResponseDto<List<DeliveryResponse>> unassignedDeliveries = deliveryController.unassigned();

        assert (unassignedDeliveries.getData().equals(deliveryResponseList));
    }

    @Test
    void testAssigningDelivery() {
        doNothing().when(deliveryService).assignDeliveryPerson(any());

        DeliveryRequest deliveryRequest = new DeliveryRequest(
                1,
                "deliveryId"
        );

        ResponseDto<String> responseDto = deliveryController.assignDelivery(deliveryRequest);

        assert (responseDto.getMessage().equals("Delivery assigned"));

    }
}