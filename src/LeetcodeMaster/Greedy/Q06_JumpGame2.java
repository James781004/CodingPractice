package LeetcodeMaster.Greedy;

public class Q06_JumpGame2 {
//    45.跳躍遊戲II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0045.%E8%B7%B3%E8%B7%83%E6%B8%B8%E6%88%8FII.md
//
//    給定一個非負整數數組，你最初位於數組的第一個位置。
//
//    數組中的每個元素代表你在該位置可以跳躍的最大長度。
//
//    你的目標是使用最少的跳躍次數到達數組的最後一個位置。
//
//    示例:
//
//    輸入: [2,3,1,1,4]
//    輸出: 2
//    解釋: 跳到最後一個位置的最小跳躍數是 2。從下標為 0 跳到下標為 1 的位置，跳 1 步，然後跳 3 步到達數組的最後一個位置。
//    說明: 假設你總是可以到達數組的最後一個位置。


    public int canJump(int[] nums) {
        if (nums == null || nums.length == 0 || nums.length == 1) return 0;
        int count = 0; // 記錄跳躍的次數
        int curDistance = 0; // 當前的覆蓋最大區域
        int maxDistance = 0; // 最大的覆蓋區域

        for (int i = 0; i < nums.length; i++) {
            maxDistance = Math.max(maxDistance, i + nums[i]); // 在可覆蓋區域內更新最大的覆蓋區域
            if (maxDistance >= nums.length - 1) { // 說明當前一步，再跳一步就到達了末尾
                count++;
                break;
            }
            // 走到當前覆蓋的最大區域時，更新下一步可達的最大區域
            if (i == curDistance) {
                curDistance = maxDistance;
                count++;
            }
        }
        return count;
    }


    public int canJump2(int[] nums) {
        int result = 0;
        int end = 0; // 當前覆蓋的最遠距離下標
        int temp = 0; // 下一步覆蓋的最遠距離下標
        for (int i = 0; i <= end && end < nums.length - 1; i++) {
            temp = Math.max(temp, i + nums[i]);
            if (i == end) { // 可達位置的改變次數就是跳躍次數
                end = temp;
                result++;
            }
        }
        return result;
    }
}
