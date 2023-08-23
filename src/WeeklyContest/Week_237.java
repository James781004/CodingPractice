package WeeklyContest;

import java.util.Arrays;
import java.util.PriorityQueue;

class Week_237 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1832.Check%20if%20the%20Sentence%20Is%20Pangram/README.md
    public boolean checkIfPangram(String sentence) {
        boolean[] vis = new boolean[26];
        for (int i = 0; i < sentence.length(); ++i) {
            vis[sentence.charAt(i) - 'a'] = true;
        }
        for (boolean v : vis) {
            if (!v) {
                return false;
            }
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1833.Maximum%20Ice%20Cream%20Bars/README.md
    public int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);
        int n = costs.length;
        for (int i = 0; i < n; ++i) {
            if (coins < costs[i]) {
                return i;
            }
            coins -= costs[i];
        }
        return n;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1834.Single-Threaded%20CPU/README.md
    public int[] getOrder(int[][] tasks) {
        // 先將任務按照 enqueueTime 從小到大排序
        int n = tasks.length;
        int[][] ts = new int[n][3];
        for (int i = 0; i < n; ++i) {
            ts[i] = new int[]{tasks[i][0], tasks[i][1], i};
        }
        Arrays.sort(ts, (a, b) -> a[0] - b[0]);

        // 用一個優先隊列（小根堆）維護當前可執行的任務，隊列中的元素為 (processingTime, index)，即任務的執行時間和任務的編號。
        // 另外用一個變量 t 表示當前時間，初始值為 0 。
        int[] ans = new int[n];
        PriorityQueue<int[]> q
                = new PriorityQueue<>((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        int i = 0, t = 0, k = 0;
        while (!q.isEmpty() || i < n) {
            // 如果當前隊列為空，說明當前沒有可執行的任務，
            // 將 t 更新為下一個任務的 enqueueTime 與當前時間 t 中的較大值。
            if (q.isEmpty()) {
                t = Math.max(t, ts[i][0]);
            }

            // 將所有 enqueueTime 小於等於 t 的任務加入隊列
            while (i < n && ts[i][0] <= t) {
                q.offer(new int[]{ts[i][1], ts[i][2]});
                ++i;
            }

            // 從隊列中取出一個任務，將其編號加入答案數組，
            // 然後將 t 更新為當前時間 t 與當前任務的執行時間之和。
            int[] p = q.poll();
            ans[k++] = p[1];
            t += p[0];
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1835.Find%20XOR%20Sum%20of%20All%20Pairs%20Bitwise%20AND/README.md
    // https://leetcode.cn/problems/find-xor-sum-of-all-pairs-bitwise-and/solutions/729166/xiang-xi-tui-dao-yi-bu-bu-luo-ji-hua-jia-tr9b/
    public int getXORSum(int[] arr1, int[] arr2) {
        int a = 0, b = 0;
        for (int v : arr1) {
            a ^= v;
        }
        for (int v : arr2) {
            b ^= v;
        }
        return a & b;
    }
}

