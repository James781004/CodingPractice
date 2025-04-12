package EndlessCheng.GenreMenu.BinarySearch.Kth;

import java.util.Arrays;

public class SmallestDistancePair {

    // https://leetcode.cn/problems/find-k-th-smallest-pair-distance/solutions/3020680/bi-ji-er-fen-hua-dong-chuang-kou-by-huan-0pmz/
    // 查找數組中距離最小的k對數的最大距離
    public int smallestDistancePair(int[] nums, int k) {
        // 先對數組進行排序，以便後續處理
        Arrays.sort(nums);
        int n = nums.length;
        // 初始化左右邊界，左邊界為-1，右邊界為數組中最大值與最小值的差
        int l = -1, r = nums[n - 1] - nums[0];
        // 在左右邊界之間進行二分查找
        while (l + 1 < r) {
            // 中間值，注意使用無符號右移操作來避免可能的整數溢出問題
            int m = l + r >>> 1;
            // 如果距離不超過m的對數大於等於k，說明答案可能在中值左側或就是中值本身，因此調整右邊界
            if (count(nums, m) >= k) {
                r = m;
            } else {
                // 否則，答案在中值右側，調整左邊界
                l = m;
            }
        }
        // 循環結束時，l和r相鄰且滿足條件，返回r（或l，因為此時它們相等）作為結果
        return r;
    }

    // 計算數組中距離不超過limit的對數
    int count(int[] nums, int limit) {
        int l = 0, r = 1, cnt = 0;
        // 使用雙指針遍歷數組，計算距離不超過limit的對數
        while (r < nums.length) {
            // 如果當前左右指針之間的距離超過limit，移動左指針
            while (nums[r] - nums[l] > limit) {
                l++;
            }
            // 此時左右指針之間的距離不超過limit，將當前右指針及其左側所有點與左指針組成的對數加到結果中
            cnt += r - l;
            // 移動右指針繼續查找
            r++;
        }
        // 返回結果
        return cnt;
    }
}



