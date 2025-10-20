package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

import EndlessCheng.TreeNode;

public class Navigation {

    // https://leetcode.cn/problems/hSRGyL/solutions/491184/wei-you-zhao-gui-lu-yao-yao-da-by-simon123-t/
    int res = 0;

    public int navigation(TreeNode root) {
        if (root == null) return 0;
        //根節點單獨處理
        int left = dfs(root.left);
        int right = dfs(root.right);
        //下面這行代碼是多種狀態綜合處理的表現形式，具體如下：
        //左右孩子狀態集(左右交換就不寫了)：
        //[0,0]：左右都沒放導航(包含子樹為空的情況)，那麼要必須放一個導航，即res+1，而且總共放一個導航；
        //[0,1]：一個孩子沒放導航，再和根節點放一起，視為最近的三叉點的一條支路。由於另一個孩子狀態為1，表示該三叉點有兩條支路未放導航，所以還要再增加一個導航，那麼res+1；
        //[0,2]：一個孩子沒放導航，再和根節點放一起，視為最近的三叉點的一條支路。另一個孩子狀態為2，表示該三叉點有兩條支路已放導航，所以不用再增加導航，返回res即可；
        //[1,1]：其中一個孩子和根節點放一起，視為一條支路，那麼就表示該合並後的支路上有導航（只要有就可以了），所以該三叉點的兩條支路有導航，返回res即可；
        //[1,2]：前面同上，該三叉點的三條支路都有導航，返回res即可；
        //[2,2]：直接同上。
        //綜合上面分類情況，左右返回的狀態值相加left+right>=2時，返回res即可，其他情況都要再加一個導航。
        if (left + right >= 2) {
            return res;
        }
        return res + 1;
    }

    public int dfs(TreeNode root) {
        //為空時，表示沒放導航
        if (root == null) return 0;
        int left = dfs(root.left);
        int right = dfs(root.right);
        //由於上面根節點單獨處理，所以這裡只要左右不為空，即為三叉點
        if (root.left != null && root.right != null) {
            //左右子樹都沒放導航，那麼必須選一條支路放一個導航，另一條支路暫時不放，返回狀態1；
            if (left == 0 && right == 0) {
                res += 1;
                return 1;
            }
            //一條支路有導航，另一條支路沒有導航，繼續暫時不放，返回狀態1，要不要加交給上面節點來判斷處理；
            //這裡多說一句，由於該三叉節點一條有導航另一條沒導航，那麼對於之前已經遍歷的下面的三叉點來說，相當於它的父節點支路有導航了，所以當時處理它時，是否有一條支路未放導航就無所謂了；
            if (left == 0 || right == 0) {
                return 1;
            }
            //左右孩子支路都有導航，那麼就返回狀態2
            return 2;
        } else if (root.left == null) {//左孩子為空，該節點狀態等於右孩子，或者說把最近的三叉點狀態往上傳遞
            return right;
        } else if (root.right == null) {//同上理
            return left;
        }

        return 0;
    }


}
