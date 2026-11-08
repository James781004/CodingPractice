package EndlessCheng.GenreMenu.DataStructure.EnumerateDiagonal.Basic;

public class CountPalindromicSubsequence {

    // https://leetcode.cn/problems/unique-length-3-palindromic-subsequences/solutions/869697/mei-ju-zhong-jian-zi-fu-ran-hou-gen-ju-q-186d/
    public int countPalindromicSubsequence(String S) {
        char[] s = S.toCharArray();
        int n = s.length;

        // 統計 [1,n-1] 每個字母的個數
        int[] sufCnt = new int[26];
        for (int i = 1; i < n; i++) {
            sufCnt[s[i] - 'a']++;
        }

        boolean[] preHas = new boolean[26];
        boolean[][] has = new boolean[26][26];
        int ans = 0;
        for (int i = 1; i < n - 1; i++) { // 枚舉中間字母 mid
            int mid = s[i] - 'a';
            sufCnt[mid]--; // 撤銷 mid 的計數，sufCnt 剩下的就是後綴 [i+1,n-1] 每個字母的個數
            preHas[s[i - 1] - 'a'] = true; // 記錄前綴 [0,i-1] 有哪些字母
            for (int alpha = 0; alpha < 26; alpha++) { // 枚舉兩側字母 alpha
                // 判斷 mid 的左右兩側是否都有字母 alpha
                if (preHas[alpha] && sufCnt[alpha] > 0 && !has[mid][alpha]) {
                    has[mid][alpha] = true;
                    ans++;
                }
            }
        }
        return ans;
    }

}
