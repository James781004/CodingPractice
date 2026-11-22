package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.HashMap;

public class MaxNonOverlapping {

    // https://leetcode.cn/problems/maximum-number-of-non-overlapping-subarrays-with-sum-equals-target/solutions/3053424/qian-zhui-he-ha-xi-biao-ling-shen-ti-dan-67vw/
    public int maxNonOverlapping(int[] arr, int k) {
        int n = arr.length;
        int[] s = new int[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + arr[i];
        }
        int res = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        cnt.put(0, 0);
        int last = 0;
        for (int i = 1; i <= n; i++) {
            int x = s[i];
            if (cnt.containsKey(x - k) && cnt.get(x - k) >= last) {
                res++;
                last = i;
            }
            cnt.put(x, i);
        }
        return res;
    }

}
