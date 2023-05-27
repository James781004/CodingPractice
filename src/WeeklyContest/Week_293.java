package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class Week_293 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2273.Find%20Resultant%20Array%20After%20Removing%20Anagrams/README.md
    public List<String> removeAnagrams(String[] words) {
        List<String> ans = new ArrayList<>();
        String prev = "";
        for (String w : words) {
            char[] cs = w.toCharArray();
            Arrays.sort(cs);
            String t = String.valueOf(cs);
            if (!t.equals(prev)) {
                ans.add(w);
            }
            prev = t;
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-consecutive-floors-without-special-floors/solution/pai-xu-by-endlesscheng-nm1r/
    // 兩個相鄰特殊樓層之間的樓層都是連續的，因此排序後遍歷數組，取最大值作為答案。
    // 代碼實現時可以把 bottom−1 和 top+1 視作兩個特殊樓層，從而簡化代碼邏輯。
    public int maxConsecutive(int bottom, int top, int[] special) {
        // 必須要一間特殊樓層
        if (bottom == top) return 0;
        // 將特殊樓層排序
        Arrays.sort(special);
        int len = special.length;
        // 直接遍歷
        int max = Math.max(top - special[len - 1], special[0] - bottom);
        for (int i = 1; i < len; i++) {
            max = Math.max(max, special[i] - special[i - 1] - 1);
        }
        return max;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2275.Largest%20Combination%20With%20Bitwise%20AND%20Greater%20Than%20Zero/README.md
    // 大於 0，實際上就是要求存在某個二進制位（0-31），滿足所有數字的這一位均為 1。
    // 位運算題目經典技巧：逐個考慮每一個比特位。
    // 假設元素值只有 0 和 1，那麼解法就很簡單了：由於不能選 0（這會導致按位與為 0），選擇所有的 1，答案即為 1 的個數。
    // 將上述結論推廣，考慮每一個比特位，統計這一位上的 1 的個數，取所有個數的最大值作為答案。
    public int largestCombination(int[] candidates) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            int t = 0;
            for (int x : candidates) t += (x >> i) & 1;
            ans = Math.max(ans, t);
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-integers-in-intervals/solution/by-ctysss-qk3g/
    // treeMap的key是每個獨立區間的左端點，在每次add操作的時候，先依次獲取 比right小的區間，如果符合要求就先刪除，
    // 刪除所有符合要求的區間之後，再添加最終的區間
    // 同時維護一個sum表示總區間大小，當刪除區間時，減去對應的數，添加區間時，加上對應的數
    class CountIntervals {
        // 統計區間裡面元素個數
        int sum = 0;
        // 存儲所有的不重疊區間左右端點
        TreeMap<Integer, Integer> map = new TreeMap<>();

        public CountIntervals() {

        }

        // 添加一個新的區間[left,right]
        public void add(int left, int right) {
            // 獲取map中最大的 <= right的鍵
            Integer L = map.floorKey(right);
            // l與r記錄當前合並的區間左右邊界:初始化為新加入的區間大小
            int l = left, r = right;
            // right左邊存在某個區間 && 該區間右邊界大於等於當前合並後的左邊界->可以合並
            while (L != null && map.get(L) >= l) {
                // 維護當前統計區間的最左邊界與最右邊界
                l = Math.min(l, L);
                r = Math.max(r, map.get(L));
                // 合並了區間L,更新sum
                sum -= map.get(L) - L + 1;
                // map去除該key
                map.remove(L);
                // 尋找下一個要合並的區間
                L = map.floorKey(right);
            }
            // 合並後的區間加入map並統計sum
            sum += r - l + 1;
            map.put(l, r);
        }

        public int count() {
            return sum;
        }
    }


    // 動態開點線段樹
    // https://zhuanlan.zhihu.com/p/246255556
    // https://leetcode.cn/problems/count-integers-in-intervals/solution/by-endlesscheng-clk2/
    class CountIntervals2 {
        CountIntervals2 left, right;
        int l, r, cnt;

        public CountIntervals2() {
            l = 1;
            r = (int) 1e9;
        }

        CountIntervals2(int l, int r) {
            this.l = l;
            this.r = r;
        }

        public void add(int L, int R) { // 為方便區分變量名，將遞歸中始終不變的入參改為大寫（視作常量）
            if (cnt == r - l + 1) return; // 當前節點已被完整覆蓋，無需執行任何操作
            if (L <= l && r <= R) { // 當前節點已被區間 [L,R] 完整覆蓋，不再繼續遞歸
                cnt = r - l + 1;
                return;
            }
            int mid = (l + r) / 2;
            if (left == null) left = new CountIntervals2(l, mid); // 動態開點
            if (right == null) right = new CountIntervals2(mid + 1, r); // 動態開點
            if (L <= mid) left.add(L, R);
            if (mid < R) right.add(L, R);
            cnt = left.cnt + right.cnt;
        }

        public int count() {
            return cnt;
        }
    }
}

