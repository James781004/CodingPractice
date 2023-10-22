package Grind.Ch07_BinaryTree;

import java.util.HashMap;
import java.util.Map;

public class Q15_PathSumIII {
    // https://leetcode.cn/problems/path-sum-iii/solutions/100992/qian-zhui-he-di-gui-hui-su-by-shi-huo-de-xia-tian/
    public int pathSumIII(TreeNode root, int sum) {
        // key是前綴和, value是大小為key的前綴和出現的次數
        Map<Long, Integer> prefixSumCount = new HashMap<>();
        // 前綴和為0的一條路徑
        prefixSumCount.put(0L, 1);
        // 前綴和的遞歸回溯思路
        return recursionPathSum(root, prefixSumCount, sum, 0L);
    }

    /**
     * 前綴和的遞歸回溯思路
     * 從當前節點反推到根節點(反推比較好理解，正向其實也只有一條)，有且僅有一條路徑，因為這是一棵樹
     * 如果此前有和為currSum-target,而當前的和又為currSum,兩者的差就肯定為target了
     * 所以前綴和對於當前路徑來說是唯一的，當前記錄的前綴和，在回溯結束，回到本層時去除，保證其不影響其他分支的結果
     *
     * @param node           樹節點
     * @param prefixSumCount 前綴和Map
     * @param target         目標值
     * @param currSum        當前路徑和
     * @return 滿足題意的解
     */
    private int recursionPathSum(TreeNode node, Map<Long, Integer> prefixSumCount, int target, long currSum) {
        // 1.遞歸終止條件
        if (node == null) {
            return 0;
        }
        // 2.本層要做的事情，記錄路徑數量
        int res = 0;
        // 當前路徑上的和
        currSum += node.val;

        //---核心代碼
        // 看看root到當前節點這條路上是否存在節點前綴和加target為currSum的路徑
        // 當前節點->root節點反推，有且僅有一條路徑，如果此前有和為currSum-target,而當前的和又為currSum,兩者的差就肯定為target了
        // currSum-target相當於找路徑的起點，起點的sum+target=currSum，當前點到起點的距離就是target
        res += prefixSumCount.getOrDefault(currSum - target, 0);
        
        // 更新路徑上當前節點前綴和的個數
        prefixSumCount.put(currSum, prefixSumCount.getOrDefault(currSum, 0) + 1);
        //---核心代碼

        // 3.進入下一層
        res += recursionPathSum(node.left, prefixSumCount, target, currSum);
        res += recursionPathSum(node.right, prefixSumCount, target, currSum);

        // 4.回到本層，恢復狀態，把已經找過的路徑都去掉，去除當前節點的前綴和數量
        prefixSumCount.put(currSum, prefixSumCount.get(currSum) - 1);
        return res;
    }
}
