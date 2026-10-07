package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class BeautifulSubsets {

    // https://leetcode.cn/problems/the-number-of-beautiful-subsets/solutions/2177818/tao-lu-zi-ji-xing-hui-su-pythonjavacgo-b-fcgs/https://leetcode.cn/problems/the-number-of-beautiful-subsets/solutions/2177818/tao-lu-zi-ji-xing-hui-su-pythonjavacgo-b-fcgs/
    public int beautifulSubsets(int[] nums, int k) {
        Map<Integer, TreeMap<Integer, Integer>> groups = new HashMap<>();
        for (int x : nums) {
            // 模 k 同余的數分到同一組，記錄元素 x 及其出現次數
            groups.computeIfAbsent(x % k, i -> new TreeMap<>()).merge(x, 1, Integer::sum);
        }

        int ans = 1;
        for (TreeMap<Integer, Integer> cnt : groups.values()) {
            // 計算這一組的方案數
            int m = cnt.size();
            int[] f = new int[m + 1];
            f[0] = 1;
            int i = 1;
            int pre = 0;
            for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
                int x = e.getKey();
                int c = e.getValue();
                if (i > 1 && x - pre == k) {
                    f[i] = f[i - 1] + f[i - 2] * ((1 << c) - 1);
                } else {
                    f[i] = f[i - 1] << c;
                }
                pre = x;
                i++;
            }
            ans *= f[m]; // 每組方案數相乘
        }
        return ans - 1; // 去掉空集
    }


    public int beautifulSubsets2(int[] nums, int k) {
        Map<Integer, TreeMap<Integer, Integer>> groups = new HashMap<>();
        for (int x : nums) {
            // 模 k 同余的數分到同一組，記錄元素 x 及其出現次數
            groups.computeIfAbsent(x % k, i -> new TreeMap<>()).merge(x, 1, Integer::sum);
        }

        int ans = 1;
        for (TreeMap<Integer, Integer> cnt : groups.values()) {
            // 計算這一組的方案數
            int f0 = 1;
            int f1 = 1; // 下面第一輪循環無論進入哪個分支，都會算出 f1 = 1 << c0
            int pre = 0; // 可以初始化成任意值
            for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
                int x = e.getKey();
                int c = e.getValue();
                int newF = x - pre == k ? f1 + f0 * ((1 << c) - 1) : f1 << c;
                f0 = f1;
                f1 = newF;
                pre = x;
            }
            ans *= f1; // 每組方案數相乘
        }
        return ans - 1; // 去掉空集
    }


    public int beautifulSubsets3(int[] nums, int k) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }

        int ans = 1;
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            int x = e.getKey();
            if (cnt.containsKey(x - k)) { // x 不是等差數列的首項
                continue;
            }
            // 計算這一組的方案數
            int f0 = 1;
            int f1 = 1 << e.getValue();
            for (x += k; cnt.containsKey(x); x += k) {
                int newF = f1 + f0 * ((1 << cnt.get(x)) - 1);
                f0 = f1;
                f1 = newF;
            }
            ans *= f1; // 每組方案數相乘
        }
        return ans - 1; // 去掉空集
    }


}
