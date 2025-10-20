package EndlessCheng.GenreMenu.Backtracking.LinkedList.Traverse;

import EndlessCheng.ListNode;

public class NumComponents {

    // https://leetcode.cn/problems/linked-list-components/solutions/1885886/zhua-wa-mou-si-by-muse-77-1rz4/
    public int numComponents(ListNode head, int[] nums) {
        // 題目限制鏈長不超過10000，題目限制每個head.val值唯一
        boolean[] flag = new boolean[10001];
        // 判斷哪個值有
        for (int num : nums) flag[num] = true;
        // pre記錄前一個值存不存在,result記錄鏈長
        int res = 0;
        boolean pre = false;
        while (head != null) {
            // 如果前一個值不存在而當前遍歷到的節點的值存在，則res+1
            if (!pre && flag[head.val]) res++;
            // 記錄當前遍歷到的節點的值存在與否
            pre = flag[head.val];
            // 繼續遍歷
            head = head.next;
        }
        return res;
    }

}
