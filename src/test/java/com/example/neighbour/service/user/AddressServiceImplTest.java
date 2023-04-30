package com.example.neighbour.service.user;

import com.example.neighbour.data.*;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.users.UserDtoTest;
import com.example.neighbour.repositories.AddressRepository;
import com.example.neighbour.repositories.PositionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceImplTest {

    @Mock private AddressRepository addressRepository;

    @Mock private PositionRepository positionRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    @Test
    void testSaveAddress() {
        UserDto userDto = UserDtoTest.getUserDto();
        User savedUser = UserTest.getNormalUser();
        UserDetail savedUserDetails = UserDetailTest.getUserDetail();

        savedUserDetails.setAddress(AddressTest.getAddress());

        when(addressRepository.save(any(Address.class))).thenReturn(savedUserDetails.getAddress());
        when(positionRepository.save(any())).thenReturn(savedUserDetails.getAddress().getPosition());

        addressService.saveAddress(userDto, savedUser, savedUserDetails);
    }


}