package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Week_279 {
    // https://leetcode.cn/problems/sort-even-and-odd-indices-independently/solution/dui-qi-ou-xia-biao-fen-bie-pai-xu-by-lee-31wr/
    public int[] sortEvenOdd(int[] nums) {
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if ((i & 1) == 1) {
                odd.add(nums[i]);
            } else {
                even.add(nums[i]);
            }
        }
        Collections.sort(even);
        Collections.sort(odd, (a, b) -> b - a);
        for (int i = 0; i < even.size(); i++) {
            nums[2 * i] = even.get(i);
        }
        for (int i = 0; i < odd.size(); i++) {
            nums[2 * i + 1] = odd.get(i);
        }
        return nums;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2165.Smallest%20Value%20of%20the%20Rearranged%20Number/README.md
    public long smallestNumber(long num) {
        if (num == 0) {
            return 0;
        }
        int[] cnt = new int[10];
        boolean neg = num < 0;
        num = Math.abs(num);
        while (num != 0) {
            cnt[(int) (num % 10)]++;
            num /= 10;
        }
        long ans = 0;

        // 如果 num 是負數，那麼最小化就是將它的相反數最大化，
        // 也就是將 num 的數字部分按照降序排序，再組合成一個新的數即可。
        if (neg) {
            for (int i = 9; i >= 0; --i) {
                while (cnt[i]-- > 0) {
                    ans = ans * 10 + i;
                }
            }
            return -ans;
        }

        // 題目規定重排數字不能有前導
        // 需要找到一個除了 0 以外的最小數字，將其和 0 進行交換
        if (cnt[0] > 0) {
            for (int i = 1; i < 10; ++i) {
                if (cnt[i] > 0) {
                    ans = i;
                    cnt[i]--;
                    break;
                }
            }
        }
        for (int i = 0; i < 10; ++i) {
            while (cnt[i]-- > 0) {
                ans = ans * 10 + i;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/design-bitset/solution/lan-biao-ji-fa-by-endlesscheng-f07m/
    class Bitset {
        /*
    反向掩膜法:
    我們每次維護一個常規數組存儲01信息的同時，把以O(1)的時間復雜度維護一個反向的掩膜
    這個反向的掩膜在fix，unfix的變更過程中時間復雜度都是O(1)，而flip過程中就相當於把常規數組換成反向掩膜
    時間復雜度也是O(1)，要注意換成反向掩膜同時size也要變
    除了toString()時間復雜度為O(N)，其余方法時間復雜度均是:O(1)，空間復雜度:O(N)
        */

        int[] arr, neg; // arr為正向掩膜，也就是常規要用的，neg為反向掩膜
        int cnt = 0;    // 記錄1的個數有多少
        int size;

        // 用 size 個位初始化 Bitset ，所有位都是 0
        public Bitset(int _size) {
            size = _size;
            arr = new int[size];
            neg = new int[size];
            Arrays.fill(neg, 1);    // neg全部初始化為1
        }

        // 將下標為 idx 的位上的值更新為 1 。如果值已經是 1 ，則不會發生任何改變
        public void fix(int idx) {
            if (arr[idx] == 0) {
                arr[idx] = 1;
                cnt++;  // 1的數目+1
                neg[idx] = 0;   // 注意負掩膜同步更新
            }
        }

        // 將下標為 idx 的位上的值更新為 0 。如果值已經是 0 ，則不會發生任何改變
        public void unfix(int idx) {
            if (arr[idx] == 1) {
                arr[idx] = 0;
                cnt--;  // 1的數目-1
                neg[idx] = 1;
            }
        }

        // 翻轉 Bitset 中每一位上的值。換句話說，所有值為 0 的位將會變成 1 ，反之亦然
        public void flip() {
            // 互換引用
            int[] t = arr;
            arr = neg;
            neg = t;
            cnt = size - cnt;   // 反轉後1的個數就變為了size-cnt
        }

        // 檢查 Bitset 中 每一位 的值是否都是 1 。如果滿足此條件，返回 true ；否則，返回 false
        public boolean all() {
            return cnt == size;
        }

        // 檢查 Bitset 中 是否 至少一位 的值是 1 。如果滿足此條件，返回 true ；否則，返回 false
        public boolean one() {
            return cnt >= 1;
        }

        // 返回 Bitset 中值為 1 的位的 總數
        public int count() {
            return cnt;
        }

        // 返回 Bitset 的當前組成情況。注意，在結果字符串中，第 i 個下標處的字符應該與 Bitset 中的第 i 位一致。
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int num : arr) {
                sb.append(num);
            }
            return sb.toString();
        }
    }


    // https://leetcode.cn/problems/minimum-time-to-remove-all-cars-containing-illegal-goods/solution/qian-hou-zhui-fen-jie-dp-by-endlesscheng-6u1b/
    public int minimumTime(String s) {
        int n = s.length();
        int[] pre = new int[n + 1];
        int[] suf = new int[n + 1];

        // 定義 pre[i] 表示移除前 i 節車廂中的所有違禁貨物車廂所花費的最少時間
        // 當 s[i]=0 時，無需移除車廂，則有 pre[i] = pre[i−1]；
        // 當 s[i]=1 時，可以單獨移除第 i 節車廂，也可以移除前 i 個車廂，二者取最小值，
        // 即 pre[i]=min(pre[i−1]+2,i+1)，這裡 i+1 是因為下標從 0 開始。
        // 注意這邊進行索引偏移處理，把前綴記錄到pre[1...n] (pre[i] 表示移除前 i-1 節車廂)
        for (int i = 0; i < n; i++) {
            pre[i + 1] = s.charAt(i) == '0' ? pre[i] : Math.min(pre[i] + 2, i + 1);
        }

        // 定義 suf[i] 表示移除後 i 節車廂中的所有違禁貨物車廂所花費的最少時間
        // 當 s[i]=0 時，無需移除車廂，則有 suf[i] = suf[i+1]；
        // 當 s[i]=1 時，可以單獨移除第 i 節車廂，也可以移除後 i 個車廂，二者取最小值，
        // 即 suf[i]=min(suf[i+1]+2,n-i)，這裡 n-i 是因為下標從 n - 1 開始。
        // 注意這邊進行索引偏移處理，把後綴記錄到suf[0...n-1] (suf[i] 表示移除後 n - i 節車廂)
        for (int i = n - 1; i >= 0; i--) {
            suf[i] = s.charAt(i) == '0' ? suf[i + 1] : Math.min(suf[i + 1] + 2, n - i);
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 1; i <= n; ++i) {
            ans = Math.min(ans, pre[i] + suf[i]);
        }
        return ans;
    }


    // 優化點：
    // 優化 1：可以先計算 suf，然後在枚舉分割線的同時計算 pre。
    //
    // 優化 2：由於計算 pre 的轉移時當前狀態只和上一個狀態有關，
    // 因此可以使用滾動數組優化，即用一個變量來表示 pre。
    //
    // 優化 3：由於 s[i]=0 時，suf[i] 的值均不會變化，
    // 因此僅需要考慮 s[i]=1 時的 pre[i]+suf[i+1] 的最小值。
    public int minimumTime2(String s) {
        int n = s.length();
        int[] suf = new int[n + 1];
        for (int i = n - 1; i >= 0; i--)
            suf[i] = s.charAt(i) == '0' ? suf[i + 1] : Math.min(suf[i + 1] + 2, n - i);
        int ans = suf[0];
        int pre = 0;
        for (int i = 0; i < n; i++)
            if (s.charAt(i) == '1') {
                pre = Math.min(pre + 2, i + 1);
                ans = Math.min(ans, pre + suf[i + 1]);
            }
        return ans;
    }


    // 進一步優化，一次遍歷：
    // 由於計算的是「移除前綴 + 移除分割線左側某些車廂 + (分割線) + 移除分割線右側某些車廂 + 移除後綴」的最少花費，
    // 其中「移除分割線左側某些車廂 + 移除分割線右側某些車廂」都是在移除中間的某些車廂，因此這是可以合並的，
    // 不妨合並到分割線左側，即計算「移除前綴 + 移除分割線左側某些車廂 + (分割線) + 移除後綴」的最少花費。
    // 合並後，計算 pre 的過程不變，而 suf 就僅為移除後綴所有車廂的花費了，這可以直接用下標計算出來。
    // 因此可以省略 suf 的計算流程，直接一次遍歷計算出答案。
    public int minimumTime3(String s) {
        int n = s.length();
        int ans = n;
        int pre = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') pre = Math.min(pre + 2, i + 1);
            ans = Math.min(ans, pre + n - 1 - i);
        }
        return ans;
    }
}

