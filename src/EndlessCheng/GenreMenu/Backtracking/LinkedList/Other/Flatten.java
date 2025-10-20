package EndlessCheng.GenreMenu.Backtracking.LinkedList.Other;

public class Flatten {

    // https://leetcode.cn/problems/flatten-a-multilevel-doubly-linked-list/solutions/1014425/tong-ge-lai-shua-ti-la-dfs-guan-jian-zai-s9x3/
    public Node flatten(Node head) {
        // 遍歷每一個節點，看它是否有子節點，有子節點的話，把它的子節點那一坨放在它和next之間
        dfs(head);
        return head;
    }

    private void dfs(Node node) {
        // 退出條件
        if (node == null) {
            return;
        }

        // 子節點不為空，需要處理
        if (node.child != null) {
            // 記錄下來下一個節點
            Node next = node.next;

            // 先把子鏈表扁平化
            dfs(node.child);

            // 修改當前節點的下一個節點為子鏈表的頭
            // 同時，子鏈表頭的上一個節點為當前節點
            node.next = node.child;
            node.child.prev = node;

            // 尋找扁平化之後的子節點的最後一個節點（即子鏈表的尾）
            Node tail = node.child;
            while (tail.next != null) {
                tail = tail.next;
            }

            // 把子鏈表的尾與原來當前節點的下一個節點連起來
            // 注意空指針的處理
            if (next != null) {
                next.prev = tail;
            }
            tail.next = next;

            // 子鏈表置空
            node.child = null;

            // 從原來的next節點繼續
            // 測試用例有漏洞，這行不加也不報錯
            // 因為局限於序列化方式，每一層最多有一個節點有子節點
            // 所以不加下面這行也沒報錯。。。
            dfs(next);
        } else {
            // 子節點為空，不需要處理
            dfs(node.next);
        }
    }

    static class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    ;

}
