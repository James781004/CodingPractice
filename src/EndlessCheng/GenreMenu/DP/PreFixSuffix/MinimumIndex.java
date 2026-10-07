package EndlessCheng.GenreMenu.DP.PreFixSuffix;

import java.util.List;

public class MinimumIndex {

    // https://leetcode.cn/problems/minimum-index-of-a-valid-split/solutions/2345798/jie-lun-zheng-ming-mei-ju-by-endlesschen-w156/
    public int minimumIndex(List<Integer> nums) {
        // 找出支配元素
        int dominant = moreVote(nums);
        // 統計支配元素的總出現次數
        int totalCount = 0;
        for (int num : nums) {
            if (num == dominant) {
                totalCount++;
            }
        }
        // 記錄左子數組中支配元素的出現次數
        int leftCount = 0;
        for (int i = 0; i < nums.size() - 1; i++) {
            if (nums.get(i) == dominant) {
                leftCount++;
            }
            // 計算右子數組中支配元素的出現次數
            int rightCount = totalCount - leftCount;

            // 判斷是否滿足合法分割的條件：
            // 對於左子數組，其長度為 i + 1（因為下標從 0 開始，到 i 結束，共 i + 1 個元素），
            // 當左子數組中支配元素的出現次數 leftCount 的兩倍大於左子數組的長度 i + 1 時，
            // 說明在左子數組中支配元素的數量超過了一半，滿足支配元素的定義。
            // 對於右子數組，其長度為 nums.size() - i - 1（總長度減去左子數組長度再減去當前分割點的一個元素），
            // 當右子數組中支配元素的出現次數 rightCount 的兩倍大於右子數組的長度 nums.size() - i - 1 時，
            // 說明在右子數組中支配元素的數量也超過了一半，滿足支配元素的定義。
            // 只有當左右子數組都滿足支配元素的定義且它們的支配元素都是我們之前找到的 dominant 時，
            // 這個分割點 i 才是一個合法的分割點，此時直接返回 i。
            if (leftCount * 2 > (i + 1) && rightCount * 2 > (nums.size() - i - 1)) {
                return i;
            }
        }
        return -1;
    }

    public int moreVote(List<Integer> nums) {
        int votes = 0;
        int ans = -1;
        for (int num : nums) {
            if (votes == 0) {
                ans = num;
            }
            if (ans == num) {
                ++votes;
            } else {
                --votes;
            }
        }
        return ans;
    }
}
