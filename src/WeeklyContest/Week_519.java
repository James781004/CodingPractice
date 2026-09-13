package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_519 {

    // https://leetcode.cn/problems/cyclically-shift-rows-and-columns/solutions/4027861/mo-ni-pythonjavacgo-by-endlesscheng-kd65/
    public int[][] cyclicShift(int n, int[][] grid, int[] rowShift, int[] colShift) {
        for (int i = 0; i < grid.length; i++) {
            int shift = rowShift[i];
            int[] row = grid[i];
            int[] newRow = new int[n];
            System.arraycopy(row, shift, newRow, 0, n - shift);
            System.arraycopy(row, 0, newRow, n - shift, shift);
            grid[i] = newRow;
        }

        int[] col = new int[n];
        for (int j = 0; j < colShift.length; j++) {
            int shift = colShift[j];
            // 收集列元素
            int k = 0;
            for (int i = shift; i < n; i++) {
                col[k++] = grid[i][j];
            }
            for (int i = 0; i < shift; i++) {
                col[k++] = grid[i][j];
            }
            // 填入列
            for (int i = 0; i < n; i++) {
                grid[i][j] = col[i];
            }
        }

        return grid;
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-every-element-palindromic/solutions/4027856/yu-chu-li-hui-wen-shu-er-fen-cha-zhao-py-45qi/
    class Solution {
        private static final int MX = 2_000_000_002;
        private static final List<Integer>[] palindromes = new ArrayList[2];
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public Solution() {
            if (initialized) {
                return;
            }
            initialized = true;

            Arrays.setAll(palindromes, v -> new ArrayList<>());
            palindromes[0].add(0);
            palindromes[1].add(0); // 哨兵

            // 預處理 [1, MX] 中的回文數
            for (int base = 1; ; base *= 10) {
                // 生成奇數長度回文數，例如 base = 10，生成的范圍是 101 ~ 999
                for (int i = base; i < base * 10; i++) {
                    int x = i;
                    for (int t = i / 10; t > 0; t /= 10) {
                        x = x * 10 + t % 10;
                    }
                    if (x > MX) {
                        return;
                    }
                    palindromes[x % 2].add(x);
                }

                // 生成偶數長度回文數，例如 base = 10，生成的范圍是 1001 ~ 9999
                for (int i = base; i < base * 10; i++) {
                    int x = i;
                    for (int t = i; t > 0; t /= 10) {
                        x = x * 10 + t % 10;
                    }
                    if (x > MX) {
                        return;
                    }
                    palindromes[x % 2].add(x);
                }
            }
        }

        public long minOperations(int[] nums) {
            long ans = 0;
            for (int x : nums) {
                List<Integer> p = palindromes[x % 2];
                int i = lowerBound(p, x);
                ans += Math.min(p.get(i) - x, x - p.get(i - 1));
            }
            return ans / 2;
        }

        // 開區間寫法
        private int lowerBound(List<Integer> nums, int target) {
            int left = -1;
            int right = nums.size(); // 開區間 (left, right)
            while (left + 1 < right) { // 區間不為空
                // 循環不變量：
                // nums[right] >= target
                // nums[left] < target
                int mid = (left + right) >>> 1;
                if (nums.get(mid) >= target) {
                    right = mid; // 范圍縮小到 (left, mid)
                } else {
                    left = mid; // 范圍縮小到 (mid, right)
                }
            }
            return right;
        }
    }


    // https://leetcode.cn/problems/count-shadow-pairs-i/solutions/4027853/mei-ju-you-wei-hu-zuo-dan-diao-zhan-pyth-hmvp/
    public long shadowPairs(int[] nums) {
        ArrayList<int[]> st = new ArrayList<>();
        st.add(new int[]{0, 0}); // 棧底哨兵
        int size = 0; // 棧的大小（cnt 之和）
        long ans = 0;
        for (int x : nums) {
            while (st.getLast()[0] > x) {
                size -= st.removeLast()[1]; // 棧頂永遠無法構成影子對
            }

            ans += size;
            if (st.getLast()[0] == x) {
                // 恰好等於 x 的 nums[i] 不能構成影子對，要減掉
                ans -= st.getLast()[1];
                st.getLast()[1]++;
            } else {
                st.add(new int[]{x, 1});
            }
            size++;
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-shadow-pairs-ii/solutions/4027908/zhi-yu-fen-zhi-dan-diao-zhan-shu-zhuang-c4sct/
    public int shadowPairsII(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        List<Integer> a = new ArrayList<>(n);
        for (int x : nums) {
            a.add(Arrays.binarySearch(sorted, x));
        }

        return solve(a, 0, n - 1);
    }

    private int solve(List<Integer> a, int low, int high) {
        int n = a.size();
        if (n <= 1 || low == high) {
            return 0;
        }

        List<Integer> lowSt = new ArrayList<>();
        List<Integer> highSt = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        List<Integer> c = new ArrayList<>();
        int mid = (low + high) / 2;
        int res = 0;

        for (int i = 0; i < n; i++) {
            int x = a.get(i);
            if (x <= mid) { // x 在下部，作為 nums[i]
                while (!lowSt.isEmpty() && a.get(lowSt.getLast()) < x) {
                    lowSt.removeLast(); // 因為 x 的出現，棧頂不能作為 nums[i]
                }
                lowSt.add(i);
                b.add(x);
            } else { // x 在上部，作為 nums[j]
                // 找到 x 左側第一個小於 x 的最近元素，作為 nums[k]
                while (!highSt.isEmpty() && a.get(highSt.getLast()) >= x) {
                    highSt.removeLast();
                }
                res += lowSt.size();
                if (!highSt.isEmpty()) {
                    // lowSt 中 < highSt.getLast() 的下標不能作為 nums[i]
                    int p = Collections.binarySearch(lowSt, highSt.getLast());
                    if (p < 0) {
                        p = ~p; // 見 Collections.binarySearch 源碼
                    }
                    res -= p;
                }
                highSt.add(i);
                c.add(x);
            }
        }

        return res + solve(b, low, mid) + solve(c, mid + 1, high);
    }


}










