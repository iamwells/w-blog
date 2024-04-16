package io.github.iamwells.admin.util;

import java.util.Random;

public class CaptchaUtil {
    private static final String charArray = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final Random random = new Random();

    public static String generateMixedCode(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(63);
            builder.append(charArray.charAt(index));
        }
        return builder.toString();
    }


    public static String generateSimpleCode(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(10);
            builder.append(index);
        }
        return builder.toString();
    }

}
