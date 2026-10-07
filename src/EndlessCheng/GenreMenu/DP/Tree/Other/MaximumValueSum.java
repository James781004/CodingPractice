package EndlessCheng.GenreMenu.DP.Tree.Other;

import java.util.LinkedList;
import java.util.List;

public class MaximumValueSum {

    // https://leetcode.cn/problems/find-the-maximum-sum-of-node-values/solutions/3684434/kan-wan-shu-xing-dpti-jie-chang-shi-jia-l0qu5/
    public long maximumValueSum(int[] nums, int k, int[][] edges) {
        int n = nums.length;
        List<Integer>[] nextNodeList = new LinkedList[n];//鄰接表
        //內存初始化
        for (int i = 0; i < n; i++)
            nextNodeList[i] = new LinkedList<>();
        //構造鄰接數組
        for (int[] edge : edges) {
            int x = edge[0];
            int y = edge[1];
            nextNodeList[x].add(y);
            nextNodeList[y].add(x);
        }
        long[] res = dfs(0, -1, nextNodeList, nums, k);
        //因為是遞歸調用，入口是一次操作都沒有進行，op=0為偶數 說以是res[0]
        return res[0];
    }

    public long[] dfs(int x, int old, List<Integer>[] nextNodeList, int[] nums, int k) {
        // f0: maxValue[x][0]——op為偶數數次時的最大X子樹的價值
        // f1: maxValue[x][1]——op為奇數次時的最大X子樹的價值
        long f0 = 0;
        long f1 = Long.MIN_VALUE;//初始值為零，op不可能為奇數，特值保證不能成立
        //求除x節點之外的X子樹的最大價值和
        for (int y : nextNodeList[x]) {
            if (y != old) {
                long[] r = dfs(y, x, nextNodeList, nums, k);
                long tmp = Math.max(r[0] + f1, r[1] + f0);
                f0 = Math.max(r[0] + f0, r[1] + f1);
                //注意上一步的f0變化了，所以提前改變了一下
                f1 = tmp;
            }
        }
        return new long[]{
                Math.max(f0 + nums[x], f1 + (nums[x] ^ k)),  //op為偶數
                Math.max(f0 + (nums[x] ^ k), f1 + nums[x])   //op為奇數
        };
    }


}
