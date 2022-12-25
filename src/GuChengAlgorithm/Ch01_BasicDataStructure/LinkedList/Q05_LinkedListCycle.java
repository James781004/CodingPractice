package GuChengAlgorithm.Ch01_BasicDataStructure.LinkedList;

public class Q05_LinkedListCycle {

    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }

    // 假設交匯點為P
    //
    // 快指針跑了a+(b+c)+b = 2b + a + c
    // 慢指針跑了a + b
    //
    // 快指針距離 = 2 * 慢指針距離
    // 2b + a + c = 2 * (a + b)
    // 2b + a + c = 2b + 2a
    //        a + c = 2a
    //              c = a
    public ListNode findCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                fast = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;
            }
        }
        return null;
    }


    // LC 287
    public int findDuplicate(int[] nums) {
        int slow = nums[0], fast = nums[0];
        while (true) {
            slow = nums[slow];
            fast = nums[nums[fast]];
            if (slow == fast) {
                fast = nums[0];
                while (fast != slow) {
                    slow = nums[slow];
                    fast = nums[fast];
                }
                return slow;
            }
        }
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }
}
