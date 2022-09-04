package LeetcodeMaster.MonoStack;

import java.util.Arrays;
import java.util.Stack;

public class Q03_NextGreaterElement2 {
//    503.下一個更大元素II
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0503.%E4%B8%8B%E4%B8%80%E4%B8%AA%E6%9B%B4%E5%A4%A7%E5%85%83%E7%B4%A0II.md
//
//    給定一個循環數組（最後一個元素的下一個元素是數組的第一個元素），輸出每個元素的下一個更大元素。
//    數字 x 的下一個更大的元素是按數組遍歷順序，這個數字之後的第一個比它更大的數，這意味著你應該循環地搜索它的下一個更大的數。如果不存在，則輸出 -1。
//
//    示例 1:
//
//    輸入: [1,2,1]
//    輸出: [2,-1,2]
//    解釋: 第一個 1 的下一個更大的數是 2；數字 2 找不到下一個更大的數；第二個 1 的下一個最大的數需要循環搜索，結果也是 2。


    public int[] nextGreaterElements(int[] nums) {
        // 邊界判斷
        if (nums == null || nums.length <= 1) {
            return new int[]{-1};
        }

        int size = nums.length;
        int[] result = new int[size]; // 存放結果
        Arrays.fill(result, -1); // 默認全部初始化為-1
        Stack<Integer> stack = new Stack<>(); // 棧中存放的是nums中的元素下標

        for (int i = 0; i < size * 2; i++) {
            // 模擬遍歷兩邊nums，注意一下都是用i % nums.size()來操作
            while (!stack.isEmpty() && nums[i % size] > nums[stack.peek()]) {
                result[stack.peek()] = nums[i % size]; // 更新result
                stack.pop();
            }
            stack.push(i % size);
        }

        return result;
    }
}
