package Grind.Ch11_Stack;

import java.util.Stack;

public class Q08_DailyTemperatures {
    // https://leetcode.cn/problems/daily-temperatures/solutions/12909/javadan-diao-zhan-ni-xu-bian-li-by-hyh-2/
    public int[] dailyTemperatures(int[] T) {
        int[] res = new int[T.length];
        // 單調棧 裡面的數 非遞增排序
        Stack<Integer> stack = new Stack();
        // 從後往前遍歷
        for (int i = T.length - 1; i >= 0; i--) {
            // 當前元素比棧頂元素大 出棧 重新調整棧直至滿足要求
            while (!stack.isEmpty() && T[i] >= T[stack.peek()]) {
                stack.pop();
            }
            // 棧為空 即後面沒有比當前天溫度高的
            // 不為空 棧頂元素對應的下標減去當前下標即為經過幾天後溫度比當前天溫度高
            res[i] = stack.isEmpty() ? 0 : stack.peek() - i;
            // 當前元素進棧
            stack.push(i);
        }
        return res;
    }
}
