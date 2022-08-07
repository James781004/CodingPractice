package 劍指offer.Sort;

public class Q21_reOrderArray {

    // 创建一个新数组，时间复杂度 O(N)，空间复杂度 O(N)。
    public int[] reOrderArray(int[] nums) {
        int oddCnt = 0;
        for (int x : nums) {
            if (x % 2 != 0) {
                oddCnt++;
            }
        }

        int[] copy = nums.clone();
        int i = 0, j = oddCnt;
        for (int num : copy) {
            if (num % 2 == 1) {
                nums[i++] = num;
            } else {
                nums[j++] = num;
            }
        }
        return nums;
    }


    // 使用冒泡思想，每次都将当前偶数上浮到当前最右边。
    // 时间复杂度 O(N2)，空间复杂度 O(1)，时间换空间。
    public int[] reOrderArrayBubble(int[] nums) {
        int N = nums.length;
        for (int i = N - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (nums[j] % 2 == 0 && nums[j + 1] % 2 != 0) {
                    swap(nums, j, j + 1);
                }
            }
        }

        return nums;
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }
}
