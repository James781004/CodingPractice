package WeeklyContest;

import java.util.ArrayList;
import java.util.List;

public class Week_299 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2319.Check%20if%20Matrix%20Is%20X-Matrix/README.md
    public boolean checkXMatrix(int[][] grid) {
        int n = grid.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j || i + j == n - 1) {
                    if (grid[i][j] == 0) return false;
                } else if (grid[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2320.Count%20Number%20of%20Ways%20to%20Place%20Houses/README.md
    // 由於街道兩側房子的擺放互不影響，因此，我們可以只考慮一側的擺放情況，最後將一側的方案數平方取模得到最終結果。
    public int countHousePlacements(int n) {
        final int mod = (int) 1e9 + 7;
        int[] f = new int[n]; // 放置前 i+1 個地塊，且最後一個地塊放置房子的方案數
        int[] g = new int[n]; // 放置前 i+1 個地塊，且最後一個地塊不放置房子的方案數
        f[0] = 1;
        g[0] = 1;

        for (int i = 1; i < n; i++) {
            f[i] = g[i - 1]; // 如果第 i+1 個地塊放置房子，那麼第 i 個地塊必須不放置房子，因此方案數 f[i] = g[i - 1]
            g[i] = (f[i - 1] + g[i - 1]) % mod; // 如果第 i+1 個地塊不放置房子，，那麼第 i 個地塊可以放置房子，也可以不放置房子子，因此方案數 g[i] = f[i - 1] + g[i - 1]
        }
        long v = (f[n - 1] + g[n - 1]) % mod;
        return (int) (v * v % mod); // (f[n - 1] + g[n - 1]) 平方取模即為答案
    }


    // 線性 DP
    // https://www.bilibili.com/video/BV1pW4y1r7xs/
    // https://leetcode.cn/problems/count-number-of-ways-to-place-houses/solution/d-by-endlesscheng-gybx/
    public int countHousePlacements2(int n) {
        int MOD = (int) 1e9 + 7;
        int[] f = helper();

        // 由於兩側的房屋互相獨立，根據乘法原理，答案為 f[n] ^ 2
        return (int) ((long) f[n] * f[n] % MOD);
    }

    // 定義 f[i] 表示前 i 個地塊的放置方案數，
    // 其中第 i 個地塊可以放房子，也可以不放房子
    private int[] helper() {
        final int MOD = (int) 1e9 + 7, MX = (int) 1e4 + 1;
        final int[] f = new int[MX];

        // 考慮第 i 個地塊：
        //
        // 若不放房子，那麼第 i−1 個地塊可放可不放，則有 f[i]=f[i−1]；
        // 若放房子，那麼第 i−1 個地塊無法放房子，第 i−2 個地塊可放可不放，
        // 則有 f[i]=f[i−2]。
        // 邊界為
        // f[0]=1，空也是一種方案；
        // f[1]=2，放與不放兩種方案。
        f[0] = 1;
        f[1] = 2;
        for (int i = 2; i < MX; ++i) {
            f[i] = (f[i - 1] + f[i - 2]) % MOD;
        }
        return f;
    }


    // https://leetcode.cn/problems/maximum-score-of-spliced-array/solution/by-endlesscheng-fm8l/
    public int maximumsSplicedArray(int[] nums1, int[] nums2) {
        return Math.max(solve(nums1, nums2), solve(nums2, nums1));
    }

    private int solve(int[] nums1, int[] nums2) {
        int s1 = 0, maxSum = 0;
        for (int i = 0, s = 0; i < nums1.length; ++i) {
            s1 += nums1[i];  // s1表示以i為結尾的子數組和
            s = Math.max(s + nums2[i] - nums1[i], 0);  // s表示前一次最大差值，如果元素交換後還變得更小，就乾脆不交換
            maxSum = Math.max(maxSum, s);  // maxSum表示兩數組元素交換的最大差值和
        }
        return s1 + maxSum;
    }


    // https://leetcode.cn/problems/minimum-score-after-removals-on-a-tree/solution/dfs-shi-jian-chuo-chu-li-shu-shang-wen-t-x1kk/
    int[] nums, xr, in, out; // xr[i]為以i為根的子樹異或和,in和out分別是節點i dfs遞歸進棧和出棧的時間點
    int clock = 0;  // 全局時間戳(以0為根的dfs序)
    List<Integer>[] list;   // 存邊

    public int minimumScore(int[] _nums, int[][] edges) {
        /*
        DFS時間戳+枚舉新樹根節點+異或性質運用:
        題目是將整棵樹切成3部分,我們就假設0為樹原來的根節點(確定根節點方便求異或),切割之後另外兩棵子樹根節點為x與y
        假設x是y的根節點,由於枚舉的是不同的點,因此不存在互相包含的情況
        此時有:in[x]<in[y]<=out[y]<=out[x] 其中in[y]<=out[y]必定成立,因此x是y的根節點等價於in[x]<in[y]&&out[y]<=out[x]
        利用時間戳的可以用來快速判斷x與y節點之間的父子關系(以0為原始根節點)
        我們枚舉刪除邊後產生的兩個新的根節點,設a,b,c分別為0,x,y為根的切斷後子樹異或和,有以下3種情況:
        1.x是y的根節點且位於初始根節點0的同側->異或和分別為:c=xr[y],b=xr[x]^xr[y],a=xr[0]^xr[x]
        2.y是x的根節點且位於初始根節點0的同側->異或和分別為:b=xr[x],c=xr[y]^xr[x],a=xr[0]^xr[y]
        3.y與x分局初始根節點0的異側->異或和分別為:c=xr[y],b=xr[x],a=xr[0]^xr[x]^xr[y]
        分別枚舉x∈[1,n],y∈[x+1,n],維護異或和差值的最小值
         */
        nums = _nums;
        int res = 0x3f3f3f3f, n = nums.length;
        xr = new int[n];
        in = new int[n];
        out = new int[n];
        list = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            int a = e[0], b = e[1];
            list[a].add(b);
            list[b].add(a);
        }
        dfs(0, -1); // 初始化各個節點時間戳以及求每個節點為根的異或和
        // 枚舉刪除邊後產生的兩個新的根節點x與y
        int a, b, c;    // 設a,b,c分別為0,x,y為根切斷後子樹的異或和
        for (int x = 1; x < n; x++) {
            for (int y = x + 1; y < n; y++) {
                if (isParent(x, y)) {   // x是y根節點，且都在同側
                    a = xr[0] ^ xr[x];
                    b = xr[x] ^ xr[y];
                    c = xr[y];
                } else if (isParent(y, x)) {    // y是x根節點，且都在同側
                    a = xr[0] ^ xr[y];
                    b = xr[x];
                    c = xr[y] ^ xr[x];
                } else {    // x與y分布異側
                    a = xr[0] ^ xr[x] ^ xr[y];
                    b = xr[x];
                    c = xr[y];
                }
                // 維護異或和最大差值的最小值
                int max = Math.max(a, Math.max(b, c)), min = Math.min(a, Math.min(b, c));
                res = Math.min(res, max - min);
                if (res == 0) return 0; // 為0提前退出
            }
        }
        return res;
    }

    // 判斷x是否為y的父節點(不重合)
    private boolean isParent(int x, int y) {
        return in[x] < in[y] && out[y] <= out[x];
    }

    // 求節點時間戳和異或和:其中i為當前節點索引,fa為其父節點(用於避免走回頭路)
    private void dfs(int i, int fa) {
        xr[i] = nums[i];    // xr[i]初始化為節點i的值
        in[i] = ++clock;    // 記錄進入的時間戳
        for (int next : list[i]) {
            if (next != fa) {   // 不走回頭路
                dfs(next, i);   // 求出以next為根的異或和與時間戳
                xr[i] ^= xr[next];  // xr[i]添上xr[next]
            }
        }
        out[i] = clock; // 記錄i出棧時間戳
    }
}
