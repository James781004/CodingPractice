package WeeklyContest;

import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;

public class Week_330 {
    // https://github.com/doocs/leetcode/blob/main/solution/2500-2599/2549.Count%20Distinct%20Numbers%20on%20Board/README.md
    // 由於每一次對桌面上的數字n進行操作，會使得數字n-1也出現在桌面上(因為 n % (n-1) == 1)，
    // 因此最終桌面上的數字為[2...n]，即n-1個數字。
    // 注意到n可能為1，因此需要特判。
    public int distinctIntegers(int n) {
        return Math.max(1, n - 1);
    }


    // https://leetcode.cn/problems/count-collisions-of-monkeys-on-a-polygon/solution/zheng-nan-ze-fan-by-endlesscheng-t7ag/
    // 每一只猴子都有兩種移動方式，即順時針或逆時針。因此，一共有 2^n 種移動方式。
    // 不碰撞的移動方式只有兩種，即所有猴子都順時針移動或所有猴子都逆時針移動。因此，碰撞的移動方式有 2^n-2 種。
    // 我們可以用快速冪求出 2^n 的值，然後用 2^n-2 求出碰撞的移動方式數，最後對 10e9+7 取余即可。
    public int monkeyMove(int n) {
        final int mod = (int) 1e9 + 7;
        return (int) (qmi(2, n, mod) - 2 + mod) % mod;
    }

    // 快速冪
    public long qmi(long a, long k, long p) {
        long res = 1;
        while (k != 0) {
            if ((k & 1) == 1) {
                res = res * a % p;
            }
            k >>= 1;
            a = a * a % p;
        }
        return res;
    }


    // https://leetcode.cn/problems/put-marbles-in-bags/solution/wen-ti-zhuan-hua-pai-xu-tan-xin-by-endle-bx8t/
    // 可以將問題轉化為：將數組 weights 分成 k 個連續的子數組，
    // 也就是說，我們要找到 k-1 個分割點，每個分割點的價格是分割點左右兩個元素的和，
    // 求最大的k-1 個分割點的價格之和與最小的 k-1 個分割點的價格之和的差值，即為答案。
    // 因此，我們可以處理數組 weights，將其轉化為一個長度為 n-1 的數組 arr，
    // 其中 arr[i] = weights[i] + weights[i+1]，然後對數組 arr 進行排序，
    // 最後求出最大的 k-1 個分割點的價格之和與最小的 k-1 個分割點的價格之和的差值即可。
    public long putMarbles(int[] wt, int k) {
        int n = wt.length;
        for (int i = 0; i < n - 1; i++) {
            wt[i] += wt[i + 1];
        }
        Arrays.sort(wt, 0, n - 1); // 相當於去掉最後一個數
        long ans = 0;
        for (int i = 0; i < k - 1; i++) {
            ans += wt[n - 2 - i] - wt[i];  // 因為只擷取0到n-2，最後一個數字為wt[n - 2 - i]
        }
        return ans;

    }


    // https://leetcode.cn/problems/put-marbles-in-bags/solution/xu-yao-hua-tu-si-kao-xia-de-by-spongebob-u45q/

    /**
     * 巧妙的題目 切k刀 其實可以畫圖看一下 我們只需要關注切的時候的左右兩側的值
     * [1],[3,5,1] 得到最小得分 (1+1) + (3+1) = 6 。
     * 分配方案 [1,3],[5,1] 得到最大得分 (1+3) + (5+1) = 10.
     * 我們發現你怎麼去切其實 頭和尾是不變的 只是中間的位置變化
     * 但是其實我們切的時候只需要考慮切點左右兩側的值就行
     */
    public static long putMarblesPQ(int[] weights, int k) {
        // 創建大根堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 1; i < weights.length; i++) {  // i位置切一刀，統計切點左右兩側的值
            maxHeap.offer(weights[i - 1] + weights[i]);
            minHeap.offer(weights[i - 1] + weights[i]);
        }
        // 只需要切k-1刀即可
        int realK = k - 1;
        long max = 0;
        long min = 0;
        while (realK-- > 0) {
            max += Objects.requireNonNull(maxHeap.poll()).longValue();
            min += Objects.requireNonNull(minHeap.poll()).longValue();
        }
        return max - min;
    }


    // https://leetcode.cn/problems/count-increasing-quadruplets/solution/you-ji-qiao-de-mei-ju-yu-chu-li-pythonja-exja/
    public long countQuadruplets(int[] nums) {
        int n = nums.length;

        // 題目要求：nums[i] < nums[k] < nums[j] < nums[l] 且 i < j < k < l
        // 所以需要先知道：k右邊有多少比 nums[j] 大的元素，以及在 j 左側有多少比 nums[k] 小的元素

        // 進行預處理，把 great[k][x] 定義為在 k 右邊比 x 大的元素個數
        // 在 k 右側的比 nums[j] 大的元素個數，記作 great[k][nums[j]]；
        // 倒序遍歷 nums，設 x<nums[k+1]，對於 x，大於它的數的個數加一，即 great[k][x] 加一
        int[][] great = new int[n][n + 1];
        for (int k = n - 2; k >= 2; k--) {
            great[k] = great[k + 1].clone();  // 比 k 大的數要累加，先把k+1累積結果複製

            // 對於比 nums[k + 1] 小的的數 x 來說，nums[k + 1] 的出現使得 x 的右邊多了一個比 x 大的數。
            // 所以更新的是比 nums[k + 1] 小的數，從 nums[k + 1] - 1 開始。
            for (int x = nums[k + 1] - 1; x > 0; x--)
                great[k][x]++; // x < nums[k+1]，對於 x，大於它的數的個數 +1
        }

        long ans = 0;

        // 進行預處理，把 less[j][x] 定義為在 j 左邊比 x 大的元素個數
        // 在 j 左側的比 nums[k] 小的元素個數，記作 less[j][nums[k]]。
        // 正序遍歷 nums，設 x>nums[j−1]，對於 x，小於它的數的個數加一，即 less[j][x] 加一
        // 這邊進行空間優化
        int[] less = new int[n + 1];
        for (int j = 1; j < n - 2; j++) {

            // 對於比 nums[j - 1] 大的的數 x 來說，nums[j - 1] 的出現使得 x 的左邊多了一個比 x 小的數。
            // 所以更新的是比 nums[j - 1] 小的數，從 nums[j - 1] + 1 開始。
            for (int x = nums[j - 1] + 1; x <= n; x++)
                less[x]++; // x > nums[j-1]，對於 x，小於它的數的個數 +1

            // 對於固定的 j 和 k，根據乘法原理，對答案的貢獻為 less[j][nums[k]] * great[k][nums[j]]
            for (int k = j + 1; k < n - 1; k++)
                if (nums[j] > nums[k])
                    ans += (long) less[nums[k]] * great[k][nums[j]];
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-increasing-quadruplets/solution/100-java-zi-ji-de-bi-ji-by-l00-96pw/
    // 比較直觀的解法
    public long countQuadruplets2(int[] nums) {
        long ans = 0l;
        int len = nums.length + 1;
        int[][] map = new int[len][len];

        // 枚舉ABCD元素，其中B > C且 C > A, B > D
        // 先固定索引C(right)，把B從前往後選擇
        for (int right = 1; right < nums.length; right++) {
            int sum = 0;
            for (int left = 0; left < right; left++) {
                if (nums[left] < nums[right]) sum++;  // 比C小的“A們”
                else map[left][right] = sum;  // 找到符合B條件的元素，統計答案
            }
        }

        // 先固定索引B(left)，把C從後往前選擇
        for (int left = nums.length - 2; left > 0; left--) {
            int sum = 0;
            for (int right = nums.length - 1; left < right; right--) {
                if (nums[left] < nums[right]) sum++;  // 比B小的“D們”
                else {
                    if (map[left][right] != 0) ans += (long) sum * map[left][right];  // 找到符合C條件的元素，統計答案
                }
            }
        }

        return ans;
    }

}
