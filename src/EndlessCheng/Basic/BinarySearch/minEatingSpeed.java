package EndlessCheng.Basic.BinarySearch;

public class minEatingSpeed {
    // https://leetcode.cn/problems/koko-eating-bananas/solutions/2710324/er-fen-da-an-fu-ti-dan-pythonjavacgojsru-eb18/
    public int minEatingSpeed(int[] piles, int h) {
        int left = 0;
        int right = 0;
        for (int p : piles) {
            right = Math.max(right, p);
        }
        while (left + 1 < right) { // 開區間不為空
            int mid = left + (right - left) / 2;
            if (check(mid, piles, h)) {
                right = mid; // 循環不變量：恆為 true
            } else {
                left = mid; // 循環不變量：恆為 false
            }
        }
        return right; // 最小的 true
    }

    private boolean check(int mid, int[] piles, int h) {
        int sum = piles.length;
        for (int pile : piles) {
            sum += (pile - 1) / mid;
            if (sum > h) {
                return false;
            }
        }
        return true;
    }

}
