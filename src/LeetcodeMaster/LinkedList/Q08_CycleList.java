package LeetcodeMaster.LinkedList;

public class Q08_CycleList {
//    141. 環形鏈表
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0141.%E7%8E%AF%E5%BD%A2%E9%93%BE%E8%A1%A8.md
//
//    給定一個鏈表，判斷鏈表中是否有環。
//
//    如果鏈表中有某個節點，可以通過連續跟蹤 next 指針再次到達，則鏈表中存在環。 為了表示給定鏈表中的環，我們使用整數 pos 來表示鏈表尾連接到鏈表中的位置（索引從 0 開始）。 如果 pos 是 -1，則在該鏈表中沒有環。注意：pos 不作為參數進行傳遞，僅僅是為了標識鏈表的實際情況。
//
//    如果鏈表中存在環，則返回 true 。 否則，返回 false 。


    class ListNode {
        private ListNode next;
        private int val;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        // 空鏈表、單節點鏈表一定不會有環
        while (fast != null && fast.next != null) {
            fast = fast.next.next; // 快指針，一次移動兩步
            slow = slow.next;      // 慢指針，一次移動一步
            if (fast == slow) {   // 快慢指針相遇，表明有環
                return true;
            }
        }
        return false; // 正常走到鏈表末尾，表明沒有環
    }


}
