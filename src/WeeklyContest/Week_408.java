package WeeklyContest;

public class Week_408 {
    // https://leetcode.cn/problems/find-if-digit-game-can-be-won/solutions/2860279/jian-ji-xie-fa-pythonjavacgo-by-endlessc-i5b5/
    public boolean canAliceWin(int[] nums) {
        int s = 0;
        for (int x : nums) {
            s += x < 10 ? x : -x;
        }
        return s != 0;
    }


    // https://leetcode.cn/problems/find-the-count-of-numbers-which-are-not-special/solutions/2860276/yu-chu-li-zhi-shu-o1hui-da-pythonjavacgo-7xaq/
    private static final int MX = 31622;
    private static final int[] PI = new int[MX + 1];

    // 預處理質數
    static {
        for (int i = 2; i <= MX; i++) {
            if (PI[i] == 0) { // i 是質數
                PI[i] = PI[i - 1] + 1;
                for (int j = i * i; j <= MX; j += i) {
                    PI[j] = -1; // 標記合數
                }
            } else {
                PI[i] = PI[i - 1];
            }
        }
    }

    // 只有質數的平方可能符合題目特殊數字條件
    // 因為 p^2 恰好有兩個真因數 1 和 p。
    // 而其他的數，1 沒有真因數，質數只有 1 個真因數，不是 1 不是質數也不是質數的平方的數有至少三個真因數。
    // 所以區間 [0,i] 內的特殊數字個數等於：[0,⌊根號i⌋] 中有多少個質數。
    // 而區間 [r,l] 內的特殊數字個數答案即為：0到根號r之間的質數數量，減去0到根號l-1之間的質數數量
    public int nonSpecialCount(int l, int r) {
        return r - l + 1 - (PI[(int) Math.sqrt(r)] - PI[(int) Math.sqrt(l - 1)]);
    }


    // https://leetcode.cn/problems/count-the-number-of-substrings-with-dominant-ones/solutions/2860198/mei-ju-zi-chuan-zhong-de-0-de-ge-shu-pyt-c654/
    public int numberOfSubstrings(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int m = 0; // 0下標
        int[] a = new int[n + 1];

        // 找出所有0下標
        for (int i = 0; i < n; i++) {
            if (s[i] == '0') {
                a[m++] = i;
            }
        }

        int tot1 = n - m; // 目前所有1個數
        a[m] = n; // 哨兵

        int ans = 0;
        int i = 0;
        for (int left = 0; left < n; left++) { // 枚舉子串左端點
            if (s[left] == '1') {
                ans += a[i] - left;
            }
            for (int k = i; k < m; k++) { // 枚舉子串0下標
                int cnt0 = k - i + 1; // 子串0數量
                if (cnt0 * cnt0 > tot1) { // 不可能符合題目要求，直接break
                    break;
                }
                int cnt1 = a[k] - left - (k - i); // 子串1數量
                ans += Math.max(a[k + 1] - a[k] - Math.max(cnt0 * cnt0 - cnt1, 0), 0);
            }
            if (s[left] == '0') {
                i++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/check-if-the-rectangle-corner-is-reachable/solutions/2860214/deng-jie-zhuan-huan-bing-cha-ji-pythonja-yf9y/
    public boolean canReachCorner(int x, int y, int[][] circles) {
        int n = circles.length;
        // 並查集中的 n 表示左邊界或上邊界，n+1 表示下邊界或右邊界
        UnionFind uf = new UnionFind(n + 2);
        for (int i = 0; i < n; i++) {
            int[] c = circles[i];
            int ox = c[0], oy = c[1], r = c[2];
            if (ox <= r || oy + r >= y) { // 圓 i 和左邊界或上邊界有交集
                uf.merge(i, n);
            }
            if (oy <= r || ox + r >= x) { // 圓 i 和下邊界或右邊界有交集
                uf.merge(i, n + 1);
            }
            for (int j = 0; j < i; j++) {
                int[] q = circles[j];
                if ((long) (ox - q[0]) * (ox - q[0]) + (long) (oy - q[1]) * (oy - q[1]) <= (long) (r + q[2]) * (r + q[2])) {
                    uf.merge(i, j); // 圓 i 和圓 j 有交集
                }
            }
            if (uf.find(n) == uf.find(n + 1)) { // 無法到達終點
                return false;
            }
        }
        return true;
    }


    class UnionFind {
        private final int[] fa;

        public UnionFind(int size) {
            fa = new int[size];
            for (int i = 1; i < size; i++) {
                fa[i] = i;
            }
        }

        public int find(int x) {
            if (fa[x] != x) {
                fa[x] = find(fa[x]);
            }
            return fa[x];
        }

        public void merge(int x, int y) {
            fa[find(x)] = find(y);
        }
    }

}


