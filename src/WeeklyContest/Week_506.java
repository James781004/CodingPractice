package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

public class Week_506 {

    // https://leetcode.cn/problems/check-good-integer/solutions/3983417/jian-zhi-you-hua-by-liang-mian-bao-jia-z-fk4k/
    public boolean checkGoodInteger(int n) {
        int digitsum = 0, squaresum = 0;
        while (n > 0) {
            int x = n % 10;
            digitsum += x;
            squaresum += x * x;
            if (squaresum - digitsum >= 50) return true; // 提前剪枝
            n /= 10;
        }
        return false;
    }

    // https://leetcode.cn/problems/frequency-balance-subarray/solutions/3983433/yue-du-li-jie-pythonjavacgo-by-endlessch-msys/
    public int getLength(int[] nums) {
        int n = nums.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            HashMap<Integer, Integer> cnt = new HashMap<>();
            HashMap<Integer, Integer> cc = new HashMap<>();

            for (int j = i; j < n; j++) {
                int x = nums[j];
                Integer c = cnt.get(x);
                if (c != null) {
                    if (cc.merge(c, -1, Integer::sum) == 0) { // --cc[c] == 0
                        cc.remove(c); // 保證我們可以正確計算 cc 的大小
                    }
                }
                c = cnt.merge(x, 1, Integer::sum); // c = ++cnt[x]
                cc.merge(c, 1, Integer::sum); // ++cc[c]

                if (cnt.size() == 1) { // 子數組只有一種元素
                    ans = Math.max(ans, j - i + 1);
                } else if (cc.size() == 2) { // 子數組的元素出現次數恰好有兩種
                    Iterator<Integer> it = cc.keySet().iterator();
                    int c1 = it.next();
                    int c2 = it.next();
                    if (Math.min(c1, c2) * 2 == Math.max(c1, c2)) {
                        ans = Math.max(ans, j - i + 1);
                    }
                }
            }
        }

        return ans;
    }


    // https://leetcode.cn/problems/maximize-sum-of-device-ratings/solutions/3983487/ba-dan-yuan-ji-zhong-dao-yi-ge-she-bei-z-afnf/
    public long maxRatings(int[][] units) {
        long ans = 0;
        if (units[0].length == 1) {
            // 每個設備都只有一個單元
            for (int[] unit : units) {
                ans += unit[0];
            }
            return ans;
        }

        int mn = Integer.MAX_VALUE;
        int mn2 = Integer.MAX_VALUE;
        for (int[] unit : units) {
            // 計算最小次小
            int unitMin = Integer.MAX_VALUE;
            int unitMin2 = Integer.MAX_VALUE;
            for (int x : unit) {
                if (x < unitMin) {
                    unitMin2 = unitMin;
                    unitMin = x;
                } else if (x < unitMin2) {
                    unitMin2 = x;
                }
            }

            ans += unitMin2; // 先加上次小
            mn2 = Math.min(mn2, unitMin2);
            mn = Math.min(mn, unitMin);
        }

        // 把包含 mn2 的那個設備作為集中站，存放每個設備的最小值
        ans += mn - mn2; // 把 ans 中的 mn2 替換成 mn
        return ans;
    }


    // https://leetcode.cn/problems/maximum-subarray-sum-after-at-most-k-swaps/solutions/3983444/zhi-yu-shu-zhuang-shu-zu-by-endlesscheng-txva/
    public long maxSum(int[] nums, int k) {
        // 離散化
        int n = nums.length;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int widthN = 32 - Integer.numberOfLeadingZeros(n);
        int[] rank = new int[n]; // rank[i] 是 nums[i] 離散化後的值（從 1 開始）
        Fenwick allTree = new Fenwick(n + 1, sorted, widthN); // 包含所有元素的樹狀數組
        long total = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            rank[i] = Arrays.binarySearch(sorted, x) + 1;
            allTree.update(rank[i], 1, x);
            total += x;
        }

        long ans = Long.MIN_VALUE;

        // 枚舉子數組左端點
        for (int left = 0; left < n; left++) {
            Fenwick inTree = new Fenwick(n + 1, sorted, widthN);
            Fenwick outTree = allTree.copy();
            int needSwap = 0;
            long subSum = 0;

            // 枚舉子數組右端點
            for (int right = left; right < n; right++) {
                // x 從子數組外移到子數組內
                int x = nums[right];
                int rk = rank[right];
                subSum += x;
                inTree.update(rk, 1, x);
                outTree.update(rk, -1, -x);

                boolean inc = false;
                int sz = right - left + 1;
                if (needSwap < k && needSwap < sz && needSwap < n - sz) {
                    // 能否多交換一次
                    if (inTree.kth(needSwap + 1) < outTree.kth(n - sz - needSwap)) {
                        inc = true;
                        needSwap++;
                    }
                }

                if (!inc && needSwap > 0) {
                    // 是否要減少交換次數
                    if (inTree.kth(needSwap) >= outTree.kth(n - sz - needSwap + 1)) {
                        needSwap--;
                    }
                }

                // 計算通過交換導致的元素和的增量
                long delta = 0;
                if (needSwap > 0) {
                    long inSum = inTree.preSum(needSwap);
                    long outSum = total - subSum - outTree.preSum(n - sz - needSwap);
                    delta = outSum - inSum;
                }

                ans = Math.max(ans, subSum + delta);
            }
        }

        return ans;
    }


    static class Fenwick {
        private final int width;
        private final int[] sorted;
        private final int[] cnt;
        private final long[] sum;

        public Fenwick(int n, int[] sorted, int width) {
            this.width = width;
            this.sorted = sorted;
            cnt = new int[n];
            sum = new long[n];
        }

        // 添加 num 個 val，其中 val 離散化後的值為 i
        // 如果 num < 0，表示減少 -num 個 val
        public void update(int i, int num, int val) {
            for (; i < cnt.length; i += i & -i) {
                cnt[i] += num;
                sum[i] += val;
            }
        }

        // 返回第 k 小的數（k 從 1 開始）
        public int kth(int k) {
            int i = 0;
            for (int b = 1 << (width - 1); b > 0; b >>= 1) {
                int nxt = i | b;
                if (nxt < cnt.length && cnt[nxt] < k) {
                    k -= cnt[nxt];
                    i = nxt;
                }
            }
            return sorted[i];
        }

        // 返回前 k 小的數之和（k 從 1 開始）
        public long preSum(int k) {
            long res = 0;
            int i = 0;
            for (int b = 1 << (width - 1); b > 0; b >>= 1) {
                int nxt = i | b;
                if (nxt < cnt.length && cnt[nxt] < k) {
                    k -= cnt[nxt];
                    res += sum[nxt];
                    i = nxt;
                }
            }
            // 加上等於第 k 小的數
            return res + (long) sorted[i] * k;
        }

        public Fenwick copy() {
            Fenwick f = new Fenwick(cnt.length, sorted, width);
            System.arraycopy(this.cnt, 0, f.cnt, 0, cnt.length);
            System.arraycopy(this.sum, 0, f.sum, 0, sum.length);
            return f;
        }
    }


}










