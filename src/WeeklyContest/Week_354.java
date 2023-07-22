package WeeklyContest;

import java.util.*;

class Week_354 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2778.Sum%20of%20Squares%20of%20Special%20Elements/README.md
    public int sumOfSquares(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 1; i <= n; ++i) {
            if (n % i == 0) {
                ans += nums[i - 1] * nums[i - 1];
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2779.Maximum%20Beauty%20of%20an%20Array%20After%20Applying%20Operation/README.md
    // 差分數組
    public int maximumBeauty(int[] nums, int k) {
        // 需要創建一個長度為 max(nums) + 2k + 2 的差分數組
        int m = Arrays.stream(nums).max().getAsInt() + k * 2 + 2;

        // 通過 d 數組計算出每個位置的前綴和，即為每個位置的美麗值
        int[] d = new int[m];
        for (int x : nums) {
            d[x]++;
            d[x + k * 2 + 1]--; // nums[i] - k 可能為負數，我們統一將所有元素加上 k
        }

        // 找到最大的美麗值
        int ans = 0, s = 0;
        for (int x : d) {
            s += x;
            ans = Math.max(ans, s);
        }
        return ans;
    }


    // https://www.youtube.com/watch?v=ReAcGMO_q-w
    // https://leetcode.cn/problems/maximum-beauty-of-an-array-after-applying-operation/solution/pai-xu-shuang-zhi-zhen-by-endlesscheng-hbqx/
    // 排序+雙指針
    // 由於替換操作替換的是一個連續範圍內的數，所以排序後，選出的子序列必然也是一段連續子數組
    // 那麼問題變成：「找最長的連續子數組，其最大值減最小值不超過 2k」，
    // 只要子數組滿足這個要求，其中的元素都可以變成同一個數。
    // 這個問題可以用 同向雙指針 解決。
    // 枚舉 nums[right] 作為子數組的最後一個數，
    // 一旦 nums[right]−nums[left]>2k，就移動左端點。
    // right−left+1 是子數組的長度，取所有長度最大值，即為答案。
    public int maximumBeauty2(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 0, n = nums.length;
        for (int l = 0, r = 0; r < n; ++r) {
            while (nums[r] > nums[l] + 2 * k) l++;
            ans = Math.max(ans, r - l + 1);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2780.Minimum%20Index%20of%20a%20Valid%20Split/README.md
    public int minimumIndex(List<Integer> nums) {
        // 用哈希表統計每個元素出現的次數，
        // 然後找出出現次數最多的元素x，即為支配元素
        int x = 0, cnt = 0;
        Map<Integer, Integer> freq = new HashMap<>();
        for (int v : nums) {
            int t = freq.merge(v, 1, Integer::sum);
            if (cnt < t) {
                cnt = t;
                x = v;
            }
        }

        // 只需要遍歷數組 nums，累加前綴中 x 的出現次數 cur，
        // 並判斷 x 在後綴中出現的次數是否滿足要求即可
        // 如果滿足要求，那麼當前下標 i 就是一個可行的分割下標，
        // 選擇所有可行分割下標中最小的那個即可
        int cur = 0;
        for (int i = 1; i <= nums.size(); ++i) {
            if (nums.get(i - 1) == x) {
                ++cur;
                if (cur * 2 > i && (cnt - cur) * 2 > nums.size() - i) {
                    return i - 1;
                }
            }
        }
        return -1;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2781.Length%20of%20the%20Longest%20Valid%20Substring/README.md
    // https://leetcode.cn/problems/length-of-the-longest-valid-substring/solution/ha-xi-biao-shuang-zhi-zhen-pythonjavacgo-bcez/
    public int longestValidSubstring(String word, List<String> forbidden) {
        Set<String> s = new HashSet<>(forbidden); // 用哈希表 s 記錄所有禁止的字符串
        int ans = 0, n = word.length();

        // 用雙指針 left 和 right 遍歷字符串 word，
        // 其中 left 和 right 分別表示當前合法子字符串的左右邊界
        // 枚舉子字符串的右端點 right，並從右向左移動指針 i，判斷此時 word[i...right]是否合法
        for (int left = 0, right = 0; right < n; right++) {
            // 注意題目有設定 forbidden[i] 的長度不超過 10
            // 同時指針 i 也不能左移到超過左邊界 left
            for (int i = right; i >= left && i > right - 10; i--) {
                // 當子串 word[i...right] 開始不合法時，合法子串一定不能包含 word[i] (最多就是 word[i+1...right])
                // 所以左邊界 left 接下來必須右移到 i+1 位置，以排除掉 word[i]
                if (s.contains(word.substring(i, right + 1))) {
                    left = i + 1;
                    break;
                }
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}

