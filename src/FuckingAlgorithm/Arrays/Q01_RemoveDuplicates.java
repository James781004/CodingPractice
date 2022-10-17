package FuckingAlgorithm.Arrays;

import java.util.Stack;

public class Q01_RemoveDuplicates {
//    https://leetcode.cn/problems/remove-duplicates-from-sorted-array/
//            26. 刪除有序數組中的重復項
//    給你一個 升序排列 的數組 nums ，請你 原地 刪除重復出現的元素，使每個元素 只出現一次 ，返回刪除後數組的新長度。元素的 相對順序 應該保持 一致 。
//
//    由於在某些語言中不能改變數組的長度，所以必須將結果放在數組nums的第一部分。更規范地說，如果在刪除重復項之後有 k 個元素，那麼 nums 的前 k 個元素應該保存最終結果。
//
//    將最終結果插入 nums 的前 k 個位置後返回 k 。
//
//    不要使用額外的空間，你必須在 原地 修改輸入數組 並在使用 O(1) 額外空間的條件下完成。


    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) return 0;
        int slow = 0, fast = 0;

        // 慢指針 slow 走在後面，快指針 fast 走在前面探路
        // 找到一個不重復的元素就賦值給 slow 並讓 slow 前進一步
        while (fast < nums.length) {
            if (nums[fast] != nums[slow]) {
                slow++;
                // 維護 nums[0..slow] 無重復
                nums[slow] = nums[fast];
            }
            fast++;
        }

        return slow + 1; // 數組長度為索引 + 1
    }


    // LC83 刪除排序鏈表中的重復元素
    class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode slow = head, fast = head;
        while (fast != null) {
            if (fast.val != slow.val) {
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }

        // 斷開與後面重復元素的連接
        slow.next = null;
        return head;
    }

//    (兩題相同)
    //    https://leetcode.cn/problems/remove-duplicate-letters/
//    316. 去除重復字母
//    給你一個字符串 s ，請你去除字符串中重復的字母，使得每個字母只出現一次。需保證 返回結果的字典序最小（要求不能打亂其他字符的相對位置）。
    //    https://leetcode.cn/problems/smallest-subsequence-of-distinct-characters/
//    1081. 不同字符的最小子序列
//    返回 s 字典序最小的子序列，該子序列包含 s 的所有不同字符，且只包含一次。

    public String removeDuplicateLetters(String s) {
        Stack<Character> stk = new Stack<>();

        // 維護一個計數器記錄字符串中字符的數量
        // 因為輸入為 ASCII 字符，大小 256 夠用了
        int[] count = new int[256];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i)]++;
        }

        boolean[] inStack = new boolean[256];
        for (char c : s.toCharArray()) {
            // 每遍歷過一個字符，都將對應的計數減一
            count[c]--;

            // 如果字符 c 存在棧中，直接跳過
            if (inStack[c]) continue;

            // 插入之前，和之前的元素比較一下大小
            // 如果字典序比前面的小，pop 前面的元素
            while (!stk.isEmpty() && stk.peek() > c) {
                // 若之後不存在棧頂元素了，則停止 pop
                if (count[stk.peek()] == 0) break;

                // 若之後還有，則可以 pop
                // 彈出棧頂元素，並把該元素標記為不在棧中
                inStack[stk.pop()] = false;
            }

            stk.push(c);
            inStack[c] = true;
        }

        StringBuilder sb = new StringBuilder();
        while (!stk.empty()) {
            sb.append(stk.pop());
        }
        return sb.reverse().toString();
    }
}
