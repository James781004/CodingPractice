package EndlessCheng.GenreMenu.Graph.MinimumSpanningTree;

import java.util.Arrays;

public class MinimumCost {

    // https://leetcode.cn/problems/minimum-cost-for-cutting-cake-ii/solutions/2843063/tan-xin-ji-qi-zheng-ming-jiao-huan-lun-z-ivtn/
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        Arrays.sort(horizontalCut);
        Arrays.sort(verticalCut);
        int i = 0;
        int j = 0;
        long ans = 0L;
        // 一開始每個節點都是單獨的聯通分量，即有m * n個聯通分量
        while (i < m - 1 && j < n - 1) {
            // n - j(m - i同理)理解：
            // 當加入一條垂直邊之後，該條邊的每一行相鄰兩列就已經聯通
            // 也就是原來每一行有n個聯通分量，行與行之間如果要聯通，則需要加入n條權重為horizontalCut[i]的邊
            // 但如果聯通本行之前添加了j條垂直邊，那麼每一行將只剩下n - j個聯通分量，只需要加入n - j條權重為horizontalCut[i]的邊即可聯通本行
            if (horizontalCut[i] <= verticalCut[j]) {
                ans += horizontalCut[i++] * (n - j);
            } else {
                ans += verticalCut[j++] * (m - i);
            }
        }
        // 此時每一行的列與列之間都已聯通(包括剩下的行)
        // 剩下的每一行看做一個聯通分量，已全部聯通的行看做另一個連通分量，這兩個聯通分量只需要加入一條權重為horizontalCut[i]的邊即可變成同一個聯通分量
        while (i < m - 1) {
            ans += horizontalCut[i++];
        }
        // 此時每一列的行與行之間都已聯通(包括剩下的列)
        // 剩下的每一列看做一個聯通分量，已全部聯通的列看做另一個連通分量，這兩個聯通分量只需要加入一條權重為verticalCut[j]的邊即可變成同一個聯通分量
        while (j < n - 1) {
            ans += verticalCut[j++];
        }
        return ans;
    }
    

}
