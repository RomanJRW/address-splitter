package com.josh.windels.addresssplitter.entity;

import com.josh.windels.addresssplitter.utils.AddressStringUtils;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class HouseNumberCandidate implements Comparable {
    private Optional<String> prefix;
    private String numericalComponent;
    private Optional<String> suffix;
    private List<String> fullAddressComponents;

    public List<String> asComponentList() {
        List<String> componentList = new ArrayList<>();
        prefix.ifPresent(componentList::add);
        componentList.add(numericalComponent);
        suffix.ifPresent(componentList::add);
        return componentList;
    }

    private boolean isAtEdgeOfAddressString() {
        boolean atBeginning = fullAddressComponents.indexOf(prefix.orElse(numericalComponent)) == 0;
        boolean atEnd = fullAddressComponents.indexOf(suffix.orElse(numericalComponent)) == fullAddressComponents.size() - 1;
        return atBeginning || atEnd;
    }

    @Override
    public int compareTo(Object otherCandidate) {
        return Comparator.comparing(HouseNumberCandidate::isAtEdgeOfAddressString)
                .thenComparing(candidate -> candidate.getPrefix().isPresent())
                .thenComparingInt(candidate -> AddressStringUtils.calculatePercentageOfNumericalCharacters(candidate.numericalComponent))
                .compare(this, (HouseNumberCandidate) otherCandidate);
    }

}
