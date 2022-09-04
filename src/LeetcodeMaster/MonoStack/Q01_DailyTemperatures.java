package LeetcodeMaster.MonoStack;

import java.util.Deque;
import java.util.LinkedList;

public class Q01_DailyTemperatures {
//    739. 每日溫度
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0739.%E6%AF%8F%E6%97%A5%E6%B8%A9%E5%BA%A6.md
//
//    請根據每日 氣溫 列表，重新生成一個列表。對應位置的輸出為：要想觀測到更高的氣溫，至少需要等待的天數。如果氣溫在這之後都不會升高，請在該位置用 0 來代替。
//
//    例如，給定一個列表 temperatures = [73, 74, 75, 71, 69, 72, 76, 73]，你的輸出應該是 [1, 1, 4, 2, 1, 1, 0, 0]。
//
//    提示：氣溫 列表長度的範圍是 [1, 30000]。每個氣溫的值的均為華氏度，都是在 [30, 100] 範圍內的整數。


    // 版本 1
    public int[] dailyTemperatures1(int[] temperatures) {
        int lens = temperatures.length;
        int[] res = new int[lens];

        /**
         如果當前遍歷的元素 大於棧頂元素，表示 棧頂元素的 右邊的最大的元素就是 當前遍歷的元素，
         所以彈出 棧頂元素，並記錄
         如果棧不空的話，還要考慮新的棧頂與當前元素的大小關系
         否則的話，可以直接入棧。
         注意，單調棧里 加入的元素是 下標。
         */
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);

        for (int i = 1; i <= lens; i++) {
            if (temperatures[i] <= temperatures[stack.peek()]) { // 底下while操作時其實已經包含這部份判斷，可省略
                stack.push(i);
            } else {
                while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                    res[stack.peek()] = i - stack.peek(); // 對應位置的輸出為：要想觀測到更高的氣溫，至少需要等待的天數
                    stack.pop();
                }
                stack.push(i);
            }
        }
        return res;
    }


    // 版本 2
    public int[] dailyTemperatures2(int[] temperatures) {
        int lens = temperatures.length;
        int[] res = new int[lens];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < lens; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {  // 注意棧不能為空
                res[stack.peek()] = i - stack.peek(); // 對應位置的輸出為：要想觀測到更高的氣溫，至少需要等待的天數
                stack.pop();
            }
            stack.push(i);
        }

        return res;
    }
}
