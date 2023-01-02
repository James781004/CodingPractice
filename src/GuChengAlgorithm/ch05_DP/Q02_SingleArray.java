package GuChengAlgorithm.ch05_DP;

import java.util.*;

public class Q02_SingleArray {
    // 序列型動態規劃4步走
    //
    // 狀態：
    // 第i個/前i個位置的答案
    // 前i個位置里，第i個一定選擇的答案（這里我們不管0~i-1是如何拿到的，可以是跳過了其中一些元素）
    //
    // 選擇：
    // 當前位置是否被之前的位置跳過來替換掉，(更大？更小？)
    // 根據題目限制，是否需要隔天才能paint, robber, sell stock, 是否放棄前面的preSum從當前位置重新開始
    //
    // 開始: 初始化前2或1個元素，或者加dummy start index0
    //
    // 結束: 一般是最後一個位置

    public int fibMemo(int N) {
        int[] memo = new int[N + 1];
        return fibHelper(N, memo);
    }

    private int fibHelper(int n, int[] memo) {
        if (n < 2) return n;
        if (memo[n] > 0) return memo[n];
        memo[n] = fibHelper(n - 1, memo) + fibHelper(n - 2, memo);
        return memo[n];
    }

    public int fib(int n) {
        if (n < 2) return n;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }

    public int fib2(int n) {
        if (n < 2) return n;
        int a = 0, b = 1, res = 0;
        for (int i = 2; i <= n; i++) {
            res = a + b;
            a = b;
            b = res;
        }
        return res;
    }

    // math O(1)
    public int fibGolden(int n) {
        double goldenRatio = (1 + Math.sqrt(5)) / 2;
        return (int) Math.round(Math.pow(goldenRatio, n) / Math.sqrt(5));
    }

    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb3ea1f8943_0_58
    public int numDecodings(String s) {
        if (s.length() == 0) return s.length();
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;    // 第0位其實不存在，但為了兩位數字轉換，先初始化為1
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 2; i <= s.length(); i++) {
            int twoDigit = Integer.parseInt(s.substring(i - 2, i));  // substring(i, j)
            int oneDigit = Integer.parseInt(s.substring(i - 1, i));
            if (10 <= twoDigit && twoDigit <= 26) dp[i] += dp[i - 2];  // 初始化 dp[0] = 1 的作用就在這邊
            if (oneDigit != 0) dp[i] += dp[i - 1];
        }
        return dp[s.length()];
    }

    public int numDecodings2(String s) {
        if (s.length() == 0 || s.charAt(0) == '0') return s.length();
        int[] memo = new int[s.length() + 1];
        return decodeHelper(s, 0, memo);
    }

    private int decodeHelper(String s, int i, int[] memo) {
        if (i == s.length()) return 1;
        if (memo[i] > 0) return memo[i];
        if (s.charAt(i) == '0') memo[i] = 0;
        else if (validTwoDigit(s, i)) memo[i] = decodeHelper(s, i + 1, memo) + decodeHelper(s, i + 1, memo);
        else memo[i] = decodeHelper(s, i + 1, memo);
        return memo[i];
    }

    private boolean validTwoDigit(String s, int i) {
        return i + 1 < s.length() && (s.charAt(i) == '1' ||
                (s.charAt(i) == '2' && s.charAt(i + 1) < '7'));
    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb344a15592_0_15
    public boolean wordBreak(String s, List<String> wordDict) {
        int N = s.length();
        boolean[] dp = new boolean[N + 1];
        dp[0] = true;  // 空字串為true
        Set<String> set = new HashSet<>(wordDict);
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < i; j++) {
                // 注意substring(j, i)其實是左閉右開取得s[j...i-1]，
                // 所以實際上dp[i]保存的是s[j...i-1]的結果
                if (dp[j] && set.contains(s.substring(j, i))) dp[i] = true;
            }
        }
        return dp[N];
    }

    public boolean wordBreak2(String s, List<String> wordDict) {
        return wordBreakHelper(s, new HashSet<>(wordDict), 0, new Boolean[s.length()]);
    }

    private boolean wordBreakHelper(String s, HashSet<String> wordDict, int start, Boolean[] memo) {
        if (start == s.length()) return true;
        if (memo[start] != null) return memo[start];
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end)) && wordBreakHelper(s, wordDict, end, memo)) {
                return memo[start] = true;
            }
        }
        return memo[start] = false;
    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb344a15592_1_71
    // 核心都是先用1的方法去判斷，然後暴力展開reconstruct string
    public List<String> wordBreakII(String s, List<String> wordDict) {
        List<String> result = new ArrayList<>();
        if (isWordBreak(s, wordDict)) dfs(s, "", new HashSet<>(wordDict), result);
        return result;
    }

    private void dfs(String s, String pre, HashSet<String> wordDict, List<String> result) {
        if (s.isEmpty()) {
            result.add(pre.trim());
            return;
        }
        for (int i = 1; i <= s.length(); i++) {
            if (wordDict.contains(s.substring(0, i))) {
                dfs(s.substring(i), pre + s.substring(0, i) + " ", wordDict, result);
            }
        }
    }

    private boolean isWordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }


    public List<String> wordBreakII2(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();
        List<Integer>[] dp = new ArrayList[s.length() - 1];  // 保存符合的下標位置
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new ArrayList<>();
        }

        dp[0].add(0);  // 初始化
        Set<String> wordSet = new HashSet<>(wordDict);
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (!dp[j].isEmpty() && wordSet.contains(s.substring(j, i))) {
                    dp[i].add(j);  // 從wordDict裡面找出符合的下標位置並保存(可以從j到i)的有效訊息
                }
            }
        }
        getResult(dp, "", s.length(), s, res);
        return res;
    }

    private void getResult(List<Integer>[] dp, String cur, int index, String str, List<String> res) {
        if (index == 0) {
            res.add(cur.trim());
            return;
        }
        for (int preIndex : dp[index]) {
            getResult(dp, str.substring(preIndex, index) + " " + cur, preIndex, str, res);
        }
    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb344a15592_1_83
    // kadan's algorithm
    public int maxArray(int[] nums) {
        int n = nums.length, maxSum = Integer.MIN_VALUE, maxEnd = 0;
        for (int i = 0; i < n; i++) {
            maxEnd = maxEnd + nums[i];
            maxSum = Math.max(maxSum, maxEnd);
            if (maxEnd < 0) maxEnd = 0;
        }
        return maxSum;
    }

    // Greedy
    public int maxArray2(int[] nums) {
        int sum = 0, res = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum = Math.max(sum + nums[i], nums[i]);
            res = Math.max(res, sum);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb3ea1f8943_0_5
    // 如果當前是正數3，
    // 前面均正(5,2)，應該選max(5)，最大正數15
    // 前面均負(-1,-4)，也應該選max(-1)，最大負數接近0,為-3 
    // 負數條件則相反
    public int maxProduct(int[] nums) {
        int N = nums.length, res = nums[0];
        int[] max = new int[N], min = new int[N];
        max[0] = nums[0];
        min[0] = nums[0];
        for (int i = 1; i < N; i++) {
            if (nums[i] > 0) {
                max[i] = Math.max(nums[i], nums[i] * max[i - 1]);
                min[i] = Math.min(nums[i], nums[i] * min[i - 1]);
            } else {
                max[i] = Math.max(nums[i], nums[i] * min[i - 1]);
                min[i] = Math.min(nums[i], nums[i] * max[i - 1]);
            }
            res = Math.max(res, max[i]);
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb3ea1f8943_0_19
    public int rob(int[] num) {
        if (num.length == 0) return 0;
        int[] dp = new int[num.length + 1];
        dp[0] = 0;
        dp[1] = num[0];
        for (int i = 2; i < num.length + 1; i++) {
            dp[i] = Math.max(num[i - 1] + dp[i - 2], dp[i - 1]);
        }
        return dp[num.length];
    }

//    public int rob(int[] num) {
//        int[][] dp = new int[num.length + 1][2];  // 0不搶, 1搶
//        for (int i = 1; i <= num.length; i++) {
//            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
//            dp[i - 1][1] = num[i - 1] + dp[i - 1][0];
//        }
//        return Math.max(dp[num.length][0], dp[num.length][1]);
//    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb3ea1f8943_0_30
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        int[][] dp = new int[n][2];
        dp[0][0] = 0;   // 股市剛開，手裡0錢也是0
        dp[0][1] = -prices[0];  // 股市剛開就買入，第一檔股票價-prices[0]
        for (int i = 1; i < n; i++) {
            // 今天手裡沒股票：max(昨天也沒股票, 昨天有股票今天賣了)
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);

            // 今天手裡有股票：max(昨天有股票今天不賣, 昨天沒股票今天買了)
            // 因為題目規定只能交易一次，所以昨天沒股票狀態一定是0元
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[n - 1][0];
    }


    // Greedy
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int min = Integer.MAX_VALUE, profit = 0;
        for (int price : prices) {
            min = Math.min(price, min);
            if (price > min) profit = Math.max(profit, price - min);
        }
        return profit;
    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb344a15592_0_24
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) return 0;
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int res = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) dp[i] = Math.max(dp[j] + 1, dp[i]);
                res = Math.max(res, dp[i]);
            }
        }
        return res;
    }


    public int lengthOfLIS2(int[] nums) {
        int size = 0, n = nums.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            int pos = binarySearch(dp, size, nums[i], nums);
            dp[pos] = nums[i];
            if (pos == size) size++;   // 我們在尾端下標的位置插入新元素，代表LIS加長了
        }

        // binarySearch雖然可能把先前保存的LIS結果打亂，
        // 但我們已經有在每個loop保存可能出現的最大長度，所以return 出現過的最大size即可
        return size;
    }

    private int binarySearch(int[] dp, int size, int target, int[] nums) {
        int left = 0, right = size - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (dp[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return left;
    }


    public int lengthOfLIS3(int[] nums) {
        int[] dp = new int[nums.length];
        int len = 0;  // 尾端下標
        for (int x : nums) {
            int i = Arrays.binarySearch(dp, 0, len, x);  // binary search x in dp[0...len]
            // Java內建方法會把不存在的key位置返回負數，
            // 這時候-(i + 1)就會是我們要的位置
            if (i < 0) i = -(i + 1);
            dp[i] = x;
            if (i == len) len++;  // 我們在尾端下標len的位置插入新元素，代表LIS加長了
        }

        // binarySearch雖然可能把先前保存的LIS結果打亂，
        // 但我們已經有在每個loop保存可能出現的最大長度，所以return 出現過的最大size即可
        return len;
    }


    // https://docs.google.com/presentation/d/1Vrjx-aYW6gIN99ccl5HLWyr1gj6lkSaVOQ-rdJdyuj4/edit#slide=id.gb3ea1f8943_0_40
    // 典型的LIS題目
    // 這里longest “increasing” subsequence
    // 比較的順序不是數字的大小而是在原數組中的順序
    public int minOperations(int[] target, int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < target.length; i++) {
            map.put(target[i], i);  // 紀錄target number下標位置
        }

        int[] nums = new int[arr.length];  // 紀錄target number下標位置在arr數組中出現的下標位置
        for (int i = 0; i < arr.length; i++) {
            nums[i] = map.getOrDefault(arr[i], -1);  // target number不存在於arr數組中就放-1
        }

        // nums的LIS就表示target number按順序出現在arr數組中的狀況
        // target長度減去nums的LIS就是我們需要的總插入次數
        return target.length - lengthOfLIS(nums);
    }

}
