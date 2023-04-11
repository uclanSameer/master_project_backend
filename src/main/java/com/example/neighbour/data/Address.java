package com.example.neighbour.data;

import com.example.neighbour.dto.myptv.AddressDto;
import com.example.neighbour.dto.myptv.Position;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_details_id")
    private UserDetail userDetail;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    @Setter
    private PositionEntity position;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "countryName", nullable = false)
    private String countryName;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "district", nullable = false)
    private String district;
    @Column(name = "subdistrict", nullable = false)
    private String subdistrict;

    @Column(name = "postalCode", nullable = false)
    private String postalCode;

    @Column(name = "houseNumber")
    private Integer houseNumber;

    @Column(name = "apartmentNumber")
    private Integer apartmentNumber;

    @Column(name = "appartmentName")
    private Integer appartmentName;


    public Address(AddressDto addressDto, Position position) {
        this.street = addressDto.getStreet();
        this.countryName = addressDto.getCountryName();
        this.state = addressDto.getState();
        this.province = addressDto.getProvince();
        this.city = addressDto.getCity();
        this.district = addressDto.getDistrict();
        this.subdistrict = addressDto.getSubdistrict();
        this.postalCode = addressDto.getPostalCode();
        this.position = new PositionEntity(position);
        this.houseNumber = addressDto.getHouseNumber();
        this.apartmentNumber = addressDto.getApartmentNumber();
        this.appartmentName = addressDto.getAppartmentName();
    }


    @Override
    public String toString() {
        if (this.houseNumber != null) {
            return this.houseNumber + " " + this.street + " " + this.subdistrict + " " + this.district + " " + this.city + " " + this.province + " " + this.postalCode + " " + this.countryName;
        }
        return formatAddressUsingAppartment();
    }

    public String formatAddressUsingAppartment() {
        return this.apartmentNumber + " " + this.appartmentName + " " + this.street + " " + this.subdistrict + " " + this.district + " " + this.city + " " + this.province + " " + this.postalCode + " " + this.countryName;
    }
}
