package EndlessCheng.GenreMenu.Backtracking.BinaryTree.TreeDP;

import EndlessCheng.TreeNode;

import java.util.HashMap;

public class CloseLampInTree {

    // https://leetcode.cn/problems/U7WvvU/solutions/1846995/shu-xing-dp-by-endlesscheng-isuo/
    //任意一個節點
    //會受到的影響
    //其祖先節點開關2 的切換次數 偶數 為 不切換 奇數為切換
    //其父節點是否是否切換了開關3
    HashMap<TreeNode, int[][]> map;

    public int closeLampInTree(TreeNode root) {

        map = new HashMap<>();
        return dfs(root, false, false);
    }


    public int dfs(TreeNode node, boolean switch2Stat, boolean switch3Stat) {

        if (node == null) return 0;

        int x = switch2Stat ? 1 : 0;
        int y = switch3Stat ? 1 : 0;
        int[][] val = new int[2][2];
        if (map.containsKey(node)) {

            val = map.get(node);

            if (val[x][y] > 0) return val[x][y];
        } else {

            map.put(node, val);
        }

        int result = 0;
        //燈 開 的情況 , 對於開關 2 和 開關 3 可以相互抵消 , 最終還是 開
        //燈 關 的情況 , 對於開關 2 和 開關 3 可以無法抵消 , 最終還是 開

        if ((node.val == 1) == (switch2Stat == switch3Stat)) {
            //枚舉打開一個開關 或者打開三個開關的情況
            int res1 = dfs(node.left, switch2Stat, false) +
                    dfs(node.right, switch2Stat, false) + 1;
            result = res1;

            int res2 = dfs(node.left, !switch2Stat, false) +
                    dfs(node.right, !switch2Stat, false) + 1;
            result = res2 < result ? res2 : result;

            int res3 = dfs(node.left, switch2Stat, true) +
                    dfs(node.right, switch2Stat, true) + 1;
            result = res3 < result ? res3 : result;

            int res123 = dfs(node.left, !switch2Stat, true) +
                    dfs(node.right, !switch2Stat, true) + 3;
            result = result < res123 ? result : res123;

            val[x][y] = result;
        } else {
            //枚舉一個都不開 或 打開兩個開關
            int res0 = dfs(node.left, switch2Stat, false) +
                    dfs(node.right, switch2Stat, false);
            result = res0;

            int res12 = dfs(node.left, !switch2Stat, false) +
                    dfs(node.right, !switch2Stat, false) + 2;
            result = res12 < result ? res12 : result;

            int res13 = dfs(node.left, switch2Stat, true) +
                    dfs(node.right, switch2Stat, true) + 2;
            result = result < res13 ? result : res13;

            int res23 = dfs(node.left, !switch2Stat, true) +
                    dfs(node.right, !switch2Stat, true) + 2;
            result = result < res23 ? result : res23;

            val[x][y] = result;
        }
        return val[x][y];
    }

}
