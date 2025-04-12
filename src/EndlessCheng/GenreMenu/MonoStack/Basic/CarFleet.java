package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayDeque;
import java.util.Deque;

public class CarFleet {

    // https://leetcode.cn/problems/car-fleet/solutions/2903448/dan-diao-zhan-chao-guo-9961-by-mi-lu-yu-ba1o2/
    public int carFleet(int target, int[] position, int[] speed) {
        double[] time = new double[target]; // 代表第i個位置出發的車到達終點所需的時間
        for (int i = 0; i < position.length; i++) {
            time[position[i]] = (target - position[i]) / (double) speed[i]; // 記錄每個位置到達終點所需的時間
        }
        Deque<Double> stack = new ArrayDeque<>(); // 記錄到每個車隊上最慢到達終點的所需時間是多少？
        for (int i = 0; i < target; i++) {
            if (time[i] > 0) {
                // 如果當前車出發到終點的所需時間>=棧頂車隊到終點的所需時間
                // （我開的慢，也就是前車都比我快會被我堵住所以就要跟著我走。我前面的車隊就會和我形成一個車隊，並且以我為准了）
                // 此時直接棧頂出棧，說明前面的車隊和我要合並了
                while (!stack.isEmpty() && time[i] >= stack.peek()) {
                    stack.pop();
                }

                // 最後將自己入棧，作為一個車隊
                stack.push(time[i]);
            }
        }
        return stack.size();
    }


    public int carFleet2(int target, int[] position, int[] speed) {
        double[] time = new double[target]; // 代表第i個位置出發的車到達終點所需的時間
        for (int i = 0; i < position.length; i++) {
            time[position[i]] = (target - position[i]) / (double) speed[i]; // 記錄每個位置到達終點所需的時間
        }
        Deque<Double> stack = new ArrayDeque<>(); // 記錄到每個車隊上最慢到達終點的所需時間是多少？
        for (int i = target - 1; i >= 0; i--) {
            if (time[i] > 0) {
                if (stack.isEmpty()) {
                    stack.push(time[i]);
                } else if (time[i] > stack.peek()) { // 前一個車的到達所需時間 大於 棧頂車隊到達所需時間，說明無法趕上前一個車隊
                    stack.push(time[i]); // 可以另做一個車隊了
                } else { // 到這裡一定是前車到達所需時間<=棧頂的這個車所需要的時間，要讓車隊最慢的車的到達時間作為這個車隊的到達時間，什麼也不用做

                }
            }
        }
        return stack.size();
    }


}
