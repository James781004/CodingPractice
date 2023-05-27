package WeeklyContest;

class Week_292 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2264.Largest%203-Same-Digit%20Number%20in%20String/README.md
    public String largestGoodInteger(String num) {
        for (int i = 9; i >= 0; i--) {
//            String ret = String.valueOf(i).repeat(3);
            String ret = String.valueOf(i) + String.valueOf(i) + String.valueOf(i);
            if (num.contains(ret)) {
                return ret;
            }
        }
        return "";
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2265.Count%20Nodes%20Equal%20to%20Average%20of%20Subtree/README.md
    private int ans;

    public int averageOfSubtree(TreeNode root) {
        ans = 0;
        dfs(root);
        return ans;
    }


    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] l = dfs(root.left);
        int[] r = dfs(root.right);
        int s = l[0] + r[0] + root.val; // 當前節點數值和
        int n = l[1] + r[1] + 1;  // 當前節點總數
        if (s / n == root.val) {
            ++ans;
        }
        return new int[]{s, n};
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    // https://leetcode.cn/problems/count-number-of-texts/solution/by-endlesscheng-gj8f/
    static class CountTexts {
        static final int MOD = (int) 1e9 + 7, MX = (int) 1e5 + 1;
        static final int[] f = new int[MX], g = new int[MX];

        // 題意及分析
        // https://leetcode.cn/problems/count-number-of-texts/solution/-by-mochi-ds-j5za/
        static {
            f[0] = g[0] = 1;
            f[1] = g[1] = 1;
            f[2] = g[2] = 2;
            f[3] = g[3] = 4;
            for (int i = 4; i < MX; i++) {  // 把相同字符分為一組，每組內只有一種字符
                // 對於字符不為 7 或 9 的情況，定義 f[i] 表示長為 i 的只有一種字符的字符串對應的文字信息種類數，
                // 可以將末尾的 1 個、2 個或 3 個字符單獨視作一個字母，那麼有轉移方程: f[i]=f[i−1]+f[i−2]+f[i−3]
                f[i] = (int) (((long) f[i - 1] + f[i - 2] + f[i - 3]) % MOD);

                // 對於字符為 7 或 9 的情況，定義 g[i] 表示長為 i 的只有一種字符的字符串對應的文字信息種類數，
                // 可以得到類似的轉移方程: g[i]=g[i−1]+g[i−2]+g[i−3]+g[i−4]
                g[i] = (int) (((long) g[i - 1] + g[i - 2] + g[i - 3] + g[i - 4]) % MOD);
            }
        }

        public int countTexts(String s) {
            int ans = 1, cnt = 0;
            for (int i = 0; i < s.length(); i++) {
                cnt++;
                int c = s.charAt(i);
                if (i == s.length() - 1 || c != s.charAt(i + 1)) {  // 根據乘法原理，把不同組的文字信息種類數相乘，得到答案
                    ans = (int) ((long) ans * (c != '7' && c != '9' ? f[cnt] : g[cnt]) % MOD);
                    cnt = 0;
                }
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/check-if-there-is-a-valid-parentheses-string-path/solution/tian-jia-zhuang-tai-hou-dfscpythonjavago-f287/
    char[][] grid;
    int m, n;
    int totalLen;
    boolean[][][] visited; // 用於標記某個格子某個括號狀態是否出現過，若出現過說明為重複路徑，剪枝

    public boolean hasValidPath(char[][] _grid) {
        grid = _grid;
        m = grid.length;
        n = grid[0].length;

        // 從起點到終點，往下走的次數是固定的，即 m−1 次，往右走的次數也是固定的，即 n−1 次，
        // 因此路徑長度（字符串長度）是一個定值，即 (m−1)+(n−1)+1 = m+n−1。
        totalLen = m + n + 1;
        visited = new boolean[m][n][totalLen];

        // 首先對於行列和是否為偶數  以及  第一個和最後一個的判斷，剪枝判斷
        // 由於字符串左括號和右括號的數目必須相同，因此字符串的長度為偶數，所以 totalLen (m+n−1) 必須是偶數
        if (grid[0][0] != '(' || totalLen % 2 == 1 || grid[m - 1][n - 1] != ')') return false;
        return dfs(0, 0, 0);
    }


    private boolean dfs(int i, int j, int cnt) {
        // 越界
        if (i < 0 || i >= m || j < 0 || j >= n) return false;

        // 到達終點，看看最後 cnt 是否已經等於 0
        if (i == m - 1 && j == n - 1) return cnt == 0;

        // 計算當前括號狀態值
        // 變量 cnt 表示括號字符串的平衡度：遇到左括號就 +1，遇到右括號就 −1。
        // 那麼合法字符串等價於任意時刻 cnt ≥ 0 且最後 cnt = 0。
        cnt += grid[i][j] == '(' ? 1 : -1;
        // 左括號不夠 || 左括號超標 || 已經求解過該狀態
        if (cnt < 0 || cnt > totalLen - i - j + 1 || visited[i][j][cnt]) return false;
        // 標記已訪問
        visited[i][j][cnt] = true;

        // 看右邊和下邊其中一個滿足條件即可
        return dfs(i + 1, j, cnt) || dfs(i, j + 1, cnt);
    }

}

