package com.example.neighbour.dto.myptv;

import com.example.neighbour.data.PositionEntity;
import com.example.neighbour.dto.place.EsLocationRecord;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Position {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double value) {
        this.latitude = value;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double value) {
        this.longitude = value;
    }

    public Position(PositionEntity positionEntity) {
        this.latitude = positionEntity.getLatitude();
        this.longitude = positionEntity.getLongitude();
    }

    public Position(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EsLocationRecord toLocation() {
        return new EsLocationRecord(this.latitude, this.longitude);
    }
}
