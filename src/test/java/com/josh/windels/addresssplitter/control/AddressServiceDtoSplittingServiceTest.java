package com.josh.windels.addresssplitter.control;

import com.josh.windels.addresssplitter.entity.dto.AddressRequestDto;
import com.josh.windels.addresssplitter.entity.dto.AddressServiceDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AddressServiceDtoSplittingServiceTest {

    @Autowired
    private AddressSplittingService addressSplittingService;

    @ParameterizedTest(name = "AddressServiceDto String {0} should be converted to {1}")
    @MethodSource({"getAddressStringsAndExpectedAddressConversions"})
    void givenAnAddressString_whenConvertedToAddress_thenFieldsAreCorrectlyPopulated(String addressString, AddressServiceDto expectedConvertedAddress) {
        assertThat(addressSplittingService.convertToAddress(new AddressRequestDto(addressString))).isEqualToComparingFieldByField(expectedConvertedAddress);
    }

    private static Stream<Arguments> getAddressStringsAndExpectedAddressConversions() {
        return Stream.of(
                Arguments.of("Winterallee 3", AddressServiceDto.builder().street("Winterallee").houseNumber("3").build()),
                Arguments.of("Musterstrasse 45", AddressServiceDto.builder().street("Musterstrasse").houseNumber("45").build()),
                Arguments.of("Blaufeldweg 123B", AddressServiceDto.builder().street("Blaufeldweg").houseNumber("123B").build()),
                Arguments.of("Am Bächle 23", AddressServiceDto.builder().street("Am Bächle").houseNumber("23").build()),
                Arguments.of("Auf der Vogelwiese 23 b", AddressServiceDto.builder().street("Auf der Vogelwiese").houseNumber("23 b").build()),
                Arguments.of("4, rue de la revolution", AddressServiceDto.builder().street("rue de la revolution").houseNumber("4").build()),
                Arguments.of("200 Broadway Av", AddressServiceDto.builder().street("Broadway Av").houseNumber("200").build()),
                Arguments.of("Calle Aduana, 29", AddressServiceDto.builder().street("Calle Aduana").houseNumber("29").build()),
                Arguments.of("Calle 39 No 1540", AddressServiceDto.builder().street("Calle 39").houseNumber("No 1540").build()),
                Arguments.of("Cottage that has no house numericalComponent", AddressServiceDto.builder().street("Cottage that has no house numericalComponent").houseNumber("").build()),
                Arguments.of("Stromstraße, 15", AddressServiceDto.builder().street("Stromstraße").houseNumber("15").build()),
                Arguments.of("200th Avenue, 10A", AddressServiceDto.builder().street("200th Avenue").houseNumber("10A").build()),
                Arguments.of("Small house on 1045th Avenue, West 1B", AddressServiceDto.builder().street("Small house on 1045th Avenue, West").houseNumber("1B").build()),
                Arguments.of("Apartment 101 Tower Block, East 10 G", AddressServiceDto.builder().street("Apartment 101 Tower Block, East").houseNumber("10 G").build())
        );
    }

}
