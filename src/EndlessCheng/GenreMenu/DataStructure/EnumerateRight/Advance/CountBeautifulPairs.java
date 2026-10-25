package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class CountBeautifulPairs {

    // https://leetcode.cn/problems/number-of-beautiful-pairs/solutions/2319695/o10n-fei-bao-li-zuo-fa-by-endlesscheng-36eb/
    public int countBeautifulPairs(int[] nums) {
        int ans = 0;
        int[] cnt = new int[10];
        for (int x : nums) {
            for (int y = 1; y < 10; y++) {
                if (cnt[y] > 0 && gcd(y, x % 10) == 1) {
                    ans += cnt[y];
                }
            }
            while (x >= 10) {
                x /= 10;
            }
            cnt[x]++; // 統計最高位的出現次數
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
