package com.example.neighbour.data;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessTest {

    public static Business createBusiness() {
        Business business = new Business();
        business.setId(1);
        business.setUser(UserTest.getBusinessUser());
        business.setBusinessInfo(UserDetailTest.getBusinessUserDetail());
        business.setAccountId(UUID.randomUUID().toString());
        business.setFeatured(true);
        return business;
    }

}