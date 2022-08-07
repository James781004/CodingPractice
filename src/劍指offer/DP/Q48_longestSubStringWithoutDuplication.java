package 劍指offer.DP;

import java.util.HashMap;
import java.util.Map;

public class Q48_longestSubStringWithoutDuplication {

    public int lengthOfLongestSubstring(String s) {
        // 哈希表 dic 统计： 指针 j 遍历字符 s ，哈希表统计字符 s[j] 最后一次出现的索引 。
        // 更新左指针 i ： 根据上轮左指针 i 和 dic[s[j]] ，
        // 每轮更新左边界 i ，保证区间 [i + 1, j] 内无重复字符且最大。
        // i = max(dic[s[j]], i)
        //
        // 更新结果 res ： 取上轮 res 和本轮双指针区间 [i + 1,j][i+1,j] 的宽度（即 j - ij−i ）中的最大值。
        // res = max(res, j - i)

        Map<Character, Integer> dic = new HashMap<>();
        int i = -1, res = 0;
        for (int j = 0; j < s.length(); j++) {
            if (dic.containsKey(s.charAt(j)))
                i = Math.max(i, dic.get(s.charAt(j))); // 更新左指针 i
            dic.put(s.charAt(j), j); // 哈希表记录
            res = Math.max(res, j - i); // 更新结果
        }
        return res;
    }


    public int lengthOfLongestSubstringDP(String s) {
        Map<Character, Integer> dic = new HashMap<>();
        int res = 0, tmp = 0;
        for (int j = 0; j < s.length(); j++) {
            int i = dic.getOrDefault(s.charAt(j), -1); // 获取索引 i
            dic.put(s.charAt(j), j); // 更新哈希表
            tmp = tmp < j - i ? tmp + 1 : j - i; // dp[j - 1] -> dp[j]
            res = Math.max(res, tmp); // max(dp[j - 1], dp[j])
        }
        return res;
    }

}
