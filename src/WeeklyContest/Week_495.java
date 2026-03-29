package WeeklyContest;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Week_495 {

    // https://leetcode.cn/problems/first-matching-character-from-both-ends/solutions/3939578/bian-li-yi-ban-ji-ke-pythonjavacgo-by-en-c7i4/
    public int firstMatchingIndex(String S) {
        char[] s = S.toCharArray();
        int n = s.length;
        for (int i = 0; i <= n / 2; i++) {
            if (s[i] == s[n - 1 - i]) {
                return i;
            }
        }
        return -1;
    }


    // https://leetcode.cn/problems/design-event-manager/solutions/3939577/lan-geng-xin-dui-pythonjavacgo-by-endles-iwuh/
    class EventManager {
        private final Map<Integer, Integer> idToPriority = new HashMap<>();
        private final PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) ->
                a[0] != b[0] ? b[0] - a[0] : a[1] - b[1]
        );

        public EventManager(int[][] events) {
            for (int[] e : events) {
                int id = e[0];
                int priority = e[1];
                idToPriority.put(id, priority);
                pq.offer(new int[]{priority, id});
            }
        }

        public void updatePriority(int eventId, int newPriority) {
            idToPriority.put(eventId, newPriority);
            pq.offer(new int[]{newPriority, eventId});
        }

        public int pollHighest() {
            while (!pq.isEmpty()) {
                int[] e = pq.poll();
                int priority = e[0];
                int id = e[1];
                if (idToPriority.getOrDefault(id, -1) == priority) {
                    idToPriority.remove(id);
                    return id;
                }
                // else 貨不對板，繼續找下一個
            }
            return -1;
        }
    }


    // https://leetcode.cn/problems/sum-of-sortable-integers/solutions/3939560/yu-chu-li-mei-ju-yin-zi-pythonjavacgo-by-o0q9/
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int[] nextDec = new int[n]; // nums[nextDec[i]] > nums[nextDec[i] + 1]
        nextDec[n - 1] = n;
        int p = n;
        // 對於每個 i，記錄下一個遞減的位置
        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > nums[i + 1]) {
                p = i;
            }
            nextDec[i] = p;
        }

        int ans = 0;
        // 枚舉 n 的因子 k
        for (int k = 1; k * k <= n; k++) {
            if (n % k == 0) {
                ans += solve(k, nums, nextDec);
                if (k * k < n) {
                    ans += solve(n / k, nums, nextDec);
                }
            }
        }
        return ans;
    }

    private int solve(int k, int[] nums, int[] nextDec) {
        int lastMax = 0; // 上一段的最大值
        for (int r = k - 1; r < nums.length; r += k) {
            int l = r - k + 1;
            int m = nextDec[l];
            if (m >= r) {
                // [l, r] 是遞增的，最小值為 nums[l]，最大值為 nums[r]
                // nums[l] 必須 >= 上一段的最大值
                if (nums[l] < lastMax) {
                    return 0;
                }
                lastMax = nums[r];
            } else {
                // [l, m] 是第一段，[m+1, r] 是第二段
                // 第二段必須是遞增的，且第二段的最小值 >= 上一段的最大值，且第二段最大值 <= 第一段的最大值
                if (nextDec[m + 1] < r || nums[m + 1] < lastMax || nums[r] > nums[l]) {
                    return 0;
                }
                lastMax = nums[m];
            }
        }
        return k;
    }


    // https://leetcode.cn/problems/incremental-even-weighted-cycle-queries/solutions/3939570/dai-quan-bing-cha-ji-pythonjavacgo-by-en-3ka2/
    public int numberOfEdgesAdded(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        int ans = 0;
        for (int[] e : edges) {
            if (uf.merge(e[0], e[1], e[2])) {
                ans++;
            }
        }
        return ans;
    }


    class UnionFind {
        private final int[] fa; // fa[x] 是 x 的代表元
        private final int[] dis; // dis[x] = 從 x 到 fa[x] 的路徑異或和

        public UnionFind(int n) {
            fa = new int[n];
            dis = new int[n];
            for (int i = 0; i < n; i++) {
                fa[i] = i;
            }
        }

        public int find(int x) {
            if (fa[x] != x) {
                int root = find(fa[x]);
                dis[x] ^= dis[fa[x]];
                fa[x] = root;
            }
            return fa[x];
        }

        public boolean merge(int from, int to, int value) {
            int x = find(from), y = find(to);
            if (x == y) {
                return (dis[from] ^ dis[to]) == value;
            }
            dis[x] = value ^ dis[to] ^ dis[from];
            fa[x] = y;
            return true;
        }
    }


}









