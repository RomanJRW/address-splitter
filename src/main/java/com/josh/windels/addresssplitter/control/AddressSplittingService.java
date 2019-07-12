package com.josh.windels.addresssplitter.control;

import com.josh.windels.addresssplitter.entity.HouseNumberCandidate;
import com.josh.windels.addresssplitter.entity.dto.AddressRequestDto;
import com.josh.windels.addresssplitter.entity.dto.AddressServiceDto;
import com.josh.windels.addresssplitter.utils.AddressStringUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressSplittingService {

    public AddressServiceDto convertToAddress(AddressRequestDto addressRequestDto) {
        List<String> allAddressComponents = AddressStringUtils.splitIntoAddressComponents(addressRequestDto.getAddressString());

        List<String> houseNumberComponents = findHouseNumberComponents(allAddressComponents);
        List<String> streetComponents = allAddressComponents.stream()
                .filter(component -> !houseNumberComponents.contains(component))
                .collect(Collectors.toList());

        return AddressServiceDto.builder()
                .houseNumber(AddressStringUtils.convertToAddressField(houseNumberComponents))
                .street(AddressStringUtils.convertToAddressField(streetComponents))
                .build();
    }

    private List<String> findHouseNumberComponents(List<String> allAddressComponents) {
        return findHouseNumberComponentCandidates(allAddressComponents).stream()
                .max(HouseNumberCandidate::compareTo)
                .map(HouseNumberCandidate::asComponentList)
                .orElse(Collections.emptyList());
    }

    private List<HouseNumberCandidate> findHouseNumberComponentCandidates(List<String> allComponents) {
        return allComponents.stream()
                .filter(Objects::nonNull)
                .filter(component -> AddressStringUtils.calculatePercentageOfNumericalCharacters(component) > 0)
                .map(numericalComponent -> HouseNumberCandidate.builder()
                        .prefix(getPrefixIfRelevant(numericalComponent, allComponents))
                        .numericalComponent(numericalComponent)
                        .suffix(getSuffixIfRelevant(numericalComponent, allComponents))
                        .fullAddressComponents(allComponents)
                        .build())
                .collect(Collectors.toList());
    }

    private Optional<String> getPrefixIfRelevant(String baseComponent, List<String> allComponents) {
        int baseComponentIndex = allComponents.indexOf(baseComponent);
        if (baseComponentIndex > 0) {
            String previousComponent = allComponents.get(baseComponentIndex - 1);
            if (!previousComponent.endsWith(",") && AddressStringUtils.COMMON_HOUSE_NUMBER_PREFIXES.contains(AddressStringUtils.removeSpecialCharacters(previousComponent).toUpperCase())) {
                return Optional.of(previousComponent);
            }
        }
        return Optional.empty();
    }

    private Optional<String> getSuffixIfRelevant(String baseComponent, List<String> allComponents) {
        int baseComponentIndex = allComponents.indexOf(baseComponent);
        if (!baseComponent.endsWith(",") && baseComponentIndex < allComponents.size() - 1) {
            String nextComponent = allComponents.get(baseComponentIndex + 1);
            if (nextComponent.length() == 1) {
                return Optional.of(nextComponent);
            }
        }
        return Optional.empty();
    }

}
