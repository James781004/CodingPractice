package WeeklyContest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Week_268 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2078.Two%20Furthest%20Houses%20With%20Different%20Colors/README.md
    public int maxDistance(int[] colors) {
        int ans = 0, n = colors.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (colors[i] != colors[j]) {
                    ans = Math.max(ans, Math.abs(i - j));
                }
            }
        }
        return ans;
    }


    // 貪心做法
    // https://leetcode.cn/problems/two-furthest-houses-with-different-colors/solution/on-zuo-fa-by-endlesscheng-an8b/
    // 判斷首尾
    // 首尾不滿足條件同時收縮
    // 判斷不同元素到兩頭的距離
    // 返回較大的值
    public int maxDistance2(int[] colors) {
        int low = 0;
        int high = colors.length - 1;
        int edge = colors[0];
        if (colors[low] != colors[high]) return high - low;
        int i = 1, j = high - 1;
        while (edge == colors[i]) i++;
        while (edge == colors[j]) j--;
        return Math.max(j - 0, high - i);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2079.Watering%20Plants/README.md
    public int wateringPlants(int[] plants, int capacity) {
        int ans = 0, cap = capacity;
        for (int i = 0; i < plants.length; ++i) {
            if (cap >= plants[i]) {
                cap -= plants[i];
                ++ans;
            } else {
                ans += (i * 2 + 1);
                cap = capacity - plants[i];
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2080.Range%20Frequency%20Queries/README.md
    class RangeFreqQuery {

        // 數值為鍵，出現下標數組為值的哈希表
        private Map<Integer, List<Integer>> mp = new HashMap<>();

        public RangeFreqQuery(int[] arr) {
            // 順序遍歷數組初始化哈希表
            for (int i = 0; i < arr.length; ++i) {
                mp.computeIfAbsent(arr[i], k -> new ArrayList<>()).add(i);
            }
        }

        public int query(int left, int right, int value) {
            // 查找對應的出現下標數組，不存在則為空
            if (!mp.containsKey(value)) {
                return 0;
            }

            // 兩次二分查找計算子數組內出現次數
            List<Integer> arr = mp.get(value);
            int l = search(arr, left - 1);
            int r = search(arr, right);
            return r - l;
        }

        private int search(List<Integer> arr, int val) {
            int left = 0, right = arr.size();
            while (left < right) {
                int mid = (left + right) >> 1;
                if (arr.get(mid) > val) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }


    // https://leetcode.cn/problems/sum-of-k-mirror-numbers/solution/mei-ju-hui-wen-shu-de-fang-fa-hui-zong-b-7sc4/
    public long kMirror(int k, int n) {
        long ans = 0;
        // 根據奇偶枚舉回文數
        for (int lo = 1, hi = 10; ; lo = hi, hi *= 10) {
            for (int i = 0; i < 2; i++) {
                for (int j = lo; j < hi; j++) {
                    long x = j, y = i % 2 == 0 ? j / 10 : j;
                    for (; y != 0; y /= 10) x = x * 10 + y % 10;
                    if (isGood(x, k)) {
                        ans += x;
                        if (--n == 0) return ans;
                    }
                }
            }
        }
    }


    public long kMirror2(int k, int n) {
        long ans = 0;
        // 根據長度枚舉回文數
        for (int len = 1; ; len++) {
            int lo = (int) Math.pow(10, (len - 1) / 2);
            int hi = (int) Math.pow(10, (len + 1) / 2);
            for (int i = lo; i < hi; i++) {
                long x = i, y = len % 2 == 1 ? i / 10 : i;
                for (; y != 0; y /= 10) x = x * 10 + y % 10;
                if (isGood(x, k)) {
                    ans += x;
                    if (--n == 0) return ans;
                }
            }
        }
    }

    public long kMirror3(int k, int n) {
        long x = 1, ans = 0;
        while (n != 0) {
            if (isGood(x, k)) {
                ans += x;
                n--;
            }
            x = nextPalindrome(x);
        }
        return ans;
    }

    // 返回大於回文數 x 的下一個回文數, 原理就是對回文根的個位加一, 再以中心為軸做一個對稱
    public long nextPalindrome(long x) {
        String s = x + "";
        int len = s.length();
        long t = Long.parseLong(s.substring(0, (len + 1) / 2)); // t 是回文根
        // 最高位是否進位, 如果進位則下一個回文數會變為 100...001 的形式 (舉例:999 -> 1001)
        if ((t + 1 + "").length() != (len + 1) / 2) return (long) Math.pow(10, len) + 1;
        StringBuilder sb = new StringBuilder(t + 1 + "");
        // 舉例:回文根 101 -> 10101, 回文根 100 -> 100001
        for (int i = len / 2 - 1; i >= 0; i--) sb.append(sb.charAt(i));
        return Long.parseLong(sb.toString());
    }


    // 判斷十進制數 x 在 k 進制下是否回文
    public boolean isGood(long x, int k) {
        long t = x, y = 0;
        while (t != 0) {
            y = y * k + t % k;
            t /= k;
        }
        return x == y;
    }


    // 打表
    long ans[][] = {{1L, 4L, 9L, 16L, 25L, 58L, 157L, 470L, 1055L, 1772L, 9219L, 18228L, 33579L, 65802L, 105795L, 159030L, 212865L, 286602L, 872187L, 2630758L, 4565149L, 6544940L, 9674153L, 14745858L, 20005383L, 25846868L, 39347399L, 759196316L, 1669569335L, 2609044274L}, {1L, 3L, 7L, 15L, 136L, 287L, 499L, 741L, 1225L, 1881L, 2638L, 31730L, 80614L, 155261L, 230718L, 306985L, 399914L, 493653L, 1342501L, 2863752L, 5849644L, 9871848L, 14090972L, 18342496L, 22630320L, 28367695L, 36243482L, 44192979L, 71904751L, 155059889L}, {1L, 3L, 6L, 11L, 66L, 439L, 832L, 1498L, 2285L, 3224L, 11221L, 64456L, 119711L, 175366L, 233041L, 739646L, 2540727L, 4755849L, 8582132L, 12448815L, 17500320L, 22726545L, 27986070L, 33283995L, 38898160L, 44577925L, 98400760L, 721411086L, 1676067545L, 53393239260L}, {1L, 3L, 6L, 10L, 16L, 104L, 356L, 638L, 1264L, 1940L, 3161L, 18912L, 37793L, 10125794L, 20526195L, 48237967L, 78560270L, 126193944L, 192171900L, 1000828708L, 1832161846L, 2664029984L, 3500161622L, 4336343260L, 6849225412L, 9446112364L, 12339666346L, 19101218022L, 31215959143L, 43401017264L}, {1L, 3L, 6L, 10L, 15L, 22L, 77L, 188L, 329L, 520L, 863L, 1297L, 2074L, 2942L, 4383L, 12050L, 19827L, 41849L, 81742L, 156389L, 325250L, 1134058L, 2043967L, 3911648L, 7009551L, 11241875L, 15507499L, 19806423L, 24322577L, 28888231L}, {1L, 3L, 6L, 10L, 15L, 21L, 29L, 150L, 321L, 563L, 855L, 17416L, 83072L, 2220384L, 6822448L, 13420404L, 20379000L, 29849749L, 91104965L, 321578997L, 788407661L, 1273902245L, 1912731081L, 2570225837L, 3428700695L, 29128200347L, 69258903451L, 115121130305L, 176576075721L, 241030621167L}, {1L, 3L, 6L, 10L, 15L, 21L, 28L, 37L, 158L, 450L, 783L, 1156L, 1570L, 2155L, 5818L, 14596L, 27727L, 41058L, 67520L, 94182L, 124285L, 154588L, 362290L, 991116L, 1651182L, 3148123L, 5083514L, 7054305L, 11253219L, 66619574L}, {1L, 3L, 6L, 10L, 15L, 21L, 28L, 36L, 227L, 509L, 882L, 1346L, 1901L, 2547L, 3203L, 10089L, 35841L, 63313L, 105637L, 156242L, 782868L, 2323319L, 4036490L, 5757761L, 7586042L, 9463823L, 11349704L, 13750746L, 16185088L, 18627530L}};

    public long kMirror4(int k, int n) {
        return ans[k - 2][n - 1];
    }
}

