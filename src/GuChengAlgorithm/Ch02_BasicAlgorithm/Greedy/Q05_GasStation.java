package GuChengAlgorithm.Ch02_BasicAlgorithm.Greedy;

public class Q05_GasStation {
    // https://docs.google.com/presentation/d/18M3cuDjvBlaaMjZ2R5a6pK1pDI_ZCDbBmVniyW1PdEE/edit#slide=id.g1c33e7d9828_0_103
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int gasTank = 0, startIndex = 0, N = gas.length, balance = 0;
        for (int i = 0; i < N; i++) {
            balance += gas[i] - cost[i];  // 總剩餘油量
            gasTank += gas[i] - cost[i];  // 目前剩餘油量
            if (gasTank < 0) {  // 目前剩餘油量沒了，必須考慮其他起點
                startIndex = (i + 1) % N;
                gasTank = 0;
            }
        }

        // 總剩餘油量如果小於零，表示不可能繞完一圈，直接-1
        return balance < 0 ? -1 : startIndex;
    }
}
