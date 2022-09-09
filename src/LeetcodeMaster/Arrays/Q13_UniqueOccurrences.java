package LeetcodeMaster.Arrays;

public class Q13_UniqueOccurrences {
//    1207.獨一無二的出現次數
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1207.%E7%8B%AC%E4%B8%80%E6%97%A0%E4%BA%8C%E7%9A%84%E5%87%BA%E7%8E%B0%E6%AC%A1%E6%95%B0.md
//
//    給你一個整數數組 arr，請你幫忙統計數組中每個數的出現次數。
//
//    如果每個數的出現次數都是獨一無二的，就返回 true；否則返回 false。
//
//    示例 1：
//
//    輸入：arr = [1,2,2,1,1,3]
//    輸出：true
//    解釋：在該數組中，1 出現了 3 次，2 出現了 2 次，3 只出現了 1 次。沒有兩個數的出現次數相同。
//    示例 2：
//
//    輸入：arr = [1,2]
//    輸出：false
//    示例 3：
//
//    輸入：arr = [-3,0,1,-3,1,1,1,-3,10,0]
//    輸出：true
//    提示：
//
//            1 <= arr.length <= 1000
//            -1000 <= arr[i] <= 1000

    public boolean uniqueOccurrences(int[] arr) {
        int[] count = new int[2002];
        for (int i = 0; i < arr.length; i++) {
            count[arr[i] + 1000]++; // 防止負數作為下標
        }

        boolean[] flag = new boolean[1002]; // 標記相同頻率是否重覆出現
        for (int i = 0; i <= 2000; i++) {
            if (count[i] > 0) {
                if (flag[count[i]] == false) {
                    flag[count[i]] = true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
