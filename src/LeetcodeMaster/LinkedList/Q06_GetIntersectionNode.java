package LeetcodeMaster.LinkedList;

public class Q06_GetIntersectionNode {
//    面試題 02.07. 鏈表相交
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/%E9%9D%A2%E8%AF%95%E9%A2%9802.07.%E9%93%BE%E8%A1%A8%E7%9B%B8%E4%BA%A4.md
//
//    給你兩個單鏈表的頭節點 headA 和 headB ，請你找出並返回兩個單鏈表相交的起始節點。如果兩個鏈表沒有交點，返回 null 。


    public static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    public Node getIntersectionNode(Node headA, Node headB) {
        Node curA = headA;
        Node curB = headB;
        int lenA = 0, lenB = 0;
        while (curA != null) { // 求鏈表A的長度
            lenA++;
            curA = curA.next;
        }
        while (curB != null) { // 求鏈表B的長度
            lenB++;
            curB = curB.next;
        }

        // 先回到兩鏈表頭部
        curA = headA;
        curB = headB;

        // 讓curA為最長鏈表的頭，lenA為其長度
        if (lenB > lenA) {
            // 1. swap (lenA, lenB);
            int tmpLen = lenA;
            lenA = lenB;
            lenB = tmpLen;
            // 2. swap (curA, curB);
            Node tmpNode = curA;
            curA = curB;
            curB = tmpNode;
        }

        // 求長度差
        int gap = lenA - lenB;

        // 讓curA和curB在同一起點上（末尾位置對齊）
        while (gap-- > 0) {
            curA = curA.next;
        }

        // 遍歷curA 和 curB，遇到相同則直接返回
        while (curA != null) {
            if (curA == curB) {
                return curA;
            }
            curA = curA.next;
            curB = curB.next;
        }

        return null;
    }

}
