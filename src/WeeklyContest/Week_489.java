package WeeklyContest;

import java.util.*;


public class Week_489 {

    // https://leetcode.cn/problems/toggle-light-bulbs/solutions/3903065/he-100-wu-guan-de-zuo-fa-pythonjavacgo-b-1coo/
    public List<Integer> toggleLightBulbs(List<Integer> bulbs) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int i : bulbs) {
            cnt.merge(i, 1, Integer::sum); // cnt[i]++
        }

        List<Integer> ans = new ArrayList<>();
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            if (e.getValue() % 2 > 0) {
                ans.add(e.getKey());
            }
        }
        Collections.sort(ans);
        return ans;
    }


    // https://leetcode.cn/problems/first-element-with-unique-frequency/solutions/3903067/tong-ji-chu-xian-ci-shu-de-chu-xian-ci-s-ewl8/
    public int firstUniqueFreq(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }

        int[] cc = new int[nums.length + 1];
        for (int c : cnt.values()) {
            cc[c]++;
        }

        for (int x : nums) {
            if (cc[cnt.get(x)] == 1) {
                return x;
            }
        }
        return -1;
    }


    // https://leetcode.cn/problems/longest-almost-palindromic-substring/solutions/3903063/zhong-xin-kuo-zhan-fa-pythonjavacgo-by-e-kmfw/
    public int almostPalindromic(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        int ans = 0;

        for (int i = 0; i < 2 * n - 1 && ans < n; i++) {
            int l = i / 2;
            int r = (i + 1) / 2;
            while (l >= 0 && r < n && s[l] == s[r]) {
                l--;
                r++;
            }
            ans = Math.max(ans, expand(s, l - 1, r)); // 刪除 s[l]，繼續擴展
            ans = Math.max(ans, expand(s, l, r + 1)); // 刪除 s[r]，繼續擴展
        }

        return Math.min(ans, n);
    }

    private int expand(char[] s, int l, int r) {
        while (l >= 0 && r < s.length && s[l] == s[r]) {
            l--;
            r++;
        }
        return r - l - 1; // [l+1, r-1] 是回文串
    }


    // https://leetcode.cn/problems/maximum-subarray-xor-with-bounded-range/solutions/3903069/hua-dong-chuang-kou-dan-diao-dui-lie-qia-5sz6/
    public int maxXor(int[] nums, int k) {
        int n = nums.length;
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] ^ nums[i];
        }

        Trie t = new Trie();
        ArrayDeque<Integer> minQ = new ArrayDeque<>();
        ArrayDeque<Integer> maxQ = new ArrayDeque<>();
        int ans = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            // 1. 入
            t.put(pre[right]);

            int x = nums[right];
            while (!minQ.isEmpty() && x <= nums[minQ.peekLast()]) {
                minQ.pollLast();
            }
            minQ.addLast(right);

            while (!maxQ.isEmpty() && x >= nums[maxQ.peekLast()]) {
                maxQ.pollLast();
            }
            maxQ.addLast(right);

            // 2. 出
            while (nums[maxQ.peekFirst()] - nums[minQ.peekFirst()] > k) {
                t.del(pre[left]);
                left++;
                if (minQ.peekFirst() < left) {
                    minQ.pollFirst();
                }
                if (maxQ.peekFirst() < left) {
                    maxQ.pollFirst();
                }
            }

            // 3. 更新答案
            ans = Math.max(ans, t.maxXor(pre[right + 1]));
        }

        return ans;
    }


    class Trie {
        private static final int WIDTH = 15; // nums[i] 二進制長度的最大值

        private static class Node {
            Node[] son = new Node[2];
            int leaf; // 子樹葉子個數
        }

        private final Node root = new Node();

        public void put(int val) {
            Node cur = root;
            for (int i = WIDTH - 1; i >= 0; i--) {
                int bit = val >> i & 1;
                if (cur.son[bit] == null) {
                    cur.son[bit] = new Node();
                }
                cur = cur.son[bit];
                cur.leaf++;
            }
        }

        public void del(int val) {
            Node cur = root;
            for (int i = WIDTH - 1; i >= 0; i--) {
                int bit = val >> i & 1;
                cur = cur.son[bit];
                cur.leaf--; // 如果減成 0 了，說明子樹是空的，可以理解成 cur is None
            }
        }

        public int maxXor(int val) {
            Node cur = root;
            int ans = 0;
            for (int i = WIDTH - 1; i >= 0; i--) {
                int bit = val >> i & 1;
                if (cur.son[bit ^ 1] != null && cur.son[bit ^ 1].leaf > 0) {
                    ans |= 1 << i;
                    bit ^= 1;
                }
                cur = cur.son[bit];
            }
            return ans;
        }
    }


}









