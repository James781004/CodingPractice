package WeeklyContest;

import java.util.*;

class Week_262 {
    // https://leetcode.cn/problems/two-out-of-three/
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        int[] h1 = new int[101];
        int[] h2 = new int[101];
        int[] h3 = new int[101];
        for (int n1 : nums1) h1[n1] = 1; // 分組累計
        for (int n2 : nums2) h2[n2] = 1;
        for (int n3 : nums3) h3[n3] = 1;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 101; i++) {
            if (h1[i] + h2[i] + h3[i] >= 2) list.add(i);
        }
        return list;
    }

    // https://leetcode.cn/problems/minimum-operations-to-make-a-uni-value-grid/
    public int minOperations(int[][] grid, int x) {
        int n = grid.length;
        int m = grid[0].length;
        int[] arr = new int[m * n];
        int i = 0;
        for (int[] a : grid)
            for (int a_ : a) {
                arr[i++] = a_;
            }
        Arrays.sort(arr);
        int j = arr[(n * m) / 2]; // 獲得中位數，然後進行加減x等於中位數的操作
        int sum = 0;
        for (int a : arr) {
            int l = Math.abs(j - a);
            if (l % x != 0) return -1;
            sum += l / x;
        }
        return sum;
    }


    // https://leetcode.cn/problems/stock-price-fluctuation/
    class StockPrice {

        private int maxTime;
        private Map<Integer, Integer> timeMap;
        private TreeMap<Integer, Integer> priceMap;

        public StockPrice() {
            maxTime = 0;
            timeMap = new HashMap<>(); // <time, price>
            priceMap = new TreeMap<>(); // <price, frequency>
        }

        public void update(int timestamp, int price) {
            if (timeMap.containsKey(timestamp)) {
                int oldPrice = timeMap.get(timestamp);
                int cnt = priceMap.get(oldPrice);
                if (cnt == 1)
                    priceMap.remove(oldPrice);
                else
                    priceMap.put(oldPrice, cnt - 1);
            }
            timeMap.put(timestamp, price);
            priceMap.put(price, priceMap.getOrDefault(price, 0) + 1);
            maxTime = Math.max(maxTime, timestamp);
        }

        public int current() {
            return timeMap.get(maxTime);
        }

        public int maximum() {
            return priceMap.lastKey();
        }

        public int minimum() {
            return priceMap.firstKey();
        }
    }

    // https://leetcode.cn/problems/partition-array-into-two-arrays-to-minimize-sum-difference/
    // https://leetcode.cn/problems/partition-array-into-two-arrays-to-minimize-sum-difference/solution/java-bu-by-699-d7q9/
    /*
        meet in the middle
        兩數組之差可以看作一個數組取正 另一個數組取負的元素和
        1.求枚舉左半邊的所有情況
        2.然後枚舉右半邊的情況
        後半段是cnt個正數, 此時在前半段從q[cnt]二分查找接近s的數
        由於求和後還要做差 那麼後半段的cnt正數 就變為了m - cnt個正數
    */
    public int minimumDifference(int[] nums) {
        int m = nums.length;
        int n = m / 2;
        // List[i] 枚舉前i個取正號 n - i 取負號的結果 （取負號）也就是做差
        List<Integer>[] sums = new List[n + 1];
        for (int i = 0; i < 1 << n; i++) {
            int cnt = Integer.bitCount(i);
            if (sums[cnt] == null) {
                sums[cnt] = new ArrayList<>();
            }
            int sum = 0;
            // 枚舉前面
            for (int j = 0; j < n; j++) {
                if (((i >> j) & 1) == 1) {
                    sum += nums[j];
                } else {
                    sum -= nums[j];
                }
            }
            sums[cnt].add(sum);
        }
        // 排序前面的情況
        for (int i = 0; i <= n; i++) {
            Collections.sort(sums[i]);
        }
        int min = Integer.MAX_VALUE;
        // 枚舉後半段的情況，假設後面有3個正號，實際上也對應前面有3個正號，這樣做差後，正好相抵
        for (int i = 0; i < 1 << n; i++) {
            int cnt = Integer.bitCount(i);
            int sum = 0;
            // 當前有cnt個數取正號
            for (int j = n; j < m; j++) {
                int t = j - n;
                if (((i >> t) & 1) == 1) {
                    sum += nums[j];
                } else {
                    sum -= nums[j];
                }
            }
            // 二分搜索，在前面相同正號數量的位置，找相近的數，先找比sum大的位置
            List<Integer> list = sums[cnt];
            int l = 0, r = list.size() - 1;
            while (l < r) {
                int mid = l + r >> 1;
                if (list.get(mid) >= sum) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            // 檢查右邊，可能到了右邊也比當前sum小
            min = Math.min(min, Math.abs(list.get(l) - sum));
            // 檢查左邊
            if (l > 0) {
                min = Math.min(min, sum - list.get(l - 1));
            }
        }
        return min;
    }
}

