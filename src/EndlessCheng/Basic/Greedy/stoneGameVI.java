package EndlessCheng.Basic.Greedy;

import java.util.Arrays;

public class stoneGameVI {

    // https://leetcode.cn/problems/stone-game-vi/solutions/2628498/xiang-xi-jie-shi-wei-shi-yao-yao-an-zhao-0zsg/
    public int stoneGameVI(int[] a, int[] b) {
        int n = a.length;
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (i, j) -> a[j] + b[j] - a[i] - b[i]);
        int diff = 0;
        for (int i = 0; i < n; i++) {
            diff += i % 2 == 0 ? a[ids[i]] : -b[ids[i]];
        }
        return Integer.compare(diff, 0);
    }


}
