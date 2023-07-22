package WeeklyContest;

import java.util.*;

class Week_253 {
    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1961.Check%20If%20String%20Is%20a%20Prefix%20of%20Array/README.md
    public boolean isPrefixString(String s, String[] words) {
        StringBuilder t = new StringBuilder();
        for (String w : words) {
            t.append(w);
            if (s.length() == t.length()) {
                return s.equals(t.toString());
            }
        }
        return false;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1962.Remove%20Stones%20to%20Minimize%20the%20Total/README.md
    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> (b - a));
        for (int p : piles) {
            q.offer(p);
        }
        while (!q.isEmpty() && k-- > 0) {
            int p = q.poll();
            q.offer((p + 1) >> 1);
        }
        int ans = 0;
        while (!q.isEmpty()) {
            ans += q.poll();
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-number-of-swaps-to-make-the-string-balanced/solution/go-tan-xin-by-endlesscheng-7h9n/
    // 貪心及其正確性證明
    // 對於一個平衡字符串，從左往右遍歷它，統計未匹配的左括號的個數 c，
    // 遇到左括號就加一，遇到右括號就減一，如果任何時刻 c 都不為負數，那麼這個字符串就是平衡的。
    // 如果遍歷時遇到右括號並且此時 c=0，那麼就需要在後面找一個左括號並與這個右括號交換。
    // 為了使後續的交換次數最小，這個被交換走的右括號應當越靠右越好，所以可以拿字符串最右邊的左括號來交換。
    public int minSwaps(String s) {
        int ans = 0;
        for (char c : s.toCharArray()) {
            if (c == '[') {
                ++ans;
            } else if (ans > 0) {
                --ans;
            }
        }
        return (ans + 1) >> 1;
    }


    // https://leetcode.cn/problems/find-the-longest-valid-obstacle-course-at-each-position/solution/zhao-chu-dao-mei-ge-wei-zhi-wei-zhi-zui-pb8mu/
    // 其實就是LIS題目的變體
    // 可以發現，我們需要求出的是數組 obstacles 中以每一個下標為結束位置的「最長遞增子序列」的長度，
    // 其中「遞增」表示子序列中相鄰兩個元素需要滿足前者小於等於後者。
    // 而 300 題需要求出的是數組中的「最長嚴格遞增子序列」，只需要修改比較兩個元素的大小關系的邏輯（將「小於等於」改成「小於」，反之亦然），
    // 就可以實現這兩種問題之間的相互轉換。
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        int n = obstacles.length;
        List<Integer> list = new ArrayList();
        list.add(obstacles[0]);
        int[] ans = new int[n];
        ans[0] = 1;
        for (int i = 1; i < n; i++) {
            if (obstacles[i] >= list.get(list.size() - 1)) {    // 很大，直接放在最後
                list.add(obstacles[i]);
                ans[i] = list.size();
            } else {
                // 尋找第一個大於obstacles[i]的數字
                int l = 0, r = list.size() - 1;
                while (l < r) {
                    int mid = l + r >> 1;
                    if (list.get(mid) <= obstacles[i]) l = mid + 1;
                    else r = mid;
                }
                list.set(l, obstacles[i]);
                ans[i] = l + 1;
            }
        }
        return ans;
    }

    // https://github.com/doocs/leetcode/blob/main/solution/1900-1999/1964.Find%20the%20Longest%20Valid%20Obstacle%20Course%20at%20Each%20Position/README.md
    // 樹狀數組，也稱作“二叉索引樹”（Binary Indexed Tree）或 Fenwick 樹。 它可以高效地實現如下兩個操作：
    // 1. 單點更新 update(x, delta)： 把序列 x 位置的數加上一個值 delta；
    // 2. 前綴和查詢 query(x)：查詢序列 [1,...x] 區間的區間和，即位置 x 的前綴和。
    // 這兩個操作的時間復雜度均為 O(logn)。
    // 當數的範圍比較大時，需要進行離散化，即先進行去重並排序，然後對每個數字進行編號。
    // 本題使用樹狀數組 tree[x] 來維護以 x 結尾的最長上升子序列的長度。
    public int[] longestObstacleCourseAtEachPositionBIT(int[] obstacles) {
        TreeSet<Integer> ts = new TreeSet();
        for (int v : obstacles) {
            ts.add(v);
        }
        int idx = 1;
        Map<Integer, Integer> m = new HashMap<>();
        for (int v : ts) {
            m.put(v, idx++);
        }
        BinaryIndexedTree tree = new BinaryIndexedTree(m.size());
        int n = obstacles.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int v = obstacles[i];
            int x = m.get(v);
            ans[i] = tree.query(x) + 1;
            tree.update(x, ans[i]);
        }
        return ans;
    }

    class BinaryIndexedTree {
        private int n;
        private int[] c;

        public BinaryIndexedTree(int n) {
            this.n = n;
            c = new int[n + 1];
        }

        public void update(int x, int val) { // 將id更新為x
            while (x <= n) {
                c[x] = Math.max(c[x], val);
                x += lowbit(x);
            }
        }

        public int query(int x) {
            int s = 0;
            while (x > 0) {
                s = Math.max(s, c[x]);
                x -= lowbit(x);
            }
            return s;
        }

        public int lowbit(int x) {
            return x & -x;
        }
    }
}


