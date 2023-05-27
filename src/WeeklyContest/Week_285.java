package WeeklyContest;

public class Week_285 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2210.Count%20Hills%20and%20Valleys%20in%20an%20Array/README.md
    public int countHillValley(int[] nums) {
        int ans = 0;
        for (int i = 1, j = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                continue; // 忽略相鄰重複元素
            }
            if (nums[i] > nums[j] && nums[i] > nums[i + 1]) {
                ++ans;
            }
            if (nums[i] < nums[j] && nums[i] < nums[i + 1]) {
                ++ans;
            }
            j = i;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2211.Count%20Collisions%20on%20a%20Road/README.md
    public int countCollisions(String directions) {
        char[] ds = directions.toCharArray();
        int n = ds.length;
        int l = 0;
        int r = n - 1;

        // 去除前綴為 L 的字符
        while (l < n && ds[l] == 'L') ++l;

        // 去除後綴為 R 的字符
        while (r >= 0 && ds[r] == 'R') --r;

        // 剩餘的字符串中，除了 S 以外的字符，都會貢獻一次碰撞次數。
        int ans = 0;
        for (int i = l; i <= r; i++) {
            if (ds[i] != 'S') ans++;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-points-in-an-archery-competition/solution/er-jin-zhi-mei-ju-by-endlesscheng-rjul/
    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {
        int n = 12;
        int maxScore = 0;
        int[] res = new int[n];
        for (int i = 0; i < 1 << n; i++) { // 即i < 2^12
            // 用i來表示每一次結果，i是一個12位的二進制串，如100000000011表示第0、1、11個區域獲勝，其他區域都失敗
            int score = 0;
            int usedArrows = 0;
            int[] bobArrows = new int[n];
            for (int j = 0; j < 12; j++) {
                if ((i >> j & 1) == 1) { // 若i右移j位後再與1的結果為1，即若i的第j位為1
                    score += j; // 得分
                    usedArrows += aliceArrows[j] + 1; // 使用箭(保證獲勝即可，只多射一支箭)
                    bobArrows[j] = aliceArrows[j] + 1; // 記錄箭使用的區域和數量
                }
            }

            if (usedArrows > numArrows) { // 若已使用的箭超出了箭的總量
                continue;
            }

            if (score > maxScore) {
                maxScore = score;
                bobArrows[0] += (numArrows - usedArrows); // 沒用完的箭隨意放在第一個區域
                res = bobArrows; // 數組複製
            }
        }
        return res;
    }


    // https://leetcode.cn/problems/longest-substring-of-one-repeating-character/solution/by-endlesscheng-qpbw/
    class LongestRepeating {
        char[] s;
        int[] pre, suf, max;

        void maintain(int o, int l, int r) {
            pre[o] = pre[o << 1];
            suf[o] = suf[o << 1 | 1];
            max[o] = Math.max(max[o << 1], max[o << 1 | 1]);
            int m = (l + r) >> 1;
            if (s[m - 1] == s[m]) { // 中間字符相同，可以合並
                if (suf[o << 1] == m - l + 1) pre[o] += pre[o << 1 | 1];
                if (pre[o << 1 | 1] == r - m) suf[o] += suf[o << 1];
                max[o] = Math.max(max[o], suf[o << 1] + pre[o << 1 | 1]);
            }
        }

        void build(int o, int l, int r) {
            if (l == r) {
                pre[o] = suf[o] = max[o] = 1;
                return;
            }
            int m = (l + r) / 2;
            build(o << 1, l, m);
            build(o << 1 | 1, m + 1, r);
            maintain(o, l, r);
        }

        void update(int o, int l, int r, int i) {
            if (l == r) return;
            int m = (l + r) / 2;
            if (i <= m) update(o << 1, l, m, i);
            else update(o << 1 | 1, m + 1, r, i);
            maintain(o, l, r);
        }

        public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
            this.s = s.toCharArray();
            int n = this.s.length, m = queryIndices.length;
            pre = new int[n << 2]; // 前綴最長連續字符個數
            suf = new int[n << 2]; // 後綴最長連續字符個數
            max = new int[n << 2]; // 該區間最長連續字符個數
            build(1, 1, n);
            int[] ans = new int[m];
            for (int i = 0; i < m; ++i) {
                this.s[queryIndices[i]] = queryCharacters.charAt(i);
                update(1, 1, n, queryIndices[i] + 1);
                ans[i] = max[1];
            }
            return ans;
        }
    }
}
