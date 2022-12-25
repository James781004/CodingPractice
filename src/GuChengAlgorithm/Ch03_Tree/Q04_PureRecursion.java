package GuChengAlgorithm.Ch03_Tree;

public class Q04_PureRecursion {
    // https://docs.google.com/presentation/d/1w2BdmMeX927q-18QCaT9VBmJ6WeTC1TDvXViURxdkas/edit#slide=id.gd4ec6171d0_0_80
    public int maxPathSum(TreeNode root) {
        int maxValue = Integer.MIN_VALUE;
        maxPathSum(root, maxValue);
        return maxValue;
    }

    // 原則上有回傳資料的遞歸，就帶有子樹資料傳遞的性質，
    // 可以從子樹獲取之前遞歸累積資料，進行樹形DP
    private int maxPathSum(TreeNode root, int maxValue) {
        if (root == null) return 0;
        int left = Math.max(0, maxPathSum(root.left, maxValue));
        int right = Math.max(0, maxPathSum(root.right, maxValue));
        maxValue = Math.max(maxValue, left + right + root.val);
        return Math.max(left, right) + root.val;
    }


    // https://docs.google.com/presentation/d/1w2BdmMeX927q-18QCaT9VBmJ6WeTC1TDvXViURxdkas/edit#slide=id.gd4ec6171d0_0_60
    public double maxAverageSubTree(TreeNode root) {
        double maxAvg = 0.0;
        sumTree(root, maxAvg);
        return maxAvg;
    }

    // 也可以自行建立一個ReturnType來儲存並傳遞資料
    private int[] sumTree(TreeNode root, double maxAvg) {
        if (root == null) return new int[2];
        int[] res = new int[2]; // {sum, count}
        int[] left = sumTree(root.left, maxAvg);
        int[] right = sumTree(root.right, maxAvg);
        res[0] = left[0] + right[0] + root.val; // sum
        res[1] = left[1] + right[1] + 1;    // count
        maxAvg = Math.max(maxAvg, (double) res[0] / (double) res[1]);
        return res;
    }


    // https://docs.google.com/presentation/d/1w2BdmMeX927q-18QCaT9VBmJ6WeTC1TDvXViURxdkas/edit#slide=id.gd4ec6171d0_0_69
    public int longestZigZag(TreeNode root) {
        int max = 0;
        zigZagHelper(root, max);
        return max;
    }

    private int[] zigZagHelper(TreeNode node, int max) {
        int[] res = new int[2]; // {leftSum, rightSum}
        if (node == null) return res;
        int[] left = zigZagHelper(node.left, max);
        int[] right = zigZagHelper(node.right, max);
        res[0] = left[1] + 1;  // 左子樹的right sum
        res[1] = right[0] + 1; // 右子樹的left sum
        max = Math.max(max, Math.max(res[0], res[1]));
        return res;
    }

    // 也可以用boolean判斷目前node所屬位置
    private int zigZagHelper(TreeNode node, boolean isLeft, int max) {
        if (node == null) return 0;
        int left = zigZagHelper(node.left, true, max);
        int right = zigZagHelper(node.right, false, max);
        max = Math.max(max, Math.max(left, right));
        return isLeft ? 1 + right : 1 + left;
    }


    // https://docs.google.com/presentation/d/1w2BdmMeX927q-18QCaT9VBmJ6WeTC1TDvXViURxdkas/edit#slide=id.gd4ec6171d0_0_112
    public int longestConsecutive(TreeNode root) {
        int maxValue = 0;
        longestPath(root, maxValue);
        return maxValue;
    }

    private int[] longestPath(TreeNode root, int maxValue) {
        if (root == null) return new int[]{0, 0}; // {最長increasing, 最長decreasing}
        int inr = 1, dcr = 1;
        if (root.left != null) {  // 差距剛好是一才符合遞增或遞減條件
            int[] l = longestPath(root.left, maxValue);
            if (root.val == root.left.val + 1) dcr = l[1] + 1;
            else if (root.val == root.left.val - 1) inr = l[0] + 1;
        }

        if (root.right != null) {
            int[] r = longestPath(root.right, maxValue);
            if (root.val == root.right.val + 1) dcr = r[1] + 1;
            else if (root.val == root.right.val - 1) inr = r[0] + 1;  // 因為inr和dcr都包含了當前node，所以必須減去重複的1
        }

        maxValue = Math.max(maxValue, dcr + inr - 1);
        return new int[]{inr, dcr};
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int v) {
            val = v;
        }
    }
}
