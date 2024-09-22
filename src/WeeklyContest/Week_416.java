package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Week_416 {
    // https://leetcode.cn/problems/report-spam-message/solutions/2925853/ha-xi-ji-he-jian-ji-xie-fa-pythonjavacgo-1noc/
    public boolean reportSpam(String[] message, String[] bannedWords) {
        Set<String> banned = new HashSet<>(Arrays.asList(bannedWords));
        int cnt = 0;
        for (String s : message) {
            if (banned.contains(s) && ++cnt > 1) {
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/minimum-number-of-seconds-to-make-mountain-height-zero/solutions/2925848/er-fen-da-an-pythonjavacgo-by-endlessche-myg4/
    public long minNumberOfSeconds(int mountainHeight, int[] workerTimes) {
        int maxT = 0;
        for (int t : workerTimes) {
            maxT = Math.max(maxT, t);
        }
        int h = (mountainHeight - 1) / workerTimes.length + 1;
        long left = 0;
        long right = (long) maxT * h * (h + 1) / 2;
        while (left + 1 < right) {
            long mid = (left + right) / 2;
            if (check(mid, mountainHeight, workerTimes)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    private boolean check(long m, int leftH, int[] workerTimes) {
        for (int t : workerTimes) {
            leftH -= (int) ((Math.sqrt(m / t * 8 + 1) - 1) / 2);
            if (leftH <= 0) {
                return true;
            }
        }
        return false;
    }

    // https://leetcode.cn/problems/count-substrings-that-can-be-rearranged-to-contain-a-string-ii/solutions/2925828/on-hua-dong-chuang-kou-qiu-ge-shu-python-0x7a/
    public long validSubstringCount(String S, String T) {
        if (S.length() < T.length()) {
            return 0;
        }

        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int[] cnt = new int[26]; // t 的字母出現次數與 s 的字母出現次數之差
        for (char b : t) {
            cnt[b - 'a']++;
        }
        int less = 0; // 統計窗口內有多少個字母的出現次數比 t 的少
        for (int c : cnt) {
            if (c > 0) {
                less++;
            }
        }

        long ans = 0;
        int left = 0;
        for (char b : s) {
            if (--cnt[b - 'a'] == 0) {
                // 窗口內 b 的出現次數和 t 一樣
                less--;
            }
            while (less == 0) { // 窗口符合要求
                if (cnt[s[left++] - 'a']++ == 0) {
                    // s[left] 移出窗口後，窗口內 s[left] 的出現次數比 t 的少
                    less++;
                }
            }
            ans += left;
        }
        return ans;
    }

   
}


