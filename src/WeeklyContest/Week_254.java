package WeeklyContest;

import java.util.Arrays;

class Week_254 {
    // https://leetcode.cn/problems/number-of-strings-that-appear-as-substrings-in-word/submissions/
    public int numOfStrings(String[] patterns, String word) {
        int ans = 0;
        for (String s : patterns) {
            if (word.contains(s)) ans++;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1968.Array%20With%20Elements%20Not%20Equal%20to%20Average%20of%20Neighbors/README.md
    public int[] rearrangeArray(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int m = (n + 1) >> 1;  // 找中位數
        int[] ans = new int[n];
        for (int i = 0, j = 0; i < n; i += 2, j++) {
            ans[i] = nums[j];
            if (j + m < n) { // 比中位數大的放ans奇數位，其他放ans偶數位
                ans[i + 1] = nums[j + m];
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-non-zero-product-of-the-array-elements/solution/cpython3java-zhao-gui-lu-tan-xin-kuai-su-iu4f/
    // 數學證明：https://leetcode.cn/problems/minimum-non-zero-product-of-the-array-elements/solution/tan-xin-ji-qi-shu-xue-zheng-ming-by-endl-uumv/
    private final long MOD = (long) (1e9 + 7);

    public int minNonZeroProduct(int p) {
        long max_num = (1L << p) - 1;  // lL有64位，1只有32位，要寫成1L
        long x = max_num - 1;
        long n = (1L << (p - 1)) - 1;

        long res = max_num % MOD * quick_mul(x, n) % MOD;
        return (int) res;
    }

    public long quick_mul(long x, long n) {
        x %= MOD;
        long res = 1L;
        while (n > 0) {
            if ((n & 1) != 0) res = (res * x) % MOD;
            x = (x * x) % MOD;
            n >>= 1;
        }
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1970.Last%20Day%20Where%20You%20Can%20Still%20Cross/README.md
    // https://leetcode.cn/problems/last-day-where-you-can-still-cross/solution/-by-gry-h40b/
    // 逆序並查集
    // 問能否從第一行走到最後一行，等價於第一行與最後一行是否連通，那麼可以使用並查集來解決問題
    // 如果順序遍歷數組，那麼就是將一個全連通的圖，逐條邊刪除，但是並查集對於刪除操作只能刪除葉子節點的邊
    // 所以選擇倒序遍歷 cells 數組，就可以不斷地向圖中增加邊
    private int[] p;
    private int row;
    private int col;
    private boolean[][] grid;
    private int[][] dirs = new int[][]{{0, -1}, {0, 1}, {1, 0}, {-1, 0}};

    public int latestDayToCross(int row, int col, int[][] cells) {
        int n = row * col;
        this.row = row;
        this.col = col;

        // 初始化parent
        // 前後多加兩個虛擬節點，方便後續操作
        p = new int[n + 2];
        for (int i = 0; i < p.length; ++i) {
            p[i] = i;
        }

        // 1. 初始化假設所有節點為水域 (false)
        grid = new boolean[row][col];

        // 超級原點 top，和第0行所有的點相連
        // 超級原點 bottom，和第row-1行所有的點相連
        int top = n, bottom = n + 1;

        // 2. 倒序遍歷 cells 數組
        for (int k = cells.length - 1; k >= 0; --k) {
            // 3. 將當前節點變為陸地 (true)
            int i = cells[k][0] - 1, j = cells[k][1] - 1;
            grid[i][j] = true; // true 表示將當前節點變為陸地

            // 4. 給當前節點的四個方向相鄰的陸地添加邊
            for (int[] e : dirs) {
                if (check(i + e[0], j + e[1])) {
                    p[find(i * col + j)] = find((i + e[0]) * col + j + e[1]);
                }
            }

            if (i == 0) {
                p[find(i * col + j)] = find(top);
            }
            if (i == row - 1) {
                p[find(i * col + j)] = find(bottom);
            }

            // 5. 判斷第一行 (top) 與最後一行 (bottom) 是否連通
            if (find(top) == find(bottom)) {
                return k;
            }
        }
        return 0;
    }

    // 並查集模板
    private int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }

    private boolean check(int i, int j) {
        return i >= 0 && i < row && j >= 0 && j < col && grid[i][j];
    }
}

