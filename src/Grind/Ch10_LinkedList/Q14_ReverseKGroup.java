package Grind.Ch10_LinkedList;

public class Q14_ReverseKGroup {
    // https://leetcode.cn/problems/reverse-nodes-in-k-group/solutions/10416/tu-jie-kge-yi-zu-fan-zhuan-lian-biao-by-user7208t/
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        // 定義一個假的節點。
        ListNode dummy = new ListNode(0);

        // 假節點的next指向head。
        // dummy->1->2->3->4->5
        dummy.next = head;

        // 初始化pre和end都指向dummy。pre指每次要翻轉的鏈表的頭結點的上一個節點。end指每次要翻轉的鏈表的尾節點
        ListNode pre = dummy;
        ListNode end = dummy;

        while (end.next != null) {
            // 循環k次，找到需要翻轉的鏈表的結尾,這裡每次循環要判斷end是否等於空,因為如果為空，end.next會報空指針異常。
            // dummy->1->2->3->4->5 若k為2，循環2次，end指向2
            for (int i = 0; i < k && end != null; i++) {
                end = end.next;
            }
            // 如果 end==null，即需要翻轉的鏈表的節點數小於k，不執行翻轉。
            if (end == null) {
                break;
            }
            // 先記錄下end.next,方便後面鏈接鏈表
            ListNode next = end.next;
            // 然後斷開鏈表
            end.next = null;
            // 記錄下要翻轉鏈表的頭節點
            ListNode start = pre.next;
            // 翻轉鏈表,pre.next指向翻轉後的鏈表。1->2 變成2->1。 dummy->2->1
            pre.next = reverse(start);
            // 翻轉後頭節點變到最後。通過.next把斷開的鏈表重新鏈接。
            start.next = next;
            // 將pre換成下次要翻轉的鏈表的頭結點的上一個節點。即start
            pre = start;
            // 翻轉結束，將end置為下次要翻轉的鏈表的頭結點的上一個節點。即start
            end = start;
        }
        return dummy.next;
    }


    // 鏈表翻轉
    // 例子：   head： 1->2->3->4
    public ListNode reverse(ListNode head) {
        // 單鏈表為空或只有一個節點，直接返回原單鏈表
        if (head == null || head.next == null) {
            return head;
        }
        // 前一個節點指針
        ListNode preNode = null;
        // 當前節點指針
        ListNode curNode = head;
        // 下一個節點指針
        ListNode nextNode = null;
        while (curNode != null) {
            nextNode = curNode.next; // nextNode 指向下一個節點,保存當前節點後面的鏈表。
            curNode.next = preNode; // 將當前節點next域指向前一個節點   null<-1<-2<-3<-4
            preNode = curNode; // preNode 指針向後移動。preNode指向當前節點。
            curNode = nextNode; // curNode指針向後移動。下一個節點變成當前節點
        }
        return preNode;
    }
}
