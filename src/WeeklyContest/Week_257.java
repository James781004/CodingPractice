package WeeklyContest;

import java.util.*;

class Week_257 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1995.Count%20Special%20Quadruplets/README.md
    public int countQuadruplets(int[] nums) {
        int ans = 0, n = nums.length;
        for (int a = 0; a < n - 3; ++a) {
            for (int b = a + 1; b < n - 2; ++b) {
                for (int c = b + 1; c < n - 1; ++c) {
                    for (int d = c + 1; d < n; ++d) {
                        if (nums[a] + nums[b] + nums[c] == nums[d]) {
                            ++ans;
                        }
                    }
                }
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/tree/main/solution/1900-1999/1996.The%20Number%20of%20Weak%20Characters%20in%20the%20Game
    public int numberOfWeakCharacters(int[][] properties) {
        // 將所有角色按照攻擊力降序排序，防御力升序排序
        Arrays.sort(properties, (a, b) -> b[0] - a[0] == 0 ? a[1] - b[1] : b[0] - a[0]);
        int ans = 0, mx = 0;

        // 遍歷所有角色，對於當前角色，如果其防御力小於之前的最大防御力，則說明其為弱角色，答案加一，
        // 否則更新最大防御力
        for (int[] x : properties) {
            if (x[1] < mx) {
                ++ans;
            }
            mx = Math.max(mx, x[1]);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/tree/main/solution/1900-1999/1997.First%20Day%20Where%20You%20Have%20Been%20in%20All%20the%20Rooms
    // https://leetcode.cn/problems/first-day-where-you-have-been-in-all-the-rooms/solution/zi-jie-tiao-dong-de-fu-jia-ti-xiang-xi-z-megj/
    public int firstDayBeenInAllRooms(int[] nextVisit) {
        int n = nextVisit.length;
        long[] f = new long[n]; // f[i] 表示第一次訪問第 i 號房間的日期編號
        final int mod = (int) 1e9 + 7;

        // f[i] = 第二次到達第i−1個房間的所用的天數+1
        // 考慮第一次到達第 i - 1 號房間的日期編號，記為 f[i - 1] ，
        // 此時需要花一天的時間回退到第 nextVisit[i - 1] 號房間，
        // 為什麼是回退呢？因為題目限制了 0 <= nextVisit[i] <= i。
        // 回退之後，此時第 nextVisit[i - 1] 號房間的訪問為奇數次，
        // 而第 [nextVisit[i - 1] + 1...i-1] 號房間均被訪問偶數次，
        // 那麼這時候從第 nextVisit[i - 1] 號房間再次走到第 i - 1 號房間，
        // 就需要花費 f[i - 1] - f[nextVisit[i - 1]] 天的時間，
        // 然後再花費一天的時間到達第 i 號房間，因此 f[i] = f[i - 1] + 1 + f[i - 1] - f[nextVisit[i - 1]] + 1。
        // 由於 f[i] 可能很大，因此需要對 1e9 + 7取余，並且為了防止負數，需要加上 1e9 + 7。
        for (int i = 1; i < n; i++) {
            f[i] = (f[i - 1] + 1 + f[i - 1] - f[nextVisit[i - 1]] + 1 + mod) % mod;
        }
        return (int) f[n - 1];
    }


    // 二維 DP
    public int firstDayBeenInAllRoomsDP(int[] nextVisit) {
        // dp[i][0]表示奇數次到達i時需要的天數
        // dp[i][1]表示偶數次到達i時需要的天數
        long[][] dp = new long[nextVisit.length][2];
        dp[0][1] = 1;//第二次到達0時需要一天
        int mod = 1000000007;
        for (int i = 1; i < nextVisit.length; i++) {
            // 第一次到達i時,得到達兩次i-1,才能達到i
            dp[i][0] = (dp[i - 1][1] + 1) % mod;
            // 第二次到達i是在第一次的基礎上+1
            // 到達奇數次dp[nextVisit[i]][0],此時想要再次到達i,
            // 需要先偶數次的到達i-1 + (dp[i - 1][1] - dp[nextVisit[i]][0]),
            // 再次到達i-1需要的天數再 +1 第二次到達i
            // 注意:這裡相減可能會出現負數,所以加上mod
            dp[i][1] = (dp[i][0] + 1 + (mod + dp[i - 1][1] - dp[nextVisit[i]][0]) + 1) % mod;
        }
        return (int) dp[dp.length - 1][0];
    }


    // https://github.com/doocs/leetcode/tree/main/solution/1900-1999/1998.GCD%20Sort%20of%20an%20Array
    // 並查集解法
    // 對於本題，最大公因數大於 1 的兩個數，可以進行交換，
    // 因此，只要一個集合中所有數都存在相同公因數，那麼這個集合中任意數都能進行兩兩交換，
    // 因此可以用並查集，把同個集合中的所有數字進行合並。
    // (在這道題中，可以先預處理每個數的質因數，數字與質因數歸屬同一個集合。)
    // 合並之後，將原數組復制一份，並進行升序排列，得到新數組 s。
    // 然後遍歷原數組，若原數組對應元素與新數組對應元素不相同，並且兩個元素也不在同一個集合中，
    // 說明不滿足條件，直接返回 false，否則遍歷結束返回 true。
    private int[] p;

    public boolean gcdSort(int[] nums) {
        int n = 100010;

        // 預處理並查集 parent 數組
        p = new int[n];
        Map<Integer, List<Integer>> f = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            p[i] = i;
        }
        int mx = 0;
        for (int num : nums) {
            mx = Math.max(mx, num);
        }

        // 預處理[2...mx]範圍每個數的質因數，每個數的質因數以 list 形式保存在f map
        for (int i = 2; i <= mx; ++i) {
            if (f.containsKey(i)) {
                continue;
            }
            for (int j = i; j <= mx; j += i) {
                f.computeIfAbsent(j, k -> new ArrayList<>()).add(i);
            }
        }

        // 將 nums 中數字與質因數歸屬同一個集合
        for (int i : nums) {
            for (int j : f.get(i)) { // 這邊是取出數字 i 的質因數們
                p[find(i)] = find(j);
            }
        }
        int[] s = new int[nums.length];

        // 將原數組復制一份，並進行升序排列
        System.arraycopy(nums, 0, s, 0, nums.length);
        Arrays.sort(s);

        // 遍歷原數組，若原數組對應元素與新數組對應元素不相同，並且兩個元素也不在同一個集合中，
        // 說明不滿足條件，直接返回 false，否則遍歷結束返回 true。
        for (int i = 0; i < nums.length; ++i) {
            if (s[i] != nums[i] && find(nums[i]) != find(s[i])) {
                return false;
            }
        }
        return true;
    }

    int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}

