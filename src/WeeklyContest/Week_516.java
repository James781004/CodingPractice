package WeeklyContest;

import java.util.*;

public class Week_516 {

    // https://leetcode.com/problems/check-ascii-palindromic/solutions/8477275/binary-string-construction-and-array-rev-uz7m/
    public boolean isPalindromic(String s) {
        String str = "";

        for (char c : s.toCharArray()) {
            String bin = Integer.toBinaryString(c);

            while (bin.length() < 8) {
                bin = "0" + bin;
            }
            str += bin;
        }

        int left = 0;
        int right = str.length() - 1;
        char[] cs = str.toCharArray();

        while (left < right) {
            char temp = cs[left];
            cs[left] = cs[right];
            cs[right] = temp;

            left++;
            right--;
        }

        return Arrays.equals(str.toCharArray(), cs);
    }

    // https://leetcode.cn/problems/find-all-numbers-disappeared-in-an-array-ii/solutions/4016377/yong-shao-bing-jian-hua-dai-ma-pythonjav-paja/
    public List<List<Integer>> findDisappearedNumbers(int[] nums, int lower, int upper) {
        int[] a = Arrays.copyOf(nums, nums.length + 2);
        a[nums.length] = lower - 1;
        a[nums.length + 1] = upper + 1;
        Arrays.sort(a);

        int l = Arrays.binarySearch(a, lower);
        if (l < 0) l = ~l;
        int r = Arrays.binarySearch(a, upper + 1);
        if (r < 0) r = ~r;

        List<List<Integer>> ans = new ArrayList<>();
        for (int i = l; i <= r; i++) {
            if (a[i] - a[i - 1] > 1) {
                ans.add(List.of(a[i - 1] + 1, a[i] - 1));
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-subarray-with-at-most-k-distinct-prime-factors/solutions/4016363/yu-chu-li-hua-dong-chuang-kou-pythonjava-4hfj/
    static class Solution_4032 {
        private static final int MX = 100_001;
        private static final List<Integer>[] primeFactors = new ArrayList[MX];
        private static boolean initialized = false;

        // 這樣寫比 static block 快
        public Solution_4032() {
            if (initialized) {
                return;
            }
            initialized = true;

            Arrays.setAll(primeFactors, v -> new ArrayList<>());
            for (int i = 2; i < MX; i++) {
                if (primeFactors[i].isEmpty()) { // i 是質數
                    for (int j = i; j < MX; j += i) { // i 的倍數 j 有質因子 i
                        primeFactors[j].add(i);
                    }
                }
            }
        }

        public int longestSubarray(int[] nums, int k) {
            HashMap<Integer, Integer> cnt = new HashMap<>();
            int left = 0;
            int ans = 0;
            for (int i = 0; i < nums.length; i++) {
                int x = nums[i];
                for (int p : primeFactors[x]) {
                    cnt.merge(p, 1, Integer::sum); // ++cnt[p]
                }
                while (cnt.size() > k) {
                    for (int p : primeFactors[nums[left]]) {
                        int c = cnt.merge(p, -1, Integer::sum); // c = --cnt[p]
                        if (c == 0) {
                            cnt.remove(p); // 保證 cnt.size() 是窗口內的不同質因子個數
                        }
                    }
                    left++;
                }
                ans = Math.max(ans, i - left + 1);
            }
            return ans;
        }
    }


    // https://leetcode.cn/problems/valid-k-unique-subarrays-i/solutions/4016355/yi-huo-ha-xi-chi-xian-shu-zhuang-shu-zu-pkxwi/
    private static final Random random = new Random();

    public boolean[] validSubarrays(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        long[] sum = new long[n + 1];
        HashMap<Integer, Long> hash = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            // 把不同的 nums[i] 映射成一個隨機的 long
            if (!hash.containsKey(x)) {
                hash.put(x, random.nextLong());
            }
            sum[i + 1] = sum[i] ^ hash.get(x);
        }

        // 離線詢問：按照右端點分組
        List<int[]>[] groups = new ArrayList[n];
        Arrays.setAll(groups, v -> new ArrayList<>());
        for (int i = 0; i < queries.length; i++) {
            int[] q = queries[i];
            groups[q[1]].add(new int[]{q[0], i});
        }

        FenwickTree t = new FenwickTree(n);
        HashMap<Integer, Integer> last = new HashMap<>(hash.size());
        boolean[] ans = new boolean[queries.length];
        for (int r = 0; r < n; r++) {
            int x = nums[r];
            if (last.containsKey(x)) {
                t.update(last.get(x), -1);
            }
            last.put(x, r);
            t.update(r, 1);
            for (int[] p : groups[r]) {
                int l = p[0];
                ans[p[1]] = sum[r + 1] == sum[l] && t.query(l, r) == k;
            }
        }
        return ans;
    }


    // 模板來源 https://leetcode.cn/discuss/post/3583665/
    static class FenwickTree {
        private final int[] tree;

        public FenwickTree(int n) {
            tree = new int[n + 1]; // 使用下標 1 到 n
        }

        // a[i] 增加 val
        // 時間復雜度 O(log n)
        public void update(int i, int val) {
            for (i++; i < tree.length; i += i & -i) {
                tree[i] += val;
            }
        }

        // 求前綴和 a[1] + ... + a[i]
        // 時間復雜度 O(log n)
        public int pre(int i) {
            int res = 0;
            for (i++; i > 0; i &= i - 1) {
                res += tree[i];
            }
            return res;
        }

        // 求區間和 a[l] + ... + a[r]
        // 時間復雜度 O(log n)
        public int query(int l, int r) {
            return pre(r) - pre(l - 1);
        }
    }

}










