package EndlessCheng.GenreMenu.Backtracking.LinkedList.Comprehensive;

public class DesignMyLinkedList {

    // https://leetcode.cn/problems/design-linked-list/solutions/288354/cdan-lian-biao-shi-xian-xiang-xi-zhu-shi-by-carlsu/
    class MyLinkedList {
        // size存儲鏈表元素的個數
        int size;
        // 虛擬頭結點
        ListNode head;

        // 初始化鏈表
        public MyLinkedList() {
            size = 0;
            head = new ListNode(0);
        }

        // 獲取第index個節點的數值，注意index是從0開始的，第0個節點就是頭結點
        public int get(int index) {
            // 如果index非法，返回-1
            if (index < 0 || index >= size) {
                return -1;
            }
            ListNode currentNode = head;
            // 包含一個虛擬頭節點，所以查找第 index+1 個節點
            for (int i = 0; i <= index; i++) {
                currentNode = currentNode.next;
            }
            return currentNode.val;
        }

        // 在鏈表最前面插入一個節點，等價於在第0個元素前添加
        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        // 在鏈表的最後插入一個節點，等價於在(末尾+1)個元素前添加
        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        // 在第 index 個節點之前插入一個新節點，例如index為0，那麼新插入的節點為鏈表的新頭節點。
        // 如果 index 等於鏈表的長度，則說明是新插入的節點為鏈表的尾結點
        // 如果 index 大於鏈表的長度，則返回空
        public void addAtIndex(int index, int val) {
            if (index > size) {
                return;
            }
            if (index < 0) {
                index = 0;
            }
            size++;
            //找到要插入節點的前驅
            ListNode pred = head;
            for (int i = 0; i < index; i++) {
                pred = pred.next;
            }
            ListNode toAdd = new ListNode(val);
            toAdd.next = pred.next;
            pred.next = toAdd;
        }

        // 刪除第index個節點
        public void deleteAtIndex(int index) {
            if (index < 0 || index >= size) {
                return;
            }
            size--;
            if (index == 0) {
                head = head.next;
                return;
            }
            ListNode pred = head;
            for (int i = 0; i < index; i++) {
                pred = pred.next;
            }
            pred.next = pred.next.next;
        }
    }

    // 單鏈表
    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }
    }

}
