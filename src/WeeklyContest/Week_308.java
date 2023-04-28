package WeeklyContest;

import java.util.*;

public class Week_308 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2389.Longest%20Subsequence%20With%20Limited%20Sum/README.md
    public int[] answerQueries(int[] nums, int[] queries) {
        Arrays.sort(nums);
        for (int i = 1; i < nums.length; ++i) {
            nums[i] += nums[i - 1];  // 前綴和
        }

        int m = queries.length;
        int[] ans = new int[m];
        for (int i = 0; i < m; ++i) {
            ans[i] = search(nums, queries[i]);  // 二分查找滿足條件的子序列的元素和
        }
        return ans;
    }

    private int search(int[] nums, int x) {
        int l = 0, r = nums.length;
        while (l < r) {
            int mid = (l + r) >> 1;
            if (nums[mid] > x) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return l;
    }


    // 排序 + 離線查詢 + 雙指針
    public int[] answerQueries2(int[] nums, int[] queries) {
        Arrays.sort(nums);
        int m = queries.length;
        Integer[] idx = new Integer[m];
        for (int i = 0; i < m; ++i) {
            idx[i] = i;
        }

        Arrays.sort(idx, (i, j) -> queries[i] - queries[j]);  // 按照 queries 中的元素值進行升序排序
        int[] ans = new int[m];
        int s = 0;  // 當前已經選擇的元素的和
        int j = 0;  // 記錄當前已經選擇的元素的個數
        for (int i : idx) {
            while (j < nums.length && s + nums[j] <= queries[i]) {
                s += nums[j++];
            }
            ans[i] = j;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2390.Removing%20Stars%20From%20a%20String/README.md
    // 使用棧模擬操作過程。
    // 遍歷字符串 s ，如果當前字符不是星號，則將其入棧；\如果當前字符是星號，則將棧頂元素出棧。
    // 最後將棧中元素拼接成字符串返回即可。
    public String removeStars(String s) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '*') {
                ans.deleteCharAt(ans.length() - 1);
            } else {
                ans.append(s.charAt(i));
            }
        }
        return ans.toString();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2391.Minimum%20Amount%20of%20Time%20to%20Collect%20Garbage/README.md
    public int garbageCollection(String[] garbage, int[] travel) {

        // 紀錄每一種字符在 garbage 中最後一次出現的下標，即每輛垃圾車必須向右開到的房子的最小值
        int[] last = new int[26];
        int n = garbage.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            int k = garbage[i].length();
            ans += k;
            for (int j = 0; j < k; ++j) {
                last[garbage[i].charAt(j) - 'A'] = i;
            }
        }

        // 算出 travel 的前綴和
        int m = travel.length;
        int[] s = new int[m + 1];
        for (int i = 0; i < m; ++i) {
            s[i + 1] = s[i] + travel[i];
        }

        // 根據每一種垃圾在 garbage 中最後一次出現的位置 i，累加 travel[0..i) 即可
        for (int i : last) {
            ans += s[i];
        }
        return ans;
    }


    // https://leetcode.cn/problems/build-a-matrix-with-conditions/solution/by-endlesscheng-gpev/
    // https://www.bilibili.com/video/BV1mG411V7fj/
    public int[][] buildMatrix(int k, int[][] rowConditions, int[][] colConditions) {
        int[] row = topoSort(k, rowConditions), col = topoSort(k, colConditions);
        if (row.length < k || col.length < k) return new int[][]{};
        int[] pos = new int[k];
        for (int i = 0; i < k; ++i)
            pos[col[i]] = i;  // 記錄col[i]的位置資訊
        int[][] ans = new int[k][k];
        for (int i = 0; i < k; ++i)
            ans[i][pos[row[i]]] = row[i] + 1;  // 之前排序時-1，這邊補回來
        return ans;
    }

    private int[] topoSort(int k, int[][] edges) {
        List<Integer>[] g = new ArrayList[k];
        Arrays.setAll(g, e -> new ArrayList<>());
        int[] inDeg = new int[k];
        for (int[] e : edges) {
            int x = e[0] - 1, y = e[1] - 1; // 頂點編號從 0 開始，方便計算
            g[x].add(y);
            ++inDeg[y];
        }

        List<Integer> order = new ArrayList<>();
        Deque<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < k; ++i)
            if (inDeg[i] == 0) q.push(i);  // 入度為0的點可以作為入口，入q
        while (!q.isEmpty()) {
            int x = q.pop();
            order.add(x);
            for (int y : g[x])
                if (--inDeg[y] == 0) q.push(y);  // x處理完後，原本x的鄰接點入度變為0的可以作為入口，入q
        }
        return order.stream().mapToInt(x -> x).toArray();
    }
}
