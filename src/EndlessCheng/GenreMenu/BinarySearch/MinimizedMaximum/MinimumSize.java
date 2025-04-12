package EndlessCheng.GenreMenu.BinarySearch.MinimizedMaximum;

import java.util.Arrays;

public class MinimumSize {

    // https://leetcode.cn/problems/minimum-limit-of-balls-in-a-bag/solutions/2026576/javac-er-fen-cha-zhao-by-tizzi-t04d/
    public int minimumSize(int[] nums, int op) {
        int l = 1, r = Arrays.stream(nums).max().getAsInt();

        // 給定花銷 mid,是否能夠在 maxOperations 次操作內使得盒子所有的數都小於等於 mid
        while (l < r) {
            int mid = (l + r) / 2;
            if (check(mid, nums, op)) r = mid;
            else l = mid + 1;
        }
        return r;
    }

    // 一個數 x 若小於等於 mid,那麼不用劃分
    // 若大於 mid,那麼需要進行劃分。
    // 當x位於[mid+1,2∗mid]需要花費一次，位於[2∗mid+1,3∗mid]需要花費2次。
    // 那麼可以看出每次的次數為(x−1)/mid
    boolean check(int mid, int[] nums, int op) {
        for (int x : nums) op -= (x - 1) / mid;
        return op >= 0;
    }


}
