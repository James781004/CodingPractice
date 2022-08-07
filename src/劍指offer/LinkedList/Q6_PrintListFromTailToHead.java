package 劍指offer.LinkedList;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q6_PrintListFromTailToHead {

    public List<Integer> printByRecursive(ListNode listNode) {
        // 要逆序打印链表 1->2->3（3,2,1)，可以先逆序打印链表 2->3(3,2)，
        // 最后再打印第一个节点 1。而链表 2->3 可以看成一个新的链表，
        // 要逆序打印该链表可以继续使用求解函数，也就是在求解函数中调用自己，这就是递归函数。
        List<Integer> ret = new ArrayList<>();
        if (listNode != null) {
            ret.addAll(printByRecursive(listNode.next));
            ret.add(listNode.val);
        }

        return ret;
    }

    public List<Integer> printByDummyHead(ListNode listNode) {
        // 头插法构建逆序链表
        // 为了能将一个节点插入头部，我们引入了一个叫头结点的辅助节点，
        // 该节点不存储值，只是为了方便进行插入操作。不要将头结点与第一个节点混起来，
        // 第一个节点是链表中第一个真正存储值的节点。
        ListNode head = new ListNode(-1);
        while (listNode != null) {
            ListNode memo = listNode.next;
            listNode.next = head.next;
            head.next = listNode;
            listNode = memo;
        }

        List<Integer> ret = new ArrayList<>();
        head = head.next;
        while (head != null) {
            ret.add(head.val);
            head = head.next;
        }
        return ret;
    }

    public List<Integer> printByStack(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.add(listNode.val);
            listNode = listNode.next;
        }
        List<Integer> ret = new ArrayList<>();
        while (!stack.isEmpty())
            ret.add(stack.pop());
        return ret;

    }
}
