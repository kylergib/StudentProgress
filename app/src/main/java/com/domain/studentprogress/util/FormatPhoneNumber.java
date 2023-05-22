package com.domain.studentprogress.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FormatPhoneNumber {

    public static String formatPhoneNumber(String phoneNumber) {
        // Remove any non-digit characters from the input
        String digitsOnly = phoneNumber.replaceAll("\\D", "");

        // Apply the desired formatting using regular expressions
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{4})");
        Matcher matcher = pattern.matcher(digitsOnly);

        if (matcher.matches()) {
            return String.format("(%s) %s-%s", matcher.group(1), matcher.group(2), matcher.group(3));
        } else {
            // Return the original phone number if it doesn't match the expected pattern
            return phoneNumber;
        }
    }


}
