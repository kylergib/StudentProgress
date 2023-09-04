package com.domain.studentprogress.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FormatStrings {
    public static String formatBetween(String string, int max) {
        return string.substring(0,Math.min(string.length(), max));
    }
    public static String formatPhoneNumber(String phoneNumber) {
        String digitsOnly = phoneNumber.replaceAll("\\D", "");
        Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{4})");
        Matcher matcher = pattern.matcher(digitsOnly);

        if (matcher.matches()) {
            return String.format("(%s) %s-%s", matcher.group(1), matcher.group(2), matcher.group(3));
        } else {
            return phoneNumber;
        }
    }
}
