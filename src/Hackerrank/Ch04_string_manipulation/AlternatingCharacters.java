package Hackerrank.Ch04_string_manipulation;

public class AlternatingCharacters {
    // https://www.hackerrank.com/challenges/alternating-characters/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=strings
    public static int alternatingCharacters(String s) {
        int count = 0;
        char[] sChar = s.toCharArray();
        for (int i = 1; i < sChar.length; i++) {
            if (sChar[i] == sChar[i - 1]) {
                count++;
            }
        }
        return count;
    }
}
