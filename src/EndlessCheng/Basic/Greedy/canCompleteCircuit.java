package EndlessCheng.Basic.Greedy;

public class canCompleteCircuit {

    // https://leetcode.cn/problems/gas-station/solutions/2933132/yong-zhe-xian-tu-zhi-guan-li-jie-pythonj-qccr/
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int ans = 0;
        int minS = 0; // 最小油量
        int s = 0; // 油量
        for (int i = 0; i < gas.length; i++) {
            s += gas[i] - cost[i]; // 在 i 處加油，然後從 i 到 i+1
            if (s < minS) {
                minS = s; // 更新最小油量
                ans = i + 1; // 注意 s 減去 c 之後，汽車在 i+1 而不是 i
            }
        }
        // 循環結束後，s 即為 gas 之和減去 cost 之和
        return s < 0 ? -1 : ans;
    }


}
