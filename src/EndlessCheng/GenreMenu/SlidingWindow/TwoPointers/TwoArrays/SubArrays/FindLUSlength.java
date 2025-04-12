package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.SubArrays;

public class FindLUSlength {

    // https://leetcode.cn/problems/longest-uncommon-subsequence-ii/solutions/2813217/mei-ju-pan-duan-zi-xu-lie-pythonjavaccgo-8256/
    public int findLUSlength(String[] strs) {
        int ans = -1;

        // continue 只能跳出當前層，想跳出兩層或多層中的指定層，就在指定層循環做 next 標記
        next:
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() <= ans) { // 不會讓 ans 變大
                continue;
            }
            for (int j = 0; j < strs.length; j++) {
                if (j != i && isSubseq(strs[i], strs[j])) {
                    continue next; // 當前 strs[i] 並非獨有子序列，所以繼續枚舉下一個 i
                }
            }
            ans = strs[i].length();
        }
        return ans;
    }

    // 判斷 s 是否為 t 的子序列
    private boolean isSubseq(String s, String t) {
        int i = 0;
        for (char c : t.toCharArray()) {
            if (s.charAt(i) == c && ++i == s.length()) { // 所有字符匹配完畢
                return true; // s 是 t 的子序列
            }
        }
        return false;
    }


}
