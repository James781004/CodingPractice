package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.TwoArrays.Pointers;

import java.util.Arrays;

public class BreakfastNumber {

    // https://leetcode.cn/problems/2vYnGI/solutions/411260/er-fen-lcp18zao-can-zu-he-by-buggerking/
    public int breakfastNumber(int[] staple, int[] drinks, int x) {
        Arrays.sort(staple);
        Arrays.sort(drinks);
        int cnt = 0;
        int m = staple.length, n = drinks.length;
        int i = 0, j = n - 1;
        while (i < m && j >= 0) {
            if (staple[i] + drinks[j] <= x) { // staple 從頭開始比對，drinks 從尾開始比對
                // 先比對當前 staple 與當前所有還沒統計過的 drinks 組合，
                // 從 drinks[0] 到 drinks[j] 的所有飲料都可以與 staple[i] 組合，這樣的組合數量為 j + 1。
                // 將這些組合數量加到 cnt 中，並將 i 向右移動一位，比對下一個 staple 與所有剩餘 drinks 組合
                cnt = (cnt + j + 1) % 1000000007;
                i++;
            } else {
                j--; // 嘗試找到更小的 drinks[j] 使得總價格不超過 x
            }
        }
        return cnt % 1000000007;
    }


}
