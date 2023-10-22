package Grind.Ch01_Array;

import java.util.HashMap;
import java.util.Map;

public class Q16_ContiguousArray {
    // https://leetcode.cn/problems/contiguous-array/solutions/201478/qian-zhui-he-chai-fen-ha-xi-biao-java-by-liweiwei1/
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> h = new HashMap<>();
        int pre = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                pre += 1;
            } else {
                pre += -1;
            }
            if (pre == 0) max = Math.max(max, i + 1); // 出現前綴和為 0 的區間，長度為 i+1
            if (!h.containsKey(pre)) h.put(pre, i); // 如果前綴和 pre 之前沒出現過，map 保存前綴和本次出現的位置
            else {
                max = Math.max(max, i - h.get(pre)); // 出現前綴和為 0 的區間，長度為 i 減去前綴和 pre 之前出現位置
            }
        }
        return max;
    }


    // https://leetcode.cn/problems/contiguous-array/solutions/201478/qian-zhui-he-chai-fen-ha-xi-biao-java-by-liweiwei1/
    // 560, 1371 類似思路
    // 連續子區間的問題，可以考慮「前綴和」，前綴和的差是區間和。
    // 題目「包含 0 和 1 的個數相等」，可以把 0 換成 −1，這樣滿足條件的區間和就可以定義為 0
    public int findMaxLength2(int[] nums) {
        int len = nums.length;

        Map<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(0, -1);

        // 由於求的是 最長的連續子數組的長度，因此只需要記錄前綴和的數值第 1 次出現的下標，
        // 相同的前綴再次出現，就說明這一段區間的和為 0（把 0 看成 −10 以後），
        // 在遍歷的過程中，記錄最長的區間的長度。
        int preSum = 0;
        int res = 0;
        for (int i = 0; i < len; i++) {
            if (nums[i] == 0) {
                preSum--;
            } else {
                preSum++;
            }

            if (!hashMap.containsKey(preSum)) {
                hashMap.put(preSum, i);
            } else {
                // 因為求的是最長的長度，只記錄前綴和第一次出現的下標，
                // 注意：這裡不需要加 1
                res = Math.max(res, i - hashMap.get(preSum));
            }
        }
        return res;
    }
}
