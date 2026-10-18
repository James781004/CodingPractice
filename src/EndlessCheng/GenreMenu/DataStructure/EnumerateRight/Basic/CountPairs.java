package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

import java.util.HashMap;
import java.util.Map;

public class CountPairs {

    // https://leetcode.cn/problems/count-caesar-cipher-pairs/solutions/3877688/mei-ju-you-wei-hu-zuo-pythonjavacgo-by-e-w5k4/
    public long countPairs(String[] words) {
        Map<String, Integer> cntWords = new HashMap<>();
        for (String s : words) {
            cntWords.merge(s, 1, Integer::sum);
        }

        Map<String, Integer> cnt = new HashMap<>();
        long ans = 0;
        for (Map.Entry<String, Integer> e : cntWords.entrySet()) {
            String s = e.getKey();
            int totalS = e.getValue();
            char[] t = s.toCharArray();
            char base = t[0];
            for (int i = 0; i < t.length; i++) {
                t[i] = (char) ((t[i] - base + 26) % 26); // 保證結果在 [0, 25] 中
            }
            s = new String(t);
            int c = cnt.getOrDefault(s, 0);
            // totalS 個 s 中選 2 個有 C(totalS, 2) 種方案
            ans += (long) c * totalS + (long) totalS * (totalS - 1) / 2;
            cnt.put(s, c + totalS);
        }
        return ans;
    }

}
