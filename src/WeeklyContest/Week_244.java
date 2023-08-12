package WeeklyContest;

import java.util.Arrays;

public class Week_244 {
    // https://leetcode.cn/problems/determine-whether-matrix-can-be-obtained-by-rotation/solutions/815345/mei-ju-si-chong-qing-kuang-jin-xing-bi-j-r3ko/
    public boolean findRotation(int[][] mat, int[][] target) {
        int n = mat.length;
        boolean b1 = true, b2 = true, b3 = true, b4 = true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // 旋轉90度
                if (mat[n - j - 1][i] != target[i][j]) {
                    b1 = false;
                }
                // 旋轉180度
                if (mat[n - i - 1][n - j - 1] != target[i][j]) {
                    b2 = false;
                }
                // 旋轉270度
                if (mat[j][n - i - 1] != target[i][j]) {
                    b3 = false;
                }
                // 旋轉360度
                if (mat[i][j] != target[i][j]) {
                    b4 = false;
                }
            }
        }
        return b1 || b2 || b3 || b4;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1887.Reduction%20Operations%20to%20Make%20the%20Array%20Elements%20Equal/README.md
    public int reductionOperations(int[] nums) {
        Arrays.sort(nums);
        int ans = 0, cnt = 0; // cnt 表示元素所需的操作次數
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                ++cnt; // 如果當前元素與前一位不相等，表示需要多一次操作
            }
            ans += cnt; // 累加 cnt (先前所需的操作次數累積)
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/solutions/815295/minimum-number-of-flips-by-ikaruga-lu32/
    // 【理解】：男人1 女人0， 男人女人要交替站位，一個男人配一個女人。因此要0101 或者 1010， 不能男同或女同！
    // 如何把 11010這種 男男女男女， 變成正確的順序呢？
    // 操作一： 通過摘下頭 拼接到尾使得男女配對，
    // 操作二：直接把s的第一個男人“變性”，達成匹配的目的。
    // 先求出最壞情況：全部使用操作二變性 所需的次數。
    // 然後看看能不能用操作一優化（減少一些變性次數）
    public int minFlips(String s) {
        int len = s.length();
        String target = "01";   // 01模式
        int cnt = 0;

        // 不考慮操作一，只使用操作二時，將s轉為0101模式需要cnt次（先計算最壞情況下）
        // s長度為偶數時，就是最壞情況，操作一不起作用）
        for (int i = 0; i < len; i++) {
            cnt += s.charAt(i) != target.charAt(i % 2) ? 1 : 0;
        }

        // 只使用操作二將s轉為1010模式需要 len - cnt 次
        // ans結果暫定為 s 只使用操作二達成匹配0101或1010，所需次數中較少的那種
        int ans = Math.min(cnt, len - cnt);

        // 如果s長度為偶數，那麼操作一無法優化（不能通過操作一減少 操作二的次數）
        // 操作一是每次將s的第一個字符摘下來，放在s末尾。 等價於對雙倍s的字符串，做滑動窗口，窗口右移一格。

        // s長度是偶數，如果s第一個字符不匹配01或10模式，那使用操作一將該字符放到末尾，也還是不匹配。
        // 因此，s長度是偶數時： 操作一有做跟沒做是一樣的。
        // 直接返回ans
        if (s.length() % 2 == 0) {
            return ans;
        }

        // ————————else————————————————————> 【s長度是奇數時，可以通過操作一的優化，使得操作二的次數減少】
        // 雙倍s，為滑動窗口做准備。
        s += s;

        // 窗口右移一格， 即執行一次操作一： 摘下索引 i 的字符拼接到窗口s的末尾，
        // for循環：對s從頭開始每個字符做一次操作一，找出其中所需操作二次數最少的結果
        //         （畢竟操作一不限次數，我們隨便用，只要能讓操作二(變性)次數少一點，就行！）
        for (int i = 0; i < len; i++) {
            // i就是要摘下的字符。此時的s：即窗口范圍是[i+1, i+len]; i+len處的字符就是從i處拼接過來的。

            if (s.charAt(i) != target.charAt(i % 2)) {
                // 奇數時，如果要摘的索引i的字符 不匹配01目標，那它放到s末尾後，就能夠匹配了。
                // 從而可以通過這次操作一節省一次操作二。
                // eg: 目標串 010101， 假設s為 11010， 通過操作一摘下的第一個字符為1，和0不匹配，
                //     但他放在s末尾後，就匹配上了。(11010 -> 10101)，
                //     因此，通過操作一即可達成匹配目標。節省了一次操作二直接將'1'轉為'0'
                //  s=11010 要成為交替字符串，可以通過操作一變成10101， 或者通過操作二變成01010
                //  那肯定選擇用操作一，因為目標是使用最少的操作二。
                //  cnt 是只使用操作二使s符合條件所需的次數（最壞情況），
                //  對於當前窗口（當前s），此時我們發現操作一可以替代操作二，因此cnt次數 - 1;
                cnt -= 1;
            }

            // 如果通過操作一把當前窗口的第一個字符拼接到窗口末尾後（窗口右移一格），
            // 發現這個字符即使通過操作一，還是不能在0101/1010的正確位子上，   那說明對這個字符來說，操作一也沒辦法了。
            // 頑固分子。對於這個字符，操作一也沒法優化，  那只能乖乖的執行一次操作二，直接變性。
            if (s.charAt(i + len) != target.charAt((i + len) % 2)) {
                cnt += 1;
            }

            // 一次下來，對於字符i，如果可以僅通過操作一達成匹配，那cnt肯定更小了，那就可以更新結果ans了。
            // 一次下來，對於字符i，如果進行了一次操作一後還不行，還需要一次操作二。那就等於沒有優化。
            ans = Math.min(ans, Math.min(cnt, len - cnt));
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-space-wasted-from-packaging/solutions/815490/zhuang-bao-guo-de-zui-xiao-lang-fei-kong-90lk/
    static final int MOD = (int) 1e9 + 7;
    static final long INF = Long.MAX_VALUE / 2;

    public int minWastedSpace(int[] packages, int[][] boxes) {
        final int n = packages.length, m = boxes.length;
        Arrays.sort(packages);

        // 計算數組 packages 的前綴和
        long[] pre = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            pre[i + 1] = pre[i] + packages[i];
        }
        long ans = INF;
        for (int[] bj : boxes) {
            Arrays.sort(bj);
            if (bj[bj.length - 1] < packages[n - 1]) continue; // 最大bj裝不下，直接跳過
            long cur = 0L;
            int pIdx = 0;  // 裝箱的包裹id
            for (int bjk : bj) {
                if (bjk < packages[pIdx]) continue; // 箱子小不夠裝，直接跳過

                // 找到小於等於bjk的最右側，也即找大於bjk的最左側減一（upperbound）
                int nextIdx = upperBound(packages, pIdx, bjk) - 1;

                long costTotal = pre[nextIdx + 1] - pre[pIdx];
                long total = (long) bjk * (nextIdx - pIdx + 1);
                // System.out.println(total + " " + costTotal);
                // 計算浪費空間
                cur += total - costTotal;
                if (cur >= ans) break; // 答案不會更好
                pIdx = nextIdx + 1; // 下一個狀態
                if (pIdx >= n) break; // 裝完
            }
            ans = Math.min(ans, cur);
        }
        return ans >= INF ? -1 : (int) (ans % MOD);
    }

    private int upperBound(int[] packages, int start, int target) {
        int left = start, right = packages.length - 1;
        int res = right + 1; // 找不到默認可以全部裝入target大的箱子
        while (left <= right) {
            int mid = (right + left) / 2;
            if (packages[mid] > target) {
                res = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return res; // 剪枝一定能找
    }
}
