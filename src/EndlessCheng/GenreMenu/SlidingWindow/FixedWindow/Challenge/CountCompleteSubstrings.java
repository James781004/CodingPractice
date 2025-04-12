package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Challenge;

public class CountCompleteSubstrings {

    // https://leetcode.cn/problems/count-complete-substrings/solutions/2551743/bao-li-hua-chuang-mei-ju-chuang-kou-nei-31j5m/
    public int countCompleteSubstrings(String word, int k) {
        int n = word.length();
        int ans = 0;
        for (int i = 0; i < n; ) {
            int st = i;

            // 「相鄰字母相差至多為 2」這個約束把 word 劃分成了多個子串 s
            for (i++; i < n && Math.abs(word.charAt(i) - word.charAt(i - 1)) <= 2; i++) ;

            // 每個子串分別處理
            ans += f(word.substring(st, i), k);
        }
        return ans;
    }

    private int f(String S, int k) {
        char[] s = S.toCharArray();
        int res = 0;
        for (int m = 1; m <= 26 && k * m <= s.length; m++) { // 枚舉有 m 種字符
            int[] cnt = new int[26];
            for (int right = 0; right < s.length; right++) {
                cnt[s[right] - 'a']++;
                int left = right + 1 - k * m;
                if (left >= 0) { // 長度固定為 m⋅k 的滑動窗口，判斷每種字符是否都出現了恰好 k 次
                    boolean ok = true;
                    for (int i = 0; i < 26; i++) {
                        if (cnt[i] > 0 && cnt[i] != k) {
                            ok = false;
                            break;
                        }
                    }
                    if (ok) res++;
                    cnt[s[left] - 'a']--;
                }
            }
        }
        return res;
    }


}
