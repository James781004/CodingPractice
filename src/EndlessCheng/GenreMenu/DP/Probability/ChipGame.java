package EndlessCheng.GenreMenu.DP.Probability;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ChipGame {

    // https://leetcode.cn/contest/ubiquant2022/problems/I3Gm2h/
    // 儲存目標每種數量的最大堆數
    private int[] targetCnt;
    // 記憶化搜索 Map
    private Map<String, Double> memo;
    private int n;

    public double chipGame(int[] nums) {
        this.n = nums.length;
        this.memo = new HashMap<>();

        // 找出 nums 中的最大值，用來建立狀態陣列的長度
        int maxVal = 0;
        for (int num : nums) {
            maxVal = Math.max(maxVal, num);
        }

        // targetCnt[i] 表示最終目標中，數量為 i 的堆應該有幾堆
        this.targetCnt = new int[maxVal + 1];
        for (int num : nums) {
            targetCnt[num]++;
        }

        // 初始狀態：數量為 0 的堆有 n 堆，其餘都是 0
        int[] startState = new int[maxVal + 1];
        startState[0] = n;

        return dfs(startState);
    }

    private double dfs(int[] curState) {
        // 將目前狀態轉為字串作為 HashMap 的 Key
        String key = Arrays.toString(curState);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // 檢查是否達到目標狀態
        boolean isTarget = true;
        for (int i = 0; i < curState.length; i++) {
            if (curState[i] != targetCnt[i]) {
                isTarget = false;
                break;
            }
        }
        if (isTarget) return 0.0;

        // 計算當前總共有多少面值已經在桌面上（包含開了但未滿的堆，以及已滿的堆）
        // 根據題目設定，無限種面值中，已被我們「指派」的面值數就是除了 curState[0] 之外的堆總數
        int activePiles = 0;
        for (int i = 1; i < curState.length; i++) {
            activePiles += curState[i];
        }

        // 總可能抽到的面值種類分母：已出現的面值數 + 1 (代表全新面值)
        double totalChoices = activePiles + 1;

        double sumNextExpectation = 0.0;
        double invalidProb = 0.0;

        // 情況 1：抽到全新的面值（概率為 1 / totalChoices）
        // 只有在我們還有「空堆（curState[0] > 0）」可以放新面值時，這才是有意義的抽取
        if (curState[0] > 0) {
            curState[0]--;
            curState[1]++;
            sumNextExpectation += (1.0 / totalChoices) * dfs(curState);
            curState[1]--; // 回溯
            curState[0]++;
        } else {
            // 沒有空堆了，抽到新面值只能放回，視為無效抽取
            invalidProb += 1.0 / totalChoices;
        }

        // 情況 2：抽到桌面上已經存在的某一堆面值
        // 遍歷所有可能進步的堆數量 i (從 1 到最大值 - 1)
        for (int i = 1; i < curState.length; i++) {
            if (curState[i] > 0) {
                // 抽到這類面值的概率為：(該類面值的堆數) / totalChoices
                double prob = (double) curState[i] / totalChoices;

                // 檢查如果讓這一堆數量 +1，是否會超過目標設定的上限
                // 這裡需要滿足動態規劃的可達性：不能盲目加，要看目標是否還需要數量為 i+1 的堆
                // 更精準的剪枝：如果 curState[i] 轉移過去不會導致狀態非法
                if (i + 1 < curState.length) {
                    curState[i]--;
                    curState[i + 1]++;
                    sumNextExpectation += prob * dfs(curState);
                    curState[i + 1]--; // 回溯
                    curState[i]++;
                } else {
                    // 超過最大面值限制，抽到只能放回
                    invalidProb += prob;
                }
            }
        }

        // 根據期望值公式移項：E = (1 + sum(P_i * E_i)) / (1 - P_invalid)
        double res = (1.0 + sumNextExpectation) / (1.0 - invalidProb);
        memo.put(key, res);
        return res;
    }
}
