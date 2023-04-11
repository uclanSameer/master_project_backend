package com.example.neighbour.configuration;

import com.example.neighbour.dto.users.UserDetailDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.dto.myptv.Position;
import com.example.neighbour.enums.Role;
import com.example.neighbour.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializationConfiguration {
    private final UserService userService;

    @Transactional()
    @PostConstruct()
    public void runBeforeStartUp() {
        log.info("Checking if the database is empty");
        if (userService.hasNoUser()) {
            createAdminUser();
        }
    }

    /**
     * Creates the admin user if the database is empty
     */
    private void createAdminUser() {
        log.info("Database is empty, creating the admin user");
        UserDetailDto userDetailDto = UserDetailDto.builder()
                .name("Admin")
                .phoneNumber("12345678901")
                .address(AddressDto.builder()
                        .city("City")
                        .countryName("Country")
                        .houseNumber(1)
                        .postalCode("PR1 1ST")
                        .subdistrict("Subdistrict")
                        .province("Province")
                        .district("District")
                        .state("State")
                        .street("Street")
                        .build())
                .position(new Position(0, 0))
                .build();
        String email = "admin@admin.com";
        UserDto userDto = UserDto.builder()
                .email(email)
                .password("admin1234")
                .userDetail(userDetailDto)
                .build();
        userService.registerUserWithRole(userDto, Role.ADMIN);

        log.info("Admin user created");
    }
}
