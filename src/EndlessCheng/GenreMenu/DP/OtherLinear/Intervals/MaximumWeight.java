package EndlessCheng.GenreMenu.DP.OtherLinear.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaximumWeight {

    // https://leetcode.cn/problems/maximum-score-of-non-overlapping-intervals/solutions/3039058/dong-tai-gui-hua-er-fen-cha-zhao-you-hua-wmuy/
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Tuple[] a = new Tuple[n];
        for (int i = 0; i < n; i++) {
            List<Integer> interval = intervals.get(i);
            a[i] = new Tuple(interval.get(0), interval.get(1), interval.get(2), i);
        }
        Arrays.sort(a, (p, q) -> p.r - q.r);

        Pair[][] f = new Pair[n + 1][5];
        Arrays.setAll(f[0], i -> new Pair(0, new ArrayList<>()));
        for (int i = 0; i < n; i++) {
            Tuple t = a[i];
            int k = search(a, i, t.l); //  k 是最大的滿足右端點嚴格小於第 i 個區間左端點的區間下標，不存在時為 −1
            f[i + 1][0] = new Pair(0, new ArrayList<>());
            for (int j = 1; j < 5; j++) {
                long s1 = f[i][j].sum;
                long s2 = f[k + 1][j - 1].sum + t.weight;
                if (s1 > s2) {
                    f[i + 1][j] = f[i][j];
                    continue;
                }
                List<Integer> newId = new ArrayList<>(f[k + 1][j - 1].id);
                newId.add(t.i);
                Collections.sort(newId);
                if (s1 == s2 && compareLists(f[i][j].id, newId) < 0) {
                    newId = f[i][j].id;
                }
                f[i + 1][j] = new Pair(s2, newId);
            }
        }
        return f[n][4].id.stream().mapToInt(v -> v).toArray();
    }

    // 返回 r < upper 的最大下標
    private int search(Tuple[] a, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (a[mid].r < upper) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

    // 比較兩個 List 的字典序
    private int compareLists(List<Integer> a, List<Integer> b) {
        for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return a.get(i) - b.get(i);
            }
        }
        return a.size() - b.size();
    }

    private record Tuple(int l, int r, int weight, int i) {
    }

    private record Pair(long sum, List<Integer> id) {
    }


}
