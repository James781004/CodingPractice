package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class XorQueries {

    // https://leetcode.cn/problems/xor-queries-of-a-subarray/solutions/330656/zhu-shi-chao-xiang-xi-de-javadai-ma-xiao-bai-ye-ke/
    public int[] xorQueries(int[] arr, int[][] queries) {
        int n = arr.length;
        int[] pre = new int[n + 1];
        //計算每個位置的前綴和 pre[i] 表示前i項的異或和
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] ^ arr[i - 1];
        }
        int[] ans = new int[queries.length];
        int i = 0;
        for (int[] querie : queries) {
            //前面多異或的部分，再重復異或一次就可以抵消了
            //假設 求 [1, 2]，那麼對於 [0, 2] 來說就是多異或了 [0, 0] 這個結果
            //根據 兩個相同值異或結果為 0，那麼我們可以再異或一次 [0, 0] 就將 [0, 0] 給抵消掉了
            //pre[querie[0]]代表了[0, 0]異或 pre[querie[1] + 1]代表了[0, 2]異或 其中[0, 0]異或兩次
            //最後相當於[1, 2]異或
            ans[i++] = pre[querie[0]] ^ pre[querie[1] + 1];
        }
        return ans;
    }

}
