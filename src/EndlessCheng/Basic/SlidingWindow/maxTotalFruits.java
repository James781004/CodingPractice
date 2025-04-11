package EndlessCheng.Basic.SlidingWindow;

public class maxTotalFruits {

    // https://leetcode.cn/problems/maximum-fruits-harvested-after-at-most-k-steps/solutions/2254860/hua-dong-chuang-kou-jian-ji-xie-fa-pytho-1c2d/
    public int maxTotalFruits(int[][] fruits, int startPos, int k) {
        int left = lowerBound(fruits, startPos - k); // 向左最遠能到 fruits[left][0]
        int ans = 0, s = 0, n = fruits.length;
        for (int right = left; right < n && fruits[right][0] <= startPos + k; right++) {
            s += fruits[right][1]; // 枚舉最右位置為 fruits[right][0]

            // 先向右再向左，那麼移動距離為 (fruits[right][0]−startPos)+(fruits[right][0]−fruits[left][0])
            // 先向左再向右，那麼移動距離為 (startPos−fruits[left][0])+(fruits[right][0]−fruits[left][0])
            // 如果上面兩個式子均大於 k，就說明 fruits[left][0] 太遠了，需要增加 left
            while (fruits[right][0] * 2 - fruits[left][0] - startPos > k &&
                    fruits[right][0] - fruits[left][0] * 2 + startPos > k)
                s -= fruits[left++][1]; // fruits[left][0] 無法到達
            ans = Math.max(ans, s); // 更新答案最大值
        }
        return ans;
    }


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
