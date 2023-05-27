package WeeklyContest;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;

public class Week_294 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2278.Percentage%20of%20Letter%20in%20String/README.md
    public int percentageLetter(String s, char letter) {
        int cnt = 0;
        for (char c : s.toCharArray()) {
            if (c == letter) {
                ++cnt;
            }
        }
        return cnt * 100 / s.length();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2279.Maximum%20Bags%20With%20Full%20Capacity%20of%20Rocks/README.md
    public int maximumBags(int[] capacity, int[] rocks, int additionalRocks) {
        int n = capacity.length;
        int[] d = new int[n];
        for (int i = 0; i < n; i++) {
            d[i] = capacity[i] - rocks[i];
        }
        Arrays.sort(d);
        int ans = 0;
        for (int v : d) {
            if (v <= additionalRocks) {
                ans++;
                additionalRocks -= v;
            } else break;
        }
        return ans;
    }


    // https://www.bilibili.com/video/BV1RY4y157nW/
    // https://leetcode.cn/problems/minimum-lines-to-represent-a-line-chart/solution/pan-duan-san-dian-gong-xian-chao-jian-ji-0n0o/
    public int minimumLines(int[][] stockPrices) {
        // 按照days[i]升序排序
        Arrays.sort(stockPrices, Comparator.comparingInt(a -> a[0]));
        int len = stockPrices.length;
        int i = 0, cnt = 0;
        while (i < len - 2) {
            // 記錄好該段起始索引
            int m = i;
            while (m < len - 2 && getMul(stockPrices[i], stockPrices[i + 1], stockPrices[m + 2])) m++;
            // 下一段開始位置
            i = m + 1;
            cnt++;
        }
        // 結束時有兩種情形:1.最後一段有2個點;2.最後一段有2個點以上
        // 情形1結束時i==len-2,最後一段沒統計,因此cnt++
        // 情形2結束時i==len-1,最後一段已經統計上了
        if (i == len - 2) cnt++;
        return cnt;
    }


    // 求3點之間的交叉積是否相等
    private boolean getMul(int[] a, int[] b, int[] c) {
        return (long) (c[1] - b[1]) * (b[0] - a[0]) == (long) (b[1] - a[1]) * (c[0] - b[0]);
    }


    // https://leetcode.cn/problems/sum-of-total-strength-of-wizards/solution/dan-diao-zhan-qian-zhui-he-de-qian-zhui-d9nki/
    public int totalStrength(int[] strength) {
        int mod = (int) 1e9 + 7;
        int n = strength.length;
        int[] left = new int[n];  // left[i] 為左側嚴格小於 strength[i] 的最近元素位置（不存在時為 -1）
        int[] right = new int[n]; // right[i] 為右側小於等於 strength[i] 的最近元素位置（不存在時為 n）

        Arrays.fill(right, n);
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 哨兵
        for (int i = 0; i < n; i++) {
            while (st.size() > 1 && strength[st.peek()] >= strength[i]) {
                right[st.pop()] = i;
            }
            left[i] = st.peek();
            st.push(i);
        }

        // 前綴和的前綴和思路參考:
        // https://leetcode.cn/problems/sum-of-total-strength-of-wizards/solution/by-wen-rou-yi-dao-123-xy2d/
        // 1、首先是單調棧求得每一個strength[i]左右兩邊第一個小於自己的strength的位置，這樣就找到了strength[i]作為最小值的子數組范圍。
        // 2、本題的難點是接下來如何快速求和。
        // 對於數組[1,5,4,3,6,7,8,2]，我們考察3這個數，i = index(3)，
        // 它左右兩邊第一個小於自己的數是1和2，所以[5,4,3,6,7,8]范圍內所有包含3的子數組，
        // 它們的最小值都是3。左邊界left = index(5)，右邊界right = index(8)。具體的，這些子數組有：
        // [3] -----------> s[3] - s[2]
        // [3,6] ---------> s[4] - s[2]
        // [3,6,7] ------> s[5] - s[2]
        // [3,6,7,8] -----> s[6] - s[2]
        // [4,3] ---------> s[3] - s[1]
        // [4,3,6] -------> s[4] - s[1]
        // [4,3,6,7] -----> s[5] - s[1]
        // [4,3,6,7,8] ---> s[6] - s[1]
        // [5,4,3] -------> s[3] - s[0]
        // [5,4,3,6] -----> s[4] - s[0]
        // [5,4,3,6,7] ---> s[5] - s[0]
        // [5,4,3,6,7,8] -> s[6] - s[0]
        // 記s[i]表示strength[0,i]的前綴和，那麼上面所有子數組的和就是：
        // a * (s[3] + s[4] + s[5] + s[6]) - b * (s[0] + s[1] + s[2])
        // 其中 a = 3 = i - left + 1，b = 4 = right - i + 1
        // 而(s[3] + s[4] + s[5] + s[6])、(s[0] + s[1] + s[2])就是對s[i]數組求和的過程，如果記ss[i]是s[i]數組的前綴和，那麼：
        // (s[3] + s[4] + s[5] + s[6]) = ss[6] - ss[2] = ss[right] - ss[i - 1] (從s[i]一直加到s[right])
        // (s[0] + s[1] + s[2]) = ss[2] - ss[-1] = ss[i - 1] - ss[left - 2] （從s[left - 1]一直加到s[i - 1]）
        // 綜上，總和sum = (i - left + 1) * (ss[right] - ss[i - 1]) - (right - i + 1) * (ss[i - 1] - ss[left - 2])
        // 所以第i個巫師貢獻的總力量 = strength[i] * sum
        long s = 0L; // 前綴和
        int[] ss = new int[n + 2]; // 前綴和的前綴和
        for (int i = 1; i <= n; i++) {
            s += strength[i - 1];
            ss[i + 1] = (int) ((ss[i] + s) % mod); // 注意取模後，下面計算兩個 ss 相減，結果可能為負
        }


        long ans = 0L;
        for (int i = 0; i < n; i++) {
            int l = left[i] + 1, r = right[i] - 1; // [l,r] 左閉右閉
            long tot = ((long) (i - l + 1) * (ss[r + 2] - ss[i + 1]) - (long) (r - i + 1) * (ss[i + 1] - ss[l])) % mod;
            ans = (ans + strength[i] * tot) % mod; // 累加貢獻
        }
        return (int) (ans + mod) % mod; // 防止算出負數
    }
}

