package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Q10_DivideAndConquer {
    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g115ee9c9ae0_0_34
    class ValidBST {
        public boolean isValidBST(TreeNode root) {
            return isValidBST(root, null, null);
        }

        private boolean isValidBST(TreeNode root, Integer max, Integer min) {
            if (root == null) return true;
            if (max != null && root.val >= max) return false;
            if (min != null && root.val <= min) return false;
            return isValidBST(root.left, root.val, min) && isValidBST(root.right, max, root.val);
        }
    }


    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g115ee9c9ae0_0_26
    class SearchMatrix {
        public boolean searchMatrix(int[][] matrix, int target) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
            return search(matrix, target, 0, 0, matrix.length - 1, matrix[0].length - 1);
        }

        private boolean search(int[][] matrix, int target, int x1, int y1, int x2, int y2) {
            if (x1 > x2 || y1 > y2) return false;
            int x = (x1 + x2) / 2;
            int y = (y1 + y2) / 2;
            if (matrix[x][y] == target) {
                return true;
            } else if (matrix[x][y] > target) {
                return search(matrix, target, x1, y1, x - 1, y2) || search(matrix, target, x1, x2, x2, y - 1);
            } else {
                return search(matrix, target, x1, y + 1, x2, y2) || search(matrix, target, x + 1, y1, x2, y2);
            }
        }


        public boolean searchMatrix2(int[][] matrix, int target) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;

            // 以左下角為起點
            int row = matrix.length - 1, col = 0;
            while (col < matrix[0].length && row >= 0) {
                int cur = matrix[row][col];
                if (cur == target) {
                    return true;
                } else if (target < cur) {
                    row--;
                } else {
                    col++;
                }
            }
            return false;
        }
    }


    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g110eabfe94d_0_17
    class Pow {
        public double myPow(double x, int n) {
            if (x == 0 || x == 1) return x;
            if (n < 0) return 1 / pow(x, -n);
            return pow(x, n);
        }

        private double pow(double x, int n) {
            if (n == 0) return 1;
            double y = pow(x, n / 2);
            if (n % 2 == 0) return y * y;
            else return y * y * x;
        }
    }


    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g110eabfe94d_1_0
    class FindKthLargest {
        public int findKthLargest(int[] nums, int k) {
            divide(nums, 0, nums.length - 1, k);
            return nums[nums.length - k];
        }

        private void divide(int[] nums, int left, int right, int k) {
            if (left >= right) return;
            int position = partition(nums, left, right);
            if (position == nums.length - k) return;
            else if (position < nums.length - k) divide(nums, position + 1, right, k);
            else divide(nums, left, position - 1, k);
        }

        private int partition(int[] nums, int left, int right) {
            int pivot = nums[right], wall = left;
            for (int i = left; i < right; i++) {
                if (nums[i] < pivot) {
                    swap(nums, i, wall);
                    wall++;
                }
            }
            swap(nums, wall, right);
            return wall;
        }
    }


    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g110eabfe94d_2_1
    class ClosetPoint {
        public int[][] kCloset(int[][] points, int k) {
            PriorityQueue<int[]> pq = new PriorityQueue<>(
                    (p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]);
            for (int[] p : points) {
                pq.offer(p);
                if (pq.size() > k) pq.poll();
            }
            int[][] res = new int[k][2];
            for (int i = 0; i < k; i++) {
                res[i] = pq.poll();
            }
            return res;
        }

        public int[][] kCloset2(int[][] points, int k) {
            int N = points.length, left = 0, right = N - 1;
            while (left <= right) {
                int mid = partition(points, left, right);
                if (mid == k) break;
                if (mid < k) left = mid + 1;
                else right = mid - 1;
            }
            return Arrays.copyOfRange(points, 0, k);
        }

        private int partition(int[][] nums, int left, int right) {
            int[] pivot = nums[right];
            int wall = left;
            for (int i = left; i < right; i++) {
                if (compare(nums[i], pivot) <= 0) {
                    swap(nums, i, wall);
                    wall++;
                }
            }
            swap(nums, wall, right);
            return wall;
        }

        private int compare(int[] p1, int[] p2) {
            return p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1];
        }

        private void swap(int[][] nums, int i, int j) {
            int[] tmp = nums[i];
            nums[i] = nums[j];
            nums[j] = tmp;
        }
    }


    // https://docs.google.com/presentation/d/1w84iTtmlxyNKebIpQzo3suTkXMNGfafii-NsLq0bhuI/edit#slide=id.g115ee9c9ae0_0_4
    class MaxSubArray {
        public int maxSubArray(int[] nums) {
            int N = nums.length, res = Integer.MIN_VALUE, curSum = 0;
            for (int i = 0; i < N; i++) {
                curSum += nums[1];
                res = Math.max(curSum, res);
                if (curSum < 0) curSum = 0;
            }
            return res;
        }

        public int maxSubArray2(int[] nums) {
            return divide(nums, 0, nums.length - 1);
        }

        private int divide(int[] nums, int left, int right) {
            if (left == right) return nums[left];
            int mid = left + (right - left) / 2;
            int leftSum = divide(nums, left, mid);
            int rightSum = divide(nums, mid + 1, right);
            int crossSum = conquer(nums, left, mid, right);
            return Math.max(left, Math.max(right, crossSum));
        }

        private int conquer(int[] nums, int left, int mid, int right) {
            int leftSum = 0, leftMax = Integer.MIN_VALUE;
            for (int i = mid; i >= left; i--) {
                leftSum += nums[i];
                leftMax = Math.max(leftMax, leftSum);
            }

            int rightSum = 0, rightMax = Integer.MIN_VALUE;
            for (int i = mid + 1; i <= right; i++) {
                rightSum += nums[i];
                rightMax = Math.max(rightMax, rightSum);
            }

            return leftMax + rightMax;
        }
    }


    class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int v) {
            val = v;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
