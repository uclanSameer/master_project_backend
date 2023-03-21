package com.example.neighbour.service.impl;

import com.example.neighbour.configuration.security.jwt.JwtUtils;
import com.example.neighbour.data.*;
import com.example.neighbour.dto.JwtResponseDto;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.UserDetailDto;
import com.example.neighbour.dto.UserDto;
import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.enums.Role;
import com.example.neighbour.repositories.AddressRepository;
import com.example.neighbour.repositories.PositionRepository;
import com.example.neighbour.repositories.UserDetailRepository;
import com.example.neighbour.repositories.UserRepository;
import com.example.neighbour.service.BusinessService;
import com.example.neighbour.service.S3Service;
import com.example.neighbour.service.UserService;
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

    private final AddressRepository addressRepository;

    private final PositionRepository positionRepository;

    private final JwtUtils jwtUtils;

    private final S3Service s3Service;

    private final BusinessService businessService;

    public ResponseDto<UserDto> registerUser(UserDto userDto) {
        try {
            log.debug("Registering the user with email {}", userDto.getEmail());

            validateUser(userDto);
            User registerUserWithRole = registerUserWithRole(userDto, Role.USER);
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
            return businessService.addBusiness(business);
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

        Address address = savedUserDetails.getAddress();
        address.setUserDetail(savedUserDetails);

        Address savedAddress = addressRepository.save(address);
        PositionEntity position = savedAddress.getPosition();
        position.setAddress(savedAddress);

        PositionEntity positionEntity = positionRepository.save(position);

        if (userDto.getUserDetail().getImageUrl() != null) {
            uploadProfilePicture(userDto.getUserDetail().getImageUrl());
        }

        log.debug("User with email {} registered successfully", userDto.getEmail());
        savedAddress.setPosition(positionEntity);
        savedUserDetails.setAddress(savedAddress);
        savedUser.setUserDetail(savedUserDetails);

        return savedUser;
    }

    @Override
    public ResponseDto<JwtResponseDto> generateJwtToken() {
        try {
            log.debug("Generating jwt token.");
            User userPrincipal = getAuthenticatedUser();
            String jwt = jwtUtils.generateJwtToken(userPrincipal);
            log.info("Token generated successfully.");
            return new ResponseDto<>(createJwtResponseDto(userPrincipal, jwt));
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
    public User updateRole(String email, Role role) {
        try {
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new ResponseStatusException(BAD_REQUEST, "User not found");
            }
            user.setRole(role);
            userRepository.save(user);
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error occurred while updating the role for the user with email {}", email, e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseDto<String> uploadProfilePicture(String url) {
        try {
            User user = getAuthenticatedUser();
            log.debug("Updating the profile picture for the user : {}", user.getUsername());
            UserDetail userDetail = user.getUserDetail();
            String key = "image/" + user.getId() + "/profile/" + user.getUserDetail().getName();
            s3Service.uploadFile(key, url);
            userDetail.setImageUrl(key);
            userDetailRepository.save(userDetail);
            log.debug("Updating the profile picture succesful for the user : {}", user.getUsername());
            return ResponseDto.success(s3Service.generatePreSignedUrl(key), "Profile picture updated successfully");
        } catch (Exception e) {
            log.error("Error occurred while updating the profile picture for the user", e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseDto<UserDto> getProfile() {
        try {
            User user = getAuthenticatedUser();
            UserDto userDto = new UserDto(user);
            userDto.setPassword("********");
            String imageUrl = user.getUserDetail().getImageUrl();
            if (imageUrl != null) userDto.getUserDetail().setImageUrl(s3Service.generatePreSignedUrl(imageUrl));
            return new ResponseDto<>(userDto);
        } catch (Exception e) {
            log.error("Error occurred while getting the profile for the user", e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    public boolean hasNoUser() {
        return userRepository.count() == 0;
    }

    /**
     * Validate user
     *
     * @param userDto user dto
     */
    private void validateUser(UserDto userDto) {
        try {
            // validates password strength
            if (userDto.getPassword().length() < 8) {
                log.error("Password is too weak for the user {}", userDto.getEmail());
                throw new ResponseStatusException(BAD_REQUEST, "Password must be at least 8 characters");
            }

            // validates email
            if (userRepository.findByEmail(userDto.getEmail()) != null) {
                log.error("Email already exists for the user {}", userDto.getEmail());
                throw new ResponseStatusException(BAD_REQUEST, "Email already exists");
            }

            // validates user detail
            if (userDto.getUserDetail() != null) {

                // validate phone number is valid uk number
                if (userDto.getUserDetail().getPhoneNumber().length() != 11) {
                    log.error("Phone number is invalid for the user {}", userDto.getEmail());
                    throw new ResponseStatusException(BAD_REQUEST, "Phone number must be 11 characters");
                }
            }
        } catch (Exception e) {
            log.error("Error occurred while validating the user with email {}", userDto.getEmail(), e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    private JwtResponseDto createJwtResponseDto(User userPrincipal, String jwt) {
        JwtResponseDto jwtResponseDto = new JwtResponseDto();
        jwtResponseDto.setEmail(userPrincipal.getEmail());
        jwtResponseDto.setToken(jwt);
        jwtResponseDto.setAddress(new AddressDto(userPrincipal.getUserDetail().getAddress()));
        jwtResponseDto.setUserRole(userPrincipal.getRole());
        return jwtResponseDto;
    }

    public void removeAllExceptAdmin() {
        Iterable<User> allByRoleNot = userRepository.findAllByRoleNot(Role.ADMIN);
        allByRoleNot.forEach(user -> {
            UserDetail userDetail = user.getUserDetail();
            Address address = userDetail.getAddress();
            PositionEntity position = address.getPosition();
            positionRepository.delete(position);
            addressRepository.delete(address);
            log.info("Deleting user with email {}", user.getEmail());
        });
        userRepository.deleteAll(allByRoleNot);
    }

    @Override
    public void updateBusinessAccountAsVerified(String accountId) {
        Business business = businessService.getBusinessByAccountId(accountId);
        User user = business.getUser();
        user.setVerified(true);
        userRepository.save(user);
        log.info("User with email {} has been verified", user.getEmail());
    }
}
