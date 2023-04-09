package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class Week_339 {
    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2609.Find%20the%20Longest%20Balanced%20Substring%20of%20a%20Binary%20String/README.md
    public int findTheLongestBalancedSubstring(String s) {
        int zero = 0, one = 0;
        int ans = 0, n = s.length();
        for (int i = 0; i < n; ++i) {
            if (s.charAt(i) == '0') {
                // 如果當前字符為 '0'，我們判斷此時 one 是否大於 0
                // 是則將 zero 和 one 重置為 0
                // 接下來將 zero 加 1。
                if (one > 0) {
                    zero = 0;
                    one = 0;
                }
                ++zero;
            } else {
                // 如果當前字符為 '1'，則將 one 加 1 ，並更新答案
                // 取zero one較小的一方進行配對
                ans = Math.max(ans, 2 * Math.min(zero, ++one));
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2610.Convert%20an%20Array%20Into%20a%202D%20Array%20With%20Conditions/README.md
    public List<List<Integer>> findMatrix(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;

        // 先用數組或哈希表 cnt 統計數組nums中每個元素出現的次數
        int[] cnt = new int[n + 1];
        for (int x : nums) {
            ++cnt[x];
        }

        // 遍歷 cnt ，對於每個元素 i，
        // 我們將其添加到答案列表中的第 0 行，第 1 行，第 2 行，...，第 cnt[i] - 1 行。
        for (int i = 1; i <= n; i++) {
            int v = cnt[i];
            for (int j = 0; j < v; j++) {
                if (ans.size() <= j) {
                    ans.add(new ArrayList<>());
                }
                ans.get(j).add(i);
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2600-2699/2611.Mice%20and%20Cheese/README.md
    // 先將所有奶酪分給第二只老鼠，接下來，考慮將其中 k 塊奶酪分給第一只老鼠
    // 第 i 塊奶酪從第二只老鼠分給第一只老鼠，得分的變化量為 reward1[i] - reward2[i]，
    // 我們希望這個變化量盡可能大，這樣才能使得總得分最大
    // 因此，我們將奶酪按照 reward1[i] - reward2[i] 從大到小排序，
    // 前 k 塊奶酪由第一只老鼠吃掉，剩下的奶酪由第二只老鼠吃掉，即可得到最大得分。
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int ans = 0;
        int n = reward1.length;
        for (int i = 0; i < n; i++) {
            ans += reward2[i];
            reward1[i] -= reward2[i];
        }

        Arrays.sort(reward1);

        for (int i = 0; i < k; i++) {
            ans += reward1[n - i - 1];
        }
        return ans;
    }


    // https://leetcode.cn/problems/minimum-reverse-operations/solution/liang-chong-zuo-fa-ping-heng-shu-bing-ch-vr0z/
    public int[] minReverseOperations(int n, int p, int[] banned, int k) {
        // 對於子數組 [L,R] 中的任意下標 i，翻轉後的下標是 L+R−i（
        // 中心對稱翻轉，兩個下標相加恆等於 L+R）。
        //
        // 那麼：
        // 當子數組向右滑動時，L 和 R 都增加 1，所以翻轉後的下標會增加 2
        // 當子數組向左滑動時，L 和 R 都減少 1，所以翻轉後的下標會減少 2
        // 因此，i 翻轉後的所有位置組成了一個公差為 2 的等差數列（不考慮banned）
        boolean[] ban = new boolean[n];
        ban[p] = true;
        for (int i : banned) ban[i] = true;

        // 用兩棵平衡樹分別維護"不等於 p" 以及 "不在 banned 中"的偶數下標和奇數下標。
        // 然後用 BFS 模擬。
        // 在對應的平衡樹上，一邊遍歷翻轉後的所有位置，一邊把平衡樹上的下標刪除，加到隊列中。
        // 這樣可以避免重復訪問已經訪問過的節點。
        TreeSet<Integer>[] sets = new TreeSet[2];
        sets[0] = new TreeSet<>();
        sets[1] = new TreeSet<>();
        for (int i = 0; i < n; i++)
            if (!ban[i]) sets[i % 2].add(i);
        sets[0].add(n);
        sets[1].add(n); // 哨兵

        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        List<Integer> q = new ArrayList<>();
        q.add(p);

        for (int step = 0; !q.isEmpty(); ++step) {
            List<Integer> tmp = q;
            q = new ArrayList<>();
            for (int i : tmp) {
                ans[i] = step;
                // 從 mn 到 mx 的所有位置都可以翻轉到
                // 如果不考慮數組的邊界，那麼范圍是 [i−k+1,i+k−1]。
                // 如果 i 在數組左邊界 0 附近，那麼翻轉時會受到數組左邊界的約束，
                // 當子數組在最左邊時，L=0,R=k−1， i 翻轉後是 0+(k−1)−i=k−i−1，所以小於  k−i−1 的點是無法翻轉到的；
                // 如果 i 在數組右邊界 n−1 附近，那麼翻轉時會受到數組右邊界的約束，
                // 當子數組在最右邊時，L=n−k,R=n−1，
                // i 翻轉後是  (n−k)+(n−1)−i=2n−k−i−1，所以大於 2n−k−i−1 的點是無法翻轉到的。
                // 所以實際范圍為 [max(i−k+1,k−i−1),min(i+k−1,2n−k−i−1)]
                int mn = Math.max(i - k + 1, k - i - 1);
                int mx = Math.min(i + k - 1, n * 2 - k - i - 1);
                TreeSet<Integer> s = sets[mn % 2];
                for (int j = s.ceiling(mn); j <= mx; j = s.ceiling(mn)) {
                    q.add(j);
                    s.remove(j);
                }
            }
        }
        return ans;
    }

}
