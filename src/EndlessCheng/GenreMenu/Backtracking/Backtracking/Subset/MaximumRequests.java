package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class MaximumRequests {

    // https://leetcode.cn/problems/maximum-number-of-achievable-transfer-requests/solutions/2309995/ling-shen-zi-ji-xing-hui-su-ke-hou-zuo-y-c724/
    int max = 0;
    private int[][] requests;
    private int[] cache; // cache 表示度：出去−1， 進來+1，如果這棟樓的淨變化為 0，那麼 cache最後應當為 0
    private int count = 0; // count 表示當前滿足的請求


    public int maximumRequests(int n, int[][] requests) {
        this.cache = new int[n];
        this.requests = requests;

        dfs(0);
        return max;
    }

    // i表示當前遍歷的元素
    public void dfs(int i) {
        if (check()) {
            max = Math.max(max, count);
        }

        // base case
        if (i == requests.length) {
            return;
        }

        // 不選
        dfs(i + 1);

        // 選第 i 個request
        cache[requests[i][0]]--; // 出去
        cache[requests[i][1]]++; // 進去
        count++;

        dfs(i + 1);

        // 恢復現場
        cache[requests[i][0]]++; // 出去
        cache[requests[i][1]]--; // 進去
        count--;
    }

    public boolean check() {
        for (int x : cache) {
            if (x != 0) {
                return false;
            }
        }
        return true;
    }


}
