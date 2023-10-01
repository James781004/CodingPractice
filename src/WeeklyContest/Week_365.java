package WeeklyContest;

import java.util.*;

public class Week_365 {
    // https://leetcode.cn/problems/maximum-value-of-an-ordered-triplet-i/solutions/2464882/on-zuo-fa-by-endlesscheng-01x5/
    // 第一題的數據範圍可以直接三個for loop暴力解決
    // 這邊是第二題的兩種解法
    // 枚舉 k，需要知道 k 左邊 nums[i]−nums[j] 的最大值 (滿足 i < j < k)。
    // 類似 121. 買賣股票的最佳時機，可以在遍歷的過程中，
    // 維護 nums[i] 的最大值 preMax，同時維護 preMax 減當前元素的最大值 maxDiff，
    // 這就是 k 左邊 nums[i]−nums[j] 的最大值。
    public long maximumTripletValue(int[] nums) {
        long ans = 0;
        int maxDiff = 0, preMax = 0;
        for (int x : nums) {
            ans = Math.max(ans, (long) maxDiff * x);
            maxDiff = Math.max(maxDiff, preMax - x);
            preMax = Math.max(preMax, x);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-value-of-an-ordered-triplet-ii/solutions/2464857/mei-ju-jzhao-qian-hou-zui-da-zhi-pythonj-um8q/
    // 枚舉 j
    // 枚舉 j，為了讓 (nums[i]−nums[j])∗nums[k] 盡量大，需要知道 j 左側元素的最大值，和 j 右側元素的最大值(滿足 i < j < k)。
    // 也就是 nums 的前綴最大值 preMax 和後綴最大值 sufMax，這都可以用遞推預處理出來：
    // preMax[i]=max(preMax[i−1],nums[i])
    // sufMax[i]=max(sufMax[i+1],nums[i])
    // 代碼實現時，可以只預處理 sufMax 數組，preMax 可以在計算答案的同時算出來。
    public long maximumTripletValue2(int[] nums) {
        int n = nums.length;
        int[] sufMax = new int[n + 1];
        for (int i = n - 1; i > 1; i--) {
            sufMax[i] = Math.max(sufMax[i + 1], nums[i]);
        }
        long ans = 0;
        int preMax = nums[0];
        for (int j = 1; j < n - 1; j++) {
            ans = Math.max(ans, (long) (preMax - nums[j]) * sufMax[j + 1]);
            preMax = Math.max(preMax, nums[j]);
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-size-subarray-in-infinite-array/solutions/2464878/hua-dong-chuang-kou-on-shi-jian-o1-kong-cqawc/
    // 假設 nums 的元素和為 total，而 target 假設在重複數組中間
    // 重複數組如果移除 target 段，剩餘元素會是nums + nums 的子數組，其元素和為 target % total
    // 所以可以把題目轉移為：在 nums + nums 數組中找和為 target % total 的最短子數組
    // 由於元素都不是負數，可以使用滑動窗口處理
    // 最後加上中間的 (target / total) 個完整子數組長度就是答案
    public int minSizeSubarray(int[] nums, int target) {
        long total = 0;
        for (int x : nums) total += x;
        int n = nums.length;
        int ans = Integer.MAX_VALUE;
        int left = 0;
        long sum = 0;
        for (int right = 0; right < n * 2; right++) {
            sum += nums[right % n];
            while (sum > target % total) {
                sum -= nums[left++ % n];
            }
            if (sum == target % total) {
                ans = Math.min(ans, right - left + 1);
            }
        }
        return ans == Integer.MAX_VALUE ? -1 : ans + (int) (target / total) * n;
    }


    // https://leetcode.cn/problems/count-visited-nodes-in-a-directed-graph/solutions/2464852/nei-xiang-ji-huan-shu-pythonjavacgo-by-e-zrzh/
    public int[] countVisitedNodes(List<Integer> edges) {
        int[] g = edges.stream().mapToInt(i -> i).toArray();
        int n = g.length;
        List<Integer>[] rg = new ArrayList[n]; // 反圖
        Arrays.setAll(rg, e -> new ArrayList<>());
        int[] deg = new int[n];
        for (int x = 0; x < n; x++) {
            int y = g[x];
            rg[y].add(x);
            deg[y]++;
        }

        // 拓撲排序，剪掉 g 上的所有樹枝
        // 拓撲排序後，deg 值為 1 的點必定在基環上，為 0 的點必定在樹枝上
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (deg[i] == 0) {
                q.add(i);
            }
        }
        while (!q.isEmpty()) {
            int x = q.poll();
            int y = g[x];
            if (--deg[y] == 0) {
                q.add(y);
            }
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            if (deg[i] <= 0) {
                continue;
            }
            List<Integer> ring = new ArrayList<>();
            for (int x = i; ; x = g[x]) {
                deg[x] = -1; // 將基環上的點的入度標記為 -1，避免重複訪問
                ring.add(x); // 收集在基環上的點
                if (g[x] == i) {
                    break;
                }
            }
            for (int r : ring) {
                rdfs(r, ring.size(), rg, deg, ans); // 為方便計算，以 ring.size() 作為初始深度
            }
        }
        return ans;
    }

    // 在反圖上遍歷樹枝
    private void rdfs(int x, int depth, List<Integer>[] rg, int[] deg, int[] ans) {
        ans[x] = depth;
        for (int y : rg[x]) {
            if (deg[y] == 0) { // 樹枝上的點在拓撲排序後，入度均為 0
                rdfs(y, depth + 1, rg, deg, ans);
            }
        }
    }
}
