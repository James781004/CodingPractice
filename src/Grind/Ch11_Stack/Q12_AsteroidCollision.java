package Grind.Ch11_Stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Q12_AsteroidCollision {
    // https://leetcode.cn/problems/asteroid-collision/solutions/1665734/xingxing-by-jiang-hui-4-tepq/
    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        for (int asteroid : asteroids) {
            // 若當前行星是大於0的，直接入棧
            if (asteroid > 0) {
                stack.push(asteroid);
            } else {
                // 若當前行星是小於0的，考慮在什麼情況下會發生碰撞，並且判斷當前行星是否存活
                // 是否存活
                boolean alive = true;
                // 題目規定正表示向右移動，負表示向左移動
                // 所以當前元素存活，並且棧不是空，並且棧頂元素大於0，才會發生碰撞
                while (alive && !stack.isEmpty() && stack.peek() > 0) {
                    // 只有棧頂元素小於當前元素的絕對值，才會存活
                    alive = stack.peek() < Math.abs(asteroid);
                    // 棧頂元素爆炸（出棧）
                    if (stack.peek() <= Math.abs(asteroid)) {
                        stack.pop();
                    }
                }
                if (alive) {
                    stack.push(asteroid);
                }
            }
        }

        // 結果輸出
        int[] ans = new int[stack.size()];
        int i = 0;
        while (!stack.isEmpty()) {
            ans[i++] = stack.pollLast();
        }
        return ans;
    }
}
