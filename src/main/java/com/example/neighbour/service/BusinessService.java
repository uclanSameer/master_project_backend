package com.example.neighbour.service;

import com.example.neighbour.data.Business;
import com.example.neighbour.dto.ResponseDto;

public interface BusinessService {


    ResponseDto<String> addBusiness(Business business);

    Business getCurrentBusiness();

    Business getBusinessByAccountId(String accountId);

}
