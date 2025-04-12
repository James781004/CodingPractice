package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.Stack;

public class CarFleetII {

    // https://leetcode.cn/problems/car-fleet-ii/solutions/625908/javadan-diao-zhan-jie-jue-che-dui-zhui-j-8744/
    // 1.首先要清楚，後面的車不會影響到前面車的速度，但前面車會影響到後面車的追擊時間，因此可以從後向前遍歷
    // 2.維護一個單調棧，棧裡保存車的速度，所有入棧的車均是已被求出答案後的，因此可以放心的彈出，不影響結果
    // 3.將棧設計成單調遞增棧，如果當前車速小於棧頂車速，則當前車不可能撞上棧頂車，直接彈出，繼續判斷當前車能否撞上更前面的車
    public double[] getCollisionTimes(int[][] cars) {
        int n = cars.length;
        double[] ans = new double[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            // 棧不為空，需要比較當前車速與棧頂車速
            while (!stack.isEmpty()) {
                // 棧頂車速大於當前車速，則當前車追不上棧頂車，但是有可能追上棧頂元素的前一輛車
                if (cars[stack.peek()][1] >= cars[i][1]) {
                    stack.pop();
                } else { // 當前車速大於棧頂車速
                    // 棧頂車撞不上它前面的車，因此，當前車一定可以撞上棧頂車
                    if (ans[stack.peek()] < 0) {
                        break;
                    } else { // 棧頂車能撞上前面的車，需要分情況討論
                        // 如果當前車能在棧頂車撞上前面車之前就能夠撞上棧頂車，則直接撞上去
                        if (((double) (cars[stack.peek()][0] - cars[i][0])) / ((double) (cars[i][1] - cars[stack.peek()][1])) <= ans[stack.peek()]) {
                            break;
                        } else { // 否則的話，當前車就只能撞到棧頂車前面的車了
                            stack.pop();
                        }
                    }
                }
            }
            // 初始時，棧為空，前面沒車可撞
            if (stack.isEmpty()) {
                ans[i] = -1;
            } else { // 棧不為空，則撞上棧頂車
                ans[i] = ((double) (cars[stack.peek()][0] - cars[i][0])) / ((double) (cars[i][1] - cars[stack.peek()][1]));
            }
            // 當前車結果求出後，入棧
            stack.push(i);
        }
        return ans;
    }


}
