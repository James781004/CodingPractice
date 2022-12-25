package GuChengAlgorithm.Ch01_BasicDataStructure.Trie;

public class Q03_MaximumXORofTwoNumbersInAnArray {
//    https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/
//    421. Maximum XOR of Two Numbers in an Array
//    Given an integer array nums, return the maximum result of nums[i] XOR nums[j], where 0 <= i <= j < n.

    public int findMaximumXOR(int[] nums) {
        int res = Integer.MIN_VALUE;
        TrieNode root = new TrieNode();
        for (int num : nums) root.addNum(root, num);
        for (int num : nums) res = Math.max(res, root.findMaxXor(root, num));
        return res;
    }


    class TrieNode {
        TrieNode[] children;

        public TrieNode() {
            children = new TrieNode[2];
        }

        public void addNum(TrieNode root, int num) {
            TrieNode cur = root;
            for (int i = 31; i >= 0; i--) {
                int curBit = (num >> i) & 1;
                if (cur.children[curBit] == null) cur.children[curBit] = new TrieNode();
                cur = cur.children[curBit];
            }
        }

        public int findMaxXor(TrieNode root, int num) {
            int sum = 0;
            TrieNode cur = root;
            for (int i = 31; i >= 0; i--) {
                int curBit = (num >> i) & 1;
                int otherChoice = curBit == 1 ? 0 : 1; // 也可以用 curBit ^ 1
                if (cur.children[otherChoice] == null) {
                    cur = cur.children[curBit];
                } else {
                    sum += (1 << i);
                    cur = cur.children[otherChoice];
                }
            }
            return sum;
        }
    }
}
