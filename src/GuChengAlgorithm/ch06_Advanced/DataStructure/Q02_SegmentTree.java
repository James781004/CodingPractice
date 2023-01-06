package GuChengAlgorithm.ch06_Advanced.DataStructure;

import java.util.LinkedList;
import java.util.List;

public class Q02_SegmentTree {
    // https://docs.google.com/presentation/d/1ai907zOZSflW9rMmEA9JdjwPwlozxuhPq_q55XO4NHA/edit#slide=id.ga91f7d8fe3_0_77

    class SegmentTree {
        int[] nums;
        Node root;

        public SegmentTree(int[] nums) {
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


        public int sunRange(Node node, int start, int end) {
            if (start > end) return 0;
            if (node.start == start && node.end == end) return node.sum;
            int mid = node.start + (node.end - node.start) / 2;
            if (end <= mid) return sunRange(node.left, start, end);
            else if (start > mid) return sunRange(node.right, start, end);
            else return sunRange(node, start, mid) + sunRange(node, mid + 1, end);
        }
    }

    class Node {
        int start, end, sum;
        Node left, right;

        Node(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }


    // https://docs.google.com/presentation/d/1ai907zOZSflW9rMmEA9JdjwPwlozxuhPq_q55XO4NHA/edit#slide=id.ga91f7d8fe3_0_82
    class NumArray {
        SegmentTree st;

        public NumArray(int[] nums) {
            st = new SegmentTree(nums);
        }

        public void update(int i, int val) {
            st.update(st.root, i, val);
        }

        public int sumRange(int i, int j) {
            return st.sunRange(st.root, i, j);
        }
    }


    class NumArray2 {
        int n;
        int[] st;

        public NumArray2(int[] nums) {
            n = nums.length;
            st = new int[n * 2];
            for (int i = n; i < n * 2; i++) {
                st[i] = nums[i - n];  // 葉節點是nums原始元素
            }
            for (int i = n - 1; i > 0; i--) {
                st[i] = st[2 * i] + st[2 * i + 1];  // parent節點是左右子節點和
            }
        }

        public void update(int i, int val) {
            int diff = val - st[i + n];
            for (i += 0; i > 0; i /= 2) {
                st[i] += diff;
            }
        }

        public int sumRange(int i, int j) {
            int res = 0;
            for (i += n, j += n; i <= j; i /= 2, j /= 2) {
                if (i % 2 == 1) res += st[i++];  // st[i]是右節點
                if (j % 2 == 0) res += st[j--];  // st[j]是左節點
            }
            return res;
        }
    }


    // https://docs.google.com/presentation/d/1ai907zOZSflW9rMmEA9JdjwPwlozxuhPq_q55XO4NHA/edit#slide=id.ga93e54e561_0_2
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new LinkedList<>();
        if (nums.length == 0) return res;
        int N = nums.length;
        for (int i = 0; i < N; i++) {
            nums[i] += 10001;
        }
        int[] exist = new int[20001];  // bucket sort

        SegmentTree st = new SegmentTree(new int[20001]);
        st.update(st.root, nums[N - 1], ++exist[nums[N - 1]]);
        res.add(0);
        for (int i = N - 1; i >= 0; i--) {
            st.update(st.root, nums[i], ++exist[nums[i]]);  // 更新統計新數字
            res.add(0, st.sunRange(st.root, 0, nums[i] - 1));  // 默認0到nums[i] - 1有幾個
        }
        return res;
    }


}
