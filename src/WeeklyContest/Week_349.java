package WeeklyContest;

import java.util.*;

class Week_349 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2733.Neither%20Minimum%20nor%20Maximum/README.md
    public int findNonMinOrMax(int[] nums) {
        int mi = 100, mx = 0;
        for (int x : nums) {
            mi = Math.min(mi, x);
            mx = Math.max(mx, x);
        }
        for (int x : nums) {
            if (x != mi && x != mx) {
                return x;
            }
        }
        return -1;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2734.Lexicographically%20Smallest%20String%20After%20Substring%20Operation/README.md
    public String smallestString(String s) {
        int n = s.length();
        int i = 0;
        while (i < n && s.charAt(i) == 'a') { // 找不是a的開頭
            ++i;
        }
        if (i == n) { // 處理全a字串，最後一個a改成z
            return s.substring(0, n - 1) + "z";
        }
        int j = i;
        char[] cs = s.toCharArray();
        while (j < n && cs[j] != 'a') {  // 非a字串全部-1
            cs[j] = (char) (cs[j] - 1);
            ++j;
        }
        return String.valueOf(cs);
    }


    // https://leetcode.cn/problems/collecting-chocolates/solution/qiao-miao-mei-ju-pythonjavacgo-by-endles-5ws2/
    // 如果不操作，第 i 個巧克力必須花費 nums[i] 收集，總成本為所有 nums[i] 之和。
    // 如果操作一次，第 i 個巧克力可以花費 min(nums[i],nums[(i+1)modn]) 收集。
    // 注意在求和的情況下，把題意理解成循環左移還是循環右移，算出的結果都是一樣的。（樣例 1 解釋中的類型變更是反過來的，但計算結果是正確的。）
    // 如果操作兩次，第 i 個巧克力可以花費 min(nums[i],nums[(i+1)modn],nums[(i+2)modn]) 收集。
    // 依此類推。
    public long minCost(int[] nums, int x) {
        int n = nums.length;
        long[] sum = new long[n];
        for (int i = 0; i < n; i++)
            sum[i] = (long) i * x; // 事先計算操作 i 次成本
        for (int i = 0; i < n; i++) { // 子數組左端點
            int minValue = nums[i];
            for (int j = i; j < n + i; j++) { // 子數組右端點（把數組視作環形的）
                minValue = Math.min(minValue, nums[j % n]); // 找出從 nums[i] 到 nums[j%n] 的最小值
                sum[j - i] += minValue; // 累加操作 j-i 次的成本
            }
        }
        long ans = Long.MAX_VALUE;
        for (long s : sum) ans = Math.min(ans, s);
        return ans;
    }


    // https://leetcode.cn/problems/maximum-sum-queries/solution/pai-xu-dan-diao-zhan-shang-er-fen-by-end-of9h/
    // https://www.bilibili.com/video/BV15V4y1m7Sb/?spm_id_from=333.999.0.0
    public int[] maximumSumQueries(int[] nums1, int[] nums2, int[][] queries) {
        int n = nums1.length;
        int q = queries.length;

        // 按 x 升序排序 nums1, nums2
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) ids[i] = i;
        Arrays.sort(ids, Comparator.comparingInt(o -> nums1[o]));

        // 按 x 降序排序 queries
        for (int i = 0; i < q; i++) {
            queries[i] = new int[]{queries[i][0], queries[i][1], i};
        }
        Arrays.sort(queries, (o1, o2) -> Integer.compare(o2[0], o1[0]));

        int[] ans = new int[q];
        Arrays.fill(ans, -1);
        Deque<int[]> stack = new ArrayDeque<>();
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        int idx = n - 1;
        for (int[] query : queries) {
            int x = query[0], y = query[1], qId = query[2];
            while (idx >= 0 && nums1[ids[idx]] >= x) {  // nums1從大到小遍歷
                int ax = nums1[ids[idx]], ay = nums2[ids[idx]];
                // ay >= stack.peek()[0]
                while (!stack.isEmpty() && stack.peek()[1] <= ax + ay) {  // ax + ay 大於棧頂，開始彈出棧頂
                    int[] pop = stack.pop();
                    treeMap.remove(pop[0]);
                }
                if (stack.isEmpty() || stack.peek()[0] < ay) {  // ay遞增，保持棧的單調性
                    stack.push(new int[]{ay, ax + ay});
                    treeMap.put(ay, ax + ay);
                }
                idx--;
            }
            
            // 單調棧上二分，這裡用treeMap實現
            // treeMap.ceilingEntry 返回大於或等於 y 的第一個entry，若不存在這種 entry 返回 null
            Map.Entry<Integer, Integer> entry = treeMap.ceilingEntry(y);
            if (entry != null) {
                ans[qId] = entry.getValue();
            }
        }
        return ans;
    }
}

