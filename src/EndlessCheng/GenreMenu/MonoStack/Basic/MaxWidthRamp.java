package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.Stack;

public class MaxWidthRamp {

    // https://leetcode.cn/problems/maximum-width-ramp/solutions/666604/zui-da-kuan-du-po-dan-diao-zhan-cun-de-s-myj9/
    public int maxWidthRamp(int[] A) {

        int n = A.length;
        int maxWidth = 0;

        // 正序遍歷數組 A，將以 A[0] 開始的遞減序列的元素下標依次存入棧中
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (stack.isEmpty() || A[stack.peek()] > A[i]) {
                stack.push(i);
            }
        }

        // 逆序遍歷數組 A，若以棧頂元素為下標的元素值 A[stack.peek()] 小於等於當前遍歷的元素 A[i]，
        // 即 A[stack.peek()] <= A[i]。
        // 此時就是一個滿足條件的坡的寬度，並且這個寬度一定是棧頂這個坡底 i 能形成的最大寬度，
        // 將棧頂元素出棧並計算當前坡的寬度，保留最大值即可
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && A[stack.peek()] <= A[i]) {
                int pos = stack.pop();
                maxWidth = Math.max(maxWidth, i - pos);
            }
        }
        return maxWidth;
    }


}
