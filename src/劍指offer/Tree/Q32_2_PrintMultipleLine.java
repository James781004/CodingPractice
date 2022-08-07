package 劍指offer.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class Q32_2_PrintMultipleLine {


    public List<ArrayList<Integer>> Print(TreeNode pRoot) {
        List<ArrayList<Integer>> ret = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(pRoot);

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
            if (list.size() != 0) ret.add(list);
        }

        return ret;
    }
}
