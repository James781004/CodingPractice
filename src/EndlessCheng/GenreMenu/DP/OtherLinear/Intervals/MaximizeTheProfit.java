package EndlessCheng.GenreMenu.DP.OtherLinear.Intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MaximizeTheProfit {

    // https://leetcode.cn/problems/maximize-the-profit-as-the-salesman/solutions/2396402/xian-xing-dpfu-xiang-si-ti-mu-pythonjava-wmh7/
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        List<int[]>[] groups = new ArrayList[n];
        Arrays.setAll(groups, i -> new ArrayList<>());
        for (List<Integer> offer : offers) { // 先把所有 end 相同的數據歸類
            groups[offer.get(1)].add(new int[]{offer.get(0), offer.get(2)});
        }

        int[] f = new int[n + 1]; // f[i+1] 表示銷售編號不超過 i 的房屋的最大盈利
        for (int end = 0; end < n; end++) {
            // 考慮編號為 i 的房屋賣或不賣
            // 不賣，有 f[i+1]=f[i]
            f[end + 1] = f[end];

            // 賣，那麼遍歷所有 end_j = i 的購買請求，有 f[i+1]=max(f[start_j]+gold_j)
            for (int[] p : groups[end]) {
                f[end + 1] = Math.max(f[end + 1], f[p[0]] + p[1]);
            }
        }
        return f[n];
    }


}
