package EndlessCheng.GenreMenu.DP.Knapsack.ZeroOne.Advance;

import java.util.Arrays;

public class ClosestCost {

    // https://leetcode.cn/problems/closest-dessert-cost/solutions/2005475/huan-shi-zi-ji-de-li-jie-kan-de-shu-fu-g-4oni/

    /**
     * <p>
     * 冰淇淋基料最小值為min
     * 從方法一中已經知道，對於 大於 upper=2*target-min的方案，其與target的差值一定大於min與target的差值，可以直接廢棄
     * <p>
     * 那麼我們可以通過 動態規劃來判斷是否存在成本在 [min, upper-1] 間的方案
     * 如果存在，從中選擇 與target 差值最小的方案即可。
     * <p>
     * base：
     * 因為基料必須選，所以初始時遍歷 baseCosts，如果 baseCosts[i] < upper，讓 dp[baseCosts[i]] = true
     * 接下來是在基料的基礎上選擇配料
     * 這裡參考0-1背包的寫法，假如輔料i的代價為x，在選擇輔料i之前dp數組[a1,a2,a3]位置為true
     * 那麼選擇完輔料i後，dp[a1,a2,a3,a1+x,a2+x,a3+x,a1+2x,a2+2x,a3+2x]為true
     * <p>
     * 也就是說對於輔料i的代價x
     * 遍歷dp數組，對於 dp[i] = true，更新 dp[i + x] 為true， dp[i + 2x] 為 true
     * 對下一個輔料進行同樣過程
     * <p>
     * 【細節一】：
     * dp數組必須倒序遍歷
     * 對於每個輔料，假如順序遍歷dp
     * dp[0]=true，更新dp[0+2x]=true， dp[0+x]=true
     * 那麼當遍歷到dp[x]時，又會更新dp[x+2x]為true，dp[x+x]=true，，？？？這相當於使用了3份輔料i，肯定不對，
     * 因此每次都需要倒序遍歷dp
     * 【細節二】
     * 必須在 dp[i] 為 true 的情況下，更新 dp[i + x] 和 dp[i + 2x] 為true
     * 因為 只有方案i存在，那麼方案i+x才會存在
     * 【細節三】
     * 因為我們只需要考慮成本在 [min, upper-1] 間的方案，因此對於此范圍外的方案一律不用考慮
     *
     * @param baseCosts
     * @param toppingCosts
     * @param target
     * @return
     */
    public int closestCost2(int[] baseCosts, int[] toppingCosts, int target) {
        // 冰淇淋基料的最小值min，
        int min = Arrays.stream(baseCosts).min().getAsInt();
        // 其他任何方案成本只會比min更大，與target的插值更大，此時直接返回min
        if (min >= target) {
            return min;
        }
        // 只需要考慮成本在 [min, upper-1] 間的方案是否存在
        int upper = 2 * target - min;
        boolean[] dp = new boolean[upper];
        // base，基料必選
        for (int baseCost : baseCosts) {
            // 不考慮區間外的方案
            if (baseCost < upper) {
                dp[baseCost] = true;
            }
        }
        // dp迭代，在基料的基礎上選擇輔料
        for (int toppingCost : toppingCosts) {
            // 倒序遍歷dp
            for (int j = upper - 1; j >= min; --j) {
                // 每種輔料可以1份或兩份，更新對應的dp為true，區間外不予考慮
                if (dp[j] && (j + toppingCost < upper)) {
                    dp[j + toppingCost] = true;
                }
                if (dp[j] && (j + 2 * toppingCost < upper)) {
                    dp[j + 2 * toppingCost] = true;
                }
            }
        }
        // 在 [min, upper-1]所有存在的方案種找出與target最接近的方案
        int ans = min;
        for (int i = min + 1; i < upper; ++i) {
            if (dp[i]) {
                // 更接近
                if (Math.abs(i - target) < Math.abs(ans - target)) {
                    ans = i;
                    // 同樣接近，選擇更小成本
                } else if (Math.abs(i - target) == Math.abs(ans - target)) {
                    ans = Math.min(ans, i);
                }
            }
        }
        return ans;
    }


}
