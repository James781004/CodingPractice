package WeeklyContest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

class Week_352 {
    // https://github.com/doocs/leetcode/tree/main/solution/2700-2799/2760.Longest%20Even%20Odd%20Subarray%20With%20Threshold
    public int longestAlternatingSubArray(int[] nums, int threshold) {
        int ans = 0, n = nums.length;
        for (int l = 0; l < n; ++l) {
            if (nums[l] % 2 == 0 && nums[l] <= threshold) { // 左邊界符合條件，才開始向右擴散窗口
                int r = l + 1;
                while (r < n && nums[r] % 2 != nums[r - 1] % 2 && nums[r] <= threshold) {
                    ++r;
                }
                ans = Math.max(ans, r - l); // 計算窗口內子數組長度
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/tree/main/solution/2700-2799/2761.Prime%20Pairs%20With%20Target%20Sum
    public List<List<Integer>> findPrimePairs(int n) {
        // 預處理[1...n]範圍內質數
        boolean[] primes = new boolean[n];
        Arrays.fill(primes, true);
        for (int i = 2; i < n; ++i) {
            if (primes[i]) {
                // 如果要用 list 收集質數，要寫在這裡
                for (int j = i + i; j < n; j += i) {
                    primes[j] = false;
                }
            }
        }
        List<List<Integer>> ans = new ArrayList<>();
        for (int x = 2; x <= n / 2; x++) {  // x枚舉到[2...n / 2]即可
            int y = n - x;
            if (primes[x] && primes[y]) {
                ans.add(Arrays.asList(x, y));
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2762.Continuous%20Subarrays/README.md
    public long continuousSubArrays(int[] nums) {
        long ans = 0;
        int i = 0, n = nums.length;
        TreeMap<Integer, Integer> tm = new TreeMap<>(); // 用一個有序列表維護當前子數組的所有元素
        for (int j = 0; j < n; ++j) { // 指針i, j為左右窗口
            tm.merge(nums[j], 1, Integer::sum);  // 右窗口擴張
            while (tm.lastEntry().getKey() - tm.firstEntry().getKey() > 2) { // 差值不能大於2，否則排出左邊界
                tm.merge(nums[i], -1, Integer::sum);
                if (tm.get(nums[i]) == 0) {
                    tm.remove(nums[i]);
                }
                ++i; // 左窗口收縮
            }
            ans += j - i + 1; // 累加子數組長度
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2763.Sum%20of%20Imbalance%20Numbers%20of%20All%20Subarrays/README.md
    public int sumImbalanceNumbers(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            TreeMap<Integer, Integer> tm = new TreeMap<>(); // 用一個有序列表維護當前子數組中的所有元素
            int cnt = 0; // 用一個變量維護當前子數組的不平衡數字
            for (int j = i; j < n; ++j) {
                Integer k = tm.ceilingKey(nums[j]); // 找到第一個大於等於nums[j]的元素 k
                if (k != null && k - nums[j] > 1) { // 如果k存在且 k - nums[j] > 1，不平衡數字加1
                    ++cnt;
                }
                Integer h = tm.floorKey(nums[j]); // 找到最後一個小於nums[j]的元素 h
                if (h != null && nums[j] - h > 1) { // 如果h存在且 nums[j] - h > 1，不平衡數字加1
                    ++cnt;
                }
                if (h != null && k != null && k - h > 1) { // k和h都存在，nums[j]插入中間使不平衡數字減1
                    --cnt;
                }
                tm.merge(nums[j], 1, Integer::sum);
                ans += cnt; // 將當前子數組的不平衡數字累加到答案中，繼續遍歷
            }
        }
        return ans;
    }


    // 枚舉
    // https://leetcode.cn/problems/sum-of-imbalance-numbers-of-all-subarrays/solution/bao-li-mei-ju-pythonjavacgo-by-endlessch-2r7p/
    // https://www.bilibili.com/video/BV1ej411m7zV/?vd_source=3ffe88901c9b0b355ae9becd01f3e4bf
    public int sumImbalanceNumbers2(int[] nums) {
        int ans = 0, n = nums.length;
        boolean[] vis = new boolean[n + 2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(vis, false);
            vis[nums[i]] = true;
            int cnt = 0;
            for (int j = i + 1; j < n; j++) {
                int x = nums[j];
                if (!vis[x]) { // 如果vis已經有相同數，子數組整理後會相鄰，總數也不變
                    cnt++; // 假設x本身對整體可以貢獻一個不平衡數
                    if (vis[x - 1]) cnt--; // 如果子數組中已經存在x - 1，整體不平衡數就少一個
                    if (vis[x + 1]) cnt--; // 如果子數組中已經存在x + 1，整體不平衡數就少一個
                    vis[x] = true; // x加入子數組
                }
                ans += cnt; // 加總當前子數組不平衡數
            }
        }
        return ans;
    }


    // 貢獻法
    // https://leetcode.cn/problems/sum-of-imbalance-numbers-of-all-subarrays/solution/bao-li-mei-ju-pythonjavacgo-by-endlessch-2r7p/
    public int sumImbalanceNumbers3(int[] nums) {
        int n = nums.length;
        int[] right = new int[n];
        int[] idx = new int[n + 1];
        Arrays.fill(idx, n);
        for (int i = n - 1; i >= 0; i--) {
            int x = nums[i];
            // right[i] 表示 nums[i] 右側的 x 和 x-1 的最近下標（不存在時為 n）
            right[i] = Math.min(idx[x], idx[x - 1]);
            idx[x] = i;
        }

        int ans = 0;
        Arrays.fill(idx, -1);
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            // 統計 x 能產生多少貢獻
            ans += (i - idx[x - 1]) * (right[i] - i); // 子數組左端點個數 * 子數組右端點個數
            idx[x] = i;
        }
        // 上面計算的時候，每個子數組的最小值必然可以作為貢獻，而這是不合法的
        // 所以每個子數組都多算了 1 個不合法的貢獻
        return ans - n * (n + 1) / 2;
    }

}

