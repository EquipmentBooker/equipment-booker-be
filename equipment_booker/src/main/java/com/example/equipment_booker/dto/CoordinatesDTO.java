package com.example.equipment_booker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesDTO {
    private double departureLatitude;
    private double departureLongitude;
    private double destinationLatitude;
    private double destinationLongitude;
}
