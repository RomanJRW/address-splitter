package com.josh.windels.addresssplitter.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressResponseDto {

    @JsonProperty("street")
    private String street;

    @JsonProperty("housenumber")
    private String houseNumber;

    public static AddressResponseDto of(AddressServiceDto addressServiceDto) {
        return AddressResponseDto.builder()
                .houseNumber(addressServiceDto.getHouseNumber())
                .street(addressServiceDto.getStreet())
                .build();
    }

}
