package FuckingAlgorithm.DP;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Q19_HouseRobber {
//    https://www.cnblogs.com/labuladong/p/13940231.html

//    https://leetcode.cn/problems/house-robber/
//    198. 打家劫舍
//    你是一個專業的小偷，計劃偷竊沿街的房屋。每間房內都藏有一定的現金，
//    影響你偷竊的唯一制約因素就是相鄰的房屋裝有相互連通的防盜系統，如果兩間相鄰的房屋在同一晚上被小偷闖入，系統會自動報警。
//
//    給定一個代表每個房屋存放金額的非負整數數組，計算你 不觸動警報裝置的情況下 ，一夜之內能夠偷竊到的最高金額。

    public int robMemo(int[] nums) {
        // 備忘錄
        int[] memo = new int[nums.length];
        Arrays.fill(memo, -1);
        return process(nums, 0, memo);
    }

    private int process(int[] nums, int start, int[] memo) {
        if (start >= nums.length) return 0;
        if (memo[start] != -1) return memo[start];

        // 決策1: 不搶當前家，直接去下家
        // 決策2: 搶當前家，然後去下下家
        memo[start] = Math.max(process(nums, start + 1, memo),
                nums[start] + process(nums, start + 2, memo));
        return memo[start];
    }

    public int robDP(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        int[] dp = new int[length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < length; i++) {
            dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]);
        }
        return dp[length - 1];
    }

    public int robDP2(int[] nums) {
        int pre = 0, cur = 0, tmp;

        // 每次循環，計算“偷到當前房子為止的最大金額”
        for (int i : nums) {
            // 循環開始時，cur 表示 dp[k-1]，pre 表示 dp[k-2]
            // dp[k] = max{ dp[k-1], dp[k-2] + i }
            tmp = Math.max(cur, pre + i);
            pre = cur;
            cur = tmp;
            // 循環結束時，cur 表示 dp[k]，pre 表示 dp[k-1]
        }

        return cur;
    }


    // https://leetcode.cn/problems/house-robber-ii/
//    213. 打家劫舍 II
//    你是一個專業的小偷，計劃偷竊沿街的房屋，每間房內都藏有一定的現金。這個地方所有的房屋都 圍成一圈 ，
//    這意味著第一個房屋和最後一個房屋是緊挨著的。
//    同時，相鄰的房屋裝有相互連通的防盜系統，如果兩間相鄰的房屋在同一晚上被小偷闖入，系統會自動報警 。
//
//    給定一個代表每個房屋存放金額的非負整數數組，計算你 在不觸動警報裝置的情況下 ，今晚能夠偷竊到的最高金額。


    public int robIIMemo(int[] nums) {
        int n = nums.length;
        if (n == 1) return nums[0];

        int[] memo1 = new int[n];
        int[] memo2 = new int[n];
        Arrays.fill(memo1, -1);
        Arrays.fill(memo2, -1);
        // 兩次調用使用兩個不同的備忘錄
        return Math.max(
                process(nums, 0, n - 2, memo1),
                process(nums, 1, n - 1, memo2)
        );
    }

    // 定義：計算閉區間 [start,end] 的最優結果
    private int process(int[] nums, int start, int end, int[] memo) {
        if (start > end) {
            return 0;
        }

        if (memo[start] != -1) {
            return memo[start];
        }
        // 狀態轉移方程
        int res = Math.max(
                process(nums, start + 2, end, memo) + nums[start],
                process(nums, start + 1, end, memo)
        );

        memo[start] = res;
        return res;
    }

    public int robIIDP(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        return Math.max(myRob(Arrays.copyOfRange(nums, 0, nums.length - 1)),
                myRob(Arrays.copyOfRange(nums, 1, nums.length)));
    }

    private int myRob(int[] nums) {
        int pre = 0, cur = 0, tmp;
        for (int num : nums) {
            tmp = cur;
            cur = Math.max(pre + num, cur);
            pre = tmp;
        }
        return cur;
    }


    //    https://leetcode.cn/problems/house-robber-iii/
//    337. 打家劫舍 III
//    小偷又發現了一個新的可行竊的地區。這個地區只有一個入口，我們稱之為 root 。
//
//    除了 root 之外，每棟房子有且只有一個“父“房子與之相連。
//    一番偵察之後，聰明的小偷意識到“這個地方的所有房屋的排列類似於一棵二叉樹”。
//    如果 兩個直接相連的房子在同一天晚上被打劫 ，房屋將自動報警。
//
//    給定二叉樹的 root 。返回 在不觸動警報的情況下 ，小偷能夠盜取的最高金額 。
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    Map<TreeNode, Integer> memo = new HashMap<>();

    public int rob3Memo(TreeNode root) {
        if (root == null) return 0;

        // 利用備忘錄消除重疊子問題
        if (memo.containsKey(root)) return memo.get(root);

        // 搶，然後去下下家
        int do_it = root.val
                + (root.left == null ?
                0 : rob3Memo(root.left.left) + rob3Memo(root.left.right))
                + (root.right == null ?
                0 : rob3Memo(root.right.left) + rob3Memo(root.right.right));
        // 不搶，然後去下家
        int not_do = rob3Memo(root.left) + rob3Memo(root.right);

        int res = Math.max(do_it, not_do);
        memo.put(root, res);
        return res;
    }


    public int rob3TreeDP(TreeNode root) {
        int[] result = robInternal(root);
        return Math.max(result[0], result[1]);
    }

    public int[] robInternal(TreeNode root) {
        if (root == null) return new int[2];
        int[] result = new int[2];

        int[] left = robInternal(root.left);
        int[] right = robInternal(root.right);

        // 每個節點可選擇偷或者不偷兩種狀態，根據題目意思，相連節點不能一起偷
        // 當前節點選擇偷時，那麼兩個孩子節點就不能選擇偷了
        // 當前節點選擇不偷時，兩個孩子節點只需要拿最多的錢出來就行(兩個孩子節點偷不偷沒關系)
        //
        // 使用一個大小為 2 的數組來表示 int[] res = new int[2] 0 代表不偷，1 代表偷
        // 任何一個節點能偷到的最大錢的狀態可以定義為:
        // 1. 當前節點選擇不偷：當前節點能偷到的最大錢數 =
        //    左孩子能偷到的最大錢數 Math.max(left[0], left[1]) + 右孩子能偷到的最大錢數 Math.max(right[0], right[1])
        //
        // 2. 當前節點選擇偷：當前節點能偷到的最大錢數 =
        //    左孩子選擇自己不偷時能得到的錢 + 右孩子選擇不偷時能得到的錢 + 當前節點的錢數 (left[0] + right[0] + root.val)
        result[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        result[1] = left[0] + right[0] + root.val;

        return result;
    }
}
