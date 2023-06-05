package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Week_348 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2716.Minimize%20String%20Length/README.md
    public int minimizedStringLength(String s) {
        Set<Character> ss = new HashSet<>();
        for (int i = 0; i < s.length(); ++i) {
            ss.add(s.charAt(i));
        }
        return ss.size();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2717.Semi-Ordered%20Permutation/README.md
    public int semiOrderedPermutation(int[] nums) {
        int n = nums.length;
        int i = 0, j = 0;
        for (int k = 0; k < n; ++k) {
            if (nums[k] == 1) {
                i = k;
            }
            if (nums[k] == n) {
                j = k;
            }
        }
        int k = i < j ? 1 : 2;
        return i + n - j - k;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2718.Sum%20of%20Matrix%20After%20Queries/README.md
    public long matrixSumQueries(int n, int[][] queries) {
        Set<Integer> row = new HashSet<>();
        Set<Integer> col = new HashSet<>();
        int m = queries.length;
        long ans = 0;

        // 後面操作會覆蓋前面操作，乾脆從後往前操作
        // 倒序遍歷所有的查詢，之前已經填過數字的部份不計算
        for (int k = m - 1; k >= 0; k--) {
            int[] q = queries[k];
            int t = q[0], i = q[1], v = q[2];
            if (t == 0) {
                // 計算col時，記錄進row
                if (row.add(i)) {  // 之前已經填過數字的部份不計算，所以乘上n - col.size()
                    ans += (long) (n - col.size()) * v;
                }
            } else {
                // 計算row時，記錄進col
                if (col.add(i)) {  // 之前已經填過數字的部份不計算，所以乘上n - row.size()
                    ans += (long) (n - row.size()) * v;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-of-integers/solution/shu-wei-dp-tong-yong-mo-ban-pythonjavacg-9tuc/
    // https://www.bilibili.com/video/BV1do4y1K7Wq/?spm_id_from=333.999.0.0
    private static final int MOD = (int) 1e9 + 7;
    private int minSum, maxSum;

    public int count(String num1, String num2, int minSum, int maxSum) {
        this.minSum = minSum;
        this.maxSum = maxSum;
        int ans = count(num2) - count(num1) + MOD; // 避免負數
        int sum = 0;
        for (char c : num1.toCharArray()) sum += c - '0';
        if (minSum <= sum && sum <= maxSum) ans++; // x=num1 是合法的，補回來
        return ans % MOD;
    }

    private int count(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[][] memo = new int[n][Math.min(9 * n, maxSum) + 1];  // memo[i][sum]: 枚舉到第i位數字，當前數位和 sum 的方案數
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        return f(s, memo, 0, 0, true); // 枚舉第一個數時，必定要先加上isLimit限制，否則可能會超出s數
    }

    private int f(char[] s, int[][] memo, int i, int sum, boolean isLimit) {
        if (sum > maxSum) return 0; // 超過maxSum就是非法數字
        if (i == s.length) return sum >= minSum ? 1 : 0;   // 如果走到底了還是小於minSum，也是是非法數字

        // 不受到 isLimit 約束時的狀態，才能夠隨意枚舉並且查詢memo，
        // 因為memo裡面存的都是 isLimit==false ，也就是枚舉了0~9的方案數，
        // 而 isLimit==true，本位數字最多只能枚舉到s[i]，後面 [i+1...9] 多出來的方案數就會導致出錯
        if (!isLimit && memo[i][sum] != -1) return memo[i][sum];

        int res = 0;
        int up = isLimit ? s[i] - '0' : 9;  // isLimit==true: 本位數字最多枚舉到s[i]; isLimit==false: 當前位可以從0~9選填
        for (int d = 0; d <= up; ++d) // 枚舉要填入的數字 d
            // 當前位置數字有被前位限制isLimit，並且枚舉到up時，才會對後面一位數字產生isLimit限制
            res = (res + f(s, memo, i + 1, sum + d, isLimit && d == up)) % MOD;

        // isLimit 為 true 的狀況實際上只走一次，因為必須同時達成 isLimit && d == up
        // d == up 表示後面無法繼續枚舉，自然就被剪枝了
        // 所以不存memo也可以，存了也沒問題只是多佔空間
        if (!isLimit) memo[i][sum] = res;
        return res;
    }
}

