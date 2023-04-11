package com.example.neighbour.service.impl;

import com.example.neighbour.configuration.security.jwt.JwtUtils;
import com.example.neighbour.data.Business;
import com.example.neighbour.data.User;
import com.example.neighbour.data.UserDetail;
import com.example.neighbour.data.Verification;
import com.example.neighbour.dto.JwtResponseDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDetailDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.enums.Role;
import com.example.neighbour.repositories.UserDetailRepository;
import com.example.neighbour.repositories.UserRepository;
import com.example.neighbour.service.BusinessService;
import com.example.neighbour.service.CartService;
import com.example.neighbour.service.UserService;
import com.example.neighbour.service.user.AddressService;
import com.example.neighbour.service.user.ProfileService;
import com.example.neighbour.service.user.VerificationService;
import com.example.neighbour.utils.UserUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.example.neighbour.utils.UserUtils.getAuthenticatedUser;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressService addressService;
    private final JwtUtils jwtUtils;
    private final ProfileService profileService;
    private final BusinessService businessService;

    private final CartService cartService;
    private final VerificationService verificationService;

    public ResponseDto<UserDto> registerUser(UserDto userDto) {
        try {
            log.debug("Registering the user with email {}", userDto.getEmail());

            UserUtils.validateUser(userDto);

            validateEmail(userDto);
            User registerUserWithRole = registerUserWithRole(userDto, Role.USER);
            cartService.createNewCart(registerUserWithRole);
            UserDto user = new UserDto(registerUserWithRole);
            user.setPassword(null);
            return new ResponseDto<>(user);
        } catch (Exception e) {
            log.error("Error occurred while registering the user with email {}", userDto.getEmail(), e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    public ResponseDto<String> registerBusiness(UserDto userDto) {
        try {
            User user = registerUserWithRole(userDto, Role.BUSINESS);
            Business business = new Business(user, user.getUserDetail());
            String setupUrl = businessService.addBusiness(business);
            verificationService.sendVerificationEmailToBusiness(user, setupUrl);
            log.debug("Business with email {} registered successfully", userDto.getEmail());
            return new ResponseDto<>("Business registered successfully");
        } catch (Exception e) {
            log.error("Error occurred while registering the business with email {}", userDto.getEmail(), e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    public User registerUserWithRole(UserDto userDto, Role role) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = new User(userDto, role);
        User savedUser = userRepository.save(user);
        UserDetailDto userDetail = userDto.getUserDetail();
        UserDetail savedUserDetails = userDetailRepository.save(new UserDetail(userDetail, user));

        addressService.saveAddress(userDto, savedUser, savedUserDetails);

        if (userDto.getUserDetail().getImageUrl() != null) {
            profileService.uploadProfilePicture(userDto.getUserDetail().getImageUrl());
        }

        if (role.equals(Role.USER)) {
            verificationService.sendUserVerificationEmail(user);
        }
        log.debug("User with email {} registered successfully", userDto.getEmail());

        return savedUser;
    }

    @Override
    public ResponseDto<JwtResponseDto> generateJwtToken() {
        try {
            log.debug("Generating jwt token.");
            User userPrincipal = getAuthenticatedUser();
            String jwt = jwtUtils.generateJwtToken(userPrincipal);
            log.info("Token generated successfully.");
            return new ResponseDto<>(UserUtils.createJwtResponseDto(userPrincipal, jwt));
        } catch (Exception e) {
            log.error("Error while generating jwt token.", e);
            throw new ResponseStatusException(BAD_REQUEST, "Invalid username or password");
        }
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    @Override
    public void updateRole(String email, Role role) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new ResponseStatusException(BAD_REQUEST, "User not found");
            }
            user.setRole(role);
            userRepository.save(user);
            userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while updating the role for the user with email {}", email, e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public boolean hasNoUser() {
        return userRepository.count() == 0;
    }

    @Override
    public void updateBusinessAccountAsVerified(String accountId) {
        Business business = businessService.getBusinessByAccountId(accountId);
        User user = business.getUser();
        Verification verification = user.getVerification();
        verification.setIsVerified(true);
        userRepository.save(user);
        log.info("User with email {} has been verified", user.getEmail());
    }

    private void validateEmail(UserDto userDto) {
        // validates email
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            log.error("Email already exists for the user {}", userDto.getEmail());
            throw new ResponseStatusException(BAD_REQUEST, "Email already exists");
        }
    }
}
