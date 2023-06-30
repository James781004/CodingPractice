package WeeklyContest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Week_263 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2042.Check%20if%20Numbers%20Are%20Ascending%20in%20a%20Sentence/README.md
    public boolean areNumbersAscending(String s) {
        int pre = 0;
        for (String t : s.split(" ")) {
            if (t.charAt(0) <= '9') {
                int cur = Integer.parseInt(t);
                if (pre >= cur) {
                    return false;
                }
                pre = cur;
            }
        }
        return true;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2043.Simple%20Bank%20System/README.md
    class Bank {
        private long[] balance;
        private int n;

        public Bank(long[] balance) {
            this.balance = balance;
            this.n = balance.length;
        }

        public boolean transfer(int account1, int account2, long money) {
            // 如果 account1 或 account2 大於 n 或 balance[account1 - 1] 小於 money，則返回 false
            if (account1 > n || account2 > n || balance[account1 - 1] < money) {
                return false;
            }

            // balance[account1 - 1] 減去 money，將 balance[account2 - 1] 加上 money，並返回 true
            balance[account1 - 1] -= money;
            balance[account2 - 1] += money;
            return true;
        }

        public boolean deposit(int account, long money) {
            if (account > n) {
                return false;
            }
            balance[account - 1] += money;
            return true;
        }

        public boolean withdraw(int account, long money) {
            if (account > n || balance[account - 1] < money) {
                return false;
            }
            balance[account - 1] -= money;
            return true;
        }
    }


    // https://leetcode.cn/problems/count-number-of-maximum-bitwise-or-subsets/solution/by-tong-zhu-mmeu/
    int ans = 0;

    public int countMaxOrSubsets(int[] nums) {
        // 數組中所有元素或運算肯定是最大值
        // 每個元素可選可不選，枚舉所有狀態，計算是不是等於最大值
        int n = nums.length;
        int max = 0;
        for (int num : nums) {
            max |= num;
        }

        dfs(nums, 0, 0, max);

        return ans;
    }

    private void dfs(int[] nums, int i, int or, int max) {
        if (or == max) {
            // or等於最大值了，沒有繼續搜索的必要，直接計算有多少種方案
            ans += 1 << (nums.length - i);
            return;
        }
        if (i == nums.length) {
            return;
        }

        // 當前元素選或者不選
        // 狀態恢復可省略，因為or是在參數簽名裡面處理的
        dfs(nums, i + 1, or | nums[i], max);
        dfs(nums, i + 1, or, max);
    }


    // https://leetcode.cn/problems/second-minimum-time-to-reach-destination/solution/tong-ge-lai-shua-ti-la-bfs-jian-zhi-9888-lsmc/
    public int secondMinimum(int n, int[][] edges, int time, int change) {
        // 每條邊的權重是一樣的，所以只需要找到次短路就可以了
        // 一樣使用BFS，一層一層的遍歷，第二次遇到 n 才結束

        // 先構造 n 個節點
        Node[] nodes = new Node[n + 1];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new Node();
        }

        // 整理邊，設置到 Node 的 nexts 中
        for (int[] edge : edges) {
            nodes[edge[0]].nexts.add(nodes[edge[1]]);
            nodes[edge[1]].nexts.add(nodes[edge[0]]);
        }

        // BFS使用的隊列
        Queue<Node> queue = new LinkedList<>();

        // 先把第 1 個節點入隊，並設置其訪問次數為 1
        queue.offer(nodes[1]);
        nodes[1].visited++;

        // ans 為返回的答案
        int ans = 0;
        // 遍歷的層級，與二叉樹的層序遍歷類似
        int level = 0;
        while (!queue.isEmpty()) {
            // 層級加一
            level++;

            // 計算時間
            // 經過 (2 * change) 燈由綠燈變成綠燈，並且維持 change 秒
            // 如果 ans 不在該範圍到達，就無法到達後立即出發，需要等紅燈
            // 等待時間為，一個 (2 * change) 周期，減去 到達時間
            // 例如：假設燈號變化週期為 [0...2 * change - 1]，區間總長 2 * change
            // [0...change-1] 為綠燈, [change...2 * change-1] 為紅燈，
            // ans % 區間總長 2 * change，如果小於 change 表示在綠燈區，不用多等待
            // 反之就需要多等待紅燈剩餘時間 (區間總長 - (ans % 區間總長))
            if (ans % (2 * change) >= change) {
                ans += 2 * change - ans % (2 * change);
            }
            ans += time;

            // 一層一層的遍歷
            int size = queue.size();
            while (size-- > 0) {
                // 彈出當前層的節點
                Node node = queue.poll();

                // 遍歷其下級的節點
                for (Node next : node.nexts) {
                    // 如果下級節點有等於 n 的，並且不是初始狀態，並且不等於當前層級
                    // 說明之前已經遍歷過一次了，那就直接返回吧
                    if (next == nodes[n] && next.level != 0 && next.level != level) {
                        return ans;
                    }
                    // 剪枝1：同一個層級同一個節點出現多次，只需要入隊一次
                    // 剪枝2：同一個節點被訪問了兩次及以上（同一層級多次算一次），它肯定不可能是次短路徑的一部分
                    if (next.level != level && next.visited < 2) {
                        queue.offer(next);
                        next.level = level;
                        next.visited++;
                    }
                }
            }

        }

        return -1;
    }

    static class Node {
        /**
         * 記錄從起點到該點的層級
         */
        int level = 0;
        /**
         * 記錄該點相連的節點
         */
        List<Node> nexts = new ArrayList<>();
        /**
         * 記錄被訪問過幾次，同一個節點在同一層級被訪問多次算一次
         */
        int visited = 0;
    }

}

