package Grind.Ch08_HashTable;

import java.util.HashSet;
import java.util.Set;

public class Q03_FirstMissingPositive {
    // https://leetcode.cn/problems/first-missing-positive/solutions/281290/javade-6chong-jie-fa-de-tu-wen-xiang-jie-wei-yun-s/
    public int firstMissingPositive(int[] nums) {
        int len = nums.length;
        Set<Integer> hashSet = new HashSet<>();
        for (int num : nums) {
            hashSet.add(num);
        }
        for (int i = 1; i <= len; i++) {
            if (!hashSet.contains(i))
                return i;
        }
        return len + 1;
    }

    // 存放對應的位置
    public int firstMissingPositive1(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            // 如果在指定的位置就不需要修改
            if (i + 1 == nums[i]) continue;
            int x = nums[i];
            if (x >= 1 && x <= nums.length && x != nums[x - 1]) {
                swap(nums, i, x - 1);
                i--; // 抵消上面的i++，如果交換之後就不++；
            }
        }
        // 最後執行一遍循環，查看對應位置的元素是否正確，如果不正確直接返回
        for (int i = 0; i < nums.length; i++) {
            if (i + 1 != nums[i])
                return i + 1;
        }
        return nums.length + 1;
    }

    // 交換兩個數的值
    public void swap(int[] A, int i, int j) {
        if (i != j) {
            A[i] ^= A[j];
            A[j] ^= A[i];
            A[i] ^= A[j];
        }
    }


    // 原地hash法
    // 答案在[1,n+1]之中產生，因此可以利用原數組進行映射
    // 因為負數、0、大於n的數字不會對結果產生影響，將非正數統一變為為0x3f3f3f3f，方便後面進行標記
    // 每當遇到[1,n]之間的數字就將其映射至索引i-1處，恰好對應[0,n-1]，將該處的數字標記為負數
    // 這樣既可以實現標記，又不會損失原來的數據
    // 最後看看首個還為正數的數字索引+1就是答案
    // 時間復雜度:O(N) 空間復雜度:O(1)
    public int firstMissingPositive2(int[] nums) {
        int n = nums.length;
        // 將0和負數標記為0x3f3f3f3f
        for (int i = 0; i < n; i++) {
            if (nums[i] <= 0) {
                nums[i] = 0x3f3f3f3f;
            }
        }
        // 將[1,n]之間的數字進行標記
        for (int i = 0; i < n; i++) {
            // 提取原來的數字
            int v = Math.abs(nums[i]);
            // num在[1,n]之間，標記為負數
            if (v <= n) {
                nums[v - 1] = -Math.abs(nums[v - 1]);
            }
        }
        // 找出首個缺失的數字
        for (int i = 0; i < n; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }
}
