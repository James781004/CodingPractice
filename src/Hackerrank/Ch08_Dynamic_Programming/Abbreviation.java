package Hackerrank.Ch08_Dynamic_Programming;

public class Abbreviation {
    public static String abbreviation(String a, String b) {
        int m = a.length();
        int n = b.length();
        if (m < n) {
            return "NO";
        }

        boolean[][] isValid = new boolean[m + 1][n + 1];
        isValid[0][0] = true;

        for (int i = 1; i <= m; i++) {
            char ca = a.charAt(i - 1);
            // mark if this character can be skipped
            boolean canBeSkipped = Character.isLowerCase(ca);
            isValid[i][0] = canBeSkipped;

            for (int j = 1; j <= n; j++) {
                char cb = b.charAt(j - 1);
                boolean matches = Character.toUpperCase(ca) == cb;
                isValid[i][j] = (matches && isValid[i - 1][j - 1]) ||
                        (canBeSkipped && isValid[i - 1][j]);
            }
        }
//    System.out.println(
//        Arrays
//          .deepToString(isValid)
//          .replace("true", "1")
//          .replace("false", "0")
//          .replace("],", "],\n"));
        return isValid[m][n] ? "YES" : "NO";
    }


}
