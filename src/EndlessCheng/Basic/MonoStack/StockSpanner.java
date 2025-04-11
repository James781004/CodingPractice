package EndlessCheng.Basic.MonoStack;

import java.util.ArrayDeque;
import java.util.Deque;

public class StockSpanner {

    // https://leetcode.cn/problems/online-stock-span/solutions/2470527/shi-pin-yi-ge-shi-pin-jiang-tou-dan-diao-cuk7/
    private final Deque<int[]> stack = new ArrayDeque<>();
    private int curDay = -1; // 第一個 next 調用算作第 0 天

    public StockSpanner() {
        stack.push(new int[]{-1, Integer.MAX_VALUE}); // 這樣無需判斷棧為空的情況
    }

    public int next(int price) {
        while (price >= stack.peek()[1]) {
            stack.pop(); // 棧頂數據後面不會再用到了，因為 price 更大
        }
        int ans = ++curDay - stack.peek()[0];
        stack.push(new int[]{curDay, price});
        return ans;
    }


}
