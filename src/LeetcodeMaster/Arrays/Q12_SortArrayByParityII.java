package LeetcodeMaster.Arrays;

public class Q12_SortArrayByParityII {
//    922. 按奇偶排序數組II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0922.%E6%8C%89%E5%A5%87%E5%81%B6%E6%8E%92%E5%BA%8F%E6%95%B0%E7%BB%84II.md
//
//    給定一個非負整數數組 A， A 中一半整數是奇數，一半整數是偶數。
//
//    對數組進行排序，以便當 A[i] 為奇數時，i 也是奇數；當 A[i] 為偶數時， i 也是偶數。
//
//    你可以返回任何滿足上述條件的數組作為答案。
//
//    示例：
//
//    輸入：[4,2,5,7]
//    輸出：[4,5,2,7]
//    解釋：[4,7,2,5]，[2,5,4,7]，[2,7,4,5] 也會被接受。

    // 方法一
    public int[] sortArrayByParityII(int[] nums) {
        //定義結果數組 result
        int[] result = new int[nums.length];
        int even = 0, odd = 1;
        for (int i = 0; i < nums.length; i++) {
            //如果為偶數
            if (nums[i] % 2 == 0) {
                result[even] = nums[i];
                even += 2;
            } else {
                result[odd] = nums[i];
                odd += 2;
            }
        }
        return result;
    }


    // 方法二：不采用額外的數組空間
    public int[] sortArrayByParityII2(int[] nums) {
        // 定義雙指針
        int oddPoint = 1, evenPoint = 0;
        // 開始移動並交換，最後一層必然為相互交換後再移動或者相同直接移動
        while (oddPoint < nums.length && evenPoint < nums.length) {
            // 進行判斷
            if (nums[oddPoint] % 2 == 0 && nums[evenPoint] % 2 == 1) {    //如果均不滿足
                int temp = 0;
                temp = nums[oddPoint];
                nums[oddPoint] = nums[evenPoint];
                nums[evenPoint] = temp;
                oddPoint += 2;
                evenPoint += 2;
            } else if (nums[oddPoint] % 2 == 0 && nums[evenPoint] % 2 == 0) {  //偶數滿足
                evenPoint += 2;
            } else if (nums[oddPoint] % 2 == 1 && nums[evenPoint] % 2 == 1) {  //奇數滿足
                oddPoint += 2;
            } else {
                oddPoint += 2;
                evenPoint += 2;
            }
        }
        return nums;
    }


    // 原數組上修改
    public int[] sortArrayByParityII3(int[] nums) {
        int oddIndex = 1;
        for (int i = 0; i < nums.length; i += 2) {
            if (nums[i] % 2 == 1) { // 在偶數位遇到了奇數
                while (nums[oddIndex] % 2 != 0) oddIndex += 2; // 在奇數位找一個偶數
                swap(nums[i], nums[oddIndex]); // 替換
            }
        }
        return nums;
    }

    private void swap(int a, int b) {
        int tmp = a;
        a = b;
        b = tmp;
    }
}
