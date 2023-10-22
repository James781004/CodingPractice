package Grind.Ch02_String;

import java.util.Arrays;

public class Q12_LargestNumber {
    // https://leetcode.cn/problems/largest-number/solutions/716212/java-jiang-shu-zu-zhuan-hua-wei-zi-fu-ch-ikrv/
    public String largestNumber(int[] nums) {
        int n = nums.length;

        // 將數組內元素逐個轉化為字串
        String numsToWord[] = new String[n];
        for (int i = 0; i < n; i++) {
            numsToWord[i] = String.valueOf(nums[i]);
        }

        // compareTo()方法比較的時候是按照ASCII碼逐位比較的
        // 通過比較(a+b)和(b+a)的大小，就可以判斷出a,b兩個字符串誰應該在前面
        // 所以[3,30,34]排序後變為[34,3,30]
        // [233，23333]排序後變為[23333，233]
        Arrays.sort(numsToWord, (a, b) -> {
            return (b + a).compareTo(a + b);
        });

        // 如果排序後的第一個元素是0，那後面的元素肯定小於或等於0，則可直接返回0
        if (numsToWord[0].equals("0")) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(numsToWord[i]);
        }
        return sb.toString();
    }
}
