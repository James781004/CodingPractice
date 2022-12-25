package GuChengAlgorithm.Ch01_BasicDataStructure.DSU;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Q03_LongestConsecutiveSequence {
//    https://leetcode.com/problems/longest-consecutive-sequence/
//    128. Longest Consecutive Sequence
//    Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
//
//    You must write an algorithm that runs in O(n) time.

    public int longestConsecutive(int[] nums) {
        DSU dsu = new DSU(nums.length);
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) continue;
            map.put(nums[i], i);

            // 如果map裡面已經有前後連續的key，就加進同一個集群裡面
            if (map.containsKey(nums[i + 1])) dsu.union(i, map.get(nums[i] + 1));
            if (map.containsKey(nums[i - 1])) dsu.union(i, map.get(nums[i] - 1));
        }

        return dsu.findMax();
    }


    class DSU {
        int[] parent;
        int[] size;

        public DSU(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            Arrays.fill(size, 1);
        }

        public int find(int x) {
            if (parent[x] != x) parent[x] = find(parent[x]);
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return;
            if (size[rootX] <= size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
        }

        public int findMax() {
            int max = 0;
            for (int s : size) max = Math.max(max, s);
            return max;
        }

    }


    public int longestConsecutive2(int[] nums) {
        // 轉化成哈希集合，方便快速查找是否存在某個元素
        HashSet<Integer> set = new HashSet<Integer>();
        for (int num : nums) {
            set.add(num);
        }

        int res = 0;

        for (int num : set) {
            if (set.contains(num - 1)) {
                // num 不是連續子序列的第一個，跳過
                continue;
            }
            // num 是連續子序列的第一個，開始向上計算連續子序列的長度
            int curNum = num;
            int curLen = 1;

            while (set.contains(curNum + 1)) {
                curNum += 1;
                curLen += 1;
            }
            // 更新最長連續序列的長度
            res = Math.max(res, curLen);
        }

        return res;
    }
}
