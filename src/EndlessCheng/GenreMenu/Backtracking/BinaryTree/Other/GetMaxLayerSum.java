package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

public class GetMaxLayerSum {

    // https://leetcode.cn/problems/WInSav/solutions/1711623/by-yu-shu-lin-feng-7-1rl0/
    //1.被擊破的節點泡泡 至多 只有一個子節點泡泡。
    //2.當被擊破的節點泡泡有子節點泡泡時，則子節點泡泡將取代被擊破泡泡的位置。
    //基本思路：
    //1.通過二叉樹的層序遍歷求出每層原始的層和
    //2.在層序遍歷的時候也可以統計那些只有一個子節點的節點，這些節點都是我們可以去擊破的節點。把他用一個隊列存儲起來
    //3.那些記錄下來的可以戳破的節點，就成為了可以去嘗試的優化方案。但是他們在每一層的表現和影響不同。例如示例2中
    //擊破的6，在第二層是6，在第三層的表現就是4，第4層的表現就是3,5.這些表現需要隨著層序遍歷不斷地下層。
    //4.層序遍歷在下層的過程中，也會加入新的優化方案，這些方案和3裡面的一樣都被levelUpGroups記錄下來。
    //5.完成一層的層序遍歷後，我們可以獲取到一層的原始層和。然後遍歷levelUpGroups這個優化方案，最終算出該層的最大可能性。
    //6.完成所有層的遍歷，最終就是結果。
    //性能方面需要注意的是:當一個優化方案和該層級的元素數量一樣的時候，說明他的方案是取消整層。這樣的優化方案是沒有意義的。
    //比如最後一層是-2,-5。我把他全拿掉，那麼就這層就成了0.但是其實本質是一出一層，這一層就不該再被計算。
    int rowSum = 0;

    public int getMaxLayerSum(TreeNode root) {
        int max = root.val;
        Queue<TreeNode> q = new LinkedList<>();
        Queue<Queue<TreeNode>> levelUpGroups = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            int rowSize = q.size();
            rowSum = 0;
            for (int i = 0; i < rowSize; i++) {
                TreeNode node = q.remove();
                //被擊破的泡泡至多之後一個子節點
                if (null == node.left || null == node.right) {
                    Queue<TreeNode> group = new LinkedList<>();
                    group.add(node);
                    levelUpGroups.add(group);
                }
                if (null != node.left)
                    q.add(node.left);
                if (null != node.right)
                    q.add(node.right);
                rowSum += node.val;
            }
            max = Math.max(max, rowSum);
            //計算當層點擊泡泡後的各種方案的最大值
            //一個group中即便有個多個值，也表達的著是這多個值同時被移除的機會，因為有些機會是上層擊破帶來的。
            //levelUpGroups是動態的(遍歷的時候也在刪除)要注意這點

            int levelUpGroupsSize = levelUpGroups.size();
            for (int i = 0; i < levelUpGroupsSize; i++) {
                Queue<TreeNode> group = levelUpGroups.remove();
                int diff = 0;
                int groupSize = group.size();
                for (int j = 0; j < groupSize; j++) {
                    TreeNode pop2Click = group.remove();
                    diff -= pop2Click.val;

                    if (null != pop2Click.left) {
                        group.add(pop2Click.left);
                        diff += pop2Click.left.val;
                    }

                    if (null != pop2Click.right) {
                        group.add(pop2Click.right);
                        diff += pop2Click.right.val;
                    }
                }

                //這句是性能測試能過的關鍵
                //首先要明白一層中的元素數量rowSize是不可能有比他更大的值
                //那麼一個優化方案中的元素個數都和這個層裡面的元素個數相等了，那麼必然表示這個優化方案要提升整層
                //而提升整層是毫無意義的事情。
                if (rowSize != groupSize && 0 != group.size()) {
                    levelUpGroups.add(group);
                }
                if (0 - diff != rowSum)
                    max = Math.max(max, rowSum + diff);
            }
        }
        return max;
    }


}
