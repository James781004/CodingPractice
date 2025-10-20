package EndlessCheng.GenreMenu.Backtracking.Backtracking.Search;

public class MaxProduct {

    // https://leetcode.cn/problems/maximum-product-of-the-length-of-two-palindromic-subsequences/solutions/992922/go-bao-sou-by-endlesscheng-ivn0/
    private int maxLen = 0;

    public int maxProduct(String s) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        dfs(0, s, sb1, sb2);
        return maxLen;
    }

    private void dfs(int idx, String s, StringBuilder sb1, StringBuilder sb2) {
        if (idx == s.length()) {
            if (sb1.length() * sb2.length() > maxLen && isPalindrom(sb1) && isPalindrom(sb2)) {
                maxLen = sb1.length() * sb2.length();
            }
            return;
        }
        // 都不選
        dfs(idx + 1, s, sb1, sb2);

        // 選擇1
        dfs(idx + 1, s, sb1.append(s.charAt(idx)), sb2);
        sb1.setLength(sb1.length() - 1);

        // 選擇2
        dfs(idx + 1, s, sb1, sb2.append(s.charAt(idx)));
        sb2.setLength(sb2.length() - 1);
    }

    private boolean isPalindrom(StringBuilder sb) {
        int left = 0;
        int right = sb.length() - 1;
        while (left < right) {
            if (sb.charAt(left) != sb.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

}
