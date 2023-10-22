package Grind.Ch10_LinkedList;

public class Q08_OddEvenLinkedList {
    // https://leetcode.cn/problems/odd-even-linked-list/solutions/483215/kuai-lai-wu-nao-miao-dong-qi-ou-lian-biao-by-sweet/
    public ListNode oddEvenList(ListNode head) {
        // 分別定義奇偶鏈表的 虛擬頭節點 和 尾節點
        ListNode oddHead = new ListNode();
        ListNode oddTail = oddHead;
        ListNode evenHead = new ListNode();
        ListNode evenTail = evenHead;

        // 遍歷原鏈表，根據 isOdd 標識位決定將當前結點插入到奇鏈表還是偶鏈表（尾插法）
        boolean isOdd = true;
        while (head != null) {
            if (isOdd) {
                oddTail.next = head;
                oddTail = oddTail.next;
            } else {
                evenTail.next = head;
                evenTail = evenTail.next;
            }
            head = head.next;
            isOdd = !isOdd;
        }
        // 將奇鏈表後面拼接上偶鏈表，並將偶鏈表的next設置為null
        oddTail.next = evenHead.next;
        evenTail.next = null;
        return oddHead.next;
    }
}
