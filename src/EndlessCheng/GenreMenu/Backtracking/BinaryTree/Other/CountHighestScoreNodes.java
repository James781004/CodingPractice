package EndlessCheng.GenreMenu.Backtracking.BinaryTree.Other;

public class CountHighestScoreNodes {

    // https://leetcode.cn/problems/count-nodes-with-the-highest-score/solutions/1327214/tong-ge-lai-shua-ti-la-liang-chong-xie-f-xu45/
    long maxScore = 0;
    int ans = 0;
    int n;

    public int countHighestScoreNodes(int[] parents) {
        // 刪除一個節點最多把整顆樹分割成三部分：左子樹、右子樹、父節點及父節點的另一半子樹
        // 所以，我們可以遍歷每個節點的左右子樹的數目，父節點及父節點的另一半子樹的數量就等於 總節點數 減去 左右子樹的數目 再減 一
        // 三者相乘就是分數，沒有的部分用 1 代替
        // 而我們需要先構造出來這顆樹才能通過DFS遍歷
        this.n = parents.length;

        int[] lefts = new int[n];
        int[] rights = new int[n];
        for (int i = 0; i < n; i++) {
            lefts[i] = -1;
            rights[i] = -1;
        }

        for (int i = 1; i < n; i++) {
            int p = parents[i];
            if (lefts[p] == -1) {
                lefts[p] = i;
            } else {
                rights[p] = i;
            }
        }

        dfs(0, lefts, rights);

        return ans;
    }

    private int dfs(int node, int[] lefts, int[] rights) {
        if (node == -1) {
            return 0;
        }

        int leftCount = dfs(lefts[node], lefts, rights);
        int rightCount = dfs(rights[node], lefts, rights);
        int remain = n - leftCount - rightCount - 1;

        long score = help(leftCount) * help(rightCount) * help(remain);
        if (score == maxScore) {
            ans++;
        } else if (score > maxScore) {
            maxScore = score;
            ans = 1;
        }

        return leftCount + rightCount + 1;
    }

    private long help(int count) {
        return count == 0 ? 1 : count;
    }


}
