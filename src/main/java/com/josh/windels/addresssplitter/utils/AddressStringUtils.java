package com.josh.windels.addresssplitter.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddressStringUtils {

    public static final List<String> COMMON_HOUSE_NUMBER_PREFIXES = Arrays.asList("NUMBER", "NO", "NUM");

    public static int calculatePercentageOfNumericalCharacters(String string) {
        if (string == null || string.length() == 0) {
            return 0;
        }
        String numericalCharacters = string.replaceAll("\\D", "");
        return numericalCharacters.length() * 100 / string.length();
    }

    public static List<String> splitIntoAddressComponents(String addressString) {
        return Arrays.asList(addressString.split(" "));
    }

    public static String removeSpecialCharacters(String addressString) {
        return addressString.replaceAll("[,;:]", "");
    }

    public static String convertToAddressField(List<String> addressStringComponents) {
        List<String> trimmedComponents = addressStringComponents.stream().map(String::trim).collect(Collectors.toList());
        if (!trimmedComponents.isEmpty()) {
            int lastComponentIndex = trimmedComponents.size() - 1;
            trimmedComponents.set(lastComponentIndex, AddressStringUtils.removeSpecialCharacters(trimmedComponents.get(lastComponentIndex)));
        }
        return String.join(" ", trimmedComponents);
    }
}
