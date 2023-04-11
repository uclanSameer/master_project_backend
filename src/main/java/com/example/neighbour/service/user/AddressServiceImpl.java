package com.example.neighbour.service.user;

import com.example.neighbour.data.Address;
import com.example.neighbour.data.PositionEntity;
import com.example.neighbour.data.User;
import com.example.neighbour.data.UserDetail;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.repositories.AddressRepository;
import com.example.neighbour.repositories.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl  implements AddressService{

    private final AddressRepository addressRepository;

    private final PositionRepository positionRepository;
    @Override
    public void saveAddress(UserDto userDto, User savedUser, UserDetail savedUserDetails) {
        Address address = savedUserDetails.getAddress();
        address.setUserDetail(savedUserDetails);

        Address savedAddress = addressRepository.save(address);
        PositionEntity position = savedAddress.getPosition();
        position.setAddress(savedAddress);

        PositionEntity positionEntity = positionRepository.save(position);

        savedAddress.setPosition(positionEntity);
        savedUserDetails.setAddress(savedAddress);
        savedUser.setUserDetail(savedUserDetails);
    }
}
