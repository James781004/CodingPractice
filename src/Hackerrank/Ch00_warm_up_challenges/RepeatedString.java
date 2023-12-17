package Hackerrank.Ch00_warm_up_challenges;

public class RepeatedString {
    // https://www.hackerrank.com/challenges/repeated-string/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
    public static long repeatedString(String s, long n) {
        System.out.println("String is: " + s);
        long count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'a') {
                count++;
            }
        }
        count = n / s.length() * count;
        long remainingString = n - (n / s.length() * s.length());
        System.out.println("Remaining String: " + remainingString);
        for (int i = 0; i < remainingString; i++) {
            if (s.charAt(i) == 'a') {
                count++;
            }
        }
        return count;
    }
}
