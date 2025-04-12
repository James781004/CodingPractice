package EndlessCheng.GenreMenu.SlidingWindow.ThreePointers;

public class CountSubarrays {

    // https://leetcode.cn/problems/count-subarrays-with-fixed-bounds/solutions/1895713/jian-ji-xie-fa-pythonjavacgo-by-endlessc-gag2/
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long ans = 0;
        int minI = -1, maxI = -1, i0 = -1;
        for (int i = 0; i < nums.length; i++) { // 枚舉子數組的右端點
            int x = nums[i];
            if (x == minK) minI = i; // 記錄 minK 上一次出現的位置 minI，以及 maxK 上一次出現的位置 maxI
            if (x == maxK) maxI = i;
            if (x < minK || x > maxK) i0 = i; // 子數組不能包含 nums[i0]

            // 遍歷到 nums[i] 時，如果 minK 和 maxK 之前出現過，
            // 則左端點 ≤ min(minI,maxI) 的子數組都是合法的
            // 如果 min(minI,maxI)−i0 < 0，則表示在 i0 右側 minK 和 maxK 沒有同時出現，
            // 此時以 i 為右端點的合法子數組的個數為 0
            ans += Math.max(Math.min(minI, maxI) - i0, 0);
        }
        return ans;
    }


}
