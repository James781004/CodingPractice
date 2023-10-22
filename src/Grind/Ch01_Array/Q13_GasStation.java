package Grind.Ch01_Array;

public class Q13_GasStation {
    // https://leetcode.com/problems/gas-station/
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        // 相當於圖像中的坐標點和最低點
        int sum = 0, minSum = 0;
        int start = 0;
        for (int i = 0; i < n; i++) {
            sum += gas[i] - cost[i];
            if (sum < minSum) {
                // 經過第 i 個站點後，使 sum 到達新低
                // 所以站點 i + 1 就是最低點（起點）
                start = i + 1;
                minSum = sum;
            }
        }
        if (sum < 0) {
            // 總油量小於總的消耗，無解
            return -1;
        }
        // 環形數組特性
        return start == n ? 0 : start;
    }


    // https://leetcode.cn/problems/gas-station/solutions/488622/134-jia-you-zhan-tan-xin-jing-dian-ti-mu-xiang-jie/
    public int canCompleteCircuit1(int[] gas, int[] cost) {
        int curSum = 0;
        int totalSum = 0;
        int index = 0;
        for (int i = 0; i < gas.length; i++) {
            curSum += gas[i] - cost[i]; // 每個加油站的剩余量 rest[i] 為 gas[i] - cost[i]
            totalSum += gas[i] - cost[i];
            if (curSum < 0) {                 // 當前累加rest[i]和 curSum 一旦小於0
                index = (i + 1) % gas.length; // 起始位置更新為i+1
                curSum = 0;                   // curSum從0開始
            }
        }
        if (totalSum < 0) return -1; // 說明怎麼走都不可能跑一圈了
        return index;
    }

    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int sum = 0;
        int min = 0;
        for (int i = 0; i < gas.length; i++) {
            sum += (gas[i] - cost[i]);
            min = Math.min(sum, min);
        }

        if (sum < 0) return -1; // 情況1：如果gas的總和小於cost總和，那麼無論從哪裡出發，一定是跑不了一圈的
        if (min >= 0) return 0; // 情況2：rest[i] = gas[i]-cost[i]為一天剩下的油，i從0開始計算累加到最後一站，如果累加沒有出現負數，說明從0出發，油就沒有斷過，那麼0就是起點。

        // 情況3：如果累加的最小值是負數，汽車就要從非0節點出發，
        // 從後向前，看哪個節點能把這個負數填平，能把這個負數填平的節點就是出發節點。
        for (int i = gas.length - 1; i > 0; i--) {
            min += (gas[i] - cost[i]);
            if (min >= 0) return i;
        }

        return -1;
    }


    // https://leetcode.cn/problems/gas-station/solutions/25644/xiang-xi-tong-su-de-si-lu-fen-xi-duo-jie-fa-by--30/
    public int canCompleteCircuit3(int[] gas, int[] cost) {
        int n = gas.length;
        for (int i = 0; i < n; i++) {
            int j = i;
            int remain = gas[i];
            while (remain - cost[j] >= 0) {
                //減去花費的加上新的點的補給
                remain = remain - cost[j] + gas[(j + 1) % n];
                j = (j + 1) % n;
                //j 回到了 i
                if (j == i) {
                    return i;
                }
            }
            //最遠距離繞到了之前，所以 i 後邊的都不可能繞一圈了
            if (j < i) {
                return -1;
            }
            //i 直接跳到 j，外層 for 循環執行 i++，相當於從 j + 1 開始考慮
            i = j;

        }
        return -1;
    }
}
