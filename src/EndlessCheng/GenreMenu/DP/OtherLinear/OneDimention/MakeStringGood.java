package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.Arrays;

public class MakeStringGood {

    // https://leetcode.cn/problems/minimum-operations-to-make-character-frequencies-equal/solutions/3020630/mei-ju-dpfen-lei-tao-lun-pythonjavacgo-b-ahfn/
    public int makeStringGood(String s) {
        int[] cnt = new int[26];
        for (char b : s.toCharArray()) {
            cnt[b - 'a']++;
        }
        int m = Arrays.stream(cnt).max().getAsInt();

        int ans = s.length(); // target = 0 時的答案
        int[] f = new int[27]; // f[i] 表示操作第 i 到 25 種字母的最小操作次數
        for (int target = 1; target <= m; target++) {
            f[25] = Math.min(cnt[25], Math.abs(cnt[25] - target));
            for (int i = 24; i >= 0; i--) {
                int x = cnt[i];
                int y = cnt[i + 1];
                // 單獨操作 x（變成 target 或 0）
                f[i] = f[i + 1] + Math.min(x, Math.abs(x - target));
                // x 變成 target 或 0，y 變成 target
                if (y < target) { // 只有當 y 需要變大時，才去執行第三種操作
                    int t = x > target ? target : 0;
                    f[i] = Math.min(f[i], f[i + 2] + Math.max(x - t, target - y));
                }
            }
            ans = Math.min(ans, f[0]);
        }
        return ans;
    }


}
