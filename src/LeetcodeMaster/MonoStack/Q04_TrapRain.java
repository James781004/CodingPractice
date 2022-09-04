package LeetcodeMaster.MonoStack;

import java.util.Stack;

public class Q04_TrapRain {
//    42. 接雨水
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0042.%E6%8E%A5%E9%9B%A8%E6%B0%B4.md
//
//    給定 n 個非負整數表示每個寬度為 1 的柱子的高度圖，計算按此排列的柱子，下雨之後能接多少雨水。
//
//    示例 1：
//
//
//
//    輸入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
//    輸出：6
//    解釋：上面是由數組 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度圖，在這種情況下，可以接 6 個單位的雨水（藍色部分表示雨水）。
//    示例 2：
//
//    輸入：height = [4,2,0,3,2,5]
//    輸出：9

    // 雙指針法
    public int trap(int[] height) {
        int sum = 0;
        for (int i = 0; i < height.length; i++) {
            // 第一個柱子和最後一個柱子不接雨水
            if (i == 0 || i == height.length - 1) continue;

            int rHeight = height[i]; // 記錄右邊柱子的最高高度
            int lHeight = height[i]; // 記錄左邊柱子的最高高度

            for (int r = 0; r < height.length; r++) {
                if (height[r] > rHeight) rHeight = height[r];
            }

            for (int l = i - 1; l >= 0; l--) {
                if (height[l] > lHeight) lHeight = height[l];
            }

            int h = Math.min(lHeight, rHeight) - height[i];
            if (h > 0) sum += h;
        }

        return sum;
    }


    // 動態規劃法
    public int trapDP(int[] height) {
        int length = height.length;
        if (length <= 2) return 0;
        int[] maxLeft = new int[length];
        int[] maxRight = new int[length];

        // 記錄每個柱子左邊柱子最大高度
        maxLeft[0] = height[0];
        for (int i = 1; i < length; i++) maxLeft[i] = Math.max(height[i], maxLeft[i - 1]);

        // 記錄每個柱子右邊柱子最大高度
        maxRight[length - 1] = height[length - 1];
        for (int i = length - 2; i >= 0; i--) maxRight[i] = Math.max(height[i], maxRight[i + 1]);

        // 求和
        int sum = 0;
        for (int i = 0; i < length; i++) {
            int count = Math.min(maxLeft[i], maxRight[i]) - height[i];
            if (count > 0) sum += count;
        }
        return sum;
    }


    // 單調棧法
    public int trapStack(int[] height) {
        int size = height.length;

        if (size <= 2) return 0;

        // in the stack, we push the index of array
        // using height[] to access the real height
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(0);

        int sum = 0;
        for (int index = 1; index < size; index++) {
            int stackTop = stack.peek();
            if (height[index] < height[stackTop]) { // 情況一
                stack.push(index);
            } else if (height[index] == height[stackTop]) { // 情況二
                // 因為相等的相鄰墻，左邊一個是不可能存放雨水的，所以pop左邊的index, push當前的index
                stack.pop();
                stack.push(index);
            } else { // 情況三
                //pop up all lower value
                int heightAtIdx = height[index];
                while (!stack.isEmpty() && (heightAtIdx > height[stackTop])) {
                    int mid = stack.pop();

                    if (!stack.isEmpty()) {
                        int left = stack.peek();

                        int h = Math.min(height[left], height[index]) - height[mid];
                        int w = index - left - 1; // 注意減一，只求中間寬度
                        int hold = h * w;
                        if (hold > 0) sum += hold;
                        stackTop = stack.peek();
                    }
                }
                stack.push(index);
            }
        }

        return sum;
    }


    // 應該是最佳解
    public static int getWater(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        int res = 0;
        int leftMax = height[0];
        int rightMax = height[height.length - 1];
        int L = 1;
        int R = height.length - 2;
        while (L <= R) {
            if (leftMax <= rightMax) { // 先計算leftMax和rightMax較小那邊，因為較大那邊的水會從較小那邊流掉
                res += Math.max(0, leftMax - height[L]); // 算當前位置這一格的水量，加進res
                leftMax = Math.max(leftMax, height[L++]); // 算完後指針就往另一邊移動，並觀察leftMax需不需要更新
            } else {
                res += Math.max(0, rightMax - height[R]); // 算當前位置這一格的水量，加進res
                rightMax = Math.max(rightMax, height[R--]); // 算完後指針就往另一邊移動，並觀察rightMax需不需要更新
            }
        }
        return res;
    }
}
