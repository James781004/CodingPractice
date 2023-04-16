package WeeklyContest;

import java.util.HashSet;
import java.util.Set;

public class Week_315 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2441.Largest%20Positive%20Integer%20That%20Exists%20With%20Its%20Negative/README.md
    public int findMaxK(int[] nums) {
        int ans = -1;
        Set<Integer> set = new HashSet<>();
        for (int v : nums) set.add(v);
        for (int v : set) {
            if (set.contains(-1)) ans = Math.max(ans, v);
        }
        return ans;
    }

    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2442.Count%20Number%20of%20Distinct%20Integers%20After%20Reverse%20Operations/README.md
    public int countDistinctIntegers(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int v : nums) set.add(v);
        for (int x : nums) {
            int y = 0;
            while (x > 0) {  // 反轉數字
                y = y * 10 + x % 10;
                x /= 10;
            }
            set.add(y);
        }
        return set.size();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2443.Sum%20of%20Number%20and%20Its%20Reverse/README.md
    public boolean sumOfNumberAndReverse(int num) {
        for (int x = 0; x <= num; x++) {
            int k = x;
            int y = 0;
            while (k > 0) {  // 反轉數字
                y = y * 10 + k % 10;
                k /= 10;
            }
            if (x + y == num) return true;
        }
        return false;
    }


    // https://www.bilibili.com/video/BV1Ae4y1i7PM/
    // https://leetcode.cn/problems/count-subarrays-with-fixed-bounds/solution/jian-ji-xie-fa-pythonjavacgo-by-endlessc-gag2/
    // 枚舉子數組的右端點。遍歷 nums，記錄 minK 上一次出現的位置 minI 和 maxK 上一次出現的位置 maxI，
    // 當遍歷到 nums[i] 時，如果 minK 和 maxK 之前出現過，則左端點 ≤min(minI,maxI) 的子數組都是合法的，
    // 合法子數組的個數為 min(minI,maxI)+1。
    // 回到原問題，由於子數組不能包含在 [minK,maxK] 范圍之外的元素，
    // 因此我們還需要記錄上一個在 [minK,maxK] 范圍之外的 nums[i] 的下標，記作 i0。
    // 此時合法子數組的個數為 min(minI,maxI)−i0
    public long countSubArrays(int[] nums, int minK, int maxK) {
        long ans = 0L;
        int n = nums.length, minI = -1, maxI = -1, i0 = -1;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (x == minK) minI = i;
            if (x == maxK) maxI = i;
            if (x < minK || x > maxK) i0 = i; // 子數組不能包含 nums[i0]
            ans += Math.max(Math.min(minI, maxI) - i0, 0);  // 計算子數組數量
        }
        return ans;
    }

}
