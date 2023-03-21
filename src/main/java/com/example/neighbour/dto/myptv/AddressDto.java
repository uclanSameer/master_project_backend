package com.example.neighbour.dto.myptv;

import com.example.neighbour.data.Address;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AddressDto {
    private String countryName;
    private String state;
    private String province;
    private String postalCode;
    private String city;
    private String district;
    private String subdistrict;
    private String street;
    private int houseNumber;

    private Integer apartmentNumber;
    private Integer appartmentName;

    private Position position;

    public AddressDto(Address address) {
        this.countryName = address.getCountryName();
        this.state = address.getState();
        this.province = address.getProvince();
        this.postalCode = address.getPostalCode();
        this.city = address.getCity();
        this.district = address.getDistrict();
        this.subdistrict = address.getSubdistrict();
        this.street = address.getStreet();
        this.houseNumber = address.getHouseNumber();
        this.position = new Position(address.getPosition());
    }

    public AddressDto(String countryName, String state, String province, String postalCode, String city, String district, String subdistrict, String street, int houseNumber) {
        this.countryName = countryName;
        this.state = state;
        this.province = province;
        this.postalCode = postalCode;
        this.city = city;
        this.district = district;
        this.subdistrict = subdistrict;
        this.street = street;
        this.houseNumber = houseNumber;
    }
}