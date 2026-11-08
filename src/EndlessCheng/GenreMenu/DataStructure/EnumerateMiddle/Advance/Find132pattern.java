package EndlessCheng.GenreMenu.DataStructure.EnumerateMiddle.Advance;

import java.util.TreeMap;

public class Find132pattern {

    // https://leetcode.cn/problems/132-pattern/solutions/3773185/mei-ju-zhong-jian-you-xu-ji-he-er-fen-li-breu/
    public boolean find132pattern(int[] nums) {
        int n = nums.length;
        if (n < 3) {
            return false;
        }

        // 1. mmin 維護前綴最小值，代表模式中的 '1' (nums[i])
        int mmin = nums[0];

        // 2. 使用 TreeMap 模擬 C++ 的 multiset，記錄右側後綴所有元素及其出現次數
        // key: 元素值, value: 出現頻率
        TreeMap<Integer, Integer> suf = new TreeMap<>();
        for (int i = 2; i < n; i++) {
            suf.put(nums[i], suf.getOrDefault(nums[i], 0) + 1);
        }

        // 3. 枚舉中間的 '3' (nums[j])，此時下標為 j (即迴圈中的 i)
        for (int i = 1; i < n - 1; i++) {
            // 只有當前元素 nums[i] 大於左側最小值 mmin 時，才可能作為合法的 '3'
            if (nums[i] > mmin) {
                // 在後綴有序集合中，利用二分尋找嚴格大於 mmin 的最小元素
                // higherKey(k) 相當於 C++ 的 upper_bound(k)
                Integer rightVal = suf.higherKey(mmin);

                // 如果存在大於 mmin 的數字，且該數字同時小於當前的 nums[i]，則構成 132 模式
                if (rightVal != null && rightVal < nums[i]) {
                    return true;
                }
            }

            // 更新左側最小值
            mmin = Math.min(mmin, nums[i]);

            // 將即將移出後綴的元素 nums[i + 1] 從 TreeMap 中移除一次計數
            int nextVal = nums[i + 1];
            int count = suf.get(nextVal);
            if (count == 1) {
                suf.remove(nextVal);
            } else {
                suf.put(nextVal, count - 1);
            }
        }

        return false;

    }
}
