package Grind.Ch01_Array;

public class Q09_SortColors {
    // https://leetcode.com/problems/sort-colors/
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) return;
        int index = 0, left = 0, right = nums.length - 1;
        while (index <= right) {
            if (nums[index] == 0) {
                swap(nums, index++, left++);
            } else if (nums[index] == 1) {
                index++;
            } else {
                swap(nums, index, right--);
            }
        }
    }


    // 類題：荷蘭國旗問題
    public void arrPartition(int[] arr, int pivot) {
        int small = -1; // 小於區域的尾
        int big = arr.length;  // 大於區域的頭
        int index = 0;
        while (index != big) {
            if (arr[index] < pivot) {
                swap(arr, ++small, index++); // 把當前數追加在小於pivot區域的後面，當前位置處理完成，所以指針向後走
            } else if (arr[index] == pivot) {
                index++;  // 當前數與pivot相等，指針向後走
            } else {
                swap(arr, --big, index); // 把當前數插入到大於pivot區域的前面，但當前位置還沒確定是正解，所以指針不動
            }
        }
    }

    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
}
