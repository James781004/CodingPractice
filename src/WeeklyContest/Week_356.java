package WeeklyContest;

import java.util.*;

class Week_356 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2798.Number%20of%20Employees%20Who%20Met%20the%20Target/README.md
    public int numberOfEmployeesWhoMetTarget(int[] hours, int target) {
        int ans = 0;
        for (int x : hours) {
            if (x >= target) {
                ++ans;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2799.Count%20Complete%20Subarrays%20in%20an%20Array/README.md
    // 方法一：哈希表 + 枚舉
    public int countCompleteSubarrays(int[] nums) {
        Set<Integer> s = new HashSet<>();
        for (int x : nums) {
            s.add(x);
        }
        int target = s.size();
        int ans = 0, n = nums.length;

        // 枚舉子數組的左端點下標 i，並維護一個集合s
        for (int i = 0; i < n; ++i) {
            s.clear();

            // 每次向右移動右端點下標 j 時，nums[j] 加入集合 s
            for (int j = i; j < n; ++j) {
                s.add(nums[j]);

                // 判斷集合 s 的大小是否等於 target。
                // 如果等於 target，則說明當前子數組是完全子數組，將答案增加 1。
                if (s.size() == target) {
                    ++ans;
                }
            }
        }
        return ans;
    }


    // 方法二：哈希表 + 雙指針
    public int countCompleteSubarrays2(int[] nums) {
        Map<Integer, Integer> d = new HashMap<>();
        for (int x : nums) {
            d.put(x, 1);
        }
        int target = d.size();
        int ans = 0, n = nums.length;
        d.clear();

        // 使用雙指針維護一個滑動窗口，滑動窗口的右端點下標為 j，左端點下標為 i。
        // 每次固定左端點下標 i，然後向右移動右端點下標 j
        for (int i = 0, j = 0; j < n; ++j) {
            d.merge(nums[j], 1, Integer::sum);

            // 當滑動窗口中的元素種類數等於 target 時，
            // 意味著從左端點下標 i 到右端點下標 j 以及右側的所有子數組都是完全子數組
            // 將答案增加  n - j，其中 n 是數組的長度
            while (d.size() == target) {
                ans += n - j;
                if (d.merge(nums[i], -1, Integer::sum) == 0) {
                    d.remove(nums[i]);
                }

                // 左端點下標 i右移一位
                ++i;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2800.Shortest%20String%20That%20Contains%20Three%20Strings/README.md
    // 枚舉三個字符串的所有排列，然後對於每個排列，對三個字符串進行合並，找到最短的且字典序最小的字符串。
    public String minimumString(String a, String b, String c) {
        String[] s = {a, b, c};
        int[][] perm = {{0, 1, 2}, {0, 2, 1}, {1, 0, 2}, {1, 2, 0}, {2, 1, 0}, {2, 0, 1}};
        String ans = "";
        for (int[] p : perm) { // 主函數枚舉流程 0, 1, 2 以不同順序拼接
            int i = p[0], j = p[1], k = p[2];
            String t = mergeString(mergeString(s[i], s[j]), s[k]);
            if ("".equals(ans) || t.length() < ans.length()
                    || (t.length() == ans.length() && t.compareTo(ans) < 0)) {
                ans = t;
            }
        }
        return ans;
    }

    private String mergeString(String s, String t) {
        // 1. t 已經包含在 s 之中
        if (s.contains(t)) {
            return s;
        }

        // 2. s 已經包含在 t 之中
        if (t.contains(s)) {
            return t;
        }

        // 3. 以下標 i 為分界，如果發現 s 後半跟 t 前半有重合部份，
        //    這邊讓 s 整段跟 t 後半拼接
        //    先不用考慮反過來的情況，因為不同順序拼接在主函數枚舉流程中會包含到
        int m = s.length(), n = t.length();
        for (int i = Math.min(m, n); i > 0; --i) {
            if (s.substring(m - i).equals(t.substring(0, i))) {
                return s + t.substring(i);
            }
        }

        // 4. 單純兩字串拼接，先不用考慮反過來的情況，因為不同順序拼接在主函數枚舉流程中會包含到
        return s + t;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2801.Count%20Stepping%20Numbers%20in%20Range/README.md
    // https://leetcode.cn/problems/count-stepping-numbers-in-range/solutions/2364624/shu-wei-dp-tong-yong-mo-ban-by-endlessch-h8fj/
    // https://www.youtube.com/watch?v=CoBQxyDHDTI
    // 對於這種區間 [l...r] 的問題，我們通常可以考慮轉化為求 [1...r] 和 [1...l-1] 的答案，然後相減即可。
    // 題目中只涉及到不同數位之間的關系，而不涉及具體的數值，因此可以考慮使用數位 DP 來解決。
    private static final int MOD = (int) 1e9 + 7;

    public int countSteppingNumbers(String low, String high) {
        return (calc(high) - calc(low) + MOD + (valid(low) ? 1 : 0)) % MOD; // +MOD 防止算出負數
    }

    private char s[];
    private int memo[][];

    private int calc(String s) {
        this.s = s.toCharArray();
        int m = s.length();
        memo = new int[m][10];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1); // -1 表示沒有計算過
        }

        // 遞歸入口：f(0, 0, true, false)，表示：
        // 從 s[0] 開始枚舉；
        // pre 初始化成什麼都可以，因為填第一個數的時候是忽略 pre 的。
        // 一開始要受到 n 的約束（否則就可以隨意填了，這肯定不行）；
        // 一開始沒有填數字。
        return f(0, 0, true, false);
    }

    // 當前處理到第 i 位，前一位數字是 pre，當前數字是否只包含前導零 isNum，當前數字是否達到上界 isLimit 的方案數。
    // 如果 isNum 為假，說明前面沒有填數字，那麼當前也可以不填數字。
    // 一旦從這裡遞歸下去，isLimit 就可以置為 false 了，這是因為 s[0] 必然是大於 0 的，後面就不受到 n 的約束了。
    // 或者說，最高位不填數字，後面無論怎麼填都比 n 小。
    // 如果 isNum 為真，那麼當前必須填一個數字。枚舉填入的數字，根據 isNum 和 isLimit 來決定填入數字的範圍。
    private int f(int i, int pre, boolean isLimit, boolean isNum) {
        // 已經處理完了所有數位
        if (i == s.length) {
            return isNum ? 1 : 0; // isNum 為 true 表示得到了一個合法數字
        }

        // 記憶化
        if (!isLimit && isNum && memo[i][pre] != -1) {
            return memo[i][pre];
        }

        int res = 0;

        // isNum 表示 i 前面的數位是否填了數字。
        // 若為假，則當前位可以跳過（不填數字），或者要填入的數字至少為 1；
        // 若為真，則要填入的數字可以從 0 開始。
        // 例如 n=123，在 i=0 時跳過的話，相當於後面要構造的是一個 99 以內的數字了，
        // 如果 i=1 不跳過，那麼相當於構造一個 10 到 99 的兩位數，
        // 如果 i=1 也跳過，相當於構造的是一個 9 以內的數字。
        if (!isNum) { // 可以跳過當前數位
            res = f(i + 1, pre, false, false);
        }

        // isLimit 表示當前是否受到了 n 的約束（注意要構造的數字不能超過 n）。
        // 若為真，則第 i 位填入的數字至多為 s[i]，否則可以是 999。
        // 如果在受到約束的情況下填了 s[i]，那麼後續填入的數字仍會受到 n 的約束。
        // 例如 n=123，那麼 i=0 填的是 1 的話，i=1 的這一位至多填 2。
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的數字都和 s 的一樣，那麼這一位至多填數字 s[i]（否則就超過 s 啦）

        for (int d = isNum ? 0 : 1; d <= up; d++) // 枚舉要填入的數字 d
            if (!isNum || Math.abs(d - pre) == 1) // 第一位數字隨便填，其余必須相差 1
                res = (res + f(i + 1, d, isLimit && d == up, true)) % MOD;
        if (!isLimit && isNum) // 記憶化
            memo[i][pre] = res;
        return res;
    }

    private boolean valid(String s) {
        for (int i = 1; i < s.length(); i++)
            if (Math.abs((int) s.charAt(i) - (int) s.charAt(i - 1)) != 1)
                return false;
        return true;
    }
}


