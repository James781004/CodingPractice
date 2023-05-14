package WeeklyContest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Week_296 {
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2293.Min%20Max%20Game/README.md
    public int minMaxGame(int[] nums) {
        for (int n = nums.length; n > 1; ) {
            n >>= 1;
            for (int i = 0; i < n; i++) {
                int a = nums[i << 1], b = nums[i << 1 | 1];
                nums[i] = i % 2 == 0 ? Math.min(a, b) : Math.max(a, b);
            }
        }
        return nums[0];
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2294.Partition%20Array%20Such%20That%20Maximum%20Difference%20Is%20K/README.md
    // 題目是要求劃分子序列，而不是子數組，因此子序列中的元素可以不連續。
    // 將數組 nums 排序，假設當前子序列的第一個元素為 a，則子序列中的最大值和最小值的差值不會超過 k
    // 因此可以遍歷數組 nums，如果當前遍歷元素 b 與 a 的差值大於 k，則更新 a 為 b，並將子序列數目加 1。
    // 遍歷結束後，即可得到最少子序列數目，注意初始時子序列數目為 1。
    public int partitionArray(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 1, a = nums[0];
        for (int b : nums) {
            if (b - a > k) {
                a = b;
                ans++;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2295.Replace%20Elements%20in%20an%20Array/README.md
    public int[] arrayChange(int[] nums, int[][] operations) {
        Map<Integer, Integer> d = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            d.put(nums[i], i); // 記錄數組 nums 中每個數字的下標
        }

        for (int[] p : operations) {
            int a = p[0], b = p[1];
            nums[d.get(a)] = b;  // nums 對應的數字替換
            d.put(b, d.get(a));  // 更新下標，因為 operations 後面還是有可能操作先前改過的下標數字，所以這更新動作是必要的
        }
        return nums;
    }


    // https://www.bilibili.com/video/BV1w34y1L7yu/
    // https://github.com/doocs/leetcode/blob/main/solution/2200-2299/2296.Design%20a%20Text%20Editor/README.md
    // 對頂棧
    class TextEditor {
        private StringBuilder left;
        private StringBuilder right;

        public TextEditor() {
            // 可以使用兩個棧 left 和 right，其中棧 left 存儲光標左邊的字符，另一個棧 right 存儲光標右邊的字符
            left = new StringBuilder();
            right = new StringBuilder();
        }

        // 當調用 addText 方法時，將 text 中的字符依次入棧 left
        public void addText(String text) {
            left.append(text);
        }

        // 當調用 deleteText 方法時，將 left 中的字符出棧最多 k 次
        public int deleteText(int k) {
            k = Math.min(k, left.length());
            left.setLength(left.length() - k);
            return k;
        }

        // 當調用 cursorLeft 方法時，將 left 中的字符出棧最多 k 次，
        // 然後將出棧的字符依次入棧 right，最後返回 left 棧最多 10 個字符
        public String cursorLeft(int k) {
            k = Math.min(k, left.length());
            for (int i = 0; i < k; i++) {
                right.append(left.charAt(left.length() - 1));
                left.deleteCharAt(left.length() - 1);
            }
            return left.substring(Math.max(left.length() - 10, 0));
        }

        // 當調用 cursorRight 方法時，將 right 中的字符出棧最多 k 次，
        // 然後將出棧的字符依次入棧 left，最後返回 left 棧最多 10 個字符
        public String cursorRight(int k) {
            k = Math.min(k, right.length());
            for (int i = 0; i < k; i++) {
                left.append(right.charAt(right.length() - 1));
                right.deleteCharAt(right.length() - 1);
            }
            return left.substring(Math.max(left.length() - 10, 0));
        }
    }


    // 雙向鏈表
    // https://leetcode.cn/problems/design-a-text-editor/solution/lian-biao-mo-ni-pythonjavacgo-by-endless-egw4/
    class TextEditorLinkedList {
        Node root, cur;

        public TextEditorLinkedList() {
            root = cur = new Node(); // 哨兵節點
            root.prev = root;
            root.next = root; // 初始化雙向鏈表，下面判斷節點的 next 若為 root，則表示 next 為空
        }

        public void addText(String text) {
            for (int i = 0; i < text.length(); i++)
                cur = cur.insert(new Node(text.charAt(i)));
        }

        public int deleteText(int k) {
            int k0 = k;
            for (; k > 0 && cur != root; --k) {
                cur = cur.prev;
                cur.next.remove();
            }
            return k0 - k;
        }

        String text() {
            StringBuilder s = new StringBuilder();
            Node cur = this.cur;
            for (int k = 10; k > 0 && cur != root; --k) {
                s.append(cur.ch);
                cur = cur.prev;
            }
            return s.reverse().toString();
        }

        public String cursorLeft(int k) {
            for (; k > 0 && cur != root; --k)
                cur = cur.prev;
            return text();
        }

        public String cursorRight(int k) {
            for (; k > 0 && cur.next != root; --k)
                cur = cur.next;
            return text();
        }
    }

    // 手寫雙向鏈表
    class Node {
        Node prev, next;
        char ch;

        Node() {
        }

        Node(char ch) {
            this.ch = ch;
        }

        // 在 this 後插入 node，並返回該 node
        Node insert(Node node) {
            node.prev = this;
            node.next = this.next;
            node.prev.next = node;
            node.next.prev = node;
            return node;
        }

        // 從鏈表中移除 this
        void remove() {
            this.prev.next = this.next;
            this.next.prev = this.prev;
        }
    }
}

