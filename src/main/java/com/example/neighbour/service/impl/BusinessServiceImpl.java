package com.example.neighbour.service.impl;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.User;
import com.example.neighbour.data.UserDetail;
import com.example.neighbour.dto.*;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.repositories.BusinessRepository;
import com.example.neighbour.service.BusinessService;
import com.example.neighbour.service.ElasticSearchService;
import com.example.neighbour.service.StripeService;
import com.example.neighbour.utils.UserUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;

    private final ElasticSearchService<EsBusinessDto> esService;

    private final StripeService stripeService;

    @Override
    public ResponseDto<String> addBusiness(
            Business business) {
        UserDetail userDetail = business.getBusinessInfo();
        User user = business.getUser();
        userDetail.setImageUrl(UserUtils.getProfileImageKey(business.getUser()));

        EsBusinessDto esBusinessDto = getBusinessDtoForES(business, userDetail);

        StripeAccountResponse stripeAccountResponse = stripeService.registerBusinessAccount(user.getEmail());
        business.setAccountId(stripeAccountResponse.accountId());
        Business savedBusiness = businessRepository.save(business);
        esService.addDocument(esBusinessDto, "seller", savedBusiness.getId().toString());

        log.debug("BUSINESS with email {} added successfully", esBusinessDto.email());
        return ResponseDto.success(stripeAccountResponse.url(), "Business added successfully");
    }

    @Override
    public Business getCurrentBusiness() {
        User user = UserUtils.getAuthenticatedUser();
        return businessRepository.findByUser(user)
                .orElseThrow(() -> new ErrorResponseException(404, "Business not found"));
    }

    @Override
    public Business getBusinessByAccountId(String accountId) {
        return businessRepository.findByAccountId(accountId)
                .orElseThrow(() -> new ErrorResponseException(404, "Business not found"));
    }

    private static EsBusinessDto getBusinessDtoForES(Business business, UserDetail userDetail) {
        UserDetailDto userDetailDto = new UserDetailDto(userDetail);
        return new EsBusinessDto(
                business.getId(),
                business.getUser().getEmail(),
                false,
                new EsLocationRecord(
                        userDetail.getAddress().getPosition().getLatitude(),
                        userDetail.getAddress().getPosition().getLongitude()),
                0D,
                userDetailDto);
    }

}
