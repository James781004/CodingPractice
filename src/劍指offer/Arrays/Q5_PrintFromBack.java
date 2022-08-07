package 劍指offer.Arrays;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Q5_PrintFromBack {

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


    // 思路:借助栈实现，或使用递归的方法。
    public List<Integer> print(ListNode listNode) {
        if (listNode == null) return null;
        List<Integer> list = new ArrayList<>();
        Stack<ListNode> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode);
            listNode = listNode.next;
        }

        while (!stack.isEmpty()) {
            list.add(stack.pop().val);
        }

        return list;
    }
}
