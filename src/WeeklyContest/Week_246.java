package WeeklyContest;

import java.util.BitSet;

class Week_246 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1903.Largest%20Odd%20Number%20in%20String/README.md
    public String largestOddNumber(String num) {
        for (int i = num.length() - 1; i >= 0; --i) {
            int c = num.charAt(i) - '0';
            if ((c & 1) == 1) {
                return num.substring(0, i + 1);
            }
        }
        return "";
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1904.The%20Number%20of%20Full%20Rounds%20You%20Have%20Played/README.md
    public int numberOfRounds(String startTime, String finishTime) {
        int start = get(startTime), finish = get(finishTime);

        // 判斷是否超過了一天（即結束的時間小於開始的時間，對應到分鐘也同理）
        if (start > finish) {
            finish += 24 * 60;
        }

        // 1. 將開始時間替換為第一場完整game開始的時間
        // 如果 startMM % 15 != 0 說明在第一場完整game開始前必定有多余的時間
        // 因此需要給startMM 加上 一局game的時間15分鐘，但是此時原本的startMM + 15 之後可能會導致錯過原來的第一場game的開始時間
        // 因此在加15的基礎上，應該減去原來第一場完整game開始前多余的時間
        start = (start + 14) / 15;

        // 2. 將結束時間替換為最後一場完整game結束的時間
        finish /= 15;

        return Math.max(0, finish - start);
    }

    // 將所有的時間都轉化為分鐘數
    private int get(String s) {
        return Integer.parseInt(s.substring(0, 2)) * 60 + Integer.parseInt(s.substring(3));
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1905.Count%20Sub%20Islands/README.md
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length, n = grid1[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid1[i][j] == 0 && grid2[i][j] == 1) {
                    // 這個島嶼肯定不是子島，淹掉
                    dfs(grid2, i, j);
                }
            }
        }
        // 現在 grid2 中剩下的島嶼都是子島，計算島嶼數量
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    res++;
                    dfs(grid2, i, j);
                }
            }
        }
        return res;
    }

    // 從 (i, j) 開始，將與之相鄰的陸地都變成海水
    private void dfs(int[][] grid, int i, int j) {
        int m = grid.length, n = grid[0].length;
        if (i < 0 || j < 0 || i >= m || j >= n) {
            return;
        }
        if (grid[i][j] == 0) {
            return;
        }

        grid[i][j] = 0;
        dfs(grid, i + 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i - 1, j);
        dfs(grid, i, j - 1);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1906.Minimum%20Absolute%20Difference%20Queries/README.md
    public int[] minDifference(int[] nums, int[][] queries) {
        int m = nums.length, n = queries.length;

        // 數組元素範圍在 [1,100] 之間，對於每個區間 [left, right]，可以遍歷整數 1~100，判斷每個整數是否出現，求得差絕對值的最小值
        // 前綴和 preSum[i][j] 表示數組前 i 個元素中包含整數 j 的個數
        int[][] preSum = new int[m + 1][101];
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= 100; ++j) {
                int t = nums[i - 1] == j ? 1 : 0;
                preSum[i][j] = preSum[i - 1][j] + t;
            }
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            int left = queries[i][0], right = queries[i][1] + 1;
            int t = Integer.MAX_VALUE;
            int last = -1;

            // j 從 1~100 進行遍歷，可以判斷每個遞增整數是否在區間中存在。
            for (int j = 1; j <= 100; ++j) {
                // 對於區間 [left, right]，如果 preSum[right + 1][j] - preSum[left][j] > 0，
                // 那麼表示此區間存在整數 j。
                if (preSum[right][j] > preSum[left][j]) {
                    if (last != -1) {
                        // 計算 j 與先前出現過的整數差值
                        t = Math.min(t, j - last);
                    }

                    // last置為當前數
                    last = j;
                }
            }

            // 找不到解，置為-1
            if (t == Integer.MAX_VALUE) {
                t = -1;
            }
            ans[i] = t;
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-absolute-difference-queries/solutions/1794266/by-mumuxinfei-xiyb/
    // 線段樹解法+位圖使用
    public int[] minDifferenceSeg(int[] nums, int[][] queries) {

        // 1 到 100?
        int n = nums.length;
        Seg root = Seg.build(0, n - 1, nums);

        int m = queries.length;
        int[] res = new int[m];

        for (int i = 0; i < m; i++) {

            res[i] = -1;
            int[] q = queries[i];

            // 獲取區間內，包含[1,100]的覆蓋值
            BitSet bs = new BitSet(101);
            Seg.query(root, q[0], q[1], bs);

            // 計算最小值
            int t1 = -1;
            for (int j = 1; j <= 100; j++) {
                // 如果區間存在整數 j 的狀況
                if (bs.get(j)) {
                    if (t1 != -1) {
                        // 如果 res[i] 位置先前沒計算過，或者當前出現更小差值的狀況
                        if (res[i] == -1 || res[i] > j - t1) {
                            // 計算 j 與先前出現過的整數差值
                            // 並記錄在 res 中
                            res[i] = j - t1;

                            // 最優解，提前退出
                            if (res[i] == 1) {
                                break;
                            }
                        }
                    }

                    // t1 置為當前數
                    t1 = j;
                }
            }
        }

        return res;
    }


    static class Seg {
        int l, r;
        BitSet bs = new BitSet(101);

        Seg left, right;

        static Seg build(int l, int r, int[] num) {
            if (l > r) return null;

            Seg root = new Seg();
            root.l = l;
            root.r = r;
            if (l == r) {
                root.bs.set(num[l]);
                return root;
            }

            int m = l + (r - l) / 2;

            root.left = Seg.build(l, m, num);
            root.right = Seg.build(m + 1, r, num);

            root.bs.or(root.left.bs);
            root.bs.or(root.right.bs);

            return root;
        }

        static void query(Seg root, int l, int r, BitSet bs) {
            if (root.l == l && root.r == r) {
                bs.or(root.bs);
                return;
            }

            int m = root.l + (root.r - root.l) / 2;

            if (r <= m) {
                query(root.left, l, r, bs);
            } else if (l > m) {
                query(root.right, l, r, bs);
            } else {
                query(root.left, l, m, bs);
                query(root.right, m + 1, r, bs);
            }

        }

    }
}


