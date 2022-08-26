package LeetcodeMaster.Greedy;

public class Q08_GasStation {
//    134. 加油站
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0134.%E5%8A%A0%E6%B2%B9%E7%AB%99.md
//
//    在一條環路上有 N 個加油站，其中第 i 個加油站有汽油 gas[i] 升。
//
//    你有一輛油箱容量無限的的汽車，從第 i 個加油站開往第 i+1 個加油站需要消耗汽油 cost[i] 升。你從其中的一個加油站出發，開始時油箱為空。
//
//    如果你可以繞環路行駛一周，則返回出發時加油站的編號，否則返回 -1。
//
//    說明:
//
//    如果題目有解，該答案即為唯一答案。
//    輸入數組均為非空數組，且長度相同。
//    輸入數組中的元素均為非負數。
//    示例 1: 輸入:
//
//    gas = [1,2,3,4,5]
//    cost = [3,4,5,1,2]
//    輸出: 3 解釋:
//
//    從 3 號加油站(索引為 3 處)出發，可獲得 4 升汽油。此時油箱有 = 0 + 4 = 4 升汽油
//    開往 4 號加油站，此時油箱有 4 - 1 + 5 = 8 升汽油
//    開往 0 號加油站，此時油箱有 8 - 2 + 1 = 7 升汽油
//    開往 1 號加油站，此時油箱有 7 - 3 + 2 = 6 升汽油
//    開往 2 號加油站，此時油箱有 6 - 4 + 3 = 5 升汽油
//    開往 3 號加油站，你需要消耗 5 升汽油，正好足夠你返回到 3 號加油站。
//    因此，3 可為起始索引。
//    示例 2: 輸入:
//
//    gas = [2,3,4]
//
//    cost = [3,4,3]
//
//    輸出: -1
//
//    解釋: 你不能從 0 號或 1 號加油站出發，因為沒有足夠的汽油可以讓你行駛到下一個加油站。我們從 2 號加油站出發，可以獲得 4 升汽油。
//    此時油箱有 = 0 + 4 = 4 升汽油。開往 0 號加油站，此時油箱有 4 - 3 + 2 = 3 升汽油。
//    開往 1 號加油站，此時油箱有 3 - 3 + 3 = 3 升汽油。
//    你無法返回 2 號加油站，因為返程需要消耗 4 升汽油，但是你的油箱只有 3 升汽油。因此，無論怎樣，你都不可能繞環路行駛一周。

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sum = 0;
        int min = 0;  // 從起點出發，油箱里的油量最小值
        for (int i = 0; i < gas.length; i++) {
            sum += (gas[i] - cost[i]);
            min = Math.min(sum, min);
        }

        // 情況一：如果gas的總和小於cost總和，那麽無論從哪出發，一定是跑不了一圈的
        if (sum < 0) return -1;  // 說明怎麽走都不可能跑一圈了

        // 情況二：rest[i] = gas[i]-cost[i]為一天剩下的油，
        // i從0開始計算累加到最後一站，如果累加沒有出現負數，說明從0出發，油就沒有斷過，那麽0就是起點。
        if (min >= 0) return 0;

        // 情況三：如果累加的最小值是負數，汽車就要從非0節點出發，從後向前，
        // 看哪個節點能這個負數填平，能把這個負數填平的節點就是出發節點。
        for (int i = gas.length - 1; i > 0; i--) {
            min += (gas[i] - cost[i]);
            if (min >= 0) return i;
        }

        return -1;
    }

    public int canCompleteCircuit2(int[] gas, int[] cost) {
        int curSum = 0;
        int totalSum = 0;
        int index = 0;
        for (int i = 0; i < gas.length; i++) {
            curSum += gas[i] - cost[i];
            totalSum += gas[i] - cost[i];
            if (curSum < 0) { // 當前累加rest[i]和 curSum一旦小於0
                index = (i + 1) % gas.length; // 起始位置更新為i+1
                curSum = 0; // curSum從0開始
            }
        }
        if (totalSum < 0) return -1;  // 說明怎麽走都不可能跑一圈了
        return index;
    }

}
