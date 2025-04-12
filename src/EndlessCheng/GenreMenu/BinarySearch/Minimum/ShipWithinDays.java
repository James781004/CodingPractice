package EndlessCheng.GenreMenu.BinarySearch.Minimum;

import java.util.Arrays;

public class ShipWithinDays {

    // https://leetcode.cn/problems/capacity-to-ship-packages-within-d-days/solutions/744307/java-er-fen-cha-zhao-zhu-shi-ban-qing-xi-7f7b/

    //從數組的最大元素開始遍歷判斷值i是否滿足verification
    public int shipWithinDays(int[] weights, int D) {
        // 二分查找 r = 數組的總和， l = 數組的最大值
        int r = Arrays.stream(weights).sum();
        int l = Arrays.stream(weights).max().getAsInt();

        while (l < r) {
            // 取中間值
            int mid = (l + r) >> 1;
            // 如果mid滿足verification，則逼近右指針，使mid時更小，而運載所需天數增加
            if (verification(weights, D, mid)) {
                // 包括mid
                r = mid;
            } else {
                // 逼近左指針，mid + 1
                l = mid + 1;
            }
        }
        // 返回當前l就是最小的滿足條件的值，即最低運載能力
        return l;
    }

    // 判斷最低運載能力為H的時候能否在D天內送達
    public boolean verification(int[] weights, int D, int H) {
        // 天數計數，初始化為1
        int count = 1;
        // 每天的包裹總量
        int singleWeight = 0;
        for (int i = 0; i < weights.length; ++i) {
            // 累計包裹總量
            singleWeight += weights[i];
            // 如果累計包裹總量singleWeight > H，天數+1
            if (singleWeight > H) {
                count++;
                singleWeight = weights[i];
            }
            // 如果當前累計的天數count > D，說明當前H不滿足條件，返回false
            if (count > D) {
                return false;
            }
        }
        // 說明當前H滿足條件，返回true
        return true;
    }

}
