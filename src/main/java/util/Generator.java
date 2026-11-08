package util;

import java.util.Random;

public class Generator {

    public static final String ALPHABET = "abcd";

    public static String random(int length, long seed) {
        Random random = new Random(seed);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
}
