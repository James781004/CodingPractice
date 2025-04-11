package EndlessCheng.Basic.Prefix;

import java.util.ArrayList;
import java.util.List;

public class canMakePaliQueries {

    // https://leetcode.cn/problems/can-make-palindrome-from-substring/solutions/2309725/yi-bu-bu-you-hua-cong-qian-zhui-he-dao-q-yh5p/
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int n = s.length();
        var sum = new int[n + 1][26]; // sum[i+1][j] 表示從 s[0] 到 s[i] 中，字母表的第 j 個小寫字母的出現次數
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i].clone();

            // 由於只關心每種字母出現次數的奇偶性，所以不需要在前綴和中存儲每種字母的出現次數，
            // 只需要保存每種字母出現次數的奇偶性，這邊用異或 1 實現奇偶轉換
            sum[i + 1][s.charAt(i) - 'a'] ^= 1; // 奇數變偶數，偶數變奇數
        }

        var ans = new ArrayList<Boolean>(queries.length); // 預分配空間
        for (var q : queries) {
            int left = q[0], right = q[1], k = q[2], m = 0; // 統計有多少種字母出現奇數次，設為 m

            // 利用前綴和計算出每種字母出現次數
            // 為方便計算，用 0 表示出現偶數次，用 1 表示出現奇數次
            // 異或運算滿足 1 和 0 的結果是 1，而 0 和 0，以及 1 和 1 的結果都是 0，所以可以用異或替換減法
            // 原本減法寫法: m += (sum[right + 1][j] != sum[left][j] ? 1 : 0);
            for (int j = 0; j < 26; j++)
                m += sum[right + 1][j] ^ sum[left][j];
            ans.add(m / 2 <= k); // 如果 (m / 2 <= k)，那麼需要替換字母的次數不大於k，answer[i] 為真，反之為假
        }
        return ans;
    }


}
