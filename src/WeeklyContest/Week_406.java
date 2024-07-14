package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Week_406 {
    // https://leetcode.cn/problems/lexicographically-smallest-string-after-a-swap/solutions/2843075/tan-xin-pythonjavacgo-by-endlesscheng-jqga/
    public String getSmallestString(String s) {
        char[] t = s.toCharArray();
        for (int i = 1; i < t.length; i++) {
            char x = t[i - 1];
            char y = t[i];
            if (x > y && x % 2 == y % 2) {
                t[i - 1] = y;
                t[i] = x;
                break;
            }
        }
        return new String(t);
    }


    // https://leetcode.cn/problems/delete-nodes-from-linked-list-present-in-array/solutions/2843071/shao-bing-jie-dian-yi-ci-bian-li-pythonj-imre/
    public ListNode modifiedList(int[] nums, ListNode head) {
        Set<Integer> set = new HashSet<>(nums.length); // 預分配空間
        for (int x : nums) {
            set.add(x);
        }
        ListNode dummy = new ListNode(0, head);
        ListNode cur = dummy;
        while (cur.next != null) {
            if (set.contains(cur.next.val)) {
                cur.next = cur.next.next; // 刪除
            } else {
                cur = cur.next; // 向後移動
            }
        }
        return dummy.next;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    // https://leetcode.cn/problems/minimum-cost-for-cutting-cake-ii/solutions/2843063/tan-xin-ji-qi-zheng-ming-jiao-huan-lun-z-ivtn/
    public long minimumCost(int m, int n, int[] horizontalCut, int[] verticalCut) {
        Arrays.sort(horizontalCut); // 下面倒序遍歷
        Arrays.sort(verticalCut);
        long ans = 0;
        int i = m - 2;
        int j = n - 2;
        int cntH = 1;
        int cntV = 1;
        while (i >= 0 || j >= 0) {
            if (j < 0 || i >= 0 && horizontalCut[i] > verticalCut[j]) {
                ans += horizontalCut[i--] * cntH; // 橫切
                cntV++; // 需要豎切的蛋糕塊增加
            } else {
                ans += verticalCut[j--] * cntV; // 豎切
                cntH++; // 需要橫切的蛋糕塊增加
            }
        }
        return ans;
    }

}


