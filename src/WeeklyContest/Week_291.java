package WeeklyContest;

import java.util.*;

public class Week_291 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2259.Remove%20Digit%20From%20Number%20to%20Maximize%20Result/README.md
    public String removeDigit(String number, char digit) {
        int last = -1;
        int n = number.length();
        for (int i = 0; i < n; i++) {
            char d = number.charAt(i);
            if (d == digit) {
                last = i;
                if (i + 1 < n && d < number.charAt(i + 1)) {
                    break;
                }
            }
        }
        return number.substring(0, last) + number.substring(last + 1);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2260.Minimum%20Consecutive%20Cards%20to%20Pick%20Up/README.md
    public int minimumCardPickup(int[] cards) {
        Map<Integer, Integer> last = new HashMap<>();
        int n = cards.length;
        int ans = n + 1;
        for (int i = 0; i < n; i++) {
            if (last.containsKey(cards[i])) { // 如果 last[x] 存在，則表示 x 有一對匹配卡牌
                ans = Math.min(ans, i - last.get(cards[i]) + 1); // 更新答案
            }
            last.put(cards[i], i);
        }
        return ans > n ? -1 : ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2261.K%20Divisible%20Elements%20Subarrays/README.md
    public int countDistinct(int[] nums, int k, int p) {
        int n = nums.length;
        Set<String> s = new HashSet<>();

        // 枚舉子數組的左右端點(i, j)
        // 每個子數組 nums[i...j]，統計其中可以被 p 整除的元素的個數 cnt，
        // 如果 ++cnt (cnt 加上當前可以被 p 整除的 nums[j]) > k，則該子數組滿足條件，
        // 將所有滿足條件的子數組的元素序列作為字符串存入哈希表中
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            String t = "";
            for (int j = i; j < n; j++) {
                if (nums[j] % p == 0 && ++cnt > k) break;
                t += nums[j] + ",";
                s.add(t);
            }
        }
        return s.size();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2262.Total%20Appeal%20of%20A%20String/README.md
    public long appealSum(String s) {
        long ans = 0;
        long t = 0; // t 表示以 s[i] 結尾的子串的引力值之和
        int[] pos = new int[26]; // 每個字符最近一次出現的位置
        Arrays.fill(pos, -1);

        // 當前遍歷到字符 s[i]，對應的引力值 t 的計算邏輯為：
        // 1. 如果 s[i] 在之前沒出現過，那麼以 s[i-1] 結尾的每個子串的引力值都會加上 1，
        //    引力值之和會增加 i，再加上 1（s[i] 單獨組成的子串的引力值），得到 s[i] 的引力值 t。
        // 2. 如果 s[i] 在之前出現過，定義最近一次出現的下標為 j，
        //    那麼向子串 s[0..i-1], s[1..i-1], ..., s[j..i-1] 的末尾添加 s[i]，引力值不變。
        //    而 s[j+1..i-1], s[j+2..i=1], ..., s[i-1..i-1] 由於不包含 s[i]，
        //    這些子串的引力值增加 1，因此有 i-j-1 個子串的引力值會增加 1，
        //    引力值之和增加 i-j-1，再加上 1，得到 s[i] 的引力值 t。
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            t += i - pos[c];
            ans += t;
            pos[c] = i;
        }
        return ans;
    }
}
