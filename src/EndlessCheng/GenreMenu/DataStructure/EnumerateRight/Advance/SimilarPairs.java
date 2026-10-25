package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

import java.util.HashMap;
import java.util.Map;

public class SimilarPairs {

    // https://leetcode.cn/problems/count-pairs-of-similar-strings/solutions/2024533/xian-xing-zuo-fa-ha-xi-biao-wei-yun-suan-lii4/
    public int similarPairs(String[] words) {
        Map<Integer, Integer> cnt = new HashMap<>();
        int ans = 0;
        for (String s : words) {
            int mask = 0; // 初始化一個空的集合
            for (char c : s.toCharArray()) {
                mask |= 1 << (c - 'a'); // 把 c 加到集合中
            }
            int c = cnt.getOrDefault(mask, 0);
            ans += c;
            cnt.put(mask, c + 1);
        }
        return ans;
    }

}
