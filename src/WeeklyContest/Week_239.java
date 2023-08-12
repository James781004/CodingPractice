package WeeklyContest;

import java.util.Arrays;
import java.util.PriorityQueue;

class Week_239 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1848.Minimum%20Distance%20to%20the%20Target%20Element/README.md
    public int getMinDistance(int[] nums, int target, int start) {
        int n = nums.length;
        int ans = n;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == target) {
                ans = Math.min(ans, Math.abs(i - start));
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1849.Splitting%20a%20String%20Into%20Descending%20Consecutive%20Values/README.md
    private String s;

    public boolean splitString(String s) {
        this.s = s;
        return dfs(0, -1, 0);
    }

    // 從字符串的第一個字符開始，枚舉所有可能的拆分位置，判斷拆分出來的子串是否滿足題目要求，
    // 如果滿足則繼續遞歸判斷剩余的子串是否滿足題目要求，直到遍歷完整個字符串
    private boolean dfs(int i, long x, int k) {
        if (i == s.length()) return k > i; // 代表能組成遞減的連續值
        long y = 0; // 枚舉 x 後面的一個數字的值
        for (int j = 0; j < i; j++) { // 從第 j 個字符開始組成數字
            y = y * 10 + (s.charAt(j) - '0');

            // 如果前面一個數字和當前數組相差為 1 (題目規定)，則繼續往下面尋找滿足條件的數組
            if ((x == -1 || x - y == 1) && dfs(j + 1, y, k + 1)) {
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/minimum-adjacent-swaps-to-reach-the-kth-smallest-number/solutions/755584/javaquan-pai-lie-by-hpq30fv0iv-1c8r/
    public int getMinSwaps(String num, int k) {
        int[] b = new int[num.length()];    // 第k個妙數
        int[] c = new int[num.length()];    // 原始數組
        for (int i = 0; i < num.length(); i++) { // 字符串轉整數數組
            b[i] = num.charAt(i) - '0';
            c[i] = num.charAt(i) - '0';
        }

        // 找出第k個妙數
        while (k-- > 0) // 等價於k-- != 0
            next_permutation(b);
        int ans = 0;    // 記住要交換的次數
        for (int i = 0; i < num.length(); i++) {
            if (c[i] != b[i]) {  // 如果原數組和妙數數組不相同
                int j = i + 1;
                while (b[j] != c[i]) // 先找到於原數組相同的數
                    j++;
                while (j != i) { // 開始交換，使得妙數數組變為原數組
                    swap(b, j - 1, j);  // 因為只能相鄰的數兩兩交換
                    ans++;
                    j--;
                }
            }
        }
        return ans;
    }

    // 求下一個全排列，也就是求下一個妙數
    // LC 31 Next Permutation
    // 從右邊開始，找到第一個正序數 (即小於右邊鄰居的數) nums[i] ，
    // 然後從右邊找第一個大於 num[i] 的數 nums[j]（j > i），
    // 找到之後交換 nums[i] 和 nums[j] ，
    // 最後將 nums[i + 1] 至 nums[nums.length - 1] 之間的數進行反轉
    public void next_permutation(int[] b) {
        for (int i = b.length - 1; i > 0; i--) {
            if (b[i] > b[i - 1]) {   // 找到第一個正序數，b[i-1]處的元素要進行位置調換
                int j = b.length - 1;
                while (b[j] <= b[i - 1]) j--;

                // 從i到j都比nums[i-1]大
                // nums[i-1]和nums[j]先調換位置
                swap(b, i - 1, j);
                // 反轉nums[i-1]之後的所有元素
                j = b.length - 1;
                while (i < j) swap(b, i++, j--);
                break;
            }
        }
    }

    // 交換b數組中第i個和第j個位置的值
    public void swap(int[] b, int i, int j) {
        int t = b[i];
        b[i] = b[j];
        b[j] = t;
    }


    // https://leetcode.cn/problems/minimum-interval-to-include-each-query/solutions/755827/javayou-xian-ji-dui-lie-jie-ti-qian-xian-v4s6/
    public int[] minInterval(int[][] intervals, int[] queries) {
        // 將區間按區間頭從小到大排序
        Arrays.sort(intervals, (o1, o2) -> (o1[0] - o2[0]));

        // 記錄queries以及i，也就是queries[i]和i
        int[][] que = new int[queries.length][2];
        for (int i = 0; i < queries.length; ++i) {
            que[i][0] = queries[i];
            que[i][1] = i;
        }

        // 將值排序，小的在前
        Arrays.sort(que, (o1, o2) -> (o1[0] - o2[0]));
        int[] res = new int[queries.length];
        Arrays.fill(res, -1);

        // 優先級隊列，區間長度小的區間優先，在隊列頭
        PriorityQueue<int[]> queue = new PriorityQueue<int[]>((o1, o2) -> (o1[1] - o1[0] - o2[1] + o2[0]));

        // 記錄第幾個區間，因為intervals和queries都是排好序的，所以用index記錄目前走到哪裡了
        int index = 0;
        for (int i = 0; i < queries.length; ++i) {
            // 先把區間左邊界小於等於queries[i]的區間加進去
            while (index < intervals.length && que[i][0] >= intervals[index][0]) {
                queue.offer(new int[]{intervals[index][0], intervals[index][1]});
                index += 1;
            }

            // 再把區間右邊界小於queries[i]的區間刪除
            while (!queue.isEmpty() && queue.peek()[1] < que[i][0]) {
                queue.poll();
            }
            /*
                為什麼不需要動index？
                正如上面的注釋，intervals和queries都是排好序的
                如果這個區間不合適被刪除了，是因為這個區間是在queries[i]的左面，而之後的queries[i + 1]都比queries[i]大
                所以這個區間在以後肯定也不合適，就刪除了，不再要了
            */
            if (!queue.isEmpty()) {
                int[] t = queue.peek();
                res[que[i][1]] = t[1] - t[0] + 1;
            }
        }
        return res;

    }
}



