package LeetcodeMaster.HashMap;

import java.util.HashMap;
import java.util.Map;

public class Q10_IsIsomorphic {
//    205. 同構字符串
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0205.%E5%90%8C%E6%9E%84%E5%AD%97%E7%AC%A6%E4%B8%B2.md
//
//    給定兩個字符串 s 和 t，判斷它們是否是同構的。
//
//    如果 s 中的字符可以按某種映射關系替換得到 t ，那麼這兩個字符串是同構的。
//
//    每個出現的字符都應當映射到另一個字符，同時不改變字符的順序。不同字符不能映射到同一個字符上，相同字符只能映射到同一個字符上，字符可以映射到自己本身。
//
//    示例 1:
//
//    輸入：s = "egg", t = "add"
//    輸出：true
//    示例 2：
//
//    輸入：s = "foo", t = "bar"
//    輸出：false
//    示例 3：
//
//    輸入：s = "paper", t = "title"
//    輸出：true
//    提示：可以假設 s 和 t 長度相同。

    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map1 = new HashMap<>();
        Map<Character, Character> map2 = new HashMap<>();
        for (int i = 0, j = 0; i < s.length(); i++, j++) {
            if (!map1.containsKey(s.charAt(i))) {
                map1.put(s.charAt(i), t.charAt(j)); // map1保存 s[i] 到 t[j]的映射
            }

            if (!map2.containsKey(t.charAt(j))) {
                map2.put(t.charAt(j), s.charAt(i)); // map2保存 t[j] 到 s[i]的映射
            }

            // 無法映射，返回 false
            if (map1.get(s.charAt(i)) != t.charAt(j) || map2.get(t.charAt(j)) != s.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}
