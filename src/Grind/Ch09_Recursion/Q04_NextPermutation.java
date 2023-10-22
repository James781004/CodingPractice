package Grind.Ch09_Recursion;

public class Q04_NextPermutation {
    // https://leetcode.cn/problems/next-permutation/solutions/577321/miao-dong-xi-lie-100-cong-xia-yi-ge-pai-gog8j/
    // 盡可能的將低位的數字變大，這樣才符合「下一個排列」的定義
    // 結合樣例來分析，假設樣例為 [1,3,5,4,1]：
    // 1. 從後往前找，找到第一個下降的位置，記為 k。注意k 以後的位置是降序的。 在樣例中就是找到 3
    // 2. 從 k 往後找，找到最小的比 k 要大的數。 找到 4
    // 3. 將兩者交換。注意此時 k 以後的位置仍然是降序的。
    // 4. 直接將 k 以後的部分翻轉（變為升序）。
    public void nextPermutation(int[] nums) {
        int n = nums.length, k = n - 1;

        // 從後往前找，找到第一個下降的位置，記為 k。注意k 以後的位置是降序的。 在樣例中就是找到 3
        while (k - 1 >= 0 && nums[k - 1] >= nums[k]) k--;
        if (k == 0) {
            reverse(nums, 0, n - 1);
        } else {
            int u = k; // 從 k 往後找，找到最小的比 k 要大的數。
            while (u + 1 < n && nums[u + 1] > nums[k - 1]) u++;
            swap(nums, k - 1, u); // 將兩者交換。注意此時 k 以後的位置仍然是降序的
            reverse(nums, k, n - 1); // 直接將 k 以後的部分翻轉（變為升序）
        }
    }

    void reverse(int[] nums, int a, int b) {
        int l = a, r = b;
        while (l < r) swap(nums, l++, r--);
    }

    void swap(int[] nums, int a, int b) {
        int c = nums[a];
        nums[a] = nums[b];
        nums[b] = c;
    }
}
