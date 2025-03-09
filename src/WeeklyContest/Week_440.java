package WeeklyContest;

import java.util.*;

public class Week_440 {

    // https://leetcode.cn/problems/choose-k-elements-with-maximum-sum/solutions/3603048/zui-xiao-dui-wei-hu-qian-k-da-pythonjava-92v6/
    public long[] findMaxSum(int[] nums1, int[] nums2, int k) {
        int n = nums1.length;
        int[][] a = new int[n][3];
        for (int i = 0; i < n; i++) {
            a[i] = new int[]{nums1[i], nums2[i], i};
        }
        Arrays.sort(a, (p, q) -> p[0] - q[0]);

        long[] ans = new long[n];
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long s = 0;
        // 分組循環模板
        for (int i = 0; i < n; ) {
            int start = i;
            int x = a[start][0];
            // 找到所有相同的 nums1[i]，這些數的答案都是一樣的
            while (i < n && a[i][0] == x) {
                ans[a[i][2]] = s;
                i++;
            }
            // 把這些相同的 nums1[i] 對應的 nums2[i] 入堆
            for (int j = start; j < i; j++) {
                int y = a[j][1];
                s += y;
                pq.offer(y);
                if (pq.size() > k) {
                    s -= pq.poll();
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/fruits-into-baskets-iii/solutions/3603049/xian-duan-shu-er-fen-pythonjavacgo-by-en-ssqf/
    public int numOfUnplacedFruits(int[] fruits, int[] baskets) {
        SegmentTree t = new SegmentTree(baskets);
        int n = baskets.length;
        int ans = 0;
        for (int x : fruits) {
            if (t.findFirstAndUpdate(1, 0, n - 1, x) < 0) {
                ans++;
            }
        }
        return ans;
    }


    class SegmentTree {
        private final int[] max;

        public SegmentTree(int[] a) {
            int n = a.length;
            max = new int[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
            build(a, 1, 0, n - 1);
        }

        // 找區間內的第一個 >= x 的數，並更新為 -1，返回這個數的下標
        public int findFirstAndUpdate(int o, int l, int r, int x) {
            if (max[o] < x) { // 區間沒有 >= x 的數
                return -1;
            }
            if (l == r) {
                max[o] = -1; // 更新為 -1，表示不能放水果
                return l;
            }
            int m = (l + r) / 2;
            int i = findFirstAndUpdate(o * 2, l, m, x); // 先遞歸左子樹
            if (i < 0) { // 左子樹沒找到
                i = findFirstAndUpdate(o * 2 + 1, m + 1, r, x); // 再遞歸右子樹
            }
            maintain(o);
            return i;
        }

        private void maintain(int o) {
            max[o] = Math.max(max[o * 2], max[o * 2 + 1]);
        }

        // 初始化線段樹
        private void build(int[] a, int o, int l, int r) {
            if (l == r) {
                max[o] = a[l];
                return;
            }
            int m = (l + r) / 2;
            build(a, o * 2, l, m);
            build(a, o * 2 + 1, m + 1, r);
            maintain(o);
        }
    }


    // https://leetcode.cn/problems/maximize-subarrays-after-removing-one-conflicting-pair/solutions/3603047/mei-ju-zuo-duan-dian-wei-hu-zui-xiao-ci-4nvu6/
    public long maxSubarrays(int n, int[][] conflictingPairs) {
        List<Integer>[] groups = new ArrayList[n + 1];
        Arrays.setAll(groups, i -> new ArrayList<>());
        for (int[] p : conflictingPairs) {
            int a = p[0];
            int b = p[1];
            groups[Math.min(a, b)].add(Math.max(a, b));
        }

        long ans = 0;
        long[] extra = new long[n + 2];
        long maxExtra = 0;
        // 維護最小 b 和次小 b
        int b0 = n + 1;
        int b1 = n + 1;
        for (int a = n; a > 0; a--) {
            List<Integer> listB = groups[a];
            if (!listB.isEmpty()) {
                Collections.sort(listB);
                if (listB.size() > 2) {
                    listB = listB.subList(0, 2);
                }
                for (int b : listB) {
                    if (b < b0) {
                        b1 = b0;
                        b0 = b;
                    } else if (b < b1) {
                        b1 = b;
                    }
                }
            }
            ans += b0 - a;
            extra[b0] += b1 - b0;
            maxExtra = Math.max(maxExtra, extra[b0]);
        }

        return ans + maxExtra;
    }


}









