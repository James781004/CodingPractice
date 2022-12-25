package GuChengAlgorithm.Ch01_BasicDataStructure.LinkedList;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Q07_TwoPointerOperations {

    // LC 234
    // 也可以使用Stack裝進全部節點，然後一個一個比較
    public boolean isPalindrome(ListNode head) {
        ListNode firstPartEnd = findFirstPartEnd(head); // 找出前半段鏈表終點
        ListNode secondPartStart = reverse(firstPartEnd.next); // 反轉後半段鏈表
        ListNode p1 = head;
        ListNode p2 = secondPartStart;
        boolean result = true;
        while (result && p2 != null) { // 頭尾往中間移動
            if (p1.val != p2.val) result = false;
            p1 = p1.next;
            p2 = p2.next;
        }
        firstPartEnd.next = reverse(secondPartStart);  // 把鏈表恢復原狀
        return result;
    }

    private ListNode findFirstPartEnd(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    private ListNode reverse(ListNode head) {
        ListNode pre = null, cur = head, next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }


    // LC 160
    // 計算長度後跳過較長的部分
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = getLength(headA), lenB = getLength(headB);
        while (lenA > lenB) {
            headA = headA.next;
            lenA--;
        }

        while (lenB > lenA) {
            headB = headB.next;
            lenB--;
        }

        while (headA != null && headB != null) {
            if (headA == headB) return headA;
            headA = headA.next;
            headB = headB.next;
        }

        return null;
    }

    private int getLength(ListNode node) {
        int len = 0;
        while (node != null) {
            len++;
            node = node.next;
        }
        return len;
    }


    // 類似環狀鏈表思維
    // 根據數學公式
    // a + c + b = b + c + a
    // 所以如果有環的話二者一定會在交匯點相遇
    // a,b走到盡頭就跳到另外一個list的頭部
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }


    // LC 138
    // O(1)解法: 在原list中插入新的list再重新拆開
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) return head;

        // 開始插入新的鏈表
        RandomListNode c = head, next = null;
        while (c != null) {
            next = c.next;
            c.next = new RandomListNode(c.val);
            c.next.next = next;
            c = next;
        }

        c = head;
        while (c != null) {
            if (c.random != null) {
                c.next.random = c.random.next;
            }
            c = c.next.next;
        }

        // 開始拆分鏈表
        c = head;
        RandomListNode newHead = head.next;
        RandomListNode copy = newHead;
        while (copy.next != null) {
            c.next = c.next.next;
            c = c.next;
            copy.next = copy.next.next;
            copy = copy.next;
        }
        c.next = c.next.next;
        return newHead;
    }


    // 使用HashMap複製所有鏈表節點，再把HashMap中的節點重新連接
    public RandomListNode copyRandomList2(RandomListNode head) {
        Map<RandomListNode, RandomListNode> map = new HashMap<>();
        for (RandomListNode cur = head; cur != null; cur = cur.next) {
            map.put(cur, new RandomListNode(cur.val));
        }

        for (RandomListNode cur = head; cur != null; cur = cur.next) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).random = map.get(cur.random);
        }

        return map.get(head);
    }


    // LC 426
    public DoubleListNode treeToList(DoubleListNode root) {
        if (root == null) return null;
        DoubleListNode first = null, pre = null;
        Stack<DoubleListNode> stack = new Stack<>();
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            // 左樹節點已經全部進入stack，開始中序處理root節點
            root = stack.pop();

            // first保存的是最左節點，一旦得到值之後就不會更新
            if (first == null) first = root;

            // 連結前驅節點和root節點
            if (pre != null) {
                pre.right = root;
                root.left = pre;
            }

            // 移動前驅節點和root節點
            pre = root;
            root = root.right;
        }

        // 連結前驅節點和first節點
        first.left = pre;
        pre.right = first;
        return first;
    }


    DoubleListNode prev = null, head = null;

    public DoubleListNode treeToList2(DoubleListNode root) {
        if (root == null) return null;
        inorderTraversal(root);
        head.left = prev;
        prev.right = head;
        return head;
    }

    private void inorderTraversal(DoubleListNode root) {
        if (root == null) return;
        inorderTraversal(root.left);
        if (head == null) head = root; // head保存的是最左節點，一旦得到值之後就不會更新

        // 連結前驅節點和root節點
        if (prev != null) {
            prev.right = root;
            root.left = prev;
        }

        // 移動前驅節點
        prev = root;

        inorderTraversal(root.right);
    }


    // 樹形DP解法
    public DoubleListNode treeToListDP(DoubleListNode root) {
        DoubleListNode[] head = flatten(root);
        if (head[0] != null) {
            head[0].left = head[1];
            head[1].right = head[0];
        }
        return head[0];
    }

    private DoubleListNode[] flatten(DoubleListNode root) {
        if (root == null) return new DoubleListNode[]{null, null}; // [當前樹最左節點, 當前樹最右節點]

        // 先取得左右子樹資料
        DoubleListNode[] left = flatten(root.left);
        DoubleListNode[] right = flatten(root.right);

        // 處理左右子樹資料
        DoubleListNode pre = null, next = null;

        // 目前root是中間節點，左子樹右節點跟root連接
        if (left[1] != null) {
            left[1].right = root;
            root.left = left[1];
            pre = left[0];
        }

        // 目前root是中間節點，右子樹左節點跟root連接
        if (right[0] != null) {
            right[0].left = root;
            root.right = right[0];
            next = right[1];
        }

        // 連接完成
        return new DoubleListNode[]{pre, next};
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }


    class RandomListNode {
        int val;
        RandomListNode next;
        RandomListNode random;

        public RandomListNode(int v) {
            val = v;
        }
    }

    class DoubleListNode {
        int val;
        DoubleListNode left;
        DoubleListNode right;

        public DoubleListNode(int v) {
            val = v;
        }
    }
}
