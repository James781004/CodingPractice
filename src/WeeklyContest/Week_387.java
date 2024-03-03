package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Week_387 {
    // https://leetcode.cn/problems/distribute-elements-into-two-arrays-i/solutions/2664659/mo-ni-pythonjavacgo-by-endlesscheng-zov9/
    public int[] resultArray(int[] nums) {
        int n = nums.length;
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        a.add(nums[0]);
        b.add(nums[1]);
        for (int i = 2; i < n; i++) {
            if (a.get(a.size() - 1) > b.get(b.size() - 1)) {
                a.add(nums[i]);
            } else {
                b.add(nums[i]);
            }
        }
        a.addAll(b);
        for (int i = 0; i < n; i++) {
            nums[i] = a.get(i);
        }
        return nums;
    }


    // https://leetcode.cn/problems/count-submatrices-with-top-left-element-and-sum-less-than-k/solutions/2664654/mo-ban-er-wei-qian-zhui-he-pythonjavacgo-fnk1/
    public int countSubmatrices(int[][] grid, int k) {
        int ans = 0;
        int m = grid.length;
        int n = grid[0].length;
        int[][] sum = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum[i + 1][j + 1] = sum[i + 1][j] + sum[i][j + 1] - sum[i][j] + grid[i][j];
                if (sum[i + 1][j + 1] <= k) {
                    ans++;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-operations-to-write-the-letter-y-on-a-grid/solutions/2664650/yi-ci-bian-li-pythonjavacgo-by-endlessch-at9u/
    public int minimumOperationsToWriteY(int[][] grid) {
        int[] cnt1 = new int[3];
        int[] cnt2 = new int[3];
        int n = grid.length;
        int m = n / 2;
        for (int i = 0; i < m; i++) {
            cnt1[grid[i][i]]++;
            cnt1[grid[i][n - 1 - i]]++;
            for (int j = 0; j < n; j++) {
                if (j != i && j != n - 1 - i) {
                    cnt2[grid[i][j]]++;
                }
            }
        }
        for (int i = m; i < n; i++) {
            cnt1[grid[i][m]]++;
            for (int j = 0; j < n; j++) {
                if (j != m) {
                    cnt2[grid[i][j]]++;
                }
            }
        }

        int maxNotChange = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != j) {
                    maxNotChange = Math.max(maxNotChange, cnt1[i] + cnt2[j]);
                }
            }
        }
        return n * n - maxNotChange;
    }


    // https://leetcode.cn/problems/distribute-elements-into-two-arrays-ii/solutions/2664646/chi-san-hua-shu-zhuang-shu-zu-pythonjava-3bb2/
    public int[] resultArrayII(int[] nums) {
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums); // 只排序不去重
        int n = nums.length;

        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        a.add(nums[0]);
        b.add(nums[1]);

        Fenwick t1 = new Fenwick(n + 1);
        Fenwick t2 = new Fenwick(n + 1);
        t1.add(Arrays.binarySearch(sortedNums, nums[0]) + 1);
        t2.add(Arrays.binarySearch(sortedNums, nums[1]) + 1);

        for (int i = 2; i < nums.length; i++) {
            int x = nums[i];
            int v = Arrays.binarySearch(sortedNums, x) + 1;
            int gc1 = a.size() - t1.pre(v); // greaterCount(a, v)
            int gc2 = b.size() - t2.pre(v); // greaterCount(b, v)
            if (gc1 > gc2 || gc1 == gc2 && a.size() <= b.size()) {
                a.add(x);
                t1.add(v);
            } else {
                b.add(x);
                t2.add(v);
            }
        }
        a.addAll(b);
        for (int i = 0; i < n; i++) {
            nums[i] = a.get(i);
        }
        return nums;
    }


    static class Fenwick {
        private final int[] tree;

        public Fenwick(int n) {
            tree = new int[n];
        }

        // 把下標為 i 的元素增加 1
        public void add(int i) {
            while (i < tree.length) {
                tree[i] += 1;
                i += i & -i;
            }
        }

        // 返回下標在 [1,i] 的元素之和
        public int pre(int i) {
            int res = 0;
            while (i > 0) {
                res += tree[i];
                i &= i - 1;
            }
            return res;
        }
    }
}


