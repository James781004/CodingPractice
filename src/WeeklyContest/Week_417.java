package WeeklyContest;

import java.util.HashMap;

public class Week_417 {

    // https://leetcode.cn/problems/find-the-k-th-character-in-string-game-i/solutions/2934318/mo-ni-by-tsreaper-i1lr/
    public char kthCharacter(int k) {
        StringBuilder s = new StringBuilder("a");
        while (s.length() < k) {
            for (char c : s.toString().toCharArray()) {
                s.append((char) ((c - 'a' + 1) % 26 + 'a'));
            }
        }
        return s.charAt(k - 1);
    }


    // https://leetcode.cn/problems/count-of-substrings-containing-every-vowel-and-k-consonants-ii/solutions/2934309/liang-ci-hua-chuang-pythonjavacgo-by-end-2lpz/
    public long countOfSubstrings(String word, int k) {
        char[] s = word.toCharArray();
        return f(s, k) - f(s, k + 1);
    }

    private long f(char[] word, int k) {
        long ans = 0;
        // 這裡用哈希表實現，替換成數組會更快
        HashMap<Character, Integer> cnt1 = new HashMap<>(); // 元音
        int cnt2 = 0; // 輔音
        int left = 0;
        for (char b : word) {
            if ("aeiou".indexOf(b) >= 0) {
                cnt1.merge(b, 1, Integer::sum); // ++cnt1[b]
            } else {
                cnt2++;
            }
            while (cnt1.size() == 5 && cnt2 >= k) {
                char out = word[left];
                if ("aeiou".indexOf(out) >= 0) {
                    if (cnt1.merge(out, -1, Integer::sum) == 0) { // --cnt1[out] == 0
                        cnt1.remove(out);
                    }
                } else {
                    cnt2--;
                }
                left++;
            }
            ans += left;
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-the-k-th-character-in-string-game-ii/solutions/2934284/liang-chong-zuo-fa-di-gui-die-dai-python-5f6z/
    public char kthCharacter(long k, int[] operations) {
        // n 不需要太大，可以和 k 的二進制長度取最小值
        int n = Math.min(operations.length, 64 - Long.numberOfLeadingZeros(k));
        return f(k, operations, n - 1);
    }

    private char f(long k, int[] operations, int i) {
        if (i < 0) {
            return 'a';
        }
        int op = operations[i];
        if (k <= (1L << i)) { // k 在左半邊
            return f(k, operations, i - 1);
        }
        // k 在右半邊
        char ans = f(k - (1L << i), operations, i - 1);
        return (char) ('a' + (ans - 'a' + op) % 26);
    }


}


