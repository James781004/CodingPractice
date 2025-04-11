package EndlessCheng.Basic.Math;

public class minimumBoxes {

    // https://leetcode.cn/problems/building-boxes/solutions/2031813/mei-xiang-ming-bai-yi-ge-dong-hua-miao-d-8vbe/
    public int minimumBoxes(int n) {
        int ans = 0, maxN = 0;
        for (int i = 1; maxN + ans + i <= n; ++i) {
            ans += i;
            maxN += ans;
        }
        for (int j = 1; maxN < n; ++j) {
            ++ans;
            maxN += j;
        }
        return ans;
    }


    public int minimumBoxes2(int n) {
        int x = (int) Math.cbrt(6L * n);
        int ans = x * (x + 1) / 2;
        int maxN = (int) ((long) x * (x + 1) * (x + 2) / 6);
        if (maxN > n) {
            maxN -= ans;
            ans -= x;
        }
        return ans + (int) Math.ceil((-1 + Math.sqrt(1 + 8 * (n - maxN))) / 2);
    }


}
