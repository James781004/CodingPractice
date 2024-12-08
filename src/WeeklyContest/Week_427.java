package WeeklyContest;

import java.util.*;

public class Week_427 {

    // https://leetcode.cn/problems/transformed-array/solutions/3013925/jian-ji-xie-fa-pythonjavacgo-by-endlessc-hzyf/
    public int[] constructTransformedArray(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            // 相當於從 i 移動到 i+nums[i]。
            // 如果下標越界，需要調整到 [0,n−1] 中，取模就行
            result[i] = nums[((i + nums[i]) % n + n) % n];
        }
        return result;
    }


    // https://leetcode.cn/problems/maximum-subarray-sum-with-length-divisible-by-k/solutions/3013897/qian-zhui-he-mei-ju-you-wei-hu-zuo-pytho-0t8p/
    public long maxSubarraySum(int[] nums, int k) {
        // 問題相當於：
        // 給定前綴和數組 s，計算最大的 s[j]−s[i]，滿足 i<j 且 j−i 是 k 的倍數
        long[] minS = new long[k];
        Arrays.fill(minS, 0, k - 1, Long.MAX_VALUE / 2); // 防止下面減法溢出

        long ans = Long.MIN_VALUE;
        long s = 0;

        // 枚舉 j，那麼符合要求的 i 是 j−k,j−2k,j−3k,⋯
        // 這些數都與 j 模 k 同余
        for (int j = 0; j < nums.length; j++) {
            s += nums[j];
            int i = j % k;
            ans = Math.max(ans, s - minS[i]);

            // 要使 s[j]−s[i] 盡量大，那麼 s[i] 要盡量小
            minS[i] = Math.min(minS[i], s);
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-area-rectangle-with-point-constraints-ii/solutions/3013907/chi-xian-xun-wen-chi-san-hua-shu-zhuang-gd604/
    private record Query(int x1, int x2, int y1, int y2, long area) {
    }

    private record Data(int qid, int sign, int y1, int y2) {
    }

    public long maxRectangleArea(int[] xCoord, int[] yCoord) {
        Map<Integer, List<Integer>> xMap = new HashMap<>(); // 同一列的所有點的縱坐標
        Map<Integer, List<Integer>> yMap = new HashMap<>(); // 同一行的所有點的橫坐標
        int n = xCoord.length;
        for (int i = 0; i < n; i++) {
            xMap.computeIfAbsent(xCoord[i], k -> new ArrayList<>()).add(yCoord[i]);
            yMap.computeIfAbsent(yCoord[i], k -> new ArrayList<>()).add(xCoord[i]);
        }

        // 預處理每個點的正下方的點
        Map<Long, Integer> below = new HashMap<>();
        for (var entry : xMap.entrySet()) {
            List<Integer> ys = entry.getValue();
            ys.sort(null);
            for (int i = 1; i < ys.size(); i++) {
                // 通過 x<<32|y 的方式，把 (x,y) 壓縮成一個 long
                below.put((long) entry.getKey() << 32 | ys.get(i), ys.get(i - 1));
            }
        }

        // 預處理每個點的正左邊的點
        Map<Long, Integer> left = new HashMap<>();
        for (var entry : yMap.entrySet()) {
            List<Integer> xs = entry.getValue();
            xs.sort(null);
            for (int i = 1; i < xs.size(); i++) {
                left.put((long) xs.get(i) << 32 | entry.getKey(), xs.get(i - 1));
            }
        }

        // 離散化用
        List<Integer> xs = new ArrayList<>(xMap.keySet());
        List<Integer> ys = new ArrayList<>(yMap.keySet());
        xs.sort(null);
        ys.sort(null);

        // 收集詢問：矩形區域（包括邊界）的點的個數
        List<Query> queries = new ArrayList<>();
        // 枚舉 (x2,y2) 作為矩形的右上角
        for (var e : xMap.entrySet()) {
            int x2 = e.getKey();
            List<Integer> listY = e.getValue();
            for (int i = 1; i < listY.size(); i++) {
                // 計算矩形左下角 (x1,y1)
                int y2 = listY.get(i);
                int x1 = left.getOrDefault((long) x2 << 32 | y2, -1);
                if (x1 < 0) {
                    continue;
                }
                int y1 = listY.get(i - 1); // (x2,y2) 下面的點（矩形右下角）的縱坐標
                // 矩形右下角的左邊的點的橫坐標必須是 x1
                if (left.getOrDefault((long) x2 << 32 | y1, -1) != x1) {
                    continue;
                }
                // 矩形左上角的下邊的點的縱坐標必須是 y1
                if (below.getOrDefault((long) x1 << 32 | y2, -1) != y1) {
                    continue;
                }
                queries.add(new Query(
                        Collections.binarySearch(xs, x1), // 離散化
                        Collections.binarySearch(xs, x2),
                        Collections.binarySearch(ys, y1),
                        Collections.binarySearch(ys, y2),
                        (long) (x2 - x1) * (y2 - y1)
                ));
            }
        }

        // 離線詢問
        List<Data>[] qs = new ArrayList[xs.size()];
        Arrays.setAll(qs, i -> new ArrayList<>());
        for (int i = 0; i < queries.size(); i++) {
            Query q = queries.get(i);
            if (q.x1 > 0) {
                qs[q.x1 - 1].add(new Data(i, -1, q.y1, q.y2));
            }
            qs[q.x2].add(new Data(i, 1, q.y1, q.y2));
        }

        // 回答詢問
        int[] res = new int[queries.size()];
        Fenwick tree = new Fenwick(ys.size() + 1);
        for (int i = 0; i < xs.size(); i++) {
            // 把橫坐標為 xs[i] 的所有點都加到樹狀數組中
            for (int y : xMap.get(xs.get(i))) {
                tree.add(Collections.binarySearch(ys, y) + 1); // 離散化
            }
            for (Data q : qs[i]) {
                res[q.qid] += q.sign * tree.query(q.y1 + 1, q.y2 + 1);
            }
        }

        long ans = -1;
        for (int i = 0; i < res.length; i++) {
            if (res[i] == 4) { // 矩形區域（包括邊界）恰好有 4 個點
                ans = Math.max(ans, queries.get(i).area);
            }
        }
        return ans;
    }

    class Fenwick {
        private final int[] tree;

        Fenwick(int n) {
            tree = new int[n];
        }

        void add(int i) {
            while (i < tree.length) {
                tree[i]++;
                i += i & -i;
            }
        }

        // [1,i] 中的元素和
        int pre(int i) {
            int res = 0;
            while (i > 0) {
                res += tree[i];
                i -= i & -i;
            }
            return res;
        }

        // [l,r] 中的元素和
        int query(int l, int r) {
            return pre(r) - pre(l - 1);
        }
    }


}









