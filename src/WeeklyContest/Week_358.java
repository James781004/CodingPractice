package WeeklyContest;

import java.util.*;

class Week_358 {
    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2815.Max%20Pair%20Sum%20in%20an%20Array/README.md
    public int maxSum(int[] nums) {
        int ans = -1;
        int n = nums.length;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int v = nums[i] + nums[j];
                if (ans < v && f(nums[i]) == f(nums[j])) {
                    ans = v;
                }
            }
        }
        return ans;
    }

    private int f(int x) {
        int y = 0;
        for (; x > 0; x /= 10) {
            y = Math.max(y, x % 10);
        }
        return y;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2816.Double%20a%20Number%20Represented%20as%20a%20Linked%20List/README.md
    // 翻轉鏈表 + 模擬
    // 先將鏈表翻轉，然後模擬乘法運算，最後再將鏈表翻轉回來
    public ListNode doubleIt(ListNode head) {
        head = reverse(head);
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        int mul = 2, carry = 0;
        while (head != null) {
            int x = head.val * mul + carry;
            carry = x / 10;
            cur.next = new ListNode(x % 10);
            cur = cur.next;
            head = head.next;
        }
        if (carry > 0) {
            cur.next = new ListNode(carry);
        }
        return reverse(dummy.next);
    }

    private ListNode reverse(ListNode head) {
        ListNode dummy = new ListNode();
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = dummy.next;
            dummy.next = cur;
            cur = next;
        }
        return dummy.next;
    }


    // https://leetcode.cn/problems/double-a-number-represented-as-a-linked-list/solutions/2385962/o1-kong-jian-zuo-fa-kan-cheng-shi-head-y-1dco/
    // 如果不考慮進位，就是每個節點的值乘以 2。
    // 什麼時候會受到進位的影響呢？只有下一個節點大於 4 的時候，才會因為進位多加一。
    // 如果鏈表頭的值大於 4，那麼需要在前面插入一個新的節點。
    public ListNode doubleIt2(ListNode head) {
        if (head.val > 4)
            head = new ListNode(0, head);
        for (ListNode cur = head; cur != null; cur = cur.next) {
            cur.val = cur.val * 2 % 10;
            if (cur.next != null && cur.next.val > 4)
                cur.val++;
        }
        return head;
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


    // https://www.youtube.com/watch?v=8NklkjXSgUo
    // https://github.com/doocs/leetcode/blob/main/solution/2800-2899/2817.Minimum%20Absolute%20Difference%20Between%20Elements%20With%20Constraint/README.md
    // 題目如果出現「最接近」關鍵字：
    // 1. 題目有單調性 (排序)，先考慮二分法
    // 2. 題目有絕對值，可以考慮平衡樹(TreeMap 和 TreeSet)
    // 3. 題目有「左邊最接近」、「右邊最接近」，可以考慮單調棧
    public int minAbsoluteDifference(List<Integer> nums, int x) {
        // 創建一個有序集合，用於存儲距離當前下標至少為 x 的元素。
        TreeMap<Integer, Integer> tm = new TreeMap<>();
        int ans = 1 << 30;
        for (int i = x; i < nums.size(); i++) {
            // 接下來，從下標 i = x 開始枚舉(形成窗口)，每次將 nums[i - x] 加入到有序集合中。
            tm.merge(nums.get(i - x), 1, Integer::sum);

            // 然後找出有序集合中與 nums[i] 最接近的兩個元素，它們的差值絕對值的最小值就是答案。
            Integer key = tm.ceilingKey(nums.get(i));
            if (key != null) {
                ans = Math.min(ans, key - nums.get(i));
            }
            key = tm.floorKey(nums.get(i));
            if (key != null) {
                ans = Math.min(ans, nums.get(i) - key);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-absolute-difference-between-elements-with-constraint/solutions/2386043/ping-heng-shu-shuang-zhi-zhen-pythonjava-50eh/
    // TreeSet 做法
    public int minAbsoluteDifference2(List<Integer> nums, int x) {
        int[] a = nums.stream().mapToInt(i -> i).toArray();
        int ans = Integer.MAX_VALUE, n = a.length;
        TreeSet<Integer> s = new TreeSet<>();
        s.add(Integer.MAX_VALUE); // 哨兵
        s.add(Integer.MIN_VALUE / 2); // MIN_VALUE / 2 是為了避免 (y - 較小值) 越界
        for (int i = x; i < n; i++) {
            s.add(a[i - x]);
            int y = a[i];
            ans = Math.min(ans, Math.min(s.ceiling(y) - y, y - s.floor(y)));
        }
        return ans;
    }


    // https://leetcode.cn/problems/apply-operations-to-maximize-score/solutions/2385936/gong-xian-fa-dan-diao-zhan-pythonjavacgo-23c4/
    private static final long MOD = (long) 1e9 + 7;
    private static final int MX = (int) 1e5 + 1;
    private static final int[] omega = new int[MX];

    static {
        for (int i = 2; i < MX; i++)
            if (omega[i] == 0) // i 是質數
                for (int j = i; j < MX; j += i)
                    omega[j]++; // i 是 j 的一個質因子
    }

    public int maximumScore(List<Integer> nums, int k) {
        int[] a = nums.stream().mapToInt(i -> i).toArray();
        int n = a.length;
        int[] left = new int[n]; // 質數分數 >= omega[nums[i]] 的左側最近元素下標
        int[] right = new int[n];// 質數分數 >  omega[nums[i]] 的右側最近元素下標
        Arrays.fill(right, n);
        Deque<Integer> st = new ArrayDeque<>();
        st.push(-1); // 方便賦值 left（哨兵）
        for (int i = 0; i < n; i++) {
            // 先排出質數分數比當前值小的棧頂（單調棧）
            while (st.size() > 1 && omega[a[st.peek()]] < omega[a[i]])
                right[st.pop()] = i; // 當前數值就是棧頂右邊最接近的數
            left[i] = st.peek(); // 排出流程結束後，棧頂就是當前數值左邊最接近的數
            st.push(i);
        }

        // 常用技巧：sort index by array element，這邊是從大到小排列，為了從大的開始枚舉
        // 後面遍歷 sorted index 即可
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) ids[i] = i;
        Arrays.sort(ids, (i, j) -> a[j] - a[i]);

        long ans = 1;
        for (int i : ids) {
            // 根據乘法原理，
            // 一共有 (i - left[i]) * (right[i] - i) 個子數組以 nums[i] 作為這個子數組的質數分數。
            long tot = (long) (i - left[i]) * (right[i] - i);
            if (tot >= k) { // 剩餘操作次數不夠
                ans = ans * pow(a[i], k) % MOD;
                break;
            }
            ans = ans * pow(a[i], (int) tot) % MOD;
            k -= tot; // 更新剩餘操作次數
        }
        return (int) ans;
    }

    private long pow(long x, int n) {
        long res = 1L;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) res = res * x % MOD;
            x = x * x % MOD;
        }
        return res;
    }
}



