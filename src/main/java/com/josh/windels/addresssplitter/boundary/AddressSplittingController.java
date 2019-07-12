package com.josh.windels.addresssplitter.boundary;

import com.josh.windels.addresssplitter.control.AddressSplittingService;
import com.josh.windels.addresssplitter.entity.dto.AddressRequestDto;
import com.josh.windels.addresssplitter.entity.dto.AddressResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/address")
public class AddressSplittingController {

    private final AddressSplittingService addressSplittingService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public AddressResponseDto createAddress(@RequestBody AddressRequestDto addressRequestDto) {
        return AddressResponseDto.of(addressSplittingService.convertToAddress(addressRequestDto));
    }

}
