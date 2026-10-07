package EndlessCheng.GenreMenu.DP.StateMachine.Advanced;

public class NumOfSubsequences {

    // https://leetcode.cn/problems/maximum-number-of-subsequences-after-one-inserting/solutions/3734800/fu-yong-115-ti-dai-ma-qian-hou-zhui-fen-gtkqz/
    public long numOfSubsequences(String S) {
        char[] s = S.toCharArray();
        int t = 0;
        for (char c : s) {
            if (c == 'T') {
                t++;
            }
        }

        long l = 0, lc = 0, lct = 0, c = 0, ct = 0, lt = 0;
        for (char b : s) {
            if (b == 'L') { // 定義 l 表示遍歷過的 L 的個數。每次遍歷到 L 的時候，就把 l 增加 1
                l++;
            } else if (b == 'C') { // 定義 lc 表示遍歷過的 LC 子序列的個數。每次遍歷到 C 的時候，就把 lc 增加 l
                lc += l;
                c++;
            } else if (b == 'T') { // 定義 lct 表示遍歷過的 LCT 子序列的個數。每次遍歷到 T 的時候，就把 lct 增加 lc
                lct += lc;
                ct += c; // 遍歷到 T 的時候，ct 增加 c
                t--;
            }
            lt = Math.max(lt, l * t);
        }

        // 返回最多插入一個字母後，字符串中可以形成的 "LCT" 子序列的最大數量
        return lct + Math.max(Math.max(ct, lc), lt);
    }


}
