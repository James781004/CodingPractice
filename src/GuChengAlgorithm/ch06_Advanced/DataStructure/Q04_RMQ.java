package GuChengAlgorithm.ch06_Advanced.DataStructure;

public class Q04_RMQ {

    class SegmentTree {
        SegmentTree left, right;
        int start, end, val;

        public SegmentTree(int start, int end) {
            this.start = start;
            this.end = end;
            setup(this, start, end);
        }

        private void setup(SegmentTree node, int start, int end) {
            if (start == end) return;
            int mid = start + (end - start) / 2;
            if (node.left == null) {
                node.left = new SegmentTree(start, mid);
                node.right = new SegmentTree(mid + 1, end);
            }
            setup(node.left, start, mid);
            setup(node.right, mid + 1, end);
            node.val = Math.max(node.left.val, node.right.val);
        }

        public void update(SegmentTree node, int index, int val) {
            if (index < node.start || index > node.end) return;
            if (node.start == node.end && node.start == index) {
                node.val = val;
                return;
            }
            update(node.left, index, val);
            update(node.right, index, val);
            node.val = Math.max(node.left.val, node.right.val);
        }

        public int rangeMaxQuery(SegmentTree node, int start, int end) {
            if (node.end < start || node.start > end) return 0;
            if (node.start >= start && node.end <= end) return node.val;
            return Math.max(rangeMaxQuery(node.left, start, end), rangeMaxQuery(node.right, start, end));
        }
    }


    class SegmentTree2 {
        int[] nums;
        Node root;

        public SegmentTree2(int[] nums) {
            this.nums = nums;
            this.root = buildTree(nums, 0, nums.length - 1);
        }

        private Node buildTree(int[] nums, int start, int end) {
            if (start > end) return null;
            Node node = new Node(start, end);
            if (start == end) node.sum = nums[start];
            else {
                int mid = start + (end - start) / 2;
                node.left = buildTree(nums, start, mid);
                node.right = buildTree(nums, mid + 1, end);
                node.sum = node.left.sum + node.right.sum;
            }
            return node;
        }

        public int sumRange(Node node, int start, int end) {
            if (node.start == start && node.end == end) return node.sum;
            int mid = node.start + (node.end - node.start) / 2;
            if (end <= mid) return sumRange(node.left, start, end);
            else if (start > mid) return sumRange(node.right, start, end);
            else return sumRange(node.left, start, mid) + sumRange(node.right, mid + 1, end);
        }

        public void update(Node node, int i, int val) {
            if (node.start == node.end) {
                node.sum = val;
                return;
            }
            int mid = node.start + (node.end - node.start) / 2;
            if (i <= mid) update(node.left, i, val);
            else if (i > mid) update(node.right, i, val);
            node.sum = node.left.sum + node.right.sum;
        }


        class Node {
            int start, end, sum;
            Node left, right;

            Node(int start, int end) {
                this.start = start;
                this.end = end;
            }
        }
    }


    // https://docs.google.com/presentation/d/1JYOvW6R6P0uNN-BrDsMqT6rUbS8OfS2CQx0GvFX6xz8/edit#slide=id.g15ac5c81f3f_0_117
    public int lengthOfLIS(int[] nums, int k) {
        SegmentTree root = new SegmentTree(1, 100000);
        int res = 0;
        for (int num : nums) {
            int preMaxLen = root.rangeMaxQuery(root, num - k, num - 1);
            root.update(root, num, preMaxLen + 1);
            res = Math.max(res, preMaxLen + 1);
        }
        return res;
    }

}
