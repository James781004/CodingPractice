package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BFS;

import EndlessCheng.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;

public class IsCousins {

    // https://leetcode.cn/problems/cousins-in-binary-tree/solutions/2635566/python3javacgotypescript-yi-ti-shuang-ji-lazd/
    public boolean isCousins(TreeNode root, int x, int y) {
        Deque<TreeNode[]> q = new ArrayDeque<>(); // 定義一個隊列 q，隊列中存儲的是節點和其父節點
        q.offer(new TreeNode[]{root, null}); // 初始時，將根節點和空節點放入隊列中
        int d1 = 0, d2 = 0;
        TreeNode p1 = null, p2 = null;
        for (int depth = 0; !q.isEmpty(); ++depth) {
            for (int n = q.size(); n > 0; --n) {
                TreeNode[] t = q.poll();
                TreeNode node = t[0], parent = t[1];

                // 每次從隊列中取出一個節點，如果該節點的值為 x 或 y，則記錄該節點的父節點和深度
                if (node.val == x) {
                    d1 = depth;
                    p1 = parent;
                } else if (node.val == y) {
                    d2 = depth;
                    p2 = parent;
                }

                // 如果該節點的左右子節點不為空，則將左右子節點和該節點放入隊列中
                if (node.left != null) {
                    q.offer(new TreeNode[]{node.left, node});
                }
                if (node.right != null) {
                    q.offer(new TreeNode[]{node.right, node});
                }
            }
        }

        // 所有節點都處理完畢後，如果 x 和 y 的深度相同且父節點不同，則返回 true，否則返回 false
        return p1 != p2 && d1 == d2;
    }


}
