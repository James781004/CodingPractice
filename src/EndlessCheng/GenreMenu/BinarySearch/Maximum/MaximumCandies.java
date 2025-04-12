package EndlessCheng.GenreMenu.BinarySearch.Maximum;

public class MaximumCandies {

    // https://leetcode.cn/problems/maximum-candies-allocated-to-k-children/solutions/1391104/by-endlesscheng-y031/
    public int maximumCandies(int[] candies, long k) {
        long sum = 0;
        for (int x : candies) {
            sum += x;
        }
        long left = 1;
        long right = sum / k;
        while (right >= left) {
            long mid = left + (right - left) / 2;
            long res = 0;
            for (int x : candies) {
                res += x / mid;
                if (res >= k) {
                    break;
                }
            }
            if (res >= k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return (int) right;
    }

}
