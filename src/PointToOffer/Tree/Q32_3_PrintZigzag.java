package PointToOffer.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Q32_3_PrintZigzag {
    public ArrayList<ArrayList<Integer>> PrintZigzag(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> ret = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);
        boolean reverse = false; // Tag to print reverse

        while (!queue.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            int sz = queue.size();

            while (sz-- > 0) {
                TreeNode node = queue.poll();
                if (node == null) continue;
                list.add(node.val);
                queue.add(node.left);
                queue.add(node.right);
            }

            if (reverse) Collections.reverse(list);
            reverse = !reverse;
            if (list.size() != 0) ret.add(list);
        }

        return ret;
    }
}
