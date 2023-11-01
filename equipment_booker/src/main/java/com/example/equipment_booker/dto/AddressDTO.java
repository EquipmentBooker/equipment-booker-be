package com.example.equipment_booker.dto;

import com.example.equipment_booker.model.Address;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Long id;
    private String street;
    private String number;
    private String city;
    private String country;
    private double longitude;
    private double latitude;

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.number = address.getNumber();
        this.city = address.getCity();
        this.country = address.getCountry();
        this.longitude = address.getLongitude();
        this.latitude = address.getLatitude();
    }
}
