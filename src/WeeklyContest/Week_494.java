package WeeklyContest;

import java.util.*;

public class Week_494 {

    // https://leetcode.cn/problems/construct-uniform-parity-array-ii/solutions/3933374/fen-lei-tao-lun-pythonjavacgo-by-endless-y80y/
    public boolean uniformArray(int[] nums1) {
        int minOdd = Integer.MAX_VALUE;
        for (int x : nums1) {
            if (x % 2 != 0) {
                minOdd = Math.min(minOdd, x);
            }
        }

        // 沒有奇數，都是偶數
        if (minOdd == Integer.MAX_VALUE) {
            return true;
        }

        for (int x : nums1) {
            // 把偶數減去奇數，變成奇數，前提是偶數 > 奇數
            if (x % 2 == 0 && x < minOdd) {
                return false;
            }
        }

        return true;
    }


    // https://leetcode.cn/problems/minimum-removals-to-achieve-target-xor/solutions/3933376/mo-ban-qia-hao-zhuang-man-xing-0-1-bei-b-llbw/
    public int minRemovals(int[] nums, int target) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int m = 32 - Integer.numberOfLeadingZeros(mx);
        if (m < 32 - Integer.numberOfLeadingZeros(target)) {
            return -1;
        }

        int n = nums.length;
        int[][] f = new int[n + 1][1 << m];
        for (int[] row : f) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        f[0][0] = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = 0; j < (1 << m); j++) {
                f[i + 1][j] = Math.max(f[i][j], f[i][j ^ x] + 1); // x 不選 or 選
            }
        }

        if (f[n][target] < 0) {
            return -1;
        }
        return nums.length - f[n][target];
    }


    // https://leetcode.cn/problems/count-good-subarrays/solutions/3933380/mo-ban-logtrick-ji-lu-mei-ge-yuan-su-de-otgcv/
    public long countGoodSubarrays(int[] nums) {
        List<int[]> orLeft = new ArrayList<>(); // (子數組或值，最小左端點)
        Map<Integer, Integer> last = new HashMap<>();
        long ans = 0;

        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            last.put(x, i);

            // 計算以 i 為右端點的子數組或值
            for (int[] p : orLeft) {
                p[0] |= x; // **根據題目修改**
            }
            // x 單獨一個數作為子數組
            orLeft.add(new int[]{x, i});

            // 原地去重（相同或值只保留最左邊的）
            // 原理見力扣 26. 刪除有序數組中的重復項
            int m = 1;
            for (int j = 1; j < orLeft.size(); j++) {
                if (orLeft.get(j)[0] != orLeft.get(j - 1)[0]) {
                    orLeft.set(m++, orLeft.get(j));
                }
            }
            orLeft.subList(m, orLeft.size()).clear();

            for (int k = 0; k < m; k++) {
                int orVal = orLeft.get(k)[0];
                int left = orLeft.get(k)[1];
                int right = k < m - 1 ? orLeft.get(k + 1)[1] - 1 : i;
                // 對於左端點在 [left, right]，右端點為 i 的子數組，OR 值都是 orVal
                int j = last.getOrDefault(orVal, -1);
                if (j >= left) {
                    ans += Math.min(right, j) - left + 1;
                }
            }
        }

        return ans;
    }


}









