package EndlessCheng.GenreMenu.Backtracking.LinkedList.Insert;

import EndlessCheng.Node;

public class InsertCycleSortList {

    // https://leetcode.cn/problems/4ueAj6/solutions/1608952/by-ac_oier-kqv3/
    public Node insert(Node head, int insertVal) {
        Node t = new Node(insertVal);
        t.next = t;
        if (head == null) return t;

        Node ans = head; // 保留返回結果的 head

        // 第一遍遍歷找到最大值和最小值
        int min = head.val, max = head.val;
        while (head.next != ans) {
            head = head.next;
            min = Math.min(min, head.val);
            max = Math.max(max, head.val);
        }

        // 根據 min 和 max 以及 insertVal 的大小進行分情況 討論
        if (min == max) {
            // 插到哪裡都一樣
            t.next = ans.next;
            ans.next = t;
        } else { // 如果不相等則找到分割點，也即是從最大跳到最小的點
            while (!(head.val == max && head.next.val == min)) head = head.next;

            // 判斷 insertVal 是 大於最大值 或者 小於最小值，
            if (insertVal >= max || insertVal <= min) {
                // 如果是則應該插在這個跳躍點處
                t.next = head.next;
                head.next = t;
            } else {
                // 否則，此時就需要從 最小值 的位置開始往後遍歷找到合適的插入位置
                while (!(head.val <= insertVal && insertVal <= head.next.val)) head = head.next;
                t.next = head.next;
                head.next = t;
            }
        }

        return ans;
    }

}
