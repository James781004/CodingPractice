package 劍指offer.Arrays;

public class Q50_FirstNotRepeatingChar {
    public int FirstNotRepeatingChar(String str) {
        // ASCII 码只有 128 个字符
        int[] cnts = new int[128];

        for (int i = 0; i < str.length(); i++) {
            cnts[str.charAt(i)]++;
        }

        for (int i = 0; i < str.length(); i++) {
            if (cnts[str.charAt(i)] == 1) return i;
        }

        return -1;
    }

}
