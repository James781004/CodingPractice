package GuChengAlgorithm.Ch02_BasicAlgorithm.Greedy;

import java.util.Arrays;

public class Q01_Sort {
    // https://docs.google.com/presentation/d/18M3cuDjvBlaaMjZ2R5a6pK1pDI_ZCDbBmVniyW1PdEE/edit#slide=id.g1c33e7d9828_0_36
    // 從最多unit的箱子開始裝，因為每個箱子空間一樣=1，如果空間不一樣就需要用dp
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        // boxTypes[numOfBoxes][numOfUnitsPerBox]
        // 直接按照numOfUnitsPerBox大到小開始選擇
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
        int i = 0, res = 0;
        while (i < boxTypes.length) {
            while (boxTypes[i][0] > 0 && truckSize > 0) {
                res += boxTypes[i][1];
                boxTypes[i][0]--;
                truckSize--;
            }
            i++;
        }
        return res;
    }
}
