package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.HashMap;
import java.util.Map;

public class MaxPartitionsAfterOperations {

    // https://leetcode.cn/problems/maximize-the-number-of-partitions-after-operations/solutions/2595072/ji-yi-hua-sou-suo-jian-ji-xie-fa-pythonj-6g5z/
    public int maxPartitionsAfterOperations(String s, int k) {
        Map<Long, Integer> memo = new HashMap<>();
        return dfs(0, 0, 0, memo, s.toCharArray(), k);
    }

    // 定義 dfs(i,mask,changed) 表示當前遍歷到 s[i]，在 i 之前（不含 i）的字符集合是 mask，
    // 之前是否修改過（changed），返回在這一狀態下，我們從剩余未分割的後綴中，可以得到的最大分割數。
    private int dfs(int i, int mask, int changed, Map<Long, Integer> memo, char[] s, int k) {
        if (i == s.length) {
            return 1;
        }

        // 把參數壓縮到一個 long 中，方便作為哈希表的 key
        long args = (long) i << 32 | mask << 1 | changed;
        if (memo.containsKey(args)) { // 之前計算過
            return memo.get(args);
        }

        int res;
        // 不改 s[i]
        int bit = 1 << (s[i] - 'a');
        int newMask = mask | bit;
        if (Integer.bitCount(newMask) > k) {
            // 分割出一個子串，這個子串的最後一個字母在 i-1
            // s[i] 作為下一段的第一個字母，也就是 bit 作為下一段的 mask 的初始值
            res = dfs(i + 1, bit, changed, memo, s, k) + 1;
        } else { // 不分割
            res = dfs(i + 1, newMask, changed, memo, s, k);
        }

        if (changed == 0) {
            // 枚舉把 s[i] 改成 a,b,c,...,z
            for (int j = 0; j < 26; j++) {
                newMask = mask | (1 << j);
                if (Integer.bitCount(newMask) > k) {
                    // 分割出一個子串，這個子串的最後一個字母在 i-1
                    // j 作為下一段的第一個字母，也就是 1<<j 作為下一段的 mask 的初始值
                    res = Math.max(res, dfs(i + 1, 1 << j, 1, memo, s, k) + 1);
                } else { // 不分割
                    res = Math.max(res, dfs(i + 1, newMask, 1, memo, s, k));
                }
            }
        }

        memo.put(args, res); // 記憶化
        return res;
    }


}
