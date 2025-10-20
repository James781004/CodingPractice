package EndlessCheng.GenreMenu.Backtracking.BinaryTree.BuildTree;

import EndlessCheng.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class CreateBinaryTree {

    // https://leetcode.cn/problems/create-binary-tree-from-descriptions/solutions/1314483/jian-tu-dfs-by-endlesscheng-vl17/
    public TreeNode createBinaryTree(int[][] descriptions) {
        Map<Integer, TreeNode> nodes = new HashMap<>();
        Map<Integer, Boolean> in = new HashMap<>();
        for (int[] d : descriptions) {
            int x = d[0]; // 父
            int y = d[1]; // 子
            int isLeft = d[2]; // 是否左節點
            if (!nodes.containsKey(x)) {
                nodes.put(x, new TreeNode(x));
            }
            if (!nodes.containsKey(y)) {
                nodes.put(y, new TreeNode(y));
            }
            // 設置節點左or右
            if (isLeft == 1) {
                nodes.get(x).left = nodes.get(y);
            } else {
                nodes.get(x).right = nodes.get(y);
            }
            // 記錄子節點的入度（有根指向本子節點）
            in.put(y, true);
        }
        // 入度為 0 的節點，即為根節點
        for (Map.Entry<Integer, TreeNode> entry : nodes.entrySet()) {
            int key = entry.getKey();
            if (!in.containsKey(key)) {
                return entry.getValue();
            }
        }

        //沒有找到根節點，返回 null
        return null;
    }
}
