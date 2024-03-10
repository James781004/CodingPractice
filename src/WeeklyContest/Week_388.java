package WeeklyContest;

import java.util.Arrays;

public class Week_388 {
    // https://leetcode.cn/problems/apple-redistribution-into-boxes/solutions/2678088/shuang-zhi-zhen-by-6666666669999999-v9d5/
    public int minimumBoxes(int[] apple, int[] capacity) {
        int appNum = 0;
        for (Integer a : apple) {
            appNum += a;
        }
        Arrays.sort(capacity);
        int box = 0, res = 0;
        for (int i = capacity.length - 1; i >= 0; i--) {
            box += capacity[i];
            res++;
            if (box >= appNum) return res;
        }
        return res;
    }


    // https://leetcode.cn/problems/maximize-happiness-of-selected-children/solutions/2678167/pai-xu-tan-xin-by-practical-rubinfni-6cen/
    public long maximumHappinessSum(int[] happiness, int k) {
        Arrays.sort(happiness);
        int len = happiness.length;
        long res = 0, round = 0;
        for (int i = len - 1; i >= 0 && round < k; i--, round++) {
            happiness[i] -= round;
            res += Math.max(happiness[i], 0);
        }
        return res;
    }


    // https://leetcode.cn/problems/shortest-uncommon-substring-in-an-array/solutions/2678694/bao-li-jian-ji-xie-fa-pythonjavacgo-by-e-tjlm/
    public String[] shortestSubstrings(String[] arr) {
        int n = arr.length;
        String[] ans = new String[n];
        for (int i = 0; i < n; i++) {
            int m = arr[i].length();
            String res = "";
            for (int size = 1; size <= m && res.isEmpty(); size++) {
                for (int j = size; j <= m; j++) {
                    String t = arr[i].substring(j - size, j);
                    if ((res.isEmpty() || t.compareTo(res) < 0) && check(arr, i, t)) {
                        res = t;
                    }
                }
            }
            ans[i] = res;
        }
        return ans;
    }

    private boolean check(String[] arr, int i, String sub) {
        for (int j = 0; j < arr.length; j++) {
            if (j != i && arr[j].contains(sub)) {
                return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/maximum-strength-of-k-disjoint-subarrays/solutions/2678061/qian-zhui-he-hua-fen-xing-dpshi-zi-bian-ap5z5/
    public long maximumStrength(int[] nums, int k) {
        int n = nums.length;
        long[] s = new long[n + 1];
        for (int i = 0; i < n; i++) {
            s[i + 1] = s[i] + nums[i];
        }
        long[][] f = new long[k + 1][n + 1];
        for (int i = 1; i <= k; i++) {
            f[i][i - 1] = Long.MIN_VALUE;
            long mx = Long.MIN_VALUE;
            int w = (k - i + 1) * (i % 2 > 0 ? 1 : -1);
            for (int j = i; j <= n - k + i; j++) { // j 的上下界是因為其它子數組至少要選一個數
                mx = Math.max(mx, f[i - 1][j - 1] - s[j - 1] * w);
                f[i][j] = Math.max(f[i][j - 1], s[j] * w + mx);
            }
        }
        return f[k][n];
    }
}


