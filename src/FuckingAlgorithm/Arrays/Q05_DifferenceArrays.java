package FuckingAlgorithm.Arrays;

public class Q05_DifferenceArrays {

    // 差分數組工具類
    class Difference {
        // 差分數組
        private int[] diff;

        /* 輸入一個初始數組，區間操作將在這個數組上進行 */
        public Difference(int[] nums) {
            assert nums.length > 0;
            diff = new int[nums.length];
            // 根據初始數組構造差分數組
            diff[0] = nums[0];
            for (int i = 1; i < nums.length; i++) {
                diff[i] = nums[i] - nums[i - 1];
            }
        }

        /* 給閉區間 [i, j] 增加 val（可以是負數）*/
        public void increment(int i, int j, int val) {
            diff[i] += val;
            if (j + 1 < diff.length) {
                diff[j + 1] -= val;
            }
        }

        /* 返回結果數組 */
        public int[] result() {
            int[] res = new int[diff.length];
            // 根據差分數組構造結果數組
            res[0] = diff[0];
            for (int i = 1; i < diff.length; i++) {
                res[i] = res[i - 1] + diff[i];
            }
            return res;
        }
    }


    // 【LeetCode - 370】區間加法
    // https://blog.csdn.net/qq_29051413/article/details/108277476

    int[] getModifiedArray(int length, int[][] updates) {
        // nums 初始化為全 0
        int[] nums = new int[length];
        // 構造差分解法
        Difference df = new Difference(nums);

        for (int[] update : updates) {
            int i = update[0];
            int j = update[1];
            int val = update[2];
            df.increment(i, j, val);
        }

        return df.result();
    }

    // https://leetcode.cn/problems/corporate-flight-bookings/
    // 1109. 航班預訂統計
    // 這裡有 n 個航班，它們分別從 1 到 n 進行編號。
    // 有一份航班預訂表 bookings ，表中第 i 條預訂記錄 bookings[i] = [firsti, lasti, seatsi] 意味著在從 firsti 到 lasti
    // （包含 firsti 和 lasti ）的 每個航班 上預訂了 seatsi 個座位。
    // 請你返回一個長度為 n 的數組 answer，裡面的元素是每個航班預定的座位總數。

    int[] corpFlightBookings(int[][] bookings, int n) {
        // nums 初始化為全 0
        int[] nums = new int[n];
        // 構造差分解法
        Difference df = new Difference(nums);

        for (int[] booking : bookings) {
            // 注意轉成數組索引要減一
            int i = booking[0] - 1;
            int j = booking[1] - 1;
            int val = booking[2];
            // 對區間 nums[i..j] 增加 val
            df.increment(i, j, val);
        }

        // 返回最終的結果數組
        return df.result();
    }


    // https://leetcode.cn/problems/car-pooling/
//    1094. 拼車
//    車上最初有 capacity 個空座位。車 只能 向一個方向行駛（也就是說，不允許掉頭或改變方向）
//    給定整數 capacity 和一個數組 trips ,  trip[i] = [numPassengersi, fromi, toi] 表示第 i 次旅行有 numPassengersi 乘客，
//    接他們和放他們的位置分別是 fromi 和 toi 。這些位置是從汽車的初始位置向東的公裡數。
//    當且僅當你可以在所有給定的行程中接送所有乘客時，返回 true，否則請返回 false。

    boolean carPooling(int[][] trips, int capacity) {
        // 最多有 1001 個車站
        int[] nums = new int[1001];
        // 構造差分解法
        Difference df = new Difference(nums);

        for (int[] trip : trips) {
            // 乘客數量
            int val = trip[0];
            // 第 trip[1] 站乘客上車
            int i = trip[1];
            // 第 trip[2] 站乘客已經下車，
            // 即乘客在車上的區間是 [trip[1], trip[2] - 1]
            int j = trip[2] - 1;
            // 進行區間操作
            df.increment(i, j, val);
        }

        int[] res = df.result();

        // 客車自始至終都不應該超載
        for (int i = 0; i < res.length; i++) {
            if (capacity < res[i]) {
                return false;
            }
        }
        return true;
    }
}
