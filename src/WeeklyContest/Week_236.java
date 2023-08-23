package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.TreeMap;

class Week_236 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1822.Sign%20of%20the%20Product%20of%20an%20Array/README.md
    public int arraySign(int[] nums) {
        int ans = 1;
        for (int v : nums) {
            if (v == 0) {
                return 0;
            }
            if (v < 0) {
                ans *= -1;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-the-winner-of-the-circular-game/solutions/1467558/pythonjavajavascriptgo-by-himymben-x57d/
    // 約瑟夫環問題
    public int findTheWinner(int n, int k) {
        if (n == 1) {
            return 1;
        }
        int ans = (findTheWinner(n - 1, k) + k) % n;
        return ans == 0 ? n : ans;
    }


    // https://leetcode.cn/problems/minimum-sideway-jumps/solutions/2071617/cong-0-dao-1-de-0-1-bfspythonjavacgo-by-1m8z4/
    // BFS
    public int minSideJumps(int[] obstacles) {
        int n = obstacles.length;
        int[][] dis = new int[n][3];
        for (int i = 0; i < n; ++i) Arrays.fill(dis[i], n);
        dis[0][1] = 0;
        Deque<int[]> q = new ArrayDeque<int[]>();
        q.add(new int[]{0, 1}); // 起點
        for (; ; ) {
            int[] p = q.pollFirst();
            int i = p[0], j = p[1], d = dis[i][j];
            if (i == n - 1) return d; // 到達終點
            if (obstacles[i + 1] != j + 1 && d < dis[i + 1][j]) { // 向右
                dis[i + 1][j] = d;
                q.addFirst(new int[]{i + 1, j}); // 加到隊首
            }
            for (int k : new int[]{(j + 1) % 3, (j + 2) % 3}) // 枚舉另外兩條跑道（向上/向下）
                if (obstacles[i] != k + 1 && d + 1 < dis[i][k]) {
                    dis[i][k] = d + 1;
                    q.addLast(new int[]{i, k}); // 加到隊尾
                }
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1824.Minimum%20Sideway%20Jumps/README.md
    // dp
    public int minSideJumps8(int[] obstacles) {
        int n = obstacles.length;
        int[][] f = new int[n][3];
        f[0][1] = 0;
        f[0][0] = f[0][2] = 1;
        for (int i = 1; i < n; i++) Arrays.fill(f[i], 0x3f3f3f3f);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 3; j++) // 實際障礙物情況：如果 j 位置無障礙物，先更新為剛剛保存的前一位置的次數
                if (obstacles[i] != j + 1)
                    f[i][j] = f[i - 1][j];
            for (int j = 0; j < 3; j++) // 比較從非 j 的位置跳過來，是否次數更小
                if (obstacles[i] != j + 1)
                    f[i][j] = Math.min(f[i][j], Math.min(f[i][(j + 1) % 3], f[i][(j + 2) % 3]) + 1);
        }
        return Math.min(f[n - 1][0], Math.min(f[n - 1][1], f[n - 1][2]));
    }

    // dp優化（滾動數組）
    // https://leetcode.cn/problems/minimum-sideway-jumps/solutions/2071629/by-lcbin-maq7/
    public int minSideJumps7(int[] obstacles) {
        int n = obstacles.length;
        int[] f = new int[3];
        f[0] = f[2] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 3; j++) // 實際障礙物情況：如果 j 位置為障礙物，f[j] 不可能抵達終點，值為無窮大
                if (obstacles[i] == j + 1)
                    f[j] = 0x3f3f3f3f;
            for (int j = 0; j < 3; j++) // 比較從非 j 的位置跳過來，是否次數更小
                if (obstacles[i] != j + 1)
                    f[j] = Math.min(f[j], Math.min(f[(j + 1) % 3], f[(j + 2) % 3]) + 1);
        }
        return Math.min(f[0], Math.min(f[1], f[2]));
    }

    // https://leetcode.cn/problems/minimum-sideway-jumps/solutions/2070379/zui-shao-ce-tiao-ci-shu-by-leetcode-solu-3y2g/
    /*
     * ☆☆☆☆☆ 貪心
     * 1.一直前跳，直到遇到障礙。
     * 2.此時，需要側跳，有 2 個落地點（若有障礙，則包括障礙）
     *  我們從 2 個落地點同時出發，直到遇到障礙，我們選擇沒有遇到障礙的跑道，繼續步驟1。
     */
    public int minSideJumpsGreedy(int[] obstacles) {
        int n = obstacles.length;
        int ans = 0;
        for (int i = 0, lane = 1; i < n - 1; i++) {
            int lanePlus = lane + 1; // 當前跑道的正確下標位置
            if (obstacles[i + 1] != lanePlus)  // 前方阻礙不在當前跑道，前進
                continue;

            // 前方阻礙出現在當前跑道
            // 此時，需要側跳，有 2 個落地點（若有障礙，則包括障礙）
            // 從 2 個落地點同時出發，直到遇到障礙
            while (i < n - 1 && (obstacles[i] == lanePlus || obstacles[i] == 0))
                i++;

            // 選擇沒有遇到障礙的跑道，繼續步驟1
            int one = lanePlus % 3;
            lane = obstacles[i] == one + 1 ? (lane + 2) % 3 : one;

            // 當前下標剛才多加了1所以要減回1
            i--;
            ans++;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1825.Finding%20MK%20Average/README.md
    class MKAverage {

        private int m, k;

        // 一個變量 s ，維護 mid 中所有元素的和
        private long s;

        // 維護兩個變量 size1 和 size3 ，
        // 分別表示 lo 和 hi 中元素的個數
        private int size1, size3;

        // 一個長度為 m 的隊列 q ，其中隊首元素為最早加入的元素，隊尾元素為最近加入的元素；
        private Deque<Integer> q = new ArrayDeque<>();

        // 三個有序集合，分別為 lo , mid , hi ，
        // 其中 lo 和 hi 分別存儲最小的 k 個元素和最大的 k 個元素，
        // 而 mid 存儲剩余的元素；
        private TreeMap<Integer, Integer> lo = new TreeMap<>();
        private TreeMap<Integer, Integer> mid = new TreeMap<>();
        private TreeMap<Integer, Integer> hi = new TreeMap<>();

        public MKAverage(int m, int k) {
            this.m = m;
            this.k = k;
        }

        public void addElement(int num) {
            if (lo.isEmpty() || num <= lo.lastKey()) {
                // 如果 lo 為空，或者 num <= lo max ，則將 num 加入 lo 中；
                lo.merge(num, 1, Integer::sum);
                ++size1;
            } else if (hi.isEmpty() || num >= hi.firstKey()) {
                // 否則如果 hi 為空，或者 num >= hi min ，則將 num 加入 hi 中；
                hi.merge(num, 1, Integer::sum);
                ++size3;
            } else {
                // 否則將 num 加入 mid 中，
                // 同時將 num 的值加到 s 中。
                mid.merge(num, 1, Integer::sum);
                s += num;
            }

            // 接下來將 num 加入隊列 q 中，
            q.offer(num);
            if (q.size() > m) {
                // 如果此時隊列 q 的長度大於 m ，則將隊首元素 x 從隊列 q 中移除，
                int x = q.poll();

                // 接下來從 lo, mid, hi  中選擇其中一個包含 x 的集合， 將 x 從該集合中移除，
                if (lo.containsKey(x)) {
                    if (lo.merge(x, -1, Integer::sum) == 0) {
                        lo.remove(x);
                    }
                    --size1;
                } else if (hi.containsKey(x)) {
                    if (hi.merge(x, -1, Integer::sum) == 0) {
                        hi.remove(x);
                    }
                    --size3;
                } else {
                    // 如果該集合為 mid ，則將 s 減去 x 的值
                    if (mid.merge(x, -1, Integer::sum) == 0) {
                        mid.remove(x);
                    }
                    s -= x;
                }
            }

            // 如果 lo 的長度大於 k ，則循環將 lo 中的最大值 max(lo) 從 lo 中移除，
            // 將 max(lo) 加入 mid 中，同時將 s 加上 max(lo) 的值。
            for (; size1 > k; --size1) {
                int x = lo.lastKey();
                if (lo.merge(x, -1, Integer::sum) == 0) {
                    lo.remove(x);
                }
                mid.merge(x, 1, Integer::sum);
                s += x;
            }

            // 如果 hi 的長度大於 k ，則循環將 lo 中的最大值 min(hi) 從 hi 中移除，
            // 將 min(hi) 加入 mid 中，同時將 s 加上 min(hi) 的值。
            for (; size3 > k; --size3) {
                int x = hi.firstKey();
                if (hi.merge(x, -1, Integer::sum) == 0) {
                    hi.remove(x);
                }
                mid.merge(x, 1, Integer::sum);
                s += x;
            }

            // 如果 lo 的長度小於 k ，並且 mid 不為空，
            // 則循環將 mid 中的最小值 min(mid) 從 mid 中移除，
            // 將 min(mid) 加入 lo 中，
            // 同時將 s 減去 min(mid)  的值。
            for (; size1 < k && !mid.isEmpty(); ++size1) {
                int x = mid.firstKey();
                if (mid.merge(x, -1, Integer::sum) == 0) {
                    mid.remove(x);
                }
                s -= x;
                lo.merge(x, 1, Integer::sum);
            }

            // 如果 hi 的長度小於 k ，並且 mid 不為空，
            // 則循環將 mid 中的最小值 max(mid) 從 mid 中移除，
            // 將 max(mid) 加入 hi 中，
            // 同時將 s 減去 max(mid)  的值。
            for (; size3 < k && !mid.isEmpty(); ++size3) {
                int x = mid.lastKey();
                if (mid.merge(x, -1, Integer::sum) == 0) {
                    mid.remove(x);
                }
                s -= x;
                hi.merge(x, 1, Integer::sum);
            }
        }

        // 題目要求刪除最小的 k 個數和最大的 k 個數
        // 所以總值 s 要除掉 (q 長度減去兩個 k)
        public int calculateMKAverage() {
            return q.size() < m ? -1 : (int) (s / (q.size() - k * 2));
        }
    }

}

