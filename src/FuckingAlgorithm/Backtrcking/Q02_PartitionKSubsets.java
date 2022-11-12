package FuckingAlgorithm.Backtrcking;

import java.util.HashMap;

public class Q02_PartitionKSubsets {
//    https://leetcode.cn/problems/partition-to-k-equal-sum-subsets/
//    698. 劃分為k個相等的子集
//    給定一個整數數組  nums 和一個正整數 k，找出是否有可能把這個數組分成 k 個非空子集，其總和都相等。


    // 以桶的視角
    public boolean canPartitionKSubsets(int[] nums, int k) {
        // 排除一些基本情況
        if (k > nums.length) return false;
        int sum = 0;
        for (int v : nums) sum += v;
        if (sum % k != 0) return false;

        HashMap<Integer, Boolean> memo = new HashMap<>();

        int used = 0; // 使用位圖技巧
        int target = sum / k;
        // k 號桶初始什麼都沒裝，從 nums[0] 開始做選擇
        return backtrack(k, 0, nums, 0, used, target, memo);
    }


    boolean backtrack(int k, int bucket,
                      int[] nums, int start, int used, int target, HashMap<Integer, Boolean> memo) {
        // base case
        if (k == 0) return true; // 所有桶都被裝滿了，而且 nums 一定全部用完了
        if (bucket == target) {
            // 裝滿了當前桶，遞歸窮舉下一個桶的選擇
            // 讓下一個桶從 nums[0] 開始選數字
            boolean res = backtrack(k - 1, 0, nums, 0, used, target, memo);
            // 緩存結果
            memo.put(used, res);
            return res;
        }

        if (memo.containsKey(used)) {
            // 避免冗余計算
            return memo.get(used);
        }

        for (int i = start; i < nums.length; i++) {
            // 剪枝
            if (((used >> i) & 1) == 1) { // 判斷第 i 位是否是 1
                // nums[i] 已經被裝入別的桶中
                continue;
            }
            if (nums[i] + bucket > target) {
                continue;
            }
            // 做選擇
            used |= 1 << i; // 將第 i 位置為 1
            bucket += nums[i];


            // 遞歸窮舉下一個數字是否裝入當前桶
            if (backtrack(k, bucket, nums, i + 1, used, target, memo)) {
                return true;
            }
            // 撤銷選擇
            used ^= 1 << i; // 將第 i 位置為 0
            bucket -= nums[i];
        }

        return false;
    }


    // 以數字的視角
    boolean canPartitionKSubsets2(int[] nums, int k) {
        // 排除一些基本情況
        if (k > nums.length) return false;
        int sum = 0;
        for (int v : nums) sum += v;
        if (sum % k != 0) return false;

        // k 個桶（集合），記錄每個桶裝的數字之和
        int[] bucket = new int[k];
        // 理論上每個桶（集合）中數字的和
        int target = sum / k;
        // 窮舉，看看 nums 是否能劃分成 k 個和為 target 的子集
        return backtrack(nums, 0, bucket, target);
    }

    // 遞歸窮舉 nums 中的每個數字
    boolean backtrack(
            int[] nums, int index, int[] bucket, int target) {

        if (index == nums.length) {
            // 檢查所有桶的數字之和是否都是 target
            for (int i = 0; i < bucket.length; i++) {
                if (bucket[i] != target) {
                    return false;
                }
            }
            // nums 成功平分成 k 個子集
            return true;
        }

        // 窮舉 nums[index] 可能裝入的桶
        for (int i = 0; i < bucket.length; i++) {
            // 剪枝，桶裝裝滿了
            if (bucket[i] + nums[index] > target) {
                continue;
            }
            // 將 nums[index] 裝入 bucket[i]
            bucket[i] += nums[index];
            // 遞歸窮舉下一個數字的選擇
            if (backtrack(nums, index + 1, bucket, target)) {
                return true;
            }
            // 撤銷選擇
            bucket[i] -= nums[index];
        }

        // nums[index] 裝入哪個桶都不行
        return false;
    }
}
