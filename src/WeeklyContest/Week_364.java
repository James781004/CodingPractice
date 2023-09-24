package WeeklyContest;

import java.util.*;

public class Week_364 {
    // https://leetcode.cn/problems/maximum-odd-binary-number/solutions/2456571/zui-hou-yi-wei-shi-1ba-sheng-xia-de-1wan-jo37/
    // 最後一位是1，把剩下的1往前面堆
    public String maximumOddBinaryNumber(String s) {
        int n = s.length();
        int num_1 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) - '0' == 1) num_1++;
        }
        StringBuilder ans = new StringBuilder();

        if (num_1 == 1) {
            for (int i = 0; i < n - 1; i++) {
                ans.append('0');
            }
            ans.append('1');
            return ans.toString();
        }

        if (num_1 > 1) {
            for (int j = 0; j < num_1 - 1; j++) {
                ans.append('1');
            }
            for (int k = 0; k < n - num_1; k++) {
                ans.append('0');
            }
            ans.append('1');
            return ans.toString();
        }

        return " ";
    }


    // https://leetcode.cn/problems/beautiful-towers-i/solutions/2456656/jian-ji-mei-ju-by-yi-lu-o-cu33/
    // 數據量可以直接暴力枚舉
    public long maximumSumOfHeights0(List<Integer> maxHeights) {
        long ans = 0;
        int n = maxHeights.size();
        for (int i = 0; i < n; i++) {
            // 假設i是山頂
            long res = 0;
            int t = maxHeights.get(i);
            // 計算以i為山頂所能形成的山狀數組的元素總和
            for (int j = i; j >= 0; j--) {
                t = Math.min(maxHeights.get(j), t);
                res += t;
            }
            t = maxHeights.get(i);
            for (int j = i + 1; j < n; j++) {
                t = Math.min(maxHeights.get(j), t);
                res += t;
            }
            ans = Math.max(res, ans);
        }
        return ans;
    }

    // https://leetcode.cn/problems/beautiful-towers-i/solutions/2456565/on-qian-hou-zhui-fen-jie-dan-diao-zhan-p-w3g0/
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        int[] a = maxHeights.stream().mapToInt(i -> i).toArray();
        int n = a.length;
        long[] suf = new long[n + 1];
        Deque<Integer> st = new ArrayDeque<>();
        st.push(n); // 哨兵，避免empty的狀況
        long sum = 0;

        // 從右向左，判斷合法後綴
        for (int i = n - 1; i >= 0; i--) {
            int x = a[i];
            // st.size() > 1 才算是非空，因為要要保留哨兵，避免empty的狀況
            // 單調棧，元素值從棧底到棧頂嚴格遞增，因為目標是求 x 往右看遞減後綴
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                sum -= (long) a[j] * (st.peek() - j); // 撤銷之前加到 sum 中的
            }
            sum += (long) x * (st.peek() - i); // 從 i 到 st.peek()-1 都是 x
            suf[i] = sum;
            st.push(i);
        }

        long ans = sum;
        st.clear();
        st.push(-1); // 哨兵，避免empty的狀況，注意這邊用-1
        long pre = 0;

        // 從左向右，判斷合法前綴
        for (int i = 0; i < n; i++) {
            int x = a[i];
            // st.size() > 1 才算是非空，因為要要保留哨兵，避免empty的狀況
            // 單調棧，元素值從棧底到棧頂嚴格遞增，因為目標是求 x 往左看遞減前綴
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                pre -= (long) a[j] * (j - st.peek()); // 撤銷之前加到 pre 中的
            }
            pre += (long) x * (i - st.peek()); // 從 st.peek()+1 到 i 都是 x
            ans = Math.max(ans, pre + suf[i + 1]);
            st.push(i);
        }
        return ans;
    }


    // https://leetcode.cn/problems/beautiful-towers-ii/solutions/2456562/qian-hou-zhui-fen-jie-dan-diao-zhan-pyth-1exe/
    // 跟上面解法相同
    public long maximumSumOfHeightsII(List<Integer> maxHeights) {
        int[] a = maxHeights.stream().mapToInt(i -> i).toArray();
        int n = a.length;
        long[] suf = new long[n + 1];
        Deque<Integer> st = new ArrayDeque<Integer>();
        st.push(n); // 哨兵
        long sum = 0;
        for (int i = n - 1; i >= 0; i--) {
            int x = a[i];
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                sum -= (long) a[j] * (st.peek() - j); // 撤銷之前加到 sum 中的
            }
            sum += (long) x * (st.peek() - i); // 從 i 到 st.peek()-1 都是 x
            suf[i] = sum;
            st.push(i);
        }

        long ans = sum;
        st.clear();
        st.push(-1); // 哨兵
        long pre = 0;
        for (int i = 0; i < n; i++) {
            int x = a[i];
            while (st.size() > 1 && x <= a[st.peek()]) {
                int j = st.pop();
                pre -= (long) a[j] * (j - st.peek()); // 撤銷之前加到 pre 中的
            }
            pre += (long) x * (i - st.peek()); // 從 st.peek()+1 到 i 都是 x
            ans = Math.max(ans, pre + suf[i + 1]);
            st.push(i);
        }
        return ans;
    }


    // https://leetcode.cn/problems/count-valid-paths-in-a-tree/solutions/2456716/tu-jie-on-xian-xing-zuo-fa-pythonjavacgo-tjz2/
    // https://leetcode.cn/problems/count-valid-paths-in-a-tree/solutions/2456738/shu-dpjian-zhi-by-hiworle-pw0j/
    final static int MAX = 100000;
    final static boolean[] notPrime = new boolean[MAX + 1];
    private HashMap<Integer, Long> cache;
    private List<List<Integer>> g;

    static {
        // 預處理，記錄非質數 boolean 數組
        notPrime[1] = true;
        for (int i = 2; i <= MAX; i++) {
            if (notPrime[i]) continue;
            for (int j = 2; i * j <= MAX; j++) {
                notPrime[i * j] = true;
            }
        }
    }

    public long countPaths(int n, int[][] edges) {
        g = new ArrayList<>();
        cache = new HashMap<>();

        // 1. 建圖
        for (int i = 0; i <= n; i++) {
            g.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            g.get(edge[0]).add(edge[1]);
            g.get(edge[1]).add(edge[0]);
        }

        // 2. 對於所有質數節點，計算經過它的可能的路徑
        long ans = 0;
        for (int x = 1; x <= n; x++) {
            if (notPrime[x]) continue;

            // sum 是當前「從 x 出發的路徑數」，即 x->... 的路徑數
            long sum = 0;
            for (int y : g.get(x)) {
                long cnt = dfs(y, -1);
                // 從 x->y->... 的路徑數 + 經過 ...->x->y->... 的路徑數
                ans += cnt + cnt * sum;
                sum += cnt;
            }
        }
        return ans;
    }

    // 深搜，找「從 x 出發，路徑節點都是合數的路徑數」
    private long dfs(int x, int fa) {
        int key = hash(x, fa);
        if (cache.containsKey(key)) return cache.get(key);
        if (!notPrime[x]) return 0;

        long cnt = 1;
        for (int y : g.get(x)) {
            if (y != fa)
                cnt += dfs(y, x);
        }
        cache.put(key, cnt);
        return cnt;
    }

    private int hash(int x, int fa) {
        return x * MAX + fa;
    }
}
