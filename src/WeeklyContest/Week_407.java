package WeeklyContest;

public class Week_407 {
    //https://leetcode.cn/problems/number-of-bit-changes-to-make-two-integers-equal/solutions/2851739/o1-wei-yun-suan-zuo-fa-pythonjavacgo-by-3lg19/
    public int minChanges(int n, int k) {
        // &：與運算，兩個位都為1時，結果才為1，否則都是0
        // ^：異或運算，兩個位相同為0，相異為1
        return (n & k) != k ? -1 : Integer.bitCount(n ^ k);
    }


    // https://leetcode.cn/problems/vowels-game-in-a-string/solutions/2851733/nao-jin-ji-zhuan-wan-pythonjavacgo-by-en-4dp0/
    public boolean doesAliceWin(String s) {
        for (char c : s.toCharArray()) {
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/maximum-number-of-operations-to-move-ones-to-the-end/solutions/2851730/du-che-pythonjavacgo-by-endlesscheng-tllv/
    public int maxOperations(String S) {
        char[] s = S.toCharArray();
        int ans = 0;
        int cnt1 = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == '1') { // 優先操作左邊的（能移動的）車，這會制造大量的「堵車」，那麼每輛車的操作次數就會更多
                cnt1++;
            } else if (i > 0 && s[i - 1] == '1') { // 找到了一段道路，可以讓 i 左邊的每輛車都操作一次，把答案增加
                ans += cnt1;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-operations-to-make-array-equal-to-target/solutions/2851722/chai-fen-shu-zu-fen-lei-tao-lun-pythonja-f8lo/
    public long minimumOperations(int[] nums, int[] target) {
        long s = target[0] - nums[0];
        long ans = Math.abs(s);
        for (int i = 1; i < nums.length; i++) {
            int k = (target[i] - target[i - 1]) - (nums[i] - nums[i - 1]);
            if (k > 0) {
                ans += s >= 0 ? k : Math.max(k + s, 0);
            } else {
                ans -= s <= 0 ? k : Math.min(k + s, 0);
            }
            s += k;
        }
        return ans;
    }


}


