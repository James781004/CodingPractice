package WeeklyContest;

import java.util.*;

class Week_351 {
    // https://github.com/doocs/leetcode/blob/main/solution/2700-2799/2748.Number%20of%20Beautiful%20Pairs/README.md
    public int countBeautifulPairs(int[] nums) {
        int[] cnt = new int[10];
        int ans = 0;
        for (int x : nums) {
            for (int y = 0; y < 10; ++y) {
                if (cnt[y] > 0 && gcd(x % 10, y) == 1) {
                    ans += cnt[y];
                }
            }
            while (x > 9) {
                x /= 10;
            }
            ++cnt[x];
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://www.bilibili.com/video/BV1du41187ZN/
    // https://leetcode.cn/problems/minimum-operations-to-make-the-integer-zero/solution/mei-ju-da-an-pythonjavacgo-by-endlessche-t4co/
    // 從小到大枚舉答案
    // 假設操作了 k 次，那麼操作後 num1 變成 num1 - num2 * k 再減去 k 個 2^i
    // 此時問題變成： num1 - num2 * k 能否拆分成 k 個 2^i 之和？
    // 設 x = num1 - num2 * k
    // 如果 x<0，無解。
    // 否則如果 x<k，那麼即使每次操作取 i=0，也至少要把 x 拆分成 k 個 1 之和，這是不可能的。
    // 否則如果 x 中二進制 1 的個數大於 k，也無法拆分成 k 個 2^i 之和，無解。
    // 否則分解方案一定存在，返回 k。
    // （因為可以把一個 2^j 分解成兩個 2^j-1，所以分解出的 2^i 的個數可以從「x 中二進制 1 的個數」一直到 x，
    // k 只要屬於這個范圍，分解方案就是存在的。）
    public int makeTheIntegerZero(int num1, int num2) {
        for (long k = 1; k <= num1 - num2 * k; k++)
            if (k >= Long.bitCount(num1 - num2 * k))
                return (int) k;
        return -1;
    }


    // https://leetcode.cn/problems/ways-to-split-array-into-good-subarrays/solution/cheng-fa-yuan-li-by-endlesscheng-hypt/
    // 根據題意，需要在每兩個 1 之間畫一條分割線，有 x 個 0 就可以畫 x+1 條分割線
    // 根據乘法原理，答案為所有分割線的方案數的乘積。
    // 如果數組中沒有1，那麼答案為 0。如果數組只有一個 1，那麼答案為 1
    public int numberOfGoodSubarraySplits(int[] nums) {
        final long MOD = (long) 1e9 + 7;
        long ans = 1;
        int pre = -1, n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) continue; // 沒找到下一個1就無法形成區間，跳過
            if (pre >= 0) ans = ans * (i - pre) % MOD;  // 乘法原理，累計上當前1和1區間分割方法總數
            pre = i; // 更新前一個1的位置
        }
        return pre < 0 ? 0 : (int) ans;
    }


    // https://leetcode.cn/problems/robot-collisions/solution/zhan-mo-ni-by-endlesscheng-fu26/
    // 本題思路和 LC 735 行星碰撞 是一樣的
    // 用列表 toLeft 維護向左的機器人，用棧 st 維護向右的機器人。
    // 按照 positions[i] 從小到大排序。遍歷機器人，如果遇到一個向左的機器人 p，分類討論：
    // 1. 如果 p 的健康度小於棧頂，那麼棧頂的健康度減一。
    // 2. 如果 p 的健康度等於棧頂，那麼移除棧頂。
    // 3. 如果 p 的健康度大於棧頂，那麼移除棧頂，將 p 的健康度減一後加入 toLeft。
    // 注意如果健康度減一，那麼在減一之前，健康度一定是大於 1 的，不存在健康度減為 0 的情況。
    // 最後剩余的就是 toLeft 和 st 中的機器人，合並，按照編號排序後，返回健康度列表。
    // 代碼實現時，也可以直接在 healths 上修改，最後返回 healths 中的正數。
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        int n = positions.length;
        Integer[] id = new Integer[n];
        for (int i = 0; i < n; i++) id[i] = i;
        Arrays.sort(id, (i, j) -> positions[i] - positions[j]);  // 從小到大排序位置

        Deque<Integer> st = new ArrayDeque<>();
        for (int i : id) {
            if (directions.charAt(i) == 'R') { // 向右，存入棧中
                st.push(i);
                continue;
            }
            // 向左，與棧中向右的機器人碰撞
            while (!st.isEmpty()) {
                int top = st.peek();
                if (healths[top] > healths[i]) { // 棧頂的健康度大，左行機器人體力歸零
                    healths[top]--; // 相撞後棧頂機器人也要扣體
                    healths[i] = 0;
                    break;
                }
                if (healths[top] == healths[i]) { // 健康度一樣大，棧頂以及左行機器人體力歸零
                    healths[st.pop()] = 0;
                    healths[i] = 0;
                    break;
                }
                healths[st.pop()] = 0;
                healths[i]--; // 當前機器人的健康度大，棧頂機器人體力歸零，相撞後左行機器人也要扣體
            }
        }

        List<Integer> ans = new ArrayList<>();
        for (int h : healths) if (h > 0) ans.add(h);
        return ans;
    }

}

