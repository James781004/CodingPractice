package WeeklyContest;

import java.util.*;

public class Week_484 {

    // https://leetcode.cn/problems/count-residue-prefixes/solutions/3877691/liang-chong-fang-fa-ha-xi-ji-he-wei-yun-ncgib/
    public int residuePrefixes(String s) {
        Set<Character> set = new HashSet<>();
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            set.add(s.charAt(i));
            if (set.size() == (i + 1) % 3) {
                ans++;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/number-of-centered-subarrays/solutions/3877689/mei-ju-suo-you-zi-shu-zu-ha-xi-ji-he-pyt-4x7b/
    public int centeredSubarrays(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            HashSet<Integer> set = new HashSet<>();
            int s = 0;
            for (int j = i; j < nums.length; j++) {
                int x = nums[j];
                set.add(x);
                s += x;
                if (set.contains(s)) {
                    ans++;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-caesar-cipher-pairs/solutions/3877688/mei-ju-you-wei-hu-zuo-pythonjavacgo-by-e-w5k4/
    public long countPairs(String[] words) {
        Map<String, Integer> cnt = new HashMap<>(); // 統計每個字符串的出現次數
        long ans = 0; // 統計凱撒加密對數目

        // 枚舉右，維護左
        for (String s : words) {
            char[] t = s.toCharArray();
            char base = t[0];

            // 循環替換，把第一個字母移動為 a，其他字母移動同樣距離
            // 比如字符串 bcd 和 cde。把字符串通過操作，改成第一個字母是 a，
            // 就能看出這兩個字符串都是 abc 通過循環替換得到的
            for (int i = 0; i < t.length; i++) {
                t[i] = (char) ((t[i] - base + 26) % 26); // 保證結果在 [0, 25] 中
            }
            s = new String(t);

            // 從 cnt 中查詢 t 的出現次數，即為 i 的個數，加到答案中
            int c = cnt.getOrDefault(s, 0);
            ans += c;
            cnt.put(s, c + 1); // 把 cnt[t] 加一
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-bitwise-and-after-increment-operations/solutions/3877680/shi-tian-fa-pai-xu-kuai-su-xuan-ze-pytho-3fyt/
    public int maximumAND(int[] nums, int k, int m) {
        int mx = 0;
        for (int x : nums) {
            mx = Math.max(mx, x);
        }

        int[] ops = new int[nums.length]; // 每個數的操作次數
        int ans = 0;
        int maxWidth = 32 - Integer.numberOfLeadingZeros(mx + k);
        for (int bit = maxWidth - 1; bit >= 0; bit--) {
            int target = ans | (1 << bit); // 注意 target 要帶著 ans 已經填好的 1
            for (int i = 0; i < nums.length; i++) {
                int x = nums[i];
                int j = 32 - Integer.numberOfLeadingZeros(target & ~x);
                // j-1 是從高到低第一個 target 是 1，x 是 0 的比特位
                // target = 10110
                //      x = 11010
                //            ^
                //           j-1
                // x 高於 j-1 的比特位不變，其余變成和 target 一樣
                // 上面的例子要把 010 變成 110
                int mask = (1 << j) - 1;
                ops[i] = (target & mask) - (x & mask);
            }

            // 貪心，取前 m 小的操作次數
            Arrays.sort(ops);
            long sum = 0;
            for (int i = 0; i < m; i++) {
                sum += ops[i];
            }
            if (sum <= k) {
                ans = target; // 答案的 bit 位可以填 1
            }
        }
        return ans;
    }


}









