package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Challenge;

public class MinFlips {

    // https://leetcode.cn/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/solutions/2339391/java-sliding-window-extensionhua-dong-ch-l5y3/
    public int minFlips(String s) {
        int n = s.length();
        s = s + s;
        StringBuilder alt1 = new StringBuilder();
        StringBuilder alt2 = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            alt1.append(i % 2 == 0 ? "0" : "1"); // 01010101010101....
            alt2.append(i % 2 == 0 ? "1" : "0"); // 10101010101010....
        }

        int res = s.length();
        int diff1 = 0;
        int diff2 = 0;
        int l = 0;
        for (int r = 0; r < s.length(); r++) {
            // 運用left, right pointer指針檢查當前字符串字符與目標字符串的差異diff
            if (s.charAt(r) != alt1.charAt(r)) {
                diff1++;
            }
            if (s.charAt(r) != alt2.charAt(r)) {
                diff2++;
            }

            // 當left pointer移動, 檢查其指向字符串字符與目標字符串是否有差異，若有差異，下一個滑窗內diff -= 1
            if ((r - l + 1) > n) {
                if (s.charAt(l) != alt1.charAt(l)) {
                    diff1--;
                }
                if (s.charAt(l) != alt2.charAt(l)) {
                    diff2--;
                }
                l++;
            }

            // 滑窗符合大小時，記錄並檢查當前結果，記錄最小差異值
            if ((r - l + 1) == n) {
                res = Math.min(res, Math.min(diff1, diff2));
            }
        }

        return res;
    }


}
