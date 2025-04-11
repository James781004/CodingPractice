package EndlessCheng.Basic.Greedy;

public class minimumSwap {

    // https://leetcode.cn/problems/minimum-swaps-to-make-strings-equal/solutions/2131832/mei-xiang-ming-bai-yi-zhang-tu-miao-dong-a6r1/
    public int minimumSwap(String s1, String s2) {
        int[] cnt = new int[2];
        for (int i = 0, n = s1.length(); i < n; i++)
            if (s1.charAt(i) != s2.charAt(i))
                cnt[s1.charAt(i) % 2]++; // x 和 y ASCII 值的二進制最低位不同
        int d = cnt[0] + cnt[1];
        return d % 2 != 0 ? -1 : d / 2 + cnt[0] % 2;
    }


}
