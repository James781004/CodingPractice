package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;
import java.util.Deque;

public class FinalPrices {

    // https://leetcode.cn/problems/final-prices-with-a-special-discount-in-a-shop/solutions/1790574/by-muse-77-nh2d/
    public int[] finalPrices(int[] prices) {
        Deque<Integer> deque = new ArrayDeque();
        for (int i = prices.length - 1; i >= 0; i--) {
            while (!deque.isEmpty() && deque.peekLast() > prices[i]) deque.removeLast();
            int result = deque.isEmpty() ? prices[i] : prices[i] - deque.peekLast();
            deque.addLast(prices[i]);
            prices[i] = result;
        }
        return prices;
    }


}
