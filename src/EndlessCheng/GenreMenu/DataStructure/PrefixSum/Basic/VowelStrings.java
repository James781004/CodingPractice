package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class VowelStrings {

    // https://leetcode.cn/problems/count-vowel-strings-in-ranges/solutions/2093978/suan-fa-xiao-ke-tang-qian-zhui-he-by-end-n0mx/
    private static final String VOWEL = "aeiou";

    public int[] vowelStrings(String[] words, int[][] queries) {
        int[] sum = new int[words.length + 1];
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            sum[i + 1] = sum[i];
            if (VOWEL.indexOf(w.charAt(0)) != -1 && VOWEL.indexOf(w.charAt(w.length() - 1)) != -1) {
                sum[i + 1]++;
            }
        }

        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            ans[i] = sum[q[1] + 1] - sum[q[0]];
        }
        return ans;
    }

}
