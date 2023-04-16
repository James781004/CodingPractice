package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_316 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2446.Determine%20if%20Two%20Events%20Have%20Conflict/README.md
    public boolean haveConflict(String[] event1, String[] event2) {
        return event1[0].compareTo(event2[1]) <= 0 && event1[1].compareTo(event2[0]) >= 0;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2447.Number%20of%20Subarrays%20With%20GCD%20Equal%20to%20K/README.md
    public int subArrayGCD(int[] nums, int k) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = i; j < n; j++) {
                x = gcd(x, nums[j]);
                if (x == k) {
                    ++ans;
                }
            }
        }
        return ans;
    }


    public int subArrayGCD2(int[] nums, int k) {
        int res = 0;
        int len = nums.length;
        List<int[]> records = new ArrayList<>(); //[gcd, 相同gcd區間的右端點]
        int index0 = -1; //gcd 不為 k 的下標索引
        for (int i = 0; i < len; i++) {
            int num = nums[i];
            if (num % k != 0) {
                index0 = i;
                records = new ArrayList<>();
            } else {
                records.add(new int[]{num, i});
                int j = 0; //原地去重
                for (int[] record : records) {
                    if (record[0] > num) record[0] = gcd(record[0], num);
                    else record[0] = gcd(num, record[0]);
                    if (records.get(j)[0] != record[0]) {

                        records.set(++j, record);
                    } else {
                        // 相同gcd 更新右端點
                        records.set(j, record);
                    }
                }

                records.subList(j + 1, records.size()).clear();
                int[] first = records.get(0);
                if (first[0] == k) res += first[1] - index0;
            }
        }
        return res;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2448.Minimum%20Cost%20to%20Make%20Array%20Equal/README.md
    public long minCost(int[] nums, int[] cost) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; ++i) {
            arr[i] = new int[]{nums[i], cost[i]};
        }

        Arrays.sort(arr, (a, b) -> a[0] - b[0]);
        long[] f = new long[n + 1];
        long[] g = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            long a = arr[i - 1][0], b = arr[i - 1][1];
            f[i] = f[i - 1] + a * b;
            g[i] = g[i - 1] + b;
        }

        long ans = Long.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            long a = arr[i - 1][0];
            long l = a * g[i - 1] - f[i - 1];
            long r = f[n] - f[i] - a * (g[n] - g[i]);
            ans = Math.min(ans, l + r);
        }
        return ans;
    }


    public long minCost2(int[] nums, int[] cost) {
        int n = nums.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; ++i) {
            arr[i] = new int[]{nums[i], cost[i]};
        }
        Arrays.sort(arr, (a, b) -> a[0] - b[0]);
        long mid = sum(cost) / 2;
        long s = 0, ans = 0;
        for (int[] e : arr) {
            int x = e[0], c = e[1];
            s += c;
            if (s > mid) {  // 找到中位數x，開始計算答案
                for (int[] t : arr) ans += (long) Math.abs(t[0] - x) * t[1];
                break;
            }
        }
        return ans;
    }

    private long sum(int[] arr) {
        long s = 0;
        for (int v : arr) {
            s += v;
        }
        return s;
    }


    // https://leetcode.cn/problems/minimum-number-of-operations-to-make-arrays-similar/solution/by-endlesscheng-lusx/
    // https://www.bilibili.com/video/BV1ne4y1e7nu/
    // 如果把問題中的 +2 和 −2 改成 +1 和 −1，要怎麼做？
    //
    // 例如 nums=[2,8]，target=[4,6]，那麼應該讓 2 和 4 一對，8 和 6 一對。
    // 如果讓 2 和 6 一對，8 和 4 一對，是會讓變化量的和變得更大的。
    //
    // 通過這種鄰項交換法，我們可以證明，讓最小的一對，次小的一對，第三小的一對，……，累加每對元素的差的絕對值，
    // 就得到了每個數的變化量的和的最小值。
    // 回到原問題，+2 和 −2 會導致無法直接排序然後一一匹配，
    // 但注意到 +2 和 −2 並不會改變元素的奇偶性，因此我們可以把偶數分為一組，奇數分為一組，
    // 每組分別計算，這樣就像提示 1 那樣一一匹配了。
    // 最後把變化量的和除以 4，即為答案。
    public long makeSimilar(int[] nums, int[] target) {
        f(nums);
        f(target);
        long ans = 0L;
        for (int i = 0; i < nums.length; i++) {
            ans += Math.abs(nums[i] - target[i]);
        }
        return ans / 4;  // +2 -2 每次操作變化量都是4，所以最後除4
    }

    private void f(int[] a) {
        // 由於元素都是正數，把奇數變成相反數，這樣排序後奇偶就自動分開了
        for (int i = 0; i < a.length; i++) {
            if (a[i] % 2 != 0) a[i] = -a[i];
        }
        Arrays.sort(a);
    }
}
