package EndlessCheng.GenreMenu.Backtracking.Backtracking.Divide;

import java.util.ArrayList;
import java.util.List;

public class RestoreIpAddresses {

    // https://leetcode.cn/problems/restore-ip-addresses/solutions/3727037/liang-chong-fang-fa-san-zhong-xun-huan-h-hxak/
    public List<String> restoreIpAddresses(String s) {
        List<String> ans = new ArrayList<>();
        int[] path = new int[4]; // path[i] 表示第 i 段的結束位置 + 1（右開區間）
        dfs(0, 0, 0, s, s.length(), path, ans);
        return ans;
    }

    // 分割 s[i] 到 s[n-1]，現在在第 j 段（j 從 0 開始），數值為 ipVal
    private void dfs(int i, int j, int ipVal, String s, int n, int[] path, List<String> ans) {
        if (i == n) { // s 分割完畢
            if (j == 4) { // 必須有 4 段
                int a = path[0], b = path[1], c = path[2];
                ans.add(s.substring(0, a) + "." + s.substring(a, b) + "." + s.substring(b, c) + "." + s.substring(c));
            }
            return;
        }

        if (j == 4) { // j=4 的時候必須分割完畢，不能有剩余字符
            return;
        }

        // 手動把字符串轉成整數，這樣字符串轉整數是嚴格 O(1) 的
        ipVal = ipVal * 10 + (s.charAt(i) - '0');
        if (ipVal > 255) { // 不合法
            return;
        }

        // 不分割，不以 s[i] 為這一段的結尾
        if (ipVal > 0) { // 無前導零
            dfs(i + 1, j, ipVal, s, n, path, ans);
        }

        // 分割，以 s[i] 為這一段的結尾
        path[j] = i + 1; // 記錄下一段的開始位置
        dfs(i + 1, j + 1, 0, s, n, path, ans);
    }


}
