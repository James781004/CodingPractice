package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

class Week_259 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2011.Final%20Value%20of%20Variable%20After%20Performing%20Operations/README.md
    public int finalValueAfterOperations(String[] operations) {
        int ans = 0;
        for (String s : operations) {
            ans += (s.charAt(1) == '+' ? 1 : -1);
        }
        return ans;
    }


    // https://leetcode.cn/problems/sum-of-beauty-in-the-array/solution/java-by-blank-829-s4qb/
    public int sumOfBeauties(int[] nums) {
        int n = nums.length;

        // 保存左邊最大
        int max = nums[0];
        // 保存右邊最小
        int min = nums[n - 1];

        // 初始化狀態數組，保存當前數是否滿足條件
        boolean[] sat = new boolean[n];

        // 先從左開始遍歷
        for (int i = 1; i <= n - 2; ++i) {
            // 中間數如果大於左邊最大，說明這個數左半邊都滿足條件
            if (max < nums[i]) {
                sat[i] = true;
                max = nums[i];
            }
        }

        // 再從右開始遍歷
        for (int i = n - 2; i >= 1; --i) {
            // 中間數如果小於右邊最小，說明這個數右半邊都滿足條件
            if (nums[i] < min) {
                min = nums[i];
            } else { // 不滿足需要重置為false，防止只滿足左邊的情況
                sat[i] = false;
            }
        }

        int res = 0;
        for (int i = 1; i <= n - 2; ++i) {
            if (sat[i]) {
                res += 2;
            } else if (nums[i - 1] < nums[i] && nums[i] < nums[i + 1]) {
                res += 1;
            }
        }

        return res;
    }


    // https://leetcode.cn/problems/detect-squares/solution/gong-shui-san-xie-jian-dan-ha-xi-biao-yu-748e/
    class DetectSquares {
        Map<Integer, Map<Integer, Integer>> xyMap = new HashMap<>();

        public void add(int[] point) {
            int x = point[0], y = point[1];
            Map<Integer, Integer> yCnt = xyMap.getOrDefault(x, new HashMap<>());
            yCnt.put(y, yCnt.getOrDefault(y, 0) + 1);
            xyMap.put(x, yCnt); // xyMap保存 <x, <y, xy點出現次數>>
        }

        public int count(int[] point) {
            int x = point[0], y = point[1];
            int ans = 0;
            Map<Integer, Integer> yCnt = xyMap.getOrDefault(x, new HashMap<>());
            for (int ny : yCnt.keySet()) {
                if (ny == y) continue;
                int c1 = yCnt.get(ny);
                int len = y - ny; // 正方形長度
                int[] nums = new int[]{x + len, x - len};
                for (int nx : nums) { // 尋找剩下兩個點
                    Map<Integer, Integer> temp = xyMap.getOrDefault(nx, new HashMap<>());
                    int c2 = temp.getOrDefault(y, 0), c3 = temp.getOrDefault(ny, 0);
                    ans += c1 * c2 * c3; // 乘法原理
                }
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/longest-subsequence-repeated-k-times/solution/zui-jian-ji-yi-dong-de-fang-fa-li-yong-z-hay1/
    // 全排列候選人
    StringBuilder ans = new StringBuilder();
    boolean[] used;

    public String longestSubsequenceRepeatedK(String s, int k) {
        int[] hash = new int[26];
        char[] arr = s.toCharArray();

        // 計數
        for (char ch : arr) {
            hash[ch - 'a']++;
        }

        // 按逆序字典序，將滿足 k 次的字符加入
        StringBuilder sb = new StringBuilder();
        for (int i = 25; i >= 0; i--) {
            for (int j = 0; j < hash[i] / k; j++) {
                sb.append((char) ('a' + i));
            }
        }

        // 搜索 i = n -> 1 長度的前 i 個字符的全排列，找第一個滿足的
        int n = sb.length();
        used = new boolean[n];
        char[] pattern = sb.toString().toCharArray();
        for (int i = n; i > 0; i--) {
            // 增加限制，限制全排列的字符串長度
            if (dfs(arr, pattern, 0, i, n, k)) {
                return ans.toString();
            }
        }

        return "";
    }


    // 生成 p 的 cnt = n -> 1 長度的全排列，每次查找的都是長度為 cnt 的子序列，最終檢查是不是滿足
    private boolean dfs(char[] s, char[] p, int len, int cnt, int n, int k) {
        if (len == cnt) {
            return check(s, cnt, k);
        }

        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                used[i] = true;
                ans.append(p[i]);
                if (dfs(s, p, len + 1, cnt, n, k)) {
                    return true;
                }
                ans.deleteCharAt(len);
                used[i] = false;
            }
        }
        return false;
    }


    // 檢查當前排列是不是滿足 s 中 k 次子序列
    private boolean check(char[] s, int n, int k) {
        int cnt = 0, idx = 0;

        for (char ch : s) {
            if (ch == ans.charAt(idx)) {
                idx++;
                if (idx == n) {
                    idx = 0;
                    cnt++;
                }
            }
        }

        // cnt 可以超過 k
        // "kkrkmktkkhqdlkzqfdmkkghjk" 9
        return cnt >= k;
    }
}

