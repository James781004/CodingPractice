package EndlessCheng.GenreMenu.DP.Tree.Diameter;

import java.util.ArrayList;
import java.util.List;

public class LongestPath {

    // https://leetcode.cn/problems/longest-path-with-different-adjacent-characters/solutions/1427611/by-endlesscheng-92fw/
    // 與124題非常類似,這類題屬於不過根節點的路徑問題,采用求兩邊最優再進行合並的解法
    // 存儲節點i的孩子節點,i為父節點
    List<Integer>[] children;
    // 節點個數
    int n;
    String s;
    // 根節點編號默認為0
    final int root = 0;
    // 結果
    int res = 0;

    public int longestPath(int[] parent, String _s) {
        // 初始化各類成員變量
        s = _s;
        n = s.length();
        children = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            children[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            // parent數組的意義:索引i->子節點;parent[i]->i的父節點
            // 父節點
            int father = parent[i];
            // father的孩紙節點時此時的索引
            children[father].add(i);
        }
        // 開啟dfs
        dfs(root);
        return res;
    }

    // 返回以編號root為起點符合條件的最長路徑長度
    // 這個以root為起點的路徑只向root的孩子節點方向進行延伸,而不考慮root父節點的方向的路徑*
    // 因為*的情況在執行dfs(root)時已經進行計算,不用重復計算
    private int dfs(int root) {
        // 越過葉子返回0,空節點不組成任何路徑
        if (root >= n) return 0;
        // max1和max2為root子節點為起點的路徑中最長的兩條,這兩部分最後會直接影響res
        int max1 = 0, max2 = 0;
        // 遍歷root的每個葉子節點並求出其符合條件的最長路徑
        for (int child : children[root]) {
            // 當前子節點child的最長合法路徑長度
            int childLen = dfs(child);
            // 更新max1與max2
            if (childLen > max1 && s.charAt(child) != s.charAt(root)) {
                // childLen最大且能與root拼接
                max2 = max1;
                max1 = childLen;
            } else if (childLen > max2 && s.charAt(child) != s.charAt(root)) {
                // childLen次大且能與root拼接
                max2 = childLen;
            }
        }
        // 將經過root的合法路徑長度更新進去res->左邊+root+右邊=經過root的合法路徑長度(root不一定為起點)
        res = Math.max(res, max1 + max2 + 1);
        // 最後返回以root為起點的合法路徑最長長度,顯然為最長的那一條長度max1+root本身長度1
        return max1 + 1;
    }


}
