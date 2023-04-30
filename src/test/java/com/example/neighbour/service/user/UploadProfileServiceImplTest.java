package com.example.neighbour.service.user;

import com.example.neighbour.data.UserDetail;
import com.example.neighbour.data.UserDetailTest;
import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.repositories.UserDetailRepository;
import com.example.neighbour.service.aws.S3Service;
import com.example.neighbour.utils.UserUtilsTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.neighbour.utils.GeneralStringConstants.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UploadProfileServiceImplTest {

    @Mock
    private S3Service s3Service;

    @Mock
    private UserDetailRepository userDetailRepository;

    @InjectMocks
    private UploadProfileServiceImpl uploadProfileService;

    @BeforeAll
    static void setUp() {
        UserUtilsTest.SET_NORMAL_USER_AUTHENTICATION();
    }


    @Test
    void uploadProfileImage() {
        doNothing().when(s3Service).uploadFile(anyString(), anyString());

        when(userDetailRepository.save(any(UserDetail.class))).thenReturn(UserDetailTest.getUserDetail());


        when(s3Service.generatePreSignedUrl(anyString())).thenReturn("test");

        ResponseDto<String> responseDto = uploadProfileService.uploadProfilePicture("test");

        assertEquals("test", responseDto.getData());

        verify(s3Service, times(1)).uploadFile(anyString(), anyString());

        verify(userDetailRepository, times(1)).save(any(UserDetail.class));
    }

    @Test
    void uploadProfileImageWithNullAuthentication() {
        ResponseDto<UserDto> profile = uploadProfileService.getProfile();

        assertEquals(SUCCESS, profile.getMessage());
    }
}