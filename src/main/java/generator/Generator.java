package generator;

import java.util.Random;

public class Generator {
    public static int randBetween(int start, int end) {
        return start + (int)Math.round(Math.random() * (end - start));
    }

    public String generateId() {
        String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder id = new StringBuilder();
        Random rnd = new Random();
        while (id.length() < 8) { // length of the random string.
            int index = (int) (rnd.nextFloat() * chars.length());
            id.append(chars.charAt(index));
        }
        return id.toString();
    }
}
