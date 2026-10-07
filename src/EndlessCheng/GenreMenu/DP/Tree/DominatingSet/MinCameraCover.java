package EndlessCheng.GenreMenu.DP.Tree.DominatingSet;

import EndlessCheng.GenreMenu.DP.Tree.TreeNode;

public class MinCameraCover {

    // https://leetcode.cn/problems/binary-tree-cameras/solutions/423212/968-jian-kong-er-cha-shu-di-gui-shang-de-zhuang-ta/
    static int ans;

    public int minCameraCover(TreeNode root) {
        ans = 0; // 初始化
        if (f(root) == 0) ans++;
        return ans;
    }

    // 定義 f 函數有三種返回值情況
    // 0：表示 x 節點沒有被相機監控，只能依靠父節點放相機
    // 1：表示 x 節點被相機監控，但相機不是放在自身節點上
    // 2：表示 x 節點被相機監控，但相機放在自身節點上
    public static int f(TreeNode x) {
        if (x == null) return 1; // 空樹認為被監控，但沒有相機
        // 左右遞歸到最深處
        int l = f(x.left);
        int r = f(x.right);
        // 有任意一個子節點為空，就需要當前節點放相機，不然以後沒機會
        if (l == 0 || r == 0) {
            ans++; // 放相機
            return 2;
        }
        // 貪心策略，左右子樹都被監控，且沒有監控到當前節點，
        // 那麼最有利的情況就是將相機放置在當前節點父節點上，
        // 因為這樣能多監控可能的子樹節點和父父節點
        if (l == 1 && r == 1) return 0;
        // 剩下情況就是左右子樹有可能為 2，即當前節點被監控
        return 1;
    }
}
