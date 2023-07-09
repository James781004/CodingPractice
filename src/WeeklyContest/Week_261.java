package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

class Week_261 {
    // https://leetcode.cn/problems/minimum-moves-to-convert-string/
    public int minimumMoves(String s) {
        int ans = 0;

        // 遍歷字符串 s，只要遇到 'X'，指針 i 就直接往後移動三格，並且答案加 1；
        // 否則指針 i 往後移動一格。
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == 'X') {
                ++ans;
                i += 2;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-missing-observations/
    public int[] missingRolls(int[] rolls, int mean, int n) {
        int m = rolls.length;
        int s = (n + m) * mean; // 計算n次骰子值的總和
        for (int v : rolls) {
            s -= v; // 減去現有的 rolls 總值
        }

        // n次骰子值的範圍為[n, n * 6]，檢測s剩餘數值是否在這個範圍內
        if (s > n * 6 || s < n) {
            return new int[0];
        }

        // 平均填充s剩餘數值的平均數，然後把餘數平均分配
        int[] ans = new int[n];
        Arrays.fill(ans, s / n);
        for (int i = 0; i < s % n; ++i) {
            ++ans[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/stone-game-ix/solution/pythonjavajavascriptgoc-bo-yi-fen-xi-by-2024o/
    // 1. 石子按模3區分，原來的大小在同一個余數堆裡沒有區別
    // 2. 模3余0的石子成對出現等於沒出現，因為對方被迫選了模3余0，我們再選模3余0還會讓他面對剛剛的局面
    // 3. 先手拿1，整體的選擇只能為 1 1 2 1 2 1 2 ...
    // 4. 先手拿2，整體的選擇只能為 2 2 1 2 1 2 1 ...
    // 5. 如果沒有模3余0的石子（成對出現了），Alice先手取更少的那邊的石子是必勝態，
    //    會逼對方必須從更少的石子中拿石子，他會先拿光
    // 6. 如果沒有模3余0的石子（成對出現了），且有一堆余1或余2的石子沒有，
    //    那麼Alice要麼在第三回合輸，要麼拿光也沒有出現模3余0，Bob必勝
    // 7. 如果有模3余0的石子（奇數個），由於出現了一個反制的選擇，如果拿更少的石子，
    //    對方拿模3余0的石子會導致自己永遠要選更少的石子而先輸掉游戲，
    //    所以必須拿更多的那一邊，只多一個或兩個還不行，因為那樣Bob總有拿光也沒有出現模3余0的策略，Bob必勝。
    //    只有當有一堆石子更多且多至少3個時候，Alice才有逼對方在這堆石子取到模3余0的策略
    //   （先拿更多的那邊，後面對方拿0我們取這堆，對方拿這堆裡的我們取0）
    //
    // 總結：
    // 偶數個整除3的石子下，Alice的策略為拿1或2更少的那邊，如果1或2裡有一堆沒有，Alice無法獲勝。
    // 奇數個整除3的石子下，Alice的策略為拿1或2更多的那邊，如果更多的那邊不比另一堆多至少3個，Alice無法獲勝。
    public boolean stoneGameIX(int[] stones) {
        int[] cnts = new int[3];
        for (int num : stones) {
            int m = num % 3;
            if (m == 0)
                cnts[m] ^= 1;
            else
                cnts[m]++;
        }
        if (cnts[0] == 0)
            return Math.min(cnts[1], cnts[2]) > 0;
        else
            return Math.abs(cnts[1] - cnts[2]) > 2;
    }


    // https://leetcode.cn/problems/smallest-k-length-subsequence-with-occurrences-of-a-letter/solution/dan-diao-zhan-si-lu-zhu-bu-fen-xi-zhu-sh-g0zs/
    public String smallestSubsequence(String s, int k, char letter, int repetition) {
        int n = s.length();

        // 統計letter出現的數量
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == letter) {
                cnt++;
            }
        }

        // 子字符串中letter的個數
        int p = 0;
        // 準備刪去的字符數
        int toErase = n - k;

        Deque<Character> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            while (toErase > 0 && !stack.isEmpty() && c < stack.peek()) {
                if (stack.peek() == letter) {
                    // 去掉當前這一個，看看後面還夠不夠
                    if (repetition > p - 1 + cnt) {
                        break;
                    }
                    p--;
                }
                stack.pop();
                // 準備刪去的字符數減少一個
                toErase--;
            }

            // 當前字符是letter的狀況，子字符串中letter的個數加一，letter的總數cnt減一
            if (c == letter) {
                p++;
                cnt--;
            }

            // 不管如何，都把這個字符放進去
            stack.push(c);
        }

        // 因為棧的特性，只有頂端出去，緊挨的下一個才可以出去
        // 正序排序 abcddefg，刪不了n-k個
        while (stack.size() > k) {
            if (stack.peek() == letter) {
                p--;
            }
            stack.pop();
        }

        // 如果letter位於後面，上一步可能會被刪除，這一步補上
        // abcdeffg，k=4，abcd
        StringBuilder sb = new StringBuilder();
        // 因為是按正序排的，所以要從後往前補，當某位不是letter時，用letter代替它
        for (int i = k - 1; p < repetition && i >= 0; i--) {
            if (stack.peek() != letter) {
                p++;
            }
            stack.pop();
            sb.append(letter);
        }
        for (char c : stack) {
            sb.append(c);
        }
        return sb.reverse().toString();
    }
}

