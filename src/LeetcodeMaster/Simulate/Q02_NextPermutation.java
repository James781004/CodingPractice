package LeetcodeMaster.Simulate;

import java.util.Arrays;

public class Q02_NextPermutation {
//    31.下一個排列
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0031.%E4%B8%8B%E4%B8%80%E4%B8%AA%E6%8E%92%E5%88%97.md
//
//    實現獲取 下一個排列 的函數，算法需要將給定數字序列重新排列成字典序中下一個更大的排列。
//
//    如果不存在下一個更大的排列，則將數字重新排列成最小的排列（即升序排列）。
//
//    必須 原地 修改，只允許使用額外常數空間。
//
//    示例 1：
//
//    輸入：nums = [1,2,3]
//    輸出：[1,3,2]
//    示例 2：
//
//    輸入：nums = [3,2,1]
//    輸出：[1,2,3]
//    示例 3：
//
//    輸入：nums = [1,1,5]
//    輸出：[1,5,1]
//    示例 4：
//
//    輸入：nums = [1]
//    輸出：[1]

    public void nextPermutation(int[] nums) {
        for (int i = nums.length - 1; i >= 0; i--) {
            for (int j = nums.length - 1; j > i; i--) {
                if (nums[j] > nums[i]) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                    // [i + 1, nums.length) 內元素升序排序
                    Arrays.sort(nums, i + 1, nums.length);
                    return;
                }
            }
        }
        Arrays.sort(nums); // 不存在下一個更大的排列，則將數字重新排列成最小的排列（即升序排列）。
    }


    // 優化時間覆雜度為O(N)，空間覆雜度為O(1)
    public void nextPermutation2(int[] nums) {
        // 1.從後向前獲取逆序區域的前一位
        int index = findIndex(nums);
        // 判斷數組是否處於最小組合狀態
        if (index != 0) {
            // 2.交換逆序區域剛好大於它的最小數字
            exchange(nums, index);
        }
        // 3.把原來的逆序區轉為順序
        reverse(nums, index);
    }

    public static int findIndex(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            if (nums[i] > nums[i - 1]) {
                return i;
            }
        }
        return 0;
    }

    public static void exchange(int[] nums, int index) {
        int head = nums[index - 1];
        for (int i = nums.length - 1; i > 0; i--) {
            if (head < nums[i]) {
                nums[index - 1] = nums[i];
                nums[i] = head;
                break;
            }
        }
    }

    public static void reverse(int[] nums, int index) {
        for (int i = index, j = nums.length - 1; i < j; i++, j--) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
