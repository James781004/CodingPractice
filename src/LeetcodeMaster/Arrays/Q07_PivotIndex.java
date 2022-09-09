package LeetcodeMaster.Arrays;

public class Q07_PivotIndex {
//    724.尋找數組的中心下標
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0724.%E5%AF%BB%E6%89%BE%E6%95%B0%E7%BB%84%E7%9A%84%E4%B8%AD%E5%BF%83%E7%B4%A2%E5%BC%95.md
//
//    給你一個整數數組 nums ，請計算數組的 中心下標 。
//
//    數組 中心下標 是數組的一個下標，其左側所有元素相加的和等於右側所有元素相加的和。
//
//    如果中心下標位於數組最左端，那麼左側數之和視為 0 ，因為在下標的左側不存在元素。這一點對於中心下標位於數組最右端同樣適用。
//
//    如果數組有多個中心下標，應該返回 最靠近左邊 的那一個。如果數組不存在中心下標，返回 -1 。
//
//    示例 1：
//
//    輸入：nums = [1, 7, 3, 6, 5, 6]
//    輸出：3
//    解釋：中心下標是 3。左側數之和 sum = nums[0] + nums[1] + nums[2] = 1 + 7 + 3 = 11 ，右側數之和 sum = nums[4] + nums[5] = 5 + 6 = 11 ，二者相等。
//    示例 2：
//
//    輸入：nums = [1, 2, 3]
//    輸出：-1
//    解釋：數組中不存在滿足此條件的中心下標。
//    示例 3：
//
//    輸入：nums = [2, 1, -1]
//    輸出：0
//    解釋：中心下標是 0。左側數之和 sum = 0 ，（下標 0 左側不存在元素），右側數之和 sum = nums[1] + nums[2] = 1 + -1 = 0 。


    // 1. 遍歷一遍求出總和sum
    // 2. 遍歷第二遍求中心索引左半和leftSum
    // 同時根據sum和leftSum 計算中心索引右半和rightSum
    // 判斷leftSum和rightSum是否相同
    public int pivotIndex(int[] nums) {
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }

        int leftSum = 0;
        int rightSum = 0;
        for (int i = 0; i < nums.length; i++) {
            leftSum += nums[i];
            rightSum = sum - leftSum + nums[i]; // leftSum 里面已經有 nums[i]，多減了一次，所以加上
            if (leftSum == rightSum) {
                return i;
            }
        }
        return -1;
    }

}
