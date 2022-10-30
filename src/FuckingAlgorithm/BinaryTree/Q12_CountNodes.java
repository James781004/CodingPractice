package FuckingAlgorithm.BinaryTree;

public class Q12_CountNodes {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    //    https://leetcode.cn/problems/count-complete-tree-nodes/
//    222. 完全二叉樹的節點個數
//    給你一棵 完全二叉樹 的根節點 root ，求出該樹的節點個數。
//
//    完全二叉樹 的定義如下：在完全二叉樹中，除了最底層節點可能沒填滿外，其余每層節點數都達到最大值，
//    並且最下面一層的節點都集中在該層最左邊的若干位置。若最底層為第 h 層，則該層包含 1~ 2h 個節點。

    public int countNodes(TreeNode root) {
        TreeNode l = root, r = root;

        // 沿最左側和最右側分別計算高度
        int hl = 0, hr = 0;
        while (l != null) {
            l = l.left;
            hl++;
        }
        while (r != null) {
            r = r.right;
            hr++;
        }

        // 如果左右側計算的高度相同，則是一棵滿二叉樹
        if (hl == hr) {
            return (int) Math.pow(2, hl) - 1;
        }

        // 如果左右側的高度不同，則按照普通二叉樹的邏輯計算
        return 1 + countNodes(root.left) + countNodes(root.right);
    }


    // 完全二叉樹的定義：它是一棵空樹或者它的葉子節點只出在最後兩層，若最後一層不滿則葉子節點只在最左側。
    public int countNodes2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = countLevel(root.left);
        int right = countLevel(root.right);
        if (left == right) {
            // left == right。這說明，左子樹一定是滿二叉樹，因為節點已經填充到右子樹了，左子樹必定已經填滿了。
            // 所以左子樹的節點總數我們可以直接得到，是 2^left - 1，加上當前這個 root 節點，則正好是 2^left。
            // 再對右子樹進行遞歸統計。
            return countNodes2(root.right) + (1 << left);
        } else {
            // left != right。說明此時最後一層不滿，但倒數第二層已經滿了，可以直接得到右子樹的節點個數。
            // 同理，右子樹節點 +root 節點，總數為 2^right。再對左子樹進行遞歸查找。
            return countNodes2(root.left) + (1 << right);
        }
    }

    private int countLevel(TreeNode root) {
        int level = 0;
        while (root != null) {
            level++;
            root = root.left;
        }
        return level;
    }

}
