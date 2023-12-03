package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Week_372 {
    // https://leetcode.cn/problems/make-three-strings-equal/solutions/2533069/ji-suan-san-ge-zi-fu-chuan-de-zui-chang-ej505/
    // 設 lcp 為三個字符串的最長公共前綴的長度。
    // 如果 lcp=0，無法操作成一樣的，返回 1。
    // 否則返回三個字符串的長度之和，減去剩下的長度 3 * lcp。
    public int findMinimumOperations(String s1, String s2, String s3) {
        int n = Math.min(Math.min(s1.length(), s2.length()), s3.length());
        int i = 0;
        while (i < n && s2.charAt(i) == s1.charAt(i) && s3.charAt(i) == s1.charAt(i)) {
            i++;
        }
        return i == 0 ? -1 : s1.length() + s2.length() + s3.length() - i * 3;
    }


    // https://leetcode.cn/problems/separate-black-and-white-balls/solutions/2532930/lei-jia-mei-ge-0-zuo-bian-de-1-de-ge-shu-luuh/
    // 對於每個 0，它左邊有多少個 1，就移動多少次。
    // 所以一邊遍歷 s，一邊統計 1 的個數 cnt1 ，
    // 遇到 0 就把 cnt1 加入答案。
    public long minimumSteps(String s) {
        long ans = 0;
        int cnt1 = 0;
        for (char c : s.toCharArray()) {
            if (c == '0') {
                ans += cnt1;
            } else {
                cnt1++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-xor-product/solutions/2532915/o1-zuo-fa-wei-yun-suan-de-qiao-miao-yun-lvnvr/
    public int maximumXorProduct(long a, long b, int n) {
        if (a < b) {
            // 保證 a >= b
            long temp = a;
            a = b;
            b = temp;
        }

        long mask = (1L << n) - 1;
        long ax = a & ~mask; // 第 n 位及其左邊，無法被 x 影響，先算出來
        long bx = b & ~mask;
        a &= mask; // 低於第 n 位，能被 x 影響
        b &= mask;

        long left = a ^ b; // 可分配：a XOR x 和 b XOR x 一個是 1 另一個是 0
        long one = mask ^ left; // 無需分配：a XOR x 和 b XOR x 均為 1
        ax |= one; // 先加到異或結果中
        bx |= one;

        // 現在要把 left 分配到 ax 和 bx 中
        // 根據基本不等式（均值定理），分配後應當使 ax 和 bx 盡量接近，乘積才能盡量大
        if (left > 0 && ax == bx) {
            // 盡量均勻分配，例如把 1111 分成 1000 和 0111
            long highBit = 1L << (63 - Long.numberOfLeadingZeros(left));
            ax |= highBit;
            left ^= highBit;
        }
        // 如果 a & ~mask 更大，則應當全部分給 bx（注意最上面保證了 a>=b）
        bx |= left;

        final long MOD = 1_000_000_007;
        return (int) (ax % MOD * (bx % MOD) % MOD);
    }


    // https://leetcode.cn/problems/find-building-where-alice-and-bob-can-meet/solutions/2533058/chi-xian-zui-xiao-dui-pythonjavacgo-by-e-9ewj/
    public int[] leftmostBuildingQueries(int[] heights, int[][] queries) {
        int[] ans = new int[queries.length];
        Arrays.fill(ans, -1);
        List<int[]>[] left = new ArrayList[heights.length];
        Arrays.setAll(left, e -> new ArrayList<>());
        for (int qi = 0; qi < queries.length; qi++) {
            int i = queries[qi][0], j = queries[qi][1];
            if (i > j) {
                int temp = i;
                i = j;
                j = temp; // 保證 i <= j
            }
            if (i == j || heights[i] < heights[j]) {
                ans[qi] = j; // i 直接跳到 j
            } else {
                left[j].add(new int[]{heights[i], qi}); // 離線
            }
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        for (int i = 0; i < heights.length; i++) { // 從小到大枚舉下標 i
            int h = heights[i];
            while (!pq.isEmpty() && pq.peek()[0] < h) {
                ans[pq.poll()[1]] = i; // 可以跳到 i（此時 i 是最小的）
            }
            for (int[] p : left[i]) {
                pq.offer(p); // 後面再回答
            }
        }
        return ans;
    }
}
