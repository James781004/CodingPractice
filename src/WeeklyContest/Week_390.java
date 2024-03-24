package WeeklyContest;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Week_390 {
    // https://leetcode.cn/problems/maximum-length-substring-with-two-occurrences/solutions/2704776/on-hua-dong-chuang-kou-pythonjavacgo-by-hl44d/
    public int maximumLengthSubstring(String S) {
        char[] s = S.toCharArray();
        int ans = 0;
        int left = 0;
        int[] cnt = new int[26];
        for (int i = 0; i < s.length; i++) {
            int b = s[i] - 'a';
            cnt[b]++;
            while (cnt[b] > 2) {
                cnt[s[left++] - 'a']--;
            }
            ans = Math.max(ans, i - left + 1);
        }
        return ans;
    }


    // https://leetcode.cn/problems/apply-operations-to-make-sum-of-array-greater-than-or-equal-to-k/solutions/2704767/liang-chong-fang-fa-mei-ju-o1-shu-xue-go-ug47/
    public int minOperations(int k) {
        int ans = Integer.MAX_VALUE;
        for (int m = 1; m <= k; m++) {
            ans = Math.min(ans, m - 1 + (k - 1) / m);
        }
        return ans;
    }


    // https://leetcode.cn/problems/most-frequent-ids/solutions/2704858/ha-xi-biao-you-xu-ji-he-pythonjavacgo-by-7brw/
    public long[] mostFrequentIDs(int[] nums, int[] freq) {
        Map<Integer, Long> cnt = new HashMap<>();
        TreeMap<Long, Integer> m = new TreeMap<>();
        int n = nums.length;
        long[] ans = new long[n];
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (cnt.containsKey(x) && m.merge(cnt.get(x), -1, Integer::sum) == 0) {
                m.remove(cnt.get(x));
            }
            m.merge(cnt.merge(x, (long) freq[i], Long::sum), 1, Integer::sum);
            ans[i] = m.lastKey();
        }
        return ans;
    }


    // https://leetcode.cn/problems/longest-common-suffix-queries/solutions/2704763/zi-dian-shu-wei-hu-zui-duan-chang-du-he-r3h3j/
    public int[] stringIndices(String[] wordsContainer, String[] wordsQuery) {
        Node root = new Node();
        for (int idx = 0; idx < wordsContainer.length; ++idx) {
            char[] s = wordsContainer[idx].toCharArray();
            int l = s.length;
            Node cur = root;
            if (l < cur.minL) {
                cur.minL = l;
                cur.i = idx;
            }
            for (int i = s.length - 1; i >= 0; i--) {
                int b = s[i] - 'a';
                if (cur.son[b] == null) {
                    cur.son[b] = new Node();
                }
                cur = cur.son[b];
                if (l < cur.minL) {
                    cur.minL = l;
                    cur.i = idx;
                }
            }
        }

        int[] ans = new int[wordsQuery.length];
        for (int idx = 0; idx < wordsQuery.length; idx++) {
            char[] s = wordsQuery[idx].toCharArray();
            Node cur = root;
            for (int i = s.length - 1; i >= 0 && cur.son[s[i] - 'a'] != null; i--) {
                cur = cur.son[s[i] - 'a'];
            }
            ans[idx] = cur.i;
        }
        return ans;
    }

    static class Node {
        Node[] son = new Node[26];
        int minL = Integer.MAX_VALUE;
        int i;
    }

}


