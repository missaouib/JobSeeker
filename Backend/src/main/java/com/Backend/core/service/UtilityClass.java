package com.Backend.core.service;

import java.text.Normalizer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class UtilityClass {

    public static void waitRandomFromToSeconds(int from, int to) {
        try {
            TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextLong(from, to));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String removePolishSigns(String city) {
        return Normalizer.normalize(city, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("ł", "l");
    }

    public static int getHtmlSubstring(String resultString, String htmlFirstPart, String htmlSecondPart) {
        int jobAmount = 0;
        if (resultString != null && resultString.contains(htmlFirstPart) && resultString.contains(htmlSecondPart))
            jobAmount = Integer.parseInt(resultString
                    .substring(resultString.indexOf(htmlFirstPart) + htmlFirstPart.length(), resultString.indexOf(htmlSecondPart))
                    .replaceAll(",", "")
                    .replaceAll("[^\\x00-\\x7F]", ""));

        return jobAmount;
    }

}
