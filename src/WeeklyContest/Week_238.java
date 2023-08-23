package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Week_238 {
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1837.Sum%20of%20Digits%20in%20Base%20K/README.md
    public int sumBase(int n, int k) {
        int ans = 0;
        while (n != 0) {
            ans += n % k;
            n /= k;
        }
        return ans;
    }


    // https://leetcode.cn/problems/frequency-of-the-most-frequent-element/solutions/742905/jian-dan-yi-dong-zui-zi-ran-de-chu-li-lu-9i9a/
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1838.Frequency%20of%20the%20Most%20Frequent%20Element/README.md
    // 方法一：排序 + 滑動窗口
    // 先對數組 nums 進行排序，然後枚舉每個數作為最高頻元素，
    // 用滑動窗口維護下標 l 到 r 的數都增加到 nums[r] 的操作次數。
    // 如果操作次數大於 k ，則窗口左端右移，直到操作次數小於等於 k。
    // 這樣就可以求出以每個數為最高頻元素的最大頻數。
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 1;
        int j = 0;
        long a = 0;
        for (int i = 1; i < nums.length; i++) {
            // 計算區間內每個值，與區間內最後一個值相差的總和
            // 每一次區間計算時，之前的區間都已經計算過了，都是等於當前區間內最後一個數的值，
            // 所以乘以(i-j)就讓區間內每個值都相等了
            a += (long) (nums[i] - nums[i - 1]) * (i - j);

            // 如果超過目標值
            while (a > k) {
                // 那麼就減去區間內最左側的值與最右側值的差距。
                // 然後再讓區間左側向右移動一位，相等於整個區間縮小了一位距離，在縮小的區間內再判斷是否滿足要求
                a -= nums[i] - nums[j];
                j++;
            }
            ans = Math.max(i - j + 1, ans);
        }
        return ans;
    }


    public int maxFrequency2(int[] nums, int k) {
        /*
        排序+滑窗+前綴和+二分:
        1.因為要求的是頻數,也就是要操作使得最接近某個數的數字優先變成該數字,因此用到排序
        2.滑窗是固定窗口右邊界,尋找左邊界的過程
        3.前綴和用於求解某段區間需要填充的操作次數,ss = nums[r] * len - sum[r - k + 1, r]
        4.二分用於尋找適合符合要求的窗口長度的最大值->最大頻數
         */
        Arrays.sort(nums);
        int len = nums.length;
        int[] sum = new int[len + 1];
        for (int i = 0; i < len; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int l = 0, r = len; // 窗口長度
        while (l < r) {
            int mid = l + (r - l + 1) / 2;
            if (check(mid, k, len, nums, sum)) {
                l = mid;
            } else {
                r = mid - 1;
            }
        }
        return l;   // l==r
    }

    // nums內是否存在以curLen為長度的窗口,窗口內可以用k填充成完全相等的元素
    private boolean check(int curLen, int k, int len, int[] nums, int[] sum) {
        // 遍歷所有長度為curLen的窗口
        for (int i = 0; i + curLen - 1 < len; i++) {
            int j = i + curLen - 1; // 窗口右端索引
            int ss = nums[j] * curLen - (sum[j + 1] - sum[i]);  // 要填充的部分
            if (k >= ss) return true;   // k可以滿足要填充的部分
        }
        return false;   // 無法完成填充
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1839.Longest%20Substring%20Of%20All%20Vowels%20in%20Order/README.md
    // 雙指針 + 模擬
    // 可以先將字符串 word 做個轉化，
    // 比如對於 word="aaaeiouu"，可以將其轉化為數據項 ('a', 3), ('e', 1), ('i', 1), ('o', 1), ('u', 2)，
    // 存放在數組 arr 中。
    // 其中每個數據項的第一個元素表示元音字母，第二個元素表示該元音字母連續出現的次數。
    // 這部分轉化可以通過雙指針來實現。
    // 接下來遍歷數組 arr，每次取相鄰的 5 個數據項，判斷這些數據項中的元音字母是否分別為 'a', 'e', 'i', 'o', 'u'，
    // 如果是，則計算這 5 個數據項中元音字母的總次數，即為當前的美麗子字符串的長度，更新答案的最大值即可。
    public int longestBeautifulSubstring(String word) {
        int n = word.length();
        List<Node> arr = new ArrayList<>();
        for (int i = 0; i < n; ) {
            int j = i;
            while (j < n && word.charAt(j) == word.charAt(i)) {
                ++j;
            }
            arr.add(new Node(word.charAt(i), j - i));
            i = j;
        }
        int ans = 0;
        for (int i = 0; i < arr.size() - 4; ++i) {
            Node a = arr.get(i), b = arr.get(i + 1), c = arr.get(i + 2), d = arr.get(i + 3),
                    e = arr.get(i + 4);
            if (a.c == 'a' && b.c == 'e' && c.c == 'i' && d.c == 'o' && e.c == 'u') {
                ans = Math.max(ans, a.v + b.v + c.v + d.v + e.v);
            }
        }
        return ans;
    }


    class Node {
        char c;
        int v;

        Node(char c, int v) {
            this.c = c;
            this.v = v;
        }
    }


    // https://leetcode.cn/problems/longest-substring-of-all-vowels-in-order/solutions/742769/pan-duan-zi-fu-chong-lei-he-da-xiao-by-a-xfmd/
    // 判斷字符種類和大小，這5個元音字符是升序的且字符串中僅包含這些元音字母，
    // 所以當字符串滿足整體升序的同時字符種類達到5，那麼這個字符串就是美麗的。
    public int longestBeautifulSubstring2(String word) {
        int ans = 0, type = 1, len = 1;
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) >= word.charAt(i - 1)) len++; // 更新當前字符串長度
            if (word.charAt(i) > word.charAt(i - 1)) type++; // 更新當前字符種類
            if (word.charAt(i) < word.charAt(i - 1)) {
                type = 1;
                len = 1;
            } // 當前字符串不美麗，從當前字符重新開始
            if (type == 5) ans = Math.max(ans, len);  // 字符種類達到5，更新最大字符串
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-building-height/solutions/742720/zui-gao-jian-zhu-gao-du-by-leetcode-solu-axbb/
    // https://github.com/doocs/leetcode/blob/main/solution/1800-1899/1840.Maximum%20Building%20Height/README.md
    public int maxBuilding(int n, int[][] restrictions) {
        List<int[]> r = new ArrayList<>();
        r.addAll(Arrays.asList(restrictions));
        r.add(new int[]{1, 0}); // 增加左端點的限制 (1, 0)
        Collections.sort(r, (a, b) -> a[0] - b[0]);
        if (r.get(r.size() - 1)[0] != n) {
            r.add(new int[]{n, n - 1}); // 增加右端點的限制(n, n - 1)
        }
        int m = r.size();

        // 從左向右傳遞限制
        for (int i = 1; i < m; ++i) {
            int[] a = r.get(i - 1), b = r.get(i);
            b[1] = Math.min(b[1], a[1] + b[0] - a[0]);
        }

        // 從右向左傳遞限制
        for (int i = m - 2; i > 0; --i) {
            int[] a = r.get(i), b = r.get(i + 1);
            a[1] = Math.min(a[1], b[1] + b[0] - a[0]);
        }

        // 計算 r[i][0] 和 r[i][1] 之間的建築的最大高度
        int ans = 0;
        for (int i = 0; i < m - 1; ++i) {
            int[] a = r.get(i), b = r.get(i + 1);
            int t = (a[1] + b[1] + b[0] - a[0]) / 2;
            ans = Math.max(ans, t);
        }
        return ans;
    }
}

