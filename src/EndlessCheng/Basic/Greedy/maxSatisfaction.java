package EndlessCheng.Basic.Greedy;

import java.util.Arrays;

public class maxSatisfaction {

    // https://leetcode.cn/problems/reducing-dishes/solutions/2492854/mei-ju-zuo-ji-dao-cai-tan-xin-pythonjava-k7w2/
    public int maxSatisfaction(int[] satisfaction) {
        Arrays.sort(satisfaction);
        int f = 0; // f(0) = 0
        int s = 0;
        for (int i = satisfaction.length - 1; i >= 0; i--) {
            s += satisfaction[i];
            if (s <= 0) { // 後面不可能找到更大的 f(k)
                break;
            }
            f += s; // f(k) = f(k-1) + s
        }
        return f;
    }


}
