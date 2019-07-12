package com.josh.windels.addresssplitter.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddressResponseDto {

    private String street;

    private String houseNumber;

    public static AddressResponseDto of(AddressServiceDto addressServiceDto) {
        return AddressResponseDto.builder()
                .houseNumber(addressServiceDto.getHouseNumber())
                .street(addressServiceDto.getStreet())
                .build();
    }

}
