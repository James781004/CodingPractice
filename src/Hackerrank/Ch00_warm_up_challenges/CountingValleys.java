package Hackerrank.Ch00_warm_up_challenges;

public class CountingValleys {
    // https://www.hackerrank.com/challenges/counting-valleys/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=warmup
    public static int countingValleys(int steps, String path) {
        int uCount = 0;
        int seaLevel;
        int result = 0;

        for (int i = 0; i < path.length(); i++) {
            seaLevel = uCount;
            if (path.charAt(i) == 'D') {
                uCount--;
                if (seaLevel == 0) {
                    result++;
                }
            } else {
                uCount++;
            }
            System.out.println(String.format("Index is: %d, U is: %d, Sea Level is: %d", i, uCount, seaLevel));
        }
        return result;
    }
}
