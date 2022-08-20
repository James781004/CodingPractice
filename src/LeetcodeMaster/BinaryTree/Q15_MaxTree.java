package LeetcodeMaster.BinaryTree;

public class Q15_MaxTree {
//    654.最大二叉樹
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0654.%E6%9C%80%E5%A4%A7%E4%BA%8C%E5%8F%89%E6%A0%91.md
//
//    給定一個不含重覆元素的整數數組。一個以此數組構建的最大二叉樹定義如下：
//
//    二叉樹的根是數組中的最大元素。
//    左子樹是通過數組中最大值左邊部分構造出的最大二叉樹。
//    右子樹是通過數組中最大值右邊部分構造出的最大二叉樹。
//    通過給定的數組構建最大二叉樹，並且輸出這個樹的根節點。


    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructMaximumBinaryTree1(nums, 0, nums.length);
    }

    // 構造樹一般采用的是前序遍歷，因為先構造中間節點，然後遞歸構造左子樹和右子樹。
    private TreeNode constructMaximumBinaryTree1(int[] nums, int leftIndex, int rightIndex) {
        if (rightIndex - leftIndex < 1) return null; // 沒有元素了
        if (rightIndex - leftIndex == 1) return new TreeNode(nums[leftIndex]); // 只有一個元素

        int maxIndex = leftIndex; // 最大值所在位置，初始化先給最左邊下標位置
        int maxValue = nums[maxIndex]; // 最大值
        for (int i = leftIndex + 1; i < rightIndex; i++) {
            if (nums[i] > maxValue) { // 範圍內找尋最大值，並更新maxIndex以及maxValue
                maxValue = nums[i];
                maxIndex = i;
            }
        }

        TreeNode root = new TreeNode(maxValue);

        // 根據maxIndex劃分左右子樹
        // 左閉右開：[left, maxIndex)
        root.left = constructMaximumBinaryTree1(nums, leftIndex, maxIndex);

        // 左閉右開：[maxValueIndex + 1, right)
        root.right = constructMaximumBinaryTree1(nums, maxIndex + 1, rightIndex);
        return root;
    }


}
