package EndlessCheng.GenreMenu.BinarySearch.Minimum;

import java.util.Arrays;

public class FindRadius {

    // https://leetcode.cn/problems/heaters/solutions/1166982/gong-shui-san-xie-er-fen-shuang-zhi-zhen-mys4/
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);
        int l = 0, r = (int) 1e9;
        while (l < r) {
            int mid = l + r >> 1;
            if (check(houses, heaters, mid)) r = mid;
            else l = mid + 1;
        }
        return r;
    }

    // 使用 i 指向當前處理到的 houses[i]
    // j 指向 可能 覆蓋到 houses[i] 的最小下標 heaters[j]
    // x 代表當前需要 check 的半徑
    boolean check(int[] houses, int[] heaters, int x) {
        int n = houses.length, m = heaters.length;
        for (int i = 0, j = 0; i < n; i++) {

            // heaters[j]+x < houses[i] 時，houses[i] 必然不能被 heaters[j] 所覆蓋，此時讓 j 自增
            while (j < m && houses[i] > heaters[j] + x) j++;

            // 檢查 heaters[j] − x <= houses[i] <= heaters[j] + x 是否滿足，即可知道 houses[i] 的覆蓋情況
            if (j < m && heaters[j] - x <= houses[i] && houses[i] <= heaters[j] + x) continue;
            return false;
        }
        return true;
    }


}
