package org.aviatrip.representativeservice.util;

public class StringPrettier {

    private static final String snakeCaseMatcher = "([a-zA-Z])([A-Z])";
    private static final String snakeCaseReplacement = "$1_$2";

    public static String toSnakeCase(String str) {
        return str.replaceAll(snakeCaseMatcher, snakeCaseReplacement)
                .replaceAll(snakeCaseMatcher, snakeCaseReplacement)
                .toLowerCase();
    }
}
