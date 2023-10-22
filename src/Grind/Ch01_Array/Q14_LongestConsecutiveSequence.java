package Grind.Ch01_Array;

import java.util.HashSet;

public class Q14_LongestConsecutiveSequence {
    // https://leetcode.com/problems/longest-consecutive-sequence/
    // https://leetcode.cn/problems/longest-consecutive-sequence/solutions/1176496/xiao-bai-lang-ha-xi-ji-he-ha-xi-biao-don-j5a2/
    public int longestConsecutive(int[] nums) {
        // 轉化成哈希集合，方便快速查找是否存在某個元素
        HashSet<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            set.add(num);
        }

        int res = 0;

        for (int num : set) {
            if (set.contains(num - 1)) {
                // num 不是連續子序列的第一個，跳過
                continue;
            }

            // num 是連續子序列的第一個，開始向上計算連續子序列的長度
            int curNum = num;
            int curLen = 1;

            while (set.contains(curNum + 1)) {
                curNum += 1;
                curLen += 1;
            }

            // 更新最長連續序列的長度
            res = Math.max(res, curLen);
        }

        return res;
    }
}
