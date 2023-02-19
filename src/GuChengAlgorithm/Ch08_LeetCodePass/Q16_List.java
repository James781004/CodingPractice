package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;

public class Q16_List {
    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_2_0
    class ReverseList {
        public ListNode reverseList(ListNode head) {
            ListNode newHead = null;
            ListNode cur = head;
            while (cur != null) {
                ListNode next = cur.next;
                cur.next = newHead;
                newHead = cur;
                cur = next;
            }
            return newHead;
        }

        public ListNode reverseList2(ListNode head) {
            return reverse(head, null);
        }

        private ListNode reverse(ListNode head, ListNode newHead) {
            if (head == null) return newHead;
            ListNode next = head.next;
            head.next = newHead;
            return reverse(next, head);
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_2_6
    class ReverseListII {
        public ListNode reverseBetween(ListNode head, int m, int n) {
            ListNode cur = head, prev = null;
            while (m > 1) {
                prev = cur;
                cur = cur.next;
                m--;
                n--;
            }

            // Two pointers fix the final connections
            ListNode con = prev, tail = cur;
            ListNode third = null;
            while (n > 0) {
                third = cur.next;
                cur.next = prev;
                prev = cur;
                cur = third;
                n--;
            }

            // Adjust the final connections
            if (con != null) con.next = prev;
            else head = prev;
            tail.next = cur;
            return head;
        }


        public ListNode reverseBetween2(ListNode head, int m, int n) {
            ListNode dummy = new ListNode(-1);
            dummy.next = head;
            ListNode prev = dummy;
            ListNode cur = dummy.next;
            int i = 1;
            while (i < m) {
                prev = cur;
                cur = cur.next;
                i++;
            }

            ListNode node = prev;
            while (i++ <= n) {
                ListNode next = cur.next;
                cur.next = prev;
                prev = cur;
                cur = next;
            }

            node.next.next = cur;  // node.next是翻轉後的尾部
            node.next = prev;  // prev是翻轉後的頭部
            return dummy.next;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_4_1
    class AddTwoNumbers {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            int carry = 0;
            ListNode dummy = new ListNode(-1), cur = dummy;
            while (l1 != null || l2 != null) {
                int sum = carry;
                if (l1 != null) {
                    sum += l1.val;
                    l1 = l1.next;
                }

                if (l2 != null) {
                    sum += l2.val;
                    l2 = l2.next;
                }

                cur.next = new ListNode(sum % 10);
                cur = cur.next;
                carry = sum / 10;
            }

            if (carry > 0) cur.next = new ListNode(carry);
            return dummy.next;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_4_10
    class MergeKLists {
        public ListNode mergeKListsPQ(ListNode[] lists) {
            ListNode dummy = new ListNode(0);
            PriorityQueue<ListNode> q = new PriorityQueue<>((a, b) -> a.val - b.val);  // 從小到大
            for (int i = 0; i < lists.length; i++) {
                if (lists[i] != null) q.offer(lists[i]);  // 放入所有list頭元素
            }

            ListNode cur = dummy;
            while (!q.isEmpty()) {
                cur.next = q.poll();
                cur = cur.next;
                if (q.isEmpty()) break;
                if (cur.next != null) q.offer(cur.next);  // 放入cur下一個元素
            }

            return dummy.next;
        }


        public ListNode mergeKLists(ListNode[] lists) {
            return partition(lists, 0, lists.length - 1);
        }

        private ListNode partition(ListNode[] lists, int start, int end) {
            if (start == end) return lists[start];
            if (start < end) {
                int mid = (start + end) / 2;
                ListNode l1 = partition(lists, start, mid);
                ListNode l2 = partition(lists, mid + 1, end);
                return merge(l1, l2);
            }
            return null;
        }

        private ListNode merge(ListNode l1, ListNode l2) {
            if (l1 == null) return l2;
            if (l2 == null) return l1;
            if (l1.val < l2.val) {
                l1.next = merge(l1.next, l2);
                return l1;
            } else {
                l2.next = merge(l1, l2.next);
                return l2;
            }
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_4_65
    class LinkedListCycleII {
        public ListNode detectCycle(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            while (fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) {
                    fast = head;
                    while (slow != fast) {
                        fast = fast.next;
                        slow = slow.next;
                    }
                    return slow;
                }
            }
            return null;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_5_52
    class RemoveNthFromEnd {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode first = dummy, second = dummy;
            for (int i = 1; i <= n + 1; i++) {
                first = first.next;
            }

            while (first != null) {
                first = first.next;
                second = second.next;
            }

            second.next = second.next.next;
            return dummy.next;
        }


        public ListNode removeNthFromEnd2(ListNode head, int n) {
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            int len = 0;
            ListNode first = head;
            while (first != null) {
                len++;
                first = first.next;
            }

            len -= n;
            first = dummy;
            while (len > 0) {
                len--;
                first = first.next;
            }

            first.next = first.next.next;
            return dummy.next;
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_5_104
    class CopyRandomList {
        public Node copyRandomList(Node head) {
            Map<Node, Node> map = new HashMap<>();
            for (Node cur = head; cur != null; cur = cur.next) {
                map.put(cur, new Node(cur.val));
            }
            for (Node cur = head; cur != null; cur = cur.next) {
                map.get(cur).next = map.get(cur.next);
                map.get(cur).random = map.get(cur.random);
            }
            return map.get(head);
        }

        public Node copyRandomList2(Node head) {
            if (head == null) return head;
            Node cur = head;

            // 插入複製節點
            while (cur != null) {
                Node insert = new Node(cur.val);
                insert.next = cur.next;
                cur.next = insert;
                cur = cur.next.next;
            }

            // 複製random節點
            cur = head;
            while (cur != null && cur.next != null) {
                if (cur.random != null) cur.next.random = cur.random.next;
                cur = cur.next.next;
            }

            // 切割list
            Node old = head;
            cur = head.next;
            Node dummy = new Node(0);
            dummy.next = cur;
            while (old != null) {
                old.next = old.next.next;
                cur.next = cur.next != null ? cur.next.next : null;
                old = old.next;
                cur = cur.next;
            }
            return dummy.next;
        }


        class Node {
            int val;
            Node next;
            Node random;

            public Node(int v) {
                val = v;
                next = null;
                random = null;
            }
        }
    }


    // https://docs.google.com/presentation/d/1Vhwcg1rZ_1xWpSmHf3ROUn_ol94XSTMvzxnZZu5DZnM/edit#slide=id.g10a57e12c34_5_117
    class TreeToDoublyList {
        public Node treeToDoublyList(Node root) {
            if (root == null) return null;
            Node first = null, pre = null;
            Stack<Node> stack = new Stack<>();
            while (root != null || !stack.isEmpty()) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
                root = stack.pop();
                if (first == null) first = root;
                if (pre != null) {
                    pre.right = root;
                    root.left = pre;
                }
                pre = root;
                root = root.right;
            }
            first.left = pre;
            pre.right = first;
            return first;
        }


        // recursive
        Node pre = null, head = null;

        public Node treeToDoublyList2(Node root) {
            if (root == null) return null;
            inorder(root);
            head.left = pre;
            pre.right = head;
            return head;
        }

        private void inorder(Node root) {
            if (root == null) return;
            inorder(root.left);
            if (head == null) head = root;
            if (pre != null) {
                pre.right = root;
                root.left = pre;
            }
            pre = root;
            inorder(root.right);
        }


        // 樹形DP解法
        public Node treeToListDP(Node root) {
            Node[] head = flatten(root);
            if (head[0] != null) {
                head[0].left = head[1];
                head[1].right = head[0];
            }
            return head[0];
        }

        private Node[] flatten(Node root) {
            if (root == null) return new Node[]{null, null}; // [當前樹最左節點, 當前樹最右節點]

            // 先取得左右子樹資料
            Node[] left = flatten(root.left);
            Node[] right = flatten(root.right);

            // 處理左右子樹資料
            Node pre = root, next = root;

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
            return new Node[]{pre, next};
        }


        class Node {
            int val;
            Node left;
            Node right;

            public Node(int v) {
                val = v;
                left = null;
                right = null;
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
