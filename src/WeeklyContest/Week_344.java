package WeeklyContest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Week_344 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2670.Find%20the%20Distinct%20Difference%20Array/README.md
    public int[] distinctDifferenceArray(int[] nums) {
        int n = nums.length;
        int[] suf = new int[n + 1];
        Set<Integer> s = new HashSet<>();
        for (int i = n - 1; i >= 0; --i) {
            s.add(nums[i]);
            suf[i] = s.size();  // 利用set長度即可計算後綴
        }
        s.clear();
        int[] ans = new int[n];
        for (int i = 0; i < n; ++i) {
            s.add(nums[i]);  // 不必重建一個前綴數組，直接利用set長度即可
            ans[i] = s.size() - suf[i + 1];
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2671.Frequency%20Tracker/README.md
    class FrequencyTracker {
        private Map<Integer, Integer> cnt = new HashMap<>();
        private Map<Integer, Integer> freq = new HashMap<>();

        public FrequencyTracker() {

        }

        public void add(int number) {
            int f = cnt.getOrDefault(number, 0);
            if (freq.getOrDefault(f, 0) > 0) {
                freq.merge(f, -1, Integer::sum);
            }
            cnt.merge(number, 1, Integer::sum);
            freq.merge(f + 1, 1, Integer::sum);
        }

        public void deleteOne(int number) {
            int f = cnt.getOrDefault(number, 0);
            if (f == 0) {
                return;
            }
            freq.merge(f, -1, Integer::sum);
            cnt.merge(number, -1, Integer::sum);
            freq.merge(f - 1, 1, Integer::sum);
        }

        public boolean hasFrequency(int frequency) {
            return freq.getOrDefault(frequency, 0) > 0;
        }
    }


    // https://leetcode.cn/problems/number-of-adjacent-elements-with-the-same-color/solution/mo-ni-pythonjavacgo-by-endlesscheng-42qp/
    // https://www.bilibili.com/video/BV1YL41187Rx/
    // 每次修改只會影響當前元素與其左右元素，所以模擬即可。
    // 用 cnt 統計相鄰相同的個數。
    // 代碼實現時，可以先去掉當前元素對 cnt 的影響，修改顏色後，再加上當前元素對 cnt 的影響。
    public int[] colorTheArray(int n, int[][] queries) {
        int q = queries.length, cnt = 0;
        int[] ans = new int[q], a = new int[n + 2]; // 避免討論下標出界的情況
        for (int qi = 0; qi < q; qi++) {
            int i = queries[qi][0] + 1, c = queries[qi][1]; // 下標改成從 1 開始
            if (a[i] > 0) {  // 如果當前格子已經有塗過色，把同色格子的影響先消除
                cnt -= (a[i] == a[i - 1] ? 1 : 0) + (a[i] == a[i + 1] ? 1 : 0);
            }
            a[i] = c;
            cnt += (a[i] == a[i - 1] ? 1 : 0) + (a[i] == a[i + 1] ? 1 : 0);
            ans[qi] = cnt;
        }
        return ans;
    }


    // https://leetcode.cn/problems/make-costs-of-paths-equal-in-a-binary-tree/solution/tan-xin-jian-ji-xie-fa-pythonjavacgo-by-5svh1/
    public int minIncrements(int n, int[] cost) {
        int ans = 0;
        for (int i = n / 2; i > 0; i--) { // 從最後一個非葉節點開始算
            ans += Math.abs(cost[i * 2 - 1] - cost[i * 2]); // 兩個子節點變成一樣的
            cost[i - 1] += Math.max(cost[i * 2 - 1], cost[i * 2]); // 累加路徑和: 把子節點值傳給父節點
        }
        return ans;
    }


    // 樹型DP
    // https://leetcode.cn/problems/make-costs-of-paths-equal-in-a-binary-tree/solution/shu-xing-di-gui-by-enchanted-2-zzut/
    int val;
    int[] cost;

    public int minIncrements2(int n, int[] c) {
        val = 0;
        cost = c;
        deal(0);
        return val;
    }

    public int deal(int root) {
        if (getLeft(root) == -1) {
            return cost[root];
        }

        int leftVal = deal(getLeft(root));
        int rightVal = deal(getRight(root));
        val += Math.abs(leftVal - rightVal);
        return cost[root] + Math.max(leftVal, rightVal);
    }

    public int getLeft(int index) {
        int left = 2 * index + 1;
        if (left >= cost.length) {
            return -1;
        }

        return left;
    }

    public int getRight(int index) {
        int right = 2 * index + 2;
        if (right >= cost.length) {
            return -1;
        }

        return right;
    }

}
