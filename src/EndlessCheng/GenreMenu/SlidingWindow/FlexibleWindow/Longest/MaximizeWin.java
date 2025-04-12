package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Longest;

public class MaximizeWin {

    // https://leetcode.cn/problems/maximize-win-from-two-segments/solutions/2093246/tong-xiang-shuang-zhi-zhen-ji-lu-di-yi-t-5hlh/
    public int maximizeWin(int[] prizePositions, int k) {
        int n = prizePositions.length;
        if (k * 2 + 1 >= prizePositions[n - 1] - prizePositions[0]) {
            return n; // 所有獎品都可以被覆蓋
        }
        int ans = 0;
        int left = 0;
        int[] mx = new int[n + 1];

        // 枚舉右（第二條線段），同時維護左（第一條線段）能覆蓋的最多獎品個數
        for (int right = 0; right < n; right++) {
            // 第二條線段右端點在 prizePositions[right] 時，最遠（最小）覆蓋的獎品的位置為 prizePositions[left]
            while (prizePositions[right] - prizePositions[left] > k) {
                left++;
            }

            // 如何計算兩條線段可以覆蓋的獎品個數？
            // 第二條線段覆蓋的獎品個數為 right−left+1。
            // 第一條線段覆蓋的獎品個數為線段右端點 ≤ prizePositions[left−1] 時，最多覆蓋的獎品個數，即 mx[left]。
            // 綜上，兩條線段可以覆蓋的獎品個數為 mx[left]+right−left+1
            ans = Math.max(ans, mx[left] + right - left + 1);

            // 定義 mx[right+1] 表示第一條線段右端點 ≤ prizePositions[i] 時，最多可以覆蓋多少個獎品
            // 線段右端點等於 prizePositions[i] 時，可以覆蓋最多的獎品，即 i−left+1。
            // 其中 left 表示右端點覆蓋獎品 prizePositions[i] 時，最左邊的被線段覆蓋的獎品。
            // 線段右端點小於 prizePositions[i] 時，可以覆蓋最多的獎品，
            // 這等價於右端點 ≤prizePositions[i−1] 時，最多可以覆蓋多少個獎品，即 mx[right]。
            // 注：這裡可以說明為什麼狀態要定義成 mx[right+1] 而不是 mx[right]，這可以避免當 i=0 時出現 i−1=−1 這種情況。
            // 二者取最大值，得 mx[right+1]=max(mx[right],i−left+1)
            mx[right + 1] = Math.max(mx[right], right - left + 1);
        }
        return ans;
    }


    public int maximizeWin2(int[] prizePositions, int k) {
        int n = prizePositions.length;
        if (k * 2 + 1 >= prizePositions[n - 1] - prizePositions[0]) {
            return n;
        }
        int ans = 0;
        int mx = 0;
        int left = 0;
        int right = 0;
        for (int mid = 0; mid < n; mid++) {
            // 把 prizePositions[mid] 視作第二條線段的左端點，計算第二條線段可以覆蓋的最大獎品下標
            while (right < n && prizePositions[right] - prizePositions[mid] <= k) {
                right++;
            }
            // 循環結束後，right-1 是第二條線段可以覆蓋的最大獎品下標
            ans = Math.max(ans, mx + right - mid);
            // 把 prizePositions[mid] 視作第一條線段的右端點，計算第一條線段可以覆蓋的最小獎品下標
            while (prizePositions[mid] - prizePositions[left] > k) {
                left++;
            }
            // 循環結束後，left 是第一條線段可以覆蓋的最小獎品下標
            mx = Math.max(mx, mid - left + 1);
        }
        return ans;
    }


}
