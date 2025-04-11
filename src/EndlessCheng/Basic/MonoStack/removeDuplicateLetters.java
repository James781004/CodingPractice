package EndlessCheng.Basic.MonoStack;

public class removeDuplicateLetters {

    // https://leetcode.cn/problems/remove-duplicate-letters/solutions/2381483/gen-zhao-wo-guo-yi-bian-shi-li-2ni-jiu-m-zd6u/
    public String removeDuplicateLetters(String S) {
        char[] s = S.toCharArray();
        int[] left = new int[26];
        for (char c : s) {
            left[c - 'a']++; // 統計每個字母的出現次數
        }
        StringBuilder ans = new StringBuilder(26);
        boolean[] inAns = new boolean[26];
        for (char c : s) {
            left[c - 'a']--;
            if (inAns[c - 'a']) { // ans 中不能有重復字母
                continue;
            }
            // 設 x = ans.charAt(ans.length() - 1)，
            // 如果 c < x，且右邊還有 x，那麼可以把 x 去掉，因為後面可以重新把 x 加到 ans 中
            while (!ans.isEmpty() && c < ans.charAt(ans.length() - 1) && left[ans.charAt(ans.length() - 1) - 'a'] > 0) {
                inAns[ans.charAt(ans.length() - 1) - 'a'] = false; // 標記 x 不在 ans 中
                ans.deleteCharAt(ans.length() - 1);
            }
            ans.append(c); // 把 c 加到 ans 的末尾
            inAns[c - 'a'] = true; // 標記 c 在 ans 中
        }
        return ans.toString();
    }


}
