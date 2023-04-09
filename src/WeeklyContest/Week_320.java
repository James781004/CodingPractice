package WeeklyContest;

import java.util.*;

public class Week_320 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2475.Number%20of%20Unequal%20Triplets%20in%20Array/README.md
    public int unequalTriplets(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] != nums[j] && nums[j] != nums[k] && nums[i] != nums[k]) {
                        ++ans;
                    }
                }
            }
        }
        return ans;
    }

    public int unequalTriplets2(int[] nums) {
        Arrays.sort(nums);
        int ans = 0, n = nums.length;
        for (int j = 1; j < n - 1; ++j) {
            int i = search(nums, nums[j], 0, j) - 1;
            int k = search(nums, nums[j] + 1, j + 1, n);
            if (i >= 0 && k < n) {
                ans += (i + 1) * (n - k);
            }
        }
        return ans;
    }

    private int search(int[] nums, int x, int left, int right) {
        while (left < right) {
            int mid = (left + right) >> 1;
            if (nums[mid] >= x) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    // 使用雜湊表 cnt 來統計陣列 nums 中每個元素的數量。
    // 然後遍歷雜湊表
    // 列舉中間元素的個數 b，左側元素個數記為 a，那麼右側元素個數有 n - a - b
    // 此時符合條件的三元組數量為 a * b * c
    // 累加到答案中。接著更新 a = a + b，繼續列舉中間元素的個數。
    public int unequalTriplets3(int[] nums) {
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int v : nums) {
            cnt.put(v, cnt.getOrDefault(v, 0) + 1);
        }
        int ans = 0, a = 0;
        int n = nums.length;
        for (int b : cnt.values()) {
            int c = n - a - b;
            ans += a * b * c;
            a += b;
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2476.Closest%20Nodes%20Queries%20in%20a%20Binary%20Search%20Tree/README.md
    class ClosestNodes {
        private List<Integer> nums = new ArrayList<>();

        public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;

            TreeNode() {
            }

            TreeNode(int val) {
                this.val = val;
            }

            TreeNode(int val, TreeNode left, TreeNode right) {
                this.val = val;
                this.left = left;
                this.right = right;
            }
        }

        public List<List<Integer>> closestNodes(TreeNode root, List<Integer> queries) {
            dfs(root);
            List<List<Integer>> ans = new ArrayList<>();
            for (int v : queries) {
                int i = search(v + 1) - 1;
                int j = search(v);
                int mi = i >= 0 && i < nums.size() ? nums.get(i) : -1;
                int mx = j >= 0 && j < nums.size() ? nums.get(j) : -1;
                ans.add(Arrays.asList(mi, mx));
            }
            return ans;
        }

        // 通過中序遍歷得到一個有序陣列
        private void dfs(TreeNode root) {
            if (root == null) {
                return;
            }
            dfs(root.left);
            nums.add(root.val);
            dfs(root.right);
        }

        // 通過二分尋找得到小於等於該查詢值的最大值和大於等於該查詢值的最小值
        private int search(int x) {
            int left = 0, right = nums.size();
            while (left < right) {
                int mid = (left + right) >> 1;
                if (nums.get(mid) >= x) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            return left;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2477.Minimum%20Fuel%20Cost%20to%20Report%20to%20the%20Capital/README.md
    class MinimumFuelCost {
        private List<Integer>[] g;
        private long ans;
        private int seats;

        // 建圖，然後用 DFS 統計以每個點為根的子樹的節點個數
        public long minimumFuelCost(int[][] roads, int seats) {
            int n = roads.length + 1;
            g = new List[n];
            Arrays.setAll(g, k -> new ArrayList<>());
            this.seats = seats;
            for (int[] e : roads) {
                int a = e[0], b = e[1];
                g[a].add(b);
                g[b].add(a);
            }
            dfs(0, -1);
            return ans;
        }

        private int dfs(int a, int fa) {
            int size = 1;
            for (int b : g[a]) {
                if (b != fa) {
                    int t = dfs(b, a);
                    ans += (t + seats - 1) / seats;
                    size += t;
                }
            }
            return size;
        }
    }


    // https://leetcode.cn/problems/number-of-beautiful-partitions/solution/dong-tai-gui-hua-jian-ji-xie-fa-xun-huan-xyw3/
    // https://www.bilibili.com/video/BV1A3411f7H3/
    class BeautifulPartitions {
        private static final int MOD = (int) 1e9 + 7;

        public int beautifulPartitions(String S, int k, int l) {
            char[] s = S.toCharArray();
            int n = S.length();
            if (k * l > n || !isPrime(s[0]) || isPrime(s[n - 1])) // 剪枝
                return 0;
            int[][] f = new int[k + 1][n + 1];
            f[0][0] = 1;
            for (int i = 0; i <= k; i++) {
                int sum = 0;

                // 最佳化：列舉的起點和終點需要給前後的子串預留出足夠的長度
                for (int j = i * l; j + (k - i) * l <= n; j++) {
                    if (canPartition(s, j - l)) sum = (sum + f[i - 1][j - l]) % MOD; // j'=j-l 雙指針
                    if (canPartition(s, j)) f[i][j] = sum;
                }
            }

            return f[k][n];
        }

        private boolean isPrime(char c) {
            return c == '2' || c == '3' || c == '5' || c == '7';
        }

        // 判斷是否可以在 j-1 和 j 之間分割（開頭和末尾也算）
        private boolean canPartition(char[] s, int j) {
            return j == 0 || j == s.length || !isPrime(s[j - 1]) && isPrime(s[j]);
        }
    }
}
