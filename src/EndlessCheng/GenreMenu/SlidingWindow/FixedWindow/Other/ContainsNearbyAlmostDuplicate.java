package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Other;

import java.util.TreeSet;

public class ContainsNearbyAlmostDuplicate {

    // https://leetcode.cn/problems/contains-duplicate-iii/solutions/13681/hua-dong-chuang-kou-er-fen-sou-suo-shu-zhao-shang-/
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        // 滑動窗口結合查找表，此時滑動窗口即為查找表本身（控制查找表的大小即可控制窗口大小）
        TreeSet<Long> set = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            // 邊添加邊查找，查找表中是否有大於等於 nums[i] - t 且小於等於 nums[i] + t 的值
            Long ceiling = set.ceiling((long) nums[i] - (long) t);
            if (ceiling != null && ceiling <= ((long) nums[i] + (long) t)) {
                return true;
            }
            // 添加後，控制查找表（窗口）大小，移除窗口最左邊元素
            set.add((long) nums[i]);
            if (set.size() == k + 1) {
                set.remove((long) nums[i - k]);
            }
        }
        return false;
    }


    // 另一種寫法：在一開始就把滑動窗口左邊的元素刪除。
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {
        int len = nums.length;
        // 特判
        if (len == 0 || k <= 0 || t < 0) {
            return false;
        }

        TreeSet<Long> set = new TreeSet<>();

        for (int i = 0; i < len; i++) {
            if (i > k) {
                set.remove((long) nums[i - k - 1]);
            }

            Long ceiling = set.ceiling((long) nums[i] - (long) t);
            if (ceiling != null && ceiling <= (long) nums[i] + (long) t) {
                return true;
            }

            set.add((long) nums[i]);
        }
        return false;
    }


}
