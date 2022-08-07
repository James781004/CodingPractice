package PointToOffer.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Q32_1_PrintFromTopToBottom {
    public List<Integer> PrintFromTopToBottom(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> ret = new ArrayList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TreeNode t = queue.poll();
                if (t == null) continue;
                ret.add(t.val);
                queue.add(t.left);
                queue.add(t.right);
            }
        }

        return ret;
    }
}
