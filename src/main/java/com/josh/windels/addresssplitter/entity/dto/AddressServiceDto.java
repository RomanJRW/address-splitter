package com.josh.windels.addresssplitter.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AddressServiceDto {

    private String street;

    private String houseNumber;

}
