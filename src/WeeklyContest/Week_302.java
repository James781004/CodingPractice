package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Week_302 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2341.Maximum%20Number%20of%20Pairs%20in%20Array/README.md
    public int[] numberOfPairs(int[] nums) {
        int[] cnt = new int[101];
        for (int x : nums) cnt[x]++;
        int s = 0;
        for (int v : cnt) s += v / 2;
        return new int[]{s, nums.length - s * 2};
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2342.Max%20Sum%20of%20a%20Pair%20With%20Equal%20Sum%20of%20Digits/README.md
    public int maximumSum(int[] nums) {
        List<Integer>[] d = new List[100];
        Arrays.setAll(d, k -> new ArrayList<>());
        for (int v : nums) {
            int y = 0;
            for (int x = v; x > 0; x /= 10) {
                y += x % 10;
            }
            d[y].add(v);
        }

        int ans = -1;
        for (List<Integer> vs : d) {
            int m = vs.size();
            if (m > 1) {
                Collections.sort(vs);
                ans = Math.max(ans, vs.get(m - 1) + vs.get(m - 2));
            }
        }
        return ans;
    }


    public int maximumSum2(int[] nums) {
        int ans = -1;
        int[] d = new int[100];
        for (int v : nums) {
            int y = 0;
            for (int x = v; x > 0; x /= 10) {
                y += x % 10;
            }
            if (d[y] > 0) {  // 直接在這邊更新答案，不使用額外空間
                ans = Math.max(ans, d[y] + v);
            }
            d[y] = Math.max(d[y], v);  // 已遍歷過的元素中數位和為該鍵的最大元素
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2343.Query%20Kth%20Smallest%20Trimmed%20Number/README.md
    public int[] smallestTrimmedNumbers(String[] nums, int[][] queries) {
        int n = nums.length;
        int m = queries.length;
        int[] ans = new int[m];
        String[][] t = new String[n][2];

        for (int i = 0; i < m; i++) {
            int k = queries[i][0], trim = queries[i][1];

            // 模擬裁剪過程
            for (int j = 0; j < n; j++) {
                t[j] = new String[]{nums[j].substring(nums[j].length() - trim), String.valueOf(j)};
            }

            // 對裁剪後的字符串進行排序
            Arrays.sort(t, (a, b) -> {
                int x = a[0].compareTo(b[0]);
                return x == 0 ? Long.compare(Integer.valueOf(a[1]), Integer.valueOf(b[1])) : x;
            });

            // 根據下標找到對應的數字
            ans[i] = Integer.valueOf(t[k - 1][1]);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2344.Minimum%20Deletions%20to%20Make%20Array%20Divisible/README.md
    // https://www.bilibili.com/video/BV1GV4y1J7kc/
    public int minOperations(int[] nums, int[] numsDivide) {
        int x = 0;
        for (int v : numsDivide) x = gcd(x, v);
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (x % nums[i] == 0) return i;
        }
        return -1;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
