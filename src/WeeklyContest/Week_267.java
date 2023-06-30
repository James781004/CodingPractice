package WeeklyContest;

public class Week_267 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2073.Time%20Needed%20to%20Buy%20Tickets/README.md
    // 第 k 個人買完之前，排在 k 後面的人最多能買 tickets[k] - 1 次，排在 k 前面的人最多能買 tickets[k] 次
    public int timeRequiredToBuy(int[] tickets, int k) {
        int ans = 0;
        for (int i = 0; i < tickets.length; i++) {
            if (i <= k) {
                ans += Math.min(tickets[k], tickets[i]);
            } else {
                ans += Math.min(tickets[k] - 1, tickets[i]);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/reverse-nodes-in-even-length-groups/solution/fan-zhuan-ou-shu-chang-du-zu-de-jie-dian-owra/
    public ListNode reverseEvenLengthGroups(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        // 前驅節點
        ListNode pre = dummy;
        // 當前節點
        ListNode cur = head;
        // 當前子鏈表數量
        int count = 0;
        while (cur != null) {
            // 當前子鏈表本應該具有的長度
            ++count;
            // 試探當前子鏈表的長度
            ListNode tryIt = cur;
            int length = 0;
            // 應該具有的長度 == 實際長度 或者 鏈表到頭時 ，終止遍歷
            while (length < count && tryIt != null) {
                tryIt = tryIt.next;
                ++length;
            }
            // 實際子鏈表長度為偶數
            if (length % 2 == 0) {
                for (int i = 0; i < length - 1; ++i) {
                    // 把當前cur節點的next節點刪除
                    ListNode removed = cur.next;
                    cur.next = cur.next.next;
                    // 把刪除的節點添加到前驅節點之後
                    removed.next = pre.next;
                    pre.next = removed;
                }
                // 此時 cur節點指向下一組子鏈表的前驅 所以該位置為pre的指向
                pre = cur;
                // 再把cur後移一個節點，指向下一組子鏈表的表頭
                cur = cur.next;
            }

            // 長度為奇數
            else {
                // 將pre和cur分別後移length次
                for (int i = 0; i < length; ++i) {
                    cur = cur.next;
                    pre = pre.next;
                }
            }
        }
        return dummy.next;

    }

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


    // https://leetcode.cn/problems/decode-the-slanted-ciphertext/solution/mo-ni-jiang-encodedtext-shi-zuo-er-wei-j-ixba/
    public String decodeCiphertext(String encodedText, int rows) {
        /*
        模擬:
        先建立一個矩陣把字母填進去，然後再通過解碼的方式讀取
        注意:
        1.可能後前導空格，但是沒有尾隨空格
        2.可能開始解碼是從(0,0)開始的
        3.矩陣的列數col可以由len/encodedText計算
        0 <= encodedText.length <= 1e6
        時間復雜度:O(N)  空間復雜度:O(N)
         */
        // 原位解密法
        int n = encodedText.length();
        int cols = n / rows;
        // 解密字符串並填入sb中
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows && i + j < cols; i++) {
                int curIdx = i * cols + (i + j);    // 逆運算出在原來字符串的位置
                sb.append(encodedText.charAt(curIdx));
            }
        }

        // 去掉尾隨空格
        int idx = sb.length() - 1;
        while (idx > 0 && sb.charAt(idx) == ' ') idx--;
        return sb.substring(0, idx + 1);
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2076.Process%20Restricted%20Friend%20Requests/README.md
    private int[] p;

    public boolean[] friendRequests(int n, int[][] restrictions, int[][] requests) {
        // 初始化並查集
        p = new int[n];
        for (int i = 0; i < n; ++i) {
            p[i] = i;
        }

        boolean[] ans = new boolean[requests.length];
        int i = 0;
        for (int[] req : requests) {
            int u = req[0], v = req[1];
            if (find(u) == find(v)) { // 處於p同集合，可以成為朋友
                ans[i++] = true;
            } else {
                boolean valid = true;
                for (int[] res : restrictions) { // 如果處於restrictions同集合就不能成為朋友
                    int x = res[0], y = res[1];
                    if ((find(u) == find(x) && find(v) == find(y))
                            || (find(u) == find(y) && find(v) == find(x))) {
                        valid = false;
                        break;
                    }
                }
                if (valid) {  // 沒有處於restrictions同集合，調整p集合並且成為朋友
                    p[find(u)] = find(v);
                    ans[i++] = true;
                } else {
                    ans[i++] = false;
                }
            }
        }
        return ans;
    }

    private int find(int x) {
        if (p[x] != x) {
            p[x] = find(p[x]);
        }
        return p[x];
    }
}
