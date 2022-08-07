package 程序员代码面试指南.ch08;

import java.util.HashMap;

public class Q11_LongestSumSubArrayLength {
    //    CD9 未排序數組中累加和為給定值的最長子數組長度
//    描述
//    給定一個無序數組arr,其中元素可正、可負、可0。給定一個整數k，求arr所有子數組中累加和為k的最長子數組長度
//    CD10 未排序數組中累加和為給定值的最長子數組系列問題補1
//    描述
//    給定一個無序數組arr，其中元素可正、可負、可0。求arr所有子數組中正數與負數個數相等的最長子數組的長度。
//    CD11 未排序數組中累加和為給定值的最長子數組系列問題補2
//    描述
//    給定一個無序數組arr，其中元素只能是1或0。求arr所有的子數組中0和1個數相等的最長子數組的長度

    public static int maxLength(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        // sum表示子數組[0...i]的數字和
        // map保存的是sum第一次出現的位置
        // 比較累計長度問題常常需要在map放入初始值(0,-1)
        // 否則比對子數組和sum剛好是k(也就是第一組可能解)的情況有可能出錯
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1); // important
        int len = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];

            // 如果map已經有保存當前sum - k的資料，表示那組sum和目前這組sum差值已經是k
            // 所以目前座標i和sum - k位置差值就是累加和為k的子數組
            if (map.containsKey(sum - k)) {
                // 當前位置減去差值sum-k前一次出現的位置，中間距離就是k
                // 此時可以更新長度
                len = Math.max(len, i - map.get(sum - k));
            }

            // map沒有保存當前sum的資料，直接存進去
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }

        return len;
    }
}
