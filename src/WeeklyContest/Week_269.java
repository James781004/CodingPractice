package WeeklyContest;

import java.util.*;

class Week_269 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2089.Find%20Target%20Indices%20After%20Sorting%20Array/README.md
    public List<Integer> targetIndices(int[] nums, int target) {
        Arrays.sort(nums);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < nums.length; ++i) {
            if (nums[i] == target) {
                ans.add(i);
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2090.K%20Radius%20Subarray%20Averages/README.md
    public int[] getAverages(int[] nums, int k) {
        int n = nums.length;

        // 前綴和
        long[] s = new long[n + 1];
        for (int i = 0; i < n; ++i) {
            s[i + 1] = s[i] + nums[i];
        }

        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n; i++) {
            // 邊界範圍內枚舉所有合法的圓心
            if (i - k >= 0 && i + k < n) {
                ans[i] = (int) ((s[i + k + 1] - s[i - k]) / (k << 1 | 1));
            }
        }
        return ans;
    }

    // 滑動窗口
    public int[] getAverages2(int[] nums, int k) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        long s = 0;
        for (int i = 0; i < n; i++) {
            s += nums[i]; // 新增右邊
            if (i >= k * 2) {
                ans[i - k] = (int) (s / (k * 2 + 1));
                s -= nums[i - k * 2]; // 排出左邊
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2091.Removing%20Minimum%20and%20Maximum%20From%20Array/README.md
    // 先找出最小值和最大值的下標 mi, mx。如果 mi 下標大於 mx，則將 mx 與 mi 兩數進行交換。
    // 最小刪除的次數，共有 3 種情況：
    // 1. 從左側往右依次刪除 nums[mi] 和 nums[mx]
    // 2. 從右側往左依次刪除 nums[mx] 和 nums[mi]
    // 3. 從左側往右刪除 nums[mi]，從右側往左刪除 nums[mx]
    // 求這 3 種情況的最小值即可。
    public int minimumDeletions(int[] nums) {
        int mi = 0, mx = 0, n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[mi]) {
                mi = i;
            }
            if (nums[i] > nums[mx]) {
                mx = i;
            }
        }
        if (mi > mx) {
            int t = mx;
            mx = mi;
            mi = t;
        }
        return Math.min(Math.min(mx + 1, n - mi), mi + 1 + n - mx);
    }


    // https://leetcode.cn/problems/find-all-people-with-secret/solution/bing-cha-ji-pai-xu-javashuang-bai-xiang-5gbrx/
    // 並查集+排序
    // 並查集數組，記錄每個元素的祖先節點
    public int[] p;

    // 查找每個元素的祖先，（路徑壓縮，並查集模板）
    public int find(int x) {
        if (p[x] != x) p[x] = find(p[x]);
        return p[x];
    }

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        p = new int[n + 1];
        // 祖先數組初始化，將每個元素的祖先標記為自己
        for (int i = 1; i <= n; i++) p[i] = i;

        // 合併0號專家與firstPerson，之後祖先為0的元素即為知道秘密的專家
        p[firstPerson] = 0;

        Map<Integer, List<int[]>> map = new TreeMap<>();
        // 構造以時刻為key，會議列表為value的Map，TreeMap將自動按照key升序排序
        for (int[] m : meetings) {
            // m[2]為會議時刻，每個時刻對應多場會議
            List<int[]> list = map.getOrDefault(m[2], new ArrayList<>());
            list.add(m);
            map.put(m[2], list);
        }

        // 對於每個時刻，遍歷兩次
        for (int x : map.keySet()) {
            // 第一輪遍歷，合併集合
            for (int[] l : map.get(x)) {
                int a = l[0], b = l[1];
                if (p[find(a)] == 0 || p[find(b)] == 0) {
                    p[find(a)] = 0;
                    p[find(b)] = 0;
                }
                p[find(b)] = p[find(a)];
            }

            // 第二輪遍歷，分場景討論
            for (int[] l : map.get(x)) {
                int a = l[0], b = l[1];

                // 場景一：兩位專家在前面的會議均不知道秘密，後面遍歷中其中一位專家知道了秘密，瞬時共享，兩人都將知道秘密
                if (p[find(a)] == 0 || p[find(b)] == 0) {
                    p[find(a)] = 0;
                    p[find(b)] = 0;
                }

                // 場景二：兩位專家在該時刻始終都不知道秘密，將合並的集合分離開，防止後面時刻有一個專家知道秘密，將秘密分享給另一個專家
                else {
                    p[a] = a;
                    p[b] = b;
                }
            }
        }

        List<Integer> ans = new ArrayList<>();
        // 祖先為0的元素即為知道秘密的專家
        for (int i = 0; i < n; i++) {
            if (p[find(i)] == 0) ans.add(i);
        }
        return ans;
    }


    // BFS
    // https://leetcode.cn/problems/find-all-people-with-secret/solution/zhao-chu-zhi-xiao-mi-mi-de-suo-you-zhuan-fzxf/
    public List<Integer> findAllPeopleBFS(int n, int[][] meetings, int firstPerson) {
        int m = meetings.length;
        // 1.按時間排序
        Arrays.sort(meetings, Comparator.comparingInt(x -> x[2]));

        // 2.已知秘密的人設置為true
        boolean[] secret = new boolean[n];
        secret[0] = secret[firstPerson] = true;

        Map<Integer, List<Integer>> edges = new HashMap<>();//關聯點

        for (int i = 0; i < m; ) {
            // 找到這輪的范圍
            int j = i;
            while (j + 1 < m && meetings[j + 1][2] == meetings[i][2]) {
                ++j;
            }

            edges.clear();
            for (int k = i; k <= j; ++k) {
                int x = meetings[k][0];
                int y = meetings[k][1];
                // 加入關聯點
                List<Integer> l = edges.getOrDefault(x, new ArrayList<>());
                l.add(y);
                edges.put(x, l);
                l = edges.getOrDefault(y, new ArrayList<>());
                l.add(x);
                edges.put(y, l);
            }

            // 當前範圍已知點
            Queue<Integer> queue = new LinkedList<>();
            for (int u : edges.keySet()) {
                if (secret[u]) {
                    queue.offer(u);
                }
            }

            // 假設一個知道，則此輪關聯點也必然知道
            while (!queue.isEmpty()) {
                int u = queue.poll();
                List<Integer> list = edges.getOrDefault(u, new ArrayList<>());
                for (int v : list) {
                    if (!secret[v]) {
                        secret[v] = true;
                        queue.offer(v);
                    }
                }
            }

            i = j + 1;
        }

        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (secret[i]) {
                ans.add(i);
            }
        }
        return ans;
    }
}

