package com.example.neighbour.service.user;

import com.example.neighbour.data.User;
import com.example.neighbour.data.UserDetail;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.repositories.UserDetailRepository;
import com.example.neighbour.service.aws.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.example.neighbour.utils.UserUtils.getAuthenticatedUser;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@Slf4j
@RequiredArgsConstructor
public class UploadProfileServiceImpl implements ProfileService {

    private final S3Service s3Service;

    private final UserDetailRepository userDetailRepository;

    @Override
    public ResponseDto<String> uploadProfilePicture(String base64Image) {
        try {
            User user = getAuthenticatedUser();
            return uploadProfilePicture(base64Image, user);
        } catch (Exception e) {
            log.error("Error occurred while updating the profile picture for the user", e);
            throw new ResponseStatusException(BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public ResponseDto<String> uploadProfilePicture(String base64Image, User user) {
        try {
            log.debug("Updating the profile picture for the user : {}", user.getUsername());
            UserDetail userDetail = user.getUserDetail();
            String key = "image/" + user.getId() + "/profile/" + user.getId();
            s3Service.uploadFile(key, base64Image);
            userDetail.setImageUrl(key);
            userDetailRepository.save(userDetail);
            log.debug("Updating the profile picture successful for the user : {}", user.getUsername());
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
}
