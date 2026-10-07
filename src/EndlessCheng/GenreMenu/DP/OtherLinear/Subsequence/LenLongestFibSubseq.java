package EndlessCheng.GenreMenu.DP.OtherLinear.Subsequence;

import java.util.HashMap;
import java.util.Map;

public class LenLongestFibSubseq {

    // https://leetcode.cn/problems/length-of-longest-fibonacci-subsequence/solutions/1656437/by-ac_oier-beo2/
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length, ans = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(arr[i], i);
        int[][] f = new int[n][n]; // f[i][j] 為使用 arr[i] 為斐波那契數列的最後一位，使用 arr[j] 為倒數第二位（即 arr[i] 的前一位）時的最長數列長度
        for (int i = 0; i < n; i++) {
            // 從0到j有j+1個元素，再加上arr[i]就是j+2，如果長度不大於當前最大的長度ans，就沒必要繼續往下找了
            for (int j = i - 1; j >= 0 && j + 2 > ans; j--) {
                // arr[j] 前一位的值為 arr[i]−arr[j]
                // 由於數組嚴格遞增，如果最後兩個數的差值大於等於倒數第二個數，則再往前找肯定不會存在
                if (arr[i] - arr[j] >= arr[j]) break;
                int t = map.getOrDefault(arr[i] - arr[j], -1);
                if (t == -1) continue;

                // arr[i]−arr[j] 值的坐標 t，可以利用 arr 的嚴格單調遞增性質，使用哈希表對坐標進行轉存，
                // 若坐標 t 存在，並且符合 t<j，說明此時至少湊成了長度為 3 的斐波那契數列，
                // 同時結合狀態定義，可以使用 f[j][t] 來更新 f[i][j]
                f[i][j] = Math.max(3, f[j][t] + 1);
                ans = Math.max(ans, f[i][j]);
            }
        }
        return ans;
    }


}
