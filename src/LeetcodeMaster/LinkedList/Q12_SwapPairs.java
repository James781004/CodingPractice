package LeetcodeMaster.LinkedList;

public class Q12_SwapPairs {
//    24. 兩兩交換鏈表中的節點
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0024.%E4%B8%A4%E4%B8%A4%E4%BA%A4%E6%8D%A2%E9%93%BE%E8%A1%A8%E4%B8%AD%E7%9A%84%E8%8A%82%E7%82%B9.md
//
//    給定一個鏈表，兩兩交換其中相鄰的節點，並返回交換後的鏈表。
//
//    你不能只是單純的改變節點內部的值，而是需要實際的進行節點交換。


    class ListNode {
        private ListNode next;
        private int val;

        public ListNode(int val) {
            this.val = val;
        }
    }

    // 遞歸版本
    public ListNode swapPairs(ListNode head) {
        // 遞歸的終止條件
        if (head == null || head.next == null) {
            return head;
        }

        // 假設鏈表是 1->2->3->4
        // 這句就先保存節點2
        ListNode tmp = head.next;

        // 繼續遞歸，處理節點3->4
        // 當遞歸結束返回後，就變成了4->3
        // 於是head節點就指向了4，變成1->4->3
        head.next = swapPairs(tmp.next);

        // 將2節點指向1，變成2->1->4->3
        tmp.next = head;
        return tmp;
    }


    // 虛擬頭結點
    public ListNode swapPairs1(ListNode head) {
        //增加一個特殊節點方便處理
        ListNode p = new ListNode(-1);
        p.next = head;
        //創建a，b兩個指針，這里還需要一個tmp指針
        ListNode a = p;
        ListNode b = p;
        ListNode tmp = p;
        while (b != null && b.next != null && b.next.next != null) {
            // a前進一位，b前進兩位
            a = a.next;
            b = b.next.next;
            // 這步很關鍵，tmp指針用來處理邊界條件的
            // 假設鏈表是1->2->3->4，a指向1，b指向2
            // 改變a和b的指向，於是就變成2->1，但是1指向誰呢？
            // 1是不能指向2的next，1應該指向4，而循環叠代的時候一次處理2個節點
            // 1和2的關系弄清楚了，3和4的關系也能弄清楚，但需要一個指針來處理
            // 2->1，4->3的關系，tmp指針就是幹這個用的
            tmp.next = b;
            a.next = b.next;
            b.next = a;
            // 現在鏈表就變成2->1->3->4
            // tmp和b都指向1節點，等下次叠代的時候
            // a就變成3，b就變成4，然後tmp就指向b，也就是1指向4
            tmp = a;
            b = a;
        }
        return p.next;
    }


    public ListNode swapPairs2(ListNode head) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode prev = dummyNode;
        while (prev.next != null && prev.next.next != null) {
            ListNode temp = head.next.next; // 緩存 next
            prev.next = head.next;          // 將 prev 的 next 改為 head 的 next
            head.next.next = head;          // 將 head.next(prev.next) 的next，指向 head
            head.next = temp;               // 將head 的 next 接上緩存的temp
            prev = head;                    // 步進1位
            head = head.next;               // 步進1位
        }
        return dummyNode.next;
    }


}
