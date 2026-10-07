package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class LongestMountain {

    // https://leetcode.cn/problems/longest-mountain-in-array/solutions/3940490/fen-zu-xun-huan-yi-ci-bian-li-by-brave_c-5f1s/
    public int longestMountain(int[] arr) {
        int ret = 0;
        int n = arr.length;
        int i = 0;

        // 改用 while 循環，自己控制 i 的步長
        while (i < n) {
            int start = i;

            // 1. 找峰頂
            while (i + 1 < n && arr[i] < arr[i + 1]) {
                i++;
            }
            int top = i;

            // 2. 找山底
            while (top > start && i + 1 < n && arr[i] > arr[i + 1]) {
                i++;
            }

            // 3. 驗證並更新結果
            if (top > start && i > top) {
                ret = Math.max(ret, i + 1 - start);
            }

            // 4. 控制指針前進：
            // 如果這一輪走完，i 完全沒動（比如一開始就是下坡，或者遇到了平地）
            // 必須強制推進一步，否則會死循環
            if (start == i) {
                i++;
            }
            // 如果 i 動了（說明走了一段上坡或完整的山脈），
            // i 現在停在山底，正好作為下一輪的 start，所以什麼都不做。
        }
        return ret;
    }

}
