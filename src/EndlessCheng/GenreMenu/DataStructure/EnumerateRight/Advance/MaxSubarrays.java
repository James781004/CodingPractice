package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MaxSubarrays {

    // https://leetcode.cn/problems/maximize-subarrays-after-removing-one-conflicting-pair/solutions/3603047/mei-ju-zuo-duan-dian-wei-hu-zui-xiao-ci-4nvu6/
    public long maxSubarrays(int n, int[][] conflictingPairs) {
        List<Integer>[] groups = new ArrayList[n + 1];
        Arrays.setAll(groups, v -> new ArrayList<>());
        for (int[] p : conflictingPairs) {
            int a = p[0];
            int b = p[1];
            groups[Math.min(a, b)].add(Math.max(a, b));
        }

        long ans = 0;
        long maxExtra = 0;
        long[] extra = new long[n + 2];
        List<Integer> b = new ArrayList<>();
        b.add(n + 1);
        b.add(n + 1);

        for (int i = n; i > 0; i--) {
            // 維護最小 b 和次小 b
            b.addAll(groups[i]);
            Collections.sort(b);
            b.subList(2, b.size()).clear();

            int b0 = b.get(0);
            ans += b0 - i;
            extra[b0] += b.get(1) - b0;
            maxExtra = Math.max(maxExtra, extra[b0]);
        }

        return ans + maxExtra;
    }

}
