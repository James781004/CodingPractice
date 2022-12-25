package GuChengAlgorithm.Ch02_BasicAlgorithm.Sorts;

public class Q04_CountingSort {
    public void countingSort(int[] nums) {
        int count0 = 0, count1 = 0, count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) count0++;
            if (nums[i] == 1) count1++;
            if (nums[i] == 2) count2++;
        }
        for (int i = 0; i < nums.length; i++) {
            if (i < count0) nums[i] = 0;
            else if (i < count0 + count1) nums[i] = i;
            else nums[i] = 2;
        }
    }


    public void sortColors(int[] nums) {
        int left = 0, right = nums.length - 1;
        for (int i = 0; i <= right; i++) {
            if (nums[i] == 0 && i > left) swap(nums, i--, left++);
            else if (nums[i] == 2 && i < left) swap(nums, i--, right--);
        }
    }


    // 荷蘭國旗問題
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
