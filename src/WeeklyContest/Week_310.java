package WeeklyContest;

import java.util.*;

public class Week_310 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2404.Most%20Frequent%20Even%20Element/README.md
    public int mostFrequentEven(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            if (x % 2 == 0) {
                cnt.merge(x, 1, Integer::sum);
            }
        }

        int ans = -1, mx = 0;
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            int x = e.getKey(), v = e.getValue();
            if (mx < v || (mx == v && ans > x)) {
                ans = x;
                mx = v;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2405.Optimal%20Partition%20of%20String/README.md
    // 根據題意，每個子字符串應該盡可能長，且包含的字符唯一。我們只需要貪心地進行劃分即可。
    // 過程中，可以用哈希表記錄當前子字符串的所有字符；
    // 也可以使用一個數字，用位運算的方式記錄字符
    public int partitionString(String s) {
        Set<Character> ss = new HashSet<>();
        int ans = 1;
        for (char c : s.toCharArray()) {
            if (ss.contains(c)) {
                ans++;
                ss.clear();
            }
            ss.add(c);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2406.Divide%20Intervals%20Into%20Minimum%20Number%20of%20Groups/README.md
    // 貪心 + 優先隊列（小根堆）
    // 先將區間按左端點排序，用小根堆維護每組的最右端點（堆頂是所有組的最右端點的最小值）。
    // 遍歷每個區間：
    // 若當前區間左端點大於堆頂元素，說明當前區間可以加入到堆頂元素所在的組中，我們直接彈出堆頂元素，然後將當前區間的右端點放入堆中；
    // 否則，說明當前沒有組能容納當前區間，那麼我們就新建一個組，將當前區間的右端點放入堆中。
    public int minGroups(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int[] e : intervals) {
            if (!q.isEmpty() && q.peek() < e[0]) {
                q.poll();
            }
            q.offer(e[1]);
        }
        return q.size();
    }


    // https://leetcode.cn/problems/longest-increasing-subsequence-ii/solution/zhi-yu-xian-duan-shu-pythonjavacgo-by-en-p1gz/
    // https://www.bilibili.com/video/BV1it4y1L7kL/
    // 線段樹的入門講解: https://www.bilibili.com/video/BV18t4y1p736?t=8m7s
    int[] max;

    public int lengthOfLIS(int[] nums, int k) {
        int u = 0;
        for (int x : nums) u = Math.max(u, x);
        max = new int[u * 4];
        for (int x : nums) {
            if (x == 1) {
                modify(1, 1, u, 1, 1);
            } else {
                int res = 1 + query(1, 1, u, Math.max(x - k, 1), x - 1);
                modify(1, 1, u, x, res);
            }
        }
        return max[1];
    }

    private void modify(int o, int l, int r, int idx, int val) {
        if (l == r) {
            max[o] = val;
            return;
        }
        int m = (l + r) / 2;
        if (idx <= m) modify(o * 2, l, m, idx, val);
        else modify(o * 2 + 1, m + 1, r, idx, val);
        max[o] = Math.max(max[o * 2], max[o * 2 + 1]);
    }


    // 返回區間 [L,R] 內的最大值
    private int query(int o, int l, int r, int L, int R) { // L 和 R 在整個遞歸過程中均不變，將其大寫，視作常量
        if (L <= l && r <= R) return max[o];
        int res = 0;
        int m = (l + r) / 2;
        if (L <= m) res = query(o * 2, l, m, L, R);
        if (R > m) res = Math.max(res, query(o * 2 + 1, m + 1, r, L, R));
        return res;
    }
}
