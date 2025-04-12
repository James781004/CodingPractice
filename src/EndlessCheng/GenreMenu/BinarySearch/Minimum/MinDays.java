package EndlessCheng.GenreMenu.BinarySearch.Minimum;

public class MinDays {

    // https://leetcode.cn/problems/minimum-number-of-days-to-make-m-bouquets/solutions/765539/chi-xiao-dou-python-wei-shi-yao-shi-er-f-24p7/

    /**
     * 制作花朵最少的時間必然是 bloomDay 數組中開花所用天數最少的那朵花 min(bloomDay)
     * 制作花朵最多的時間也只能是 bloomDay 數組中開花所需天數最多的那朵花 max(bloomDay)
     * 尋找制作花束的最少天數必然落在上面所說的區間裡 [min(bloomDay), max(bloomDay)][min(bloomDay),max(bloomDay)]
     * 連續的一個正整數區間, 因此可以通過二分查找來提升查找效率!
     * 除此之外, 還有一些細節來填充, 代碼就算有一個完整的雛形了.
     * 數組中的花朵不夠用來制作花束的, 直接返回 -1
     * 必須是連續的花朵, 這個可以通過變量 flower 來計數是否連續, 一旦不連續就重置為 0
     * 每滿足一次連續的 k 朵花, 就可以制作一束花, flower 計數重新為 0 開始計數
     *
     * @param nums
     * @param m
     * @param k
     * @return
     */
    public int minDays(int[] nums, int m, int k) {
        int len = nums.length;
        // 判斷總的花夠不夠
        if (len < m * k) return -1;
        int left = 0, right = 0;
        for (int num : nums) {
            left = Math.min(left, num);
            right = Math.max(right, num);
        }
        // 因為right賦值為len(nums), 所以這裡需要開區間 [left, right), 否則越界了
        // 終止條件是 left == right
        while (left < right) {
            // 假設嘗試 mid 作為最小的天數, 看看能不能制作要求的花束
            int mid = left + (right - left) / 2;
            //尋找滿足條件的值
            if (check(nums, mid, m, k)) {
                // 如果可以制作花束, 那麼可以嘗試天數再減小一點, 因此right往左走, 縮小搜索范圍
                right = mid;
            } else {
                // 如果無法制作花束, 那麼可以嘗試天數再增大一點, 因此left往右邊走, 區間的左邊值加大
                left = mid + 1;
            }
        }
        return left;
    }

    private boolean check(int[] nums, int mid, int m, int k) {
        // 數連續的花朵數量
        int follower = 0;
        // 記錄花束
        int cnt = 0;
        for (int num : nums) {
            if (num <= mid) {
                follower++;
                // 做成了一束花
                if (follower == k) {
                    cnt++;
                    // 置0, 重新開始計算
                    follower = 0;
                }
            } else {
                // 表示花朵不連續了, 置 0
                follower = 0;
            }
            if (cnt >= m) {
                break;
            }

        }
        return cnt >= m;
    }


}
