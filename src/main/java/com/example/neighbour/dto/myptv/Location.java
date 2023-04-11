package com.example.neighbour.dto.myptv;

import com.example.neighbour.dto.place.LocationResponse;

public class Location {
    private Position referencePosition;
    private Position roadAccessPosition;
    private AddressDto address;
    private String formattedAddress;
    private String locationType;
    private Quality quality;

    public Position getReferencePosition() {
        return referencePosition;
    }

    public void setReferencePosition(Position value) {
        this.referencePosition = value;
    }

    public Position getRoadAccessPosition() {
        return roadAccessPosition;
    }

    public void setRoadAccessPosition(Position value) {
        this.roadAccessPosition = value;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto value) {
        this.address = value;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String value) {
        this.formattedAddress = value;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String value) {
        this.locationType = value;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality value) {
        this.quality = value;
    }

    public LocationResponse toLocationResponse() {
        return new LocationResponse(
                this.referencePosition,
                this.address,
                this.formattedAddress
        );
    }
}
