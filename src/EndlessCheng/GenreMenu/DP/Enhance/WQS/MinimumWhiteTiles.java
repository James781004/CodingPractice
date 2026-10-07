package EndlessCheng.GenreMenu.DP.Enhance.WQS;

public class MinimumWhiteTiles {

    // https://leetcode.cn/problems/minimum-white-tiles-after-covering-with-carpets/solutions/3981522/xiao-bai-si-lu-hua-fen-xing-dp-lian-xu-z-yezd/
    class Solution {
        // 輔助類別：用來記錄 (值 b, 地毯使用張數 x)
        private static class Pair {
            int b; // 當前狀態下的目標值（白磚數/帶懲罰的覆蓋效益）
            int x; // 使用地毯的張數

            Pair(int b, int x) {
                this.b = b;
                this.x = x;
            }
        }

        private static final int INF = 0x3f3f3f3f;

        public int minimumWhiteTiles(String floor, int numCarpets, int carpetLen) {
            int n = floor.length();
            int[] s = new int[n + 1]; // 前綴和陣列，s[i] 表示 floor[0...i-1] 中白磚 ('1') 的數量
            for (int i = 0; i < n; i++) {
                s[i + 1] = s[i] + (floor.charAt(i) - '0');
            }

            // 1. 先測試當獎勵/懲罰 k = 0 時的情況
            Pair res = dp(floor, numCarpets, carpetLen, n, s, 0);
            // 如果手頭地毯隨便用都不會超過 numCarpets，直接返回結果
            if (res.x <= numCarpets) {
                return res.b;
            }

            // 2. WQS 二分查找合適的二分值 (mid)
            int ans = 0;
            int left = -s[n] - 1, right = 0;

            while (left + 1 < right) {
                int mid = left + (right - left) / 2;
                res = dp(floor, numCarpets, carpetLen, n, s, mid);

                if (res.x <= numCarpets) {
                    // 加回之前在 DP 裡扣除/調整的 k * numCarpets
                    ans = mid * numCarpets + res.b;
                    left = mid;  // 嘗試減小懲罰/增加獎勵
                } else {
                    right = mid; // 用的地毯太多了，調大懲罰
                }
            }

            return ans;
        }

        // 核心 DP：給定參數 k，算出最佳值與地毯使用數量
        private Pair dp(String floor, int numCarpets, int carpetLen, int n, int[] s, int k) {
            Pair[] f = new Pair[n + 1];
            f[0] = new Pair(0, 0);

            // 初始化前 carpetLen 長度的狀態
            for (int i = 1; i <= carpetLen && i <= n; i++) {
                Pair opt0 = new Pair(s[i], 0);    // 不使用地毯，剩下的白磚數為 s[i]
                Pair opt1 = new Pair(-k, 1);      // 鋪一張地毯蓋住前面全部，白磚數為 0，價值加上 -k

                if (less(opt0, opt1)) {
                    f[i] = opt1;
                } else {
                    f[i] = opt0;
                }
            }

            Pair mn = new Pair(INF, 0);
            // 動態規劃填表
            for (int i = carpetLen; i <= n; i++) {
                int j = i - carpetLen;

                // 考慮在 j 到 i 之間鋪一張地毯的候選狀態
                Pair cand = new Pair(f[j].b - s[i], f[j].x);
                if (less(mn, cand)) {
                    mn = cand;
                }

                Pair opt0 = new Pair(s[i], 0);
                Pair optCarpet = new Pair(mn.b + s[i] - k, mn.x + 1);

                if (less(opt0, optCarpet)) {
                    f[i] = optCarpet;
                } else {
                    f[i] = opt0;
                }
            }

            return f[n];
        }

        // 比較函數：判斷 p 是否「劣於」q (如果 p 比 q 劣，返回 true 意即 p 要被 q 替換)
        // 我們希望白磚數/目標值儘可能小；若值相同，希望地毯使用張數 x 儘可能小
        private boolean less(Pair p, Pair q) {
            if (p.b > q.b) {
                return true;
            }
            return p.b == q.b && p.x > q.x;
        }
    }

}
