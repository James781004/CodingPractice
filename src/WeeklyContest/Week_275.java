package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;

public class Week_275 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2133.Check%20if%20Every%20Row%20and%20Column%20Contains%20All%20Numbers/README.md
    public boolean checkValid(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            boolean[] seen = new boolean[n];
            for (int j = 0; j < n; ++j) {
                int v = matrix[i][j] - 1;
                if (seen[v]) {
                    return false;
                }
                seen[v] = true;
            }
        }
        for (int j = 0; j < n; ++j) {
            boolean[] seen = new boolean[n];
            for (int i = 0; i < n; i++) {
                int v = matrix[i][j] - 1;
                if (seen[v]) {
                    return false;
                }
                seen[v] = true;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/minimum-swaps-to-group-all-1s-together-ii/solution/zui-shao-jiao-huan-ci-shu-lai-zu-he-suo-iaghf/
    public int minSwaps(int[] nums) {
        /*
        滑窗+虛擬環形:
        統計出數組中1的個數為cnt，那麼固定一個cnt的窗口，裡面0的個數最少有多少個，就是我們要交換的最少次數
        因為裡面的1一定是不用動的，而裡面有多少個0就對應外面有多少個0，而這裡的數組是環形的，因此要遍歷兩遍
         */
        int n = nums.length;
        int cnt = 0;    // 存儲1的個數
        for (int num : nums) {
            if (num == 1) cnt++;
        }
        if (cnt == 0 || cnt == n) return 0;

        // 初始化窗口
        int curZero = 0;
        for (int i = 0; i < cnt; i++) {
            if (nums[i] == 0) curZero++;
        }

        int min = curZero;
        // 這裡的i為要進來的元素索引，那麼出窗口的元素索引為i-cnt
        for (int i = cnt; i < 2 * n; i++) {
            if (nums[(i + n - cnt) % n] == 0) curZero--;  // 出窗口元素為0
            if (nums[i % n] == 0) curZero++;    // 進窗口元素為0
            if (curZero < min) min = curZero;   // 更新min
        }
        return min;
    }


    // https://leetcode.cn/problems/count-words-obtained-after-adding-a-letter/solution/ni-xiang-si-wei-wei-yun-suan-ha-xi-biao-l4153/
    // 1. 前期鋪墊：
    // 因為字符數組中的任一字符串中的每個字母最多出現一次
    // 所以我們可以取字符串中每一個字符用其 ascii碼 - 'a' 進行左移位運算最終得到結果 mask ,
    // 不同的字符將體現在 mask 的不同二進制位上。
    // 如果要去掉想去掉某個字符, 只需要用該字符 ascii - 'a' 進行左移位運算得到的結果再與該字符串的 mask 進行異或運算,
    // 那麼代表該字符的位就被置為 0, 剩下的結果就相當於是取掉該字符後得到的 mask。
    // 2. 算法：
    // 1) 計算出 startWords 裡每一個字符串 startWords[i] 的 mask, 並存入 HashSet。
    // 2) 先計算出 targetWords 裡面一個字符串 targetWords[i] 的 mask。
    // 3) 再遍歷 targetWords[i] 的每一個字符 ch, 嘗試用該字符的 mask 異或 1 << (ch - 'a'),
    //    去掉 ch 字符代表的那一個二進位位
    // 4) 如果 HashSet 中包含該字符串, 表示當前字符串 targetWords[i] 可以被轉換, 那麼記錄結果的變量 res++,
    //    並且無需對該字符繼續進判斷, 跳出循環對下一個字符串進行相同的操作。
    // 5) 返回 res。
    public int wordCount(String[] startWords, String[] targetWords) {
        HashSet<Integer> set = new HashSet<>();
        int res = 0;
        for (String word : startWords) {
            int mask = 0;
            for (char ch : word.toCharArray()) {
                mask = mask | (1 << (ch - 'a'));
            }
            set.add(mask);
        }

        for (String word : targetWords) {
            int mask = 0;
            char[] wordArray = word.toCharArray();
            for (char ch : wordArray) {
                mask = mask | (1 << (ch - 'a'));
            }
            for (char ch : wordArray) {
                if (set.contains(mask ^ (1 << (ch - 'a')))) {
                    res++;
                    break;
                }
            }
        }
        return res;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2136.Earliest%20Possible%20Day%20of%20Full%20Bloom/README.md
    // 貪心 + 排序
    // 生長時間越長的種子，越先播種，因此將 growTime 降序排列。
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int n = plantTime.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; ++i) {
            arr[i] = new int[]{plantTime[i], growTime[i]};
        }
        Arrays.sort(arr, (a, b) -> b[1] - a[1]);
        int ans = 0;
        int t = 0;
        for (int[] e : arr) {
            t += e[0];
            ans = Math.max(ans, t + e[1]);
        }
        return ans;
    }

}
