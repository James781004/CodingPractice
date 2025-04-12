package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class MaxTotalFruits {

    // https://leetcode.cn/problems/maximum-fruits-harvested-after-at-most-k-steps/solutions/2254860/hua-dong-chuang-kou-jian-ji-xie-fa-pytho-1c2d/
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int left = lowerBound(fruits, startPos - k); // 向左最遠能到 fruits[left][0]
        int right = left, s = 0, n = fruits.length;
        for (; right < n && fruits[right][0] <= startPos; right++)
            s += fruits[right][1]; // 從 fruits[left][0] 到 startPos 的水果數
        int ans = s;
        for (; right < n && fruits[right][0] <= startPos + k; right++) {
            s += fruits[right][1]; // 枚舉最右位置為 fruits[right][0]
            while (fruits[right][0] * 2 - fruits[left][0] - startPos > k &&
                    fruits[right][0] - fruits[left][0] * 2 + startPos > k)
                s -= fruits[left++][1]; // fruits[left][0] 無法到達
            ans = Math.max(ans, s); // 更新答案最大值
        }
        return ans;
    }

    // 見 https://www.bilibili.com/video/BV1AP41137w7/
    private int lowerBound(int[][] fruits, int target) {
        int left = -1, right = fruits.length; // 開區間 (left, right)
        while (left + 1 < right) { // 開區間不為空
            // 循環不變量：
            // fruits[left][0] < target
            // fruits[right][0] >= target
            int mid = (left + right) >>> 1;
            if (fruits[mid][0] < target)
                left = mid; // 范圍縮小到 (mid, right)
            else
                right = mid; // 范圍縮小到 (left, mid)
        }
        return right;
    }


}
