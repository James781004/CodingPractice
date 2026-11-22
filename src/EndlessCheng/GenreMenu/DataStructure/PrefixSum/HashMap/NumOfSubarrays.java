package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

public class NumOfSubarrays {

    // https://leetcode.cn/problems/number-of-sub-arrays-with-odd-sum/solutions/4053909/qian-zhui-he-wei-hu-ou-shu-qi-shu-ge-shu-hcpd/
    public int numOfSubarrays(int[] arr) {
        final int MOD = 1_000_000_007;
        int[] cnt = {1, 0}; // s[0] = 0 出現一次
        int s = 0;
        long ans = 0;
        for (int x : arr) {
            s = (s + x) & 1; // &1 比 %2 快
            ans += cnt[s ^ 1];
            cnt[s]++;
        }
        return (int) (ans % MOD);
    }

}
