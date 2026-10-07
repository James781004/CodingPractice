package EndlessCheng.GenreMenu.DP.Print;

public class ShortestSuperstring {

    // https://leetcode.cn/problems/find-the-shortest-superstring/solutions/2347547/gong-shui-san-xie-zhuang-ya-dp-yun-yong-p6hlz/
    public String shortestSuperstring(String[] ws) {
        int n = ws.length, mask = 1 << n;
        int[][] g = new int[n][n]; // 預處理二維數組 g 來表示字符串 ws[i] 和 ws[j] 的重疊部分的長度
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                String a = ws[i], b = ws[j];
                int l1 = a.length(), l2 = b.length(), len = Math.min(l1, l2);
                for (int k = len; k >= 1; k--) {
                    if (a.substring(l1 - k).equals(b.substring(0, k))) {
                        g[i][j] = k;
                        break;
                    }
                }
            }
        }

        // f[s][i] 代表當前狀態為 s 且當前最後一個使用到的字符串為 ws[i]
        // （當前超級串 ans 的結尾部分為 ws[i]）時的最大重合長度。
        // 使用二維數組 p[s][i] 記錄每個狀態是由哪個前驅轉移而來：
        // 若有 p[s][i]=j，代表取得最大重疊長度過程中，字符串 ws[j] 接在 ws[i] 後面。
        int[][] f = new int[mask][n], p = new int[mask][n];
        for (int s = 0; s < mask; s++) {
            for (int i = 0; i < n; i++) {
                if (((s >> i) & 1) == 0) continue;
                for (int j = 0; j < n; j++) {
                    if (((s >> j) & 1) == 1) continue;
                    if (f[s | (1 << j)][j] <= f[s][i] + g[i][j]) {
                        f[s | (1 << j)][j] = f[s][i] + g[i][j];
                        p[s | (1 << j)][j] = i;
                    }
                }
            }
        }
        int max = f[mask - 1][0], idx = 0, last = -1, status = mask - 1;
        for (int i = 1; i < n; i++) {
            if (max < f[mask - 1][i]) {
                max = f[mask - 1][i];
                idx = i;
            }
        }
        String ans = "";
        while (status != 0) {
            if (last == -1) ans = ws[idx];
            else ans = ws[idx].substring(0, ws[idx].length() - g[idx][last]) + ans;
            last = idx;
            idx = p[status][idx];
            status ^= (1 << last);
        }
        return ans;
    }

}
