package WeeklyContest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Week_312 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2418.Sort%20the%20People/README.md
    public String[] sortPeople(String[] names, int[] heights) {
        int n = heights.length;
        int[][] arr = new int[n][2];
        for (int i = 0; i < n; ++i) {
            arr[i] = new int[]{heights[i], i};
        }
        Arrays.sort(arr, (a, b) -> b[0] - a[0]);
        String[] ans = new String[n];
        for (int i = 0; i < n; ++i) {
            ans[i] = names[arr[i][1]];
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2419.Longest%20Subarray%20With%20Maximum%20Bitwise%20AND/README.md
    // 由於按位與的操作，不會使得數字變大，因此最大值就是數組中的最大值。
    // 題目可以轉換為求最大值在數組中最多連續出現的次數。
    // 先遍歷一遍數組，求出最大值，然後再遍歷一遍數組，求出最大值連續出現的次數，最後返回這個次數即可。
    public int longestSubArray(int[] nums) {
        int mx = 0;
        for (int v : nums) {
            mx = Math.max(mx, v);
        }
        int ans = 0, cnt = 0;
        for (int v : nums) {
            if (v == mx) {
                ++cnt;
                ans = Math.max(ans, cnt);
            } else {
                cnt = 0;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2420.Find%20All%20Good%20Indices/README.md
    public List<Integer> goodIndices(int[] nums, int k) {
        int n = nums.length;

        // 定義兩個數組 decr 和 incr，分別表示從左到右和從右到左的非遞增和非遞減的最長子數組長度。
        // 遍歷數組，更新 decr 和 incr 數組。
        int[] decr = new int[n];
        int[] incr = new int[n];
        Arrays.fill(decr, 1);
        Arrays.fill(incr, 1);
        for (int i = 2; i < n - 1; ++i) {
            if (nums[i - 1] <= nums[i - 2]) {
                decr[i] = decr[i - 1] + 1;
            }
        }
        for (int i = n - 3; i >= 0; --i) {
            if (nums[i + 1] <= nums[i + 2]) {
                incr[i] = incr[i + 1] + 1;
            }
        }

        // 順序遍歷下標 i，若 decr[i] >= k && incr[i] >= k，則 i 為好下標。
        List<Integer> ans = new ArrayList<>();
        for (int i = k; i < n - k; i++) {
            if (decr[i] >= k && incr[i] >= k) {
                ans.add(i);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/number-of-good-paths/solution/bing-cha-ji-by-endlesscheng-tbz8/
    // https://www.bilibili.com/video/BV1ve411K7P5/
    int[] fa;

    public int numberOfGoodPaths(int[] vals, int[][] edges) {
        int n = vals.length;
        List<Integer>[] g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建圖
        }

        // 初始化並查集
        fa = new int[n];
        for (int i = 0; i < n; i++) fa[i] = i;


        // size[x] 表示節點值等於 vals[x] 的節點個數，如果按照節點值從小到大合並，size[x] 也是連通塊內的等於最大節點值的節點個數
        int[] size = new int[n];
        Arrays.fill(size, 1);
        Integer[] id = IntStream.range(0, n).boxed().toArray(Integer[]::new);
        Arrays.sort(id, (i, j) -> vals[i] - vals[j]);  // 根據vals從小到大重新排序id

        int ans = n;
        for (int x : id) {
            int vx = vals[x], fx = find(x);  // fx表示x所處集合代表元(最大節點值)
            for (int y : g[x]) {
                y = find(y);  // fy表示y所處集合代表元(最大節點值)
                if (y == fx || vals[y] > vx) continue; // 只考慮最大節點值比 vx 小的連通塊
                if (vals[y] == vx) { // 可以構成好路徑(最大節點值作為起點和終點的集合)
                    ans += size[fx] * size[y]; // 乘法原理
                    size[fx] += size[y]; // 統計連通塊內節點值等於 vx 的節點個數
                }
                fa[y] = fx; // 把小的節點值合並到大的節點值上
            }
        }
        return ans;
    }

    // 並查集找尋父節點方法
    int find(int x) {
        if (fa[x] != x) fa[x] = find(fa[x]);
        return fa[x];
    }
}
