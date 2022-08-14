package LeetcodeMaster.LinkedList;

public class Q02_DesignLinkedlist {
// 707.設計鏈表
// 力https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0707.%E8%AE%BE%E8%AE%A1%E9%93%BE%E8%A1%A8.md

// 題意：

// 在鏈表類中實現這些功能：

// get(index)：獲取鏈表中第 index 個節點的值。如果索引無效，則返回-1。
// addAtHead(val)：在鏈表的第一個元素之前添加一個值為 val 的節點。插入後，新節點將成為鏈表的第一個節點。
// addAtTail(val)：將值為 val 的節點追加到鏈表的最後一個元素。
// addAtIndex(index,val)：在鏈表中的第 index 個節點之前添加值為 val  的節點。如果 index 等於鏈表的長度，則該節點將附加到鏈表的末尾。如果 index 大於鏈表長度，則不會插入節點。如果index小於0，則在頭部插入節點。
// deleteAtIndex(index)：如果索引 index 有效，則刪除鏈表中的第 index 個節點。

    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    class MyLinkedList {
        //size存儲鏈表元素的個數
        int size;
        //虛擬頭結點
        Node head;

        public MyLinkedList() {
            size = 0;
            head = new Node(0);
        }

        public int get(int index) {
            if (index < 0 || index >= size) return -1;
            Node cur = head;
            for (int i = 0; i <= index; i++) {
                cur = cur.next;
            }

            return cur.value;
        }

        //在鏈表最前面插入一個節點
        public void addAtHead(int val) {
            addAtIndex(0, val);
        }

        //在鏈表的最後插入一個節點
        public void addAtTail(int val) {
            addAtIndex(size, val);
        }

        // 在第 index 個節點之前插入一個新節點，例如index為0，那麽新插入的節點為鏈表的新頭節點。
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
            Node pre = head;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            Node toAdd = new Node(val);
            toAdd.next = pre.next;
            pre.next = toAdd;
        }

        //刪除第index個節點
        public void deleteAtIndex(int index) {
            if (index < 0 || index >= size) {
                return;
            }
            size--;
            Node pre = head;
            for (int i = 0; i < index; i++) {
                pre = pre.next;
            }
            pre.next = pre.next.next;
        }

    }


    //雙鏈表
    class MyDoubleLinkedList {
        class ListNode {
            int val;
            ListNode next, prev;

            ListNode(int x) {
                val = x;
            }
        }

        int size;
        ListNode head, tail;//Sentinel node

        /**
         * Initialize your data structure here.
         */
        public MyDoubleLinkedList() {
            size = 0;
            head = new ListNode(0);
            tail = new ListNode(0);
            head.next = tail;
            tail.prev = head;
        }

        /**
         * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
         */
        public int get(int index) {
            if (index < 0 || index >= size) {
                return -1;
            }
            ListNode cur = head;

            // 通過判斷 index < (size - 1) / 2 來決定是從頭結點還是尾節點遍歷，提高效率
            if (index < (size - 1) / 2) {
                for (int i = 0; i <= index; i++) {
                    cur = cur.next;
                }
            } else {
                cur = tail;
                for (int i = 0; i <= size - index - 1; i++) {
                    cur = cur.prev;
                }
            }
            return cur.val;
        }

        /**
         * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
         */
        public void addAtHead(int val) {
            ListNode cur = head;
            ListNode newNode = new ListNode(val);
            newNode.next = cur.next;
            cur.next.prev = newNode;
            cur.next = newNode;
            newNode.prev = cur;
            size++;
        }

        /**
         * Append a node of value val to the last element of the linked list.
         */
        public void addAtTail(int val) {
            ListNode cur = tail;
            ListNode newNode = new ListNode(val);
            newNode.next = tail;
            newNode.prev = cur.prev;
            cur.prev.next = newNode;
            cur.prev = newNode;
            size++;
        }

        /**
         * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
         */
        public void addAtIndex(int index, int val) {
            if (index > size) {
                return;
            }
            if (index < 0) {
                index = 0;
            }
            ListNode cur = head;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            ListNode newNode = new ListNode(val);
            newNode.next = cur.next;
            cur.next.prev = newNode;
            newNode.prev = cur;
            cur.next = newNode;
            size++;
        }

        /**
         * Delete the index-th node in the linked list, if the index is valid.
         */
        public void deleteAtIndex(int index) {
            if (index >= size || index < 0) {
                return;
            }
            ListNode cur = head;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            cur.next.next.prev = cur;
            cur.next = cur.next.next;
            size--;
        }
    }


}
