package GuChengAlgorithm.Ch02_BasicAlgorithm.Recursive;

public class Q01_TreeTraversal {
    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_75
    public int search(int[] nums, int target) {
        return binarySearch(nums, 0, nums.length - 1, target);
    }

    private int binarySearch(int[] nums, int start, int end, int target) {
        if (start > end) return -1;
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            return binarySearch(nums, mid + 1, end, target);
        } else {
            return binarySearch(nums, start, mid - 1, target);
        }
    }


    // https://docs.google.com/presentation/d/1kEm-0bFbe7PrDXVj3-fAGI7ugROjYvuUPmJUVlCYSEA/edit#slide=id.g1bbba5d6710_0_82
    int res = 0;

    public int minCameraCover(TreeNode root) {
        return (dfs(root) < 1 ? 1 : 0) + res;
    }

    // 0: An uncovered leaf
    // 1: A covered node with a camera
    // 2: A covered node without a camera
    private int dfs(TreeNode root) {
        if (root == null) return 2;
        int left = dfs(root.left), right = dfs(root.right);
        if (left == 0 || right == 0) {  // leaves are not covered, add a camera on current node
            res++;  // add a camera
            return 1;
        }

        if (left == 1 || right == 1) {  // current node is covered by leaves
            return 2;
        }

        if (left == 2 || right == 2) {  // leaves without cameras are covered, which means current node is uncovered
            return 0;
        }

        return Integer.MAX_VALUE;
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }
}
