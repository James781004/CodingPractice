package WeeklyContest;

import java.util.*;

class Week_249 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1929.Concatenation%20of%20Array/README.md
    public int[] getConcatenation(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n << 1];
        for (int i = 0; i < n << 1; ++i) {
            ans[i] = nums[i % n];
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1930.Unique%20Length-3%20Palindromic%20Subsequences/README.md
    public int countPalindromicSubsequence(String s) {
        int ans = 0;

        // 字符串中只包含小寫字母，因此可以直接枚舉所有的兩端字符。
        // 對於每一對兩端字符，找出它們在字符串中第一次和最後一次出現的位置 l 和 r，
        // 如果 r - l > 1，說明找到了滿足條件（即長度為3）的回文序列，
        // 將 [l+1...r-1] 之間的字符去重後統計個數，即為以 c 為兩端字符的回文序列個數，加入答案中。
        for (char c = 'a'; c <= 'z'; c++) {
            int l = s.indexOf(c), r = s.lastIndexOf(c);
            Set<Character> cs = new HashSet<>();
            for (int i = l + 1; i < r; i++) {
                cs.add(s.charAt(i));
            }
            ans += cs.size();
        }
        return ans;
    }


    // https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/solutions/870045/yong-san-chong-bu-tong-yan-se-wei-wang-g-7nb2/
    public int colorTheGrid(int m, int n) {
        final int MOD = 1_000_000_007;
        // key 為有效的 mask 值，value 為 mask 值對應的 3 進制數
        Map<Integer, int[]> valid = new HashMap<>();
        int maskEnd = (int) Math.pow(3, m);

        // 初始化 valid
        for (int mask = 0; mask < maskEnd; ++mask) {
            int[] color = new int[m];
            int mm = mask;
            // 分析三進制值，如果相鄰兩個值相同，則不符合要求，舍棄
            boolean check = true;
            for (int i = 0; i < m; ++i) {
                color[i] = mm % 3;
                if (i > 0 && color[i - 1] == color[i]) check = false;
                mm /= 3;
            }
            if (check) valid.put(mask, color);
        }

        // 預處理，分析所有可能相鄰的二元組，將該信息放置在 map 中
        // key 值為 mask 值，value 為可與該 mask 相鄰的 mask 值列表
        Map<Integer, List<Integer>> adjacent = new HashMap<>();
        for (Integer mask1 : valid.keySet()) {
            List<Integer> list = new ArrayList<>();
            for (Integer mask2 : valid.keySet()) {
                boolean check = true;
                for (int i = 0; i < m; ++i) {
                    if (valid.get(mask1)[i] == valid.get(mask2)[i]) {
                        check = false;
                        break;
                    }
                }
                if (check) list.add(mask2);
            }
            adjacent.put(mask1, list);
        }

        // 由於 dp[i][mask] 僅與 dp[i-1][mask'] 有關，因此，可以使用一維數組實現 dp
        int[] dp = new int[maskEnd];

        // 邊界條件：dp[i][mask], i == 0 時
        for (Integer mask : valid.keySet()) {
            dp[mask] = 1;
        }

        // 動態規劃
        for (int i = 1; i < n; ++i) {
            int[] tmpDp = new int[maskEnd];
            for (Integer mask : valid.keySet()) {
                for (Integer index : adjacent.get(mask)) {
                    tmpDp[mask] += dp[index];
                    tmpDp[mask] %= MOD;
                }
            }
            dp = tmpDp;
        }

        int ans = 0;
        for (Integer mask : valid.keySet()) {
            ans += dp[mask];
            ans %= MOD;
        }
        return ans;
    }


    // https://leetcode.cn/problems/merge-bsts-to-create-single-bst/solutions/870425/fen-xiang-xia-si-kao-guo-cheng-by-time-l-6o84/
    Map<Integer, TreeNode> valueToNode = new HashMap<>();

    public TreeNode canMerge(List<TreeNode> trees) {
        // 合成一棵樹的前提條件
        // 條件一：葉節點不重複
        // 合併操作只會刪掉根節點，無法刪除其他位置的節點。
        // 因此如果葉子節點有重複，必然無法構造出二叉搜索樹。
        Set<Integer> leaves = new HashSet<>();
        for (TreeNode node : trees) {
            if (node.left != null) {
                if (leaves.contains(node.left.val)) {
                    return null;
                }
                leaves.add(node.left.val);
            }
            if (node.right != null) {
                if (leaves.contains(node.right.val)) {
                    return null;
                }
                leaves.add(node.right.val);
            }
        }

        // 構造 node->val 到 node 的映射
        for (TreeNode node : trees) {
            valueToNode.put(node.val, node);
        }

        // 條件二：設 S 為葉子節點的值的集合，則有且僅有一個根節點的值不在 S 內。
        // 當有多個根節點的值不在 S 內時，意味著有多棵樹無法合併到其他樹的葉子節點，則必然無法合成一棵樹。
        Set<Integer> roots = new HashSet<>();
        roots.addAll(valueToNode.keySet());
        roots.removeAll(leaves);
        if (roots.size() != 1) {
            return null;
        }

        // 當所有根節點的值都在 S 內時，意味著有出現了合併的回路
        // 開始遍歷 final_root 代表的樹
        Integer rootValue = roots.iterator().next();
        TreeNode rootNode = valueToNode.get(rootValue);
        dfs(rootNode);
        valueToNode.remove(rootValue);
        if (!valueToNode.isEmpty()) {
            return null;
        }

        // 再做一次中序遍歷，如果中序遍歷是升序，則為二叉搜索樹
        List<Integer> result = new ArrayList<>();
        inOrderTraverse(rootNode, result);
        for (int i = 0; i < result.size() - 1; i++) {
            if (result.get(i) > result.get(i + 1)) {
                return null;
            }
        }
        return rootNode;
    }

    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            // 題目給的節點不超過兩層，所以node雖然看似是終結節點，實際上還是有子樹的
            TreeNode child = valueToNode.get(node.val);
            if (child == null) {
                return;
            }
            node.left = child.left;
            node.right = child.right;
            valueToNode.remove(node.val);
        }
        dfs(node.left);
        dfs(node.right);
    }

    private void inOrderTraverse(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inOrderTraverse(node.left, result);
        result.add(node.val);
        inOrderTraverse(node.right, result);
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}


