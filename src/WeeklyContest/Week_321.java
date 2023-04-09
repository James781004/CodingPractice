package WeeklyContest;

import java.util.*;

public class Week_321 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2485.Find%20the%20Pivot%20Integer/README.md
    public int pivotInteger(int n) {
        for (int i = 0; i < 1000; i++) {
            if ((1 + i) * i == (i + n) * (n - i + 1)) return i;
        }
        return -1;
    }


    // O(1)解法
    // https://leetcode.cn/problems/find-the-pivot-integer/solution/o1-zuo-fa-by-endlesscheng-571j/
    public int pivotInteger2(int n) {
        int m = n * (n + 1) / 2;
        int x = (int) Math.sqrt(m);
        if (x * x == m) {
            return x;
        } else {
            return -1;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2486.Append%20Characters%20to%20String%20to%20Make%20Subsequence/README.md
    public int appendCharacters(String s, String t) {
        int m = s.length(), n = t.length();
        for (int i = 0, j = 0; j < n; j++) {
            while (i < m && s.charAt(i) != t.charAt(j)) {
                i++;  // 當前下標位置字符不同，i後移
            }

            // i到底，說明s之中找不到t[j]，返回t中剩餘字符數即可
            // 否則，將指針 i 和 j 同時後移，繼續遍歷字符串 t
            if (i++ == m) return n - j;
        }
        return 0;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2487.Remove%20Nodes%20From%20Linked%20List/README.md
    public ListNode removeNodes(ListNode head) {
        // 先將鏈表中的節點值存入數組，然後遍歷數組
        List<Integer> nums = new ArrayList<>();
        while (head != null) {
            nums.add(head.val);
            head = head.next;
        }

        // 維護一個從棧底到棧頂單調遞減的棧，
        // 如果當前元素比棧頂元素大，則將棧頂元素出棧，
        // 直到當前元素小於等於棧頂元素，將當前元素入棧
        Deque<Integer> stk = new ArrayDeque<>();
        for (int v : nums) {
            while (!stk.isEmpty() && stk.peek() < v) {
                stk.pop();
            }
            stk.push(v);
        }

        // 將棧中的元素逆序，構造得到的鏈表即為答案
        ListNode dummy = new ListNode(-1);
        head = dummy;
        while (!stk.isEmpty()) {
            head.next = new ListNode(stk.pollLast());
            head = head.next;
        }
        return dummy.next;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    // https://leetcode.cn/problems/count-subarrays-with-median-k/solution/deng-jie-zhuan-huan-pythonjavacgo-by-end-5w11/
    public int countSubArrays(int[] nums, int k) {
        int pos = 0, n = nums.length;
        while (nums[pos] != k) ++pos;  // 尋找k位置

        // 由於題目保證 nums 中的整數互不相同，
        // 「k 是長為奇數的子數組的中位數」等價於「子數組中小於 k 的數的個數 = 大於 k 的數的個數」。
        //
        // 這相當於「左側小於 + 右側小於 = 左側大於 + 右側大於」。
        // 變形得到「左側小於 − 左側大於 = 右側大於 − 右側小於」。
        //
        // 為了方便計算，把這四類數字等價轉換：
        // 左側小於：在 k 左側且比 k 小的視作 1；
        // 左側大於：在 k 左側且比 k 大的視作 −1；
        // 右側大於：在 k 右側且比 k 大的視作 1；
        // 右側小於：在 k 右側且比 k 小的視作 −1。
        // 此外，把 k 視作 0。

        // i=pos 的時候 x 是 0，直接記到 cnt 中，這樣下面不是大於 k 就是小於 k
        Map<Integer, Integer> cnt = new HashMap<>();
        cnt.put(0, 1);
        for (int i = pos - 1, x = 0; i >= 0; i--) { // 從 pos-1 開始累加 x
            x += nums[i] < k ? 1 : -1; // 左側小於k就+1，反之-1
            cnt.merge(x, 1, Integer::sum);  // 計算左側小於 - 左側大於
        }


        // 對於子數組長為偶數的情況，
        // 「k 是長為偶數的子數組的中位數」等價於
        // 「左側小於 =+ 右側小於 == 左側大於 + 右側大於 −1」，
        // 即「左側小於 − 左側大於 = 右側大於 − 右側小於 −1」。
        // 相比奇數的情況，等號右側多了個 −1，
        // 那麼接著上面的「右側大於 − 右側小於」的值 x 來說，
        // cnt[x−1] 就是該右端點對應的符合題目要求的長為偶數的子數組個數。
        // 累加這些 cnt[x−1]，就是子數組長為偶數時的答案。

        // i=pos 的時候 x 是 0，直接加到答案中，這樣下面不是大於 k 就是小於 k
        int ans = cnt.get(0) + cnt.getOrDefault(-1, 0);
        for (int i = pos + 1, x = 0; i < n; i++) { // 從 pos+1 開始累加 x
            x += nums[i] > k ? 1 : -1;    // 右側大於k就+1，反之-1
            ans += cnt.getOrDefault(x, 0) + cnt.getOrDefault(x - 1, 0);
        }
        return ans;
    }

}
