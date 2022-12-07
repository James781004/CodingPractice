package FuckingAlgorithm.Extra;

import java.util.HashMap;

public class Q03_SplitArrayIntoConsecutiveSubsequences {
//    https://leetcode.cn/problems/split-array-into-consecutive-subsequences/
//    659. 分割數組為連續子序列
//    給你一個按升序排序的整數數組 num（可能包含重復數字），請你將它們分割成一個或多個長度至少為 3 的子序列，其中每個子序列都由連續整數組成。
//
//    如果可以完成上述分割，則返回 true ；否則，返回 false 。

    public boolean isPossible(int[] nums) {
        HashMap<Integer, Integer> freq = new HashMap<>();
        HashMap<Integer, Integer> need = new HashMap<>();
        // 統計 nums 中元素的頻率
        for (int n : nums) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        for (int n : nums) {
            // 已經被用到其他子序列中(排除)
            if (freq.get(n) == 0) continue;
            // 先判斷 n 是否能接到其他子序列後面
            if (need.containsKey(n) && need.get(n) > 0) {
                // n 可以接到之前的某個序列後面
                //對n的需求減一
                need.put(n, need.get(n) - 1);
                freq.put(n, freq.get(n) - 1);
                //對n+1的需求加一
                need.put(n + 1, need.getOrDefault(n + 1, 0) + 1);
            } else if (freq.containsKey(n) && freq.containsKey(n + 1) && freq.containsKey(n + 2)
                    && freq.get(n) > 0 && freq.get(n + 1) > 0 && freq.get(n + 2) > 0) {
                // 將 n 作為開頭，新建一個長度為 3 的子序列 [n,n+1,n+2]
                freq.put(n, freq.get(n) - 1);
                freq.put(n + 1, freq.get(n + 1) - 1);
                freq.put(n + 2, freq.get(n + 2) - 1);
                // 對 n + 3 的需求加一
                need.put(n + 3, need.getOrDefault(n + 3, 0) + 1);
            } else {
                // 兩種情況都不符合，則無法分配
                return false;
            }

        }

        return true;
    }


    public boolean isPossibleGreedy(int[] nums) {
        int n = nums.length;
        int dp1 = 0;    // 長度為1的子序列數目
        int dp2 = 0;    // 長度為2的子序列數目
        int dp3 = 0;    // 長度>=3的子序列數目
        int idx = 0;
        int start = 0;  // 起始位置

        while (idx < n) {
            start = idx;                        // 重新將起始位置賦值
            int x = nums[idx];
            while (idx < n && nums[idx] == x) {
                idx++;
            }
            int cnt = idx - start;

            if (start > 0 && x != nums[start - 1] + 1) {  // 對於nums[idx] != nums[idx - 1] + 1，說明當前整數無法加入到以nums[idx-1] 為結尾的序列中
                if (dp1 + dp2 > 0) {                      // 如果 dp1+dp2>0，說明有些長度≤2的序列無法被滿足，因此不存在相應的分割方案。
                    return false;
                } else {                                  // 否則，此前的序列全部作廢
                    dp1 = cnt;
                    dp2 = dp3 = 0;
                }
            } else {                                      // 對於nums[idx] == nums[idx - 1] + 1，說明當前整數可以加入到所有以nums[idx-1]為結尾的序列中。假設數組中x的數目為cnt：
                if (dp1 + dp2 > cnt) {                    // 首先，根據貪心的策略，我們要盡可能地先把 x 添加到長度≤2 的子序列中，從而盡可能地保證子序列的長度都≥3。如果x的數量不夠，說明不存在相應的分割方案。
                    return false;
                }
                int left = cnt - dp1 - dp2;             // 此時 還剩下left = cnt -dp1 -dp2個 nums[idx-1](x)
                int keep = Math.min(dp3, left);          // 盡量將余下的left個整數添加到長度≥3的子序列中
                // 最後，更新 dp1,dp2,dp3的取值
                dp3 = keep + dp2;
                dp2 = dp1;
                dp1 = left - keep;                      // 如果還有剩余，才將開啟對應數量的新序列。新序列的數目等於left−keep。
            }
        }

        return dp1 == 0 && dp2 == 0;
    }
}
