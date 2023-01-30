package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.*;

public class Q07_Sums {
    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gf6a684186a_0_5
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int diff = target - nums[i];
            if (map.containsKey(diff)) return new int[]{map.get(diff), i};
            map.put(nums[i], i);
        }
        return null;
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gf6a684186a_0_17
    // 因為已經sorted，我們直接用2 pointer, O(N)解法，
    // follow up如果數字很大很小越界怎麼辦，我們就換成Long去做，
    // 如果target是double怎麼辦？我們就用高精度去做 target - tmp < 0.01
    public int[] twoSumII(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int tmp = nums[left] + nums[right];
            if (tmp == target) return new int[]{left + 1, right + 1};
            else if (tmp > target) right--;
            else left--;
        }
        return null;
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gf6a684186a_0_26
    class TwoSumIII {
        Set<Integer> sums = new HashSet<>();
        Set<Integer> nums = new HashSet<>();

        public void add(int number) {
            for (int n : nums) sums.add(number + n);
            nums.add(number);
        }

        public boolean find(int value) {
            return sums.contains(value);
        }

        Map<Integer, Integer> map = new HashMap<>();

        public void add2(int number) {
            map.put(number, map.getOrDefault(number, 0) + 1);
        }

        public boolean find2(int value) {
            if (map.size() == 0) return false;
            for (int key : map.keySet()) {
                int diff = value - key;
                if (diff != key && map.containsKey(diff)) return true;
                if (diff == key && map.get(key) > 1) return true;
            }
            return false;
        }
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gf6a684186a_0_34
    class TwoSumIV {
        public boolean findTarget(TreeNode root, int k) {
            Set<Integer> set = new HashSet<>();
            return dfs(root, set, k);
        }

        private boolean dfs(TreeNode root, Set<Integer> set, int k) {
            if (root == null) return false;
            if (set.contains(k - root.val)) return true;
            set.add(root.val);
            return dfs(root.left, set, k) || dfs(root.right, set, k);
        }


        public boolean findTarget2(TreeNode root, int k) {
            List<Integer> nums = new ArrayList<>();
            inorder(root, nums);
            int left = 0, right = nums.size() - 1;
            while (left < right) {
                int sum = nums.get(left) + nums.get(right);
                if (sum == k) return true;
                if (sum < k) left++;
                else right--;
            }
            return false;
        }

        private void inorder(TreeNode root, List<Integer> nums) {
            if (root == null) return;
            inorder(root.left, nums);
            nums.add(root.val);
            inorder(root.right, nums);
        }


        public boolean findTarget3(TreeNode root, int k) {
            BSTIterator left = new BSTIterator(root, true);
            BSTIterator right = new BSTIterator(root, false);

            while (left.hasNext() && right.hasNext()) {
                int l = left.peek(), r = right.peek();
                if (l >= r) return false;
                if (l + r == k) return true;
                else if (l + r < k) left.next();
                else right.next();
            }
            return false;
        }

        class BSTIterator {
            Stack<TreeNode> stack;
            boolean forward;

            public BSTIterator(TreeNode root, boolean forward) {
                this.stack = new Stack<>();
                this.forward = forward;
                if (forward) pushAllLeft(root);
                else pushAllRight(root);
            }

            public boolean hasNext() {
                return !stack.isEmpty();
            }

            public int next() {
                TreeNode node = stack.pop();
                if (forward) pushAllLeft(node.right);
                else pushAllRight(node.left);
                return node.val;
            }

            public int peek() {
                return stack.peek().val;
            }

            private void pushAllLeft(TreeNode root) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
            }

            private void pushAllRight(TreeNode root) {
                while (root != null) {
                    stack.push(root);
                    root = root.right;
                }
            }
        }


        class TreeNode {
            int val;
            TreeNode left, right;

            public TreeNode(int v) {
                val = v;
            }
        }
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gf6a684186a_0_69
    // 3Sum問題可以轉化為2Sum的問題, 這里2Sum有hashset和two pointer, 建議用two pointer更簡單更快
    class ThreeSum {
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> threeSum(int[] nums) {
            Arrays.sort(nums);
            for (int i = 0; i < nums.length - 2; i++) {
                if (i != 0 && nums[i] == nums[i - 1]) continue;
                int left = i + 1, right = nums.length - 1;
                twoSum(nums, left, right, nums[i]);
            }
            return res;
        }

        private void twoSum(int[] nums, int left, int right, int curNum) {
            while (left < right) {
                int sum = nums[left] + nums[right] + curNum;
                if (sum == 0) {
                    res.add(Arrays.asList(curNum, nums[left++], nums[right--]));
                    while (nums[left] == nums[left - 1]) left++;
                    while (nums[right] == nums[right + 1]) right--;
                } else if (sum < 0) left++;
                else right--;
            }
        }


        public List<List<Integer>> threeSum2(int[] nums) {
            Arrays.sort(nums);
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] > 0) break;
                if (i != 0 && nums[i] == nums[i - 1]) continue;
                int left = i + 1, right = nums.length - 1;
                helper(nums, left, right, -nums[i]);
            }
            return res;
        }

        private void helper(int[] nums, int left, int right, int target) {
            Set<Integer> set = new HashSet<>();
            for (int i = left; i <= right; i++) {
                if (set.contains(target - nums[i])) {
                    res.add(Arrays.asList(-target, nums[i], target - nums[i]));
                    while (i < right && nums[i] == nums[i - 1]) i++;
                }
                set.add(nums[i]);
            }
        }
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gf6a684186a_0_78
    public int threeSumClosest(int[] nums, int target) {
        int N = nums.length;
        int res = nums[0] + nums[1] + nums[N - 1];
        Arrays.sort(nums);  // sums類型題目，sort array是必要的前置動作
        for (int i = 0; i < N - 2; i++) {  // 有sort array這個前提接下來2 pointer才有辦法運作
            int left = i + 1, right = N - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < Math.abs(res - target)) res = sum;
                if (sum > target) right--;
                else left++;
            }
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gcb5084357d_0_0
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int count = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum >= target) {
                    right--;
                } else {
                    count += right - left;  // 有sort array，把區間pair數量全加起來
                    left++;
                }
            }
        }
        return count;
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gcb5084357d_0_14
    class ThreeSumMulti {
        public int threeSumMulti(int[] A, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            int res = 0;
            int mod = 1000000007;
            for (int i = 0; i < A.length; i++) {
                // 新數字和前兩個數字匹配
                res = (res + map.getOrDefault(target - A[i], 0)) % mod;
                for (int j = 0; j < i; j++) {  // 匹配i + j
                    int temp = A[i] + A[j];
                    map.put(temp, map.getOrDefault(temp, 0) + 1);
                }
            }
            return res;
        }

        public int threeSumMulti2(int[] A, int target) {
            long res = 0;
            int mod = 1000000007;
            Map<Integer, Integer> count = new HashMap<>();
            for (int a : A) count.put(a, 1 + count.getOrDefault(a, 0));
            for (int i : count.keySet()) {
                for (int j : count.keySet()) {
                    int k = target - i - j;
                    if (count.containsKey(k)) {
                        long cnt = count.get(i), cnt2 = count.get(j), cnt3 = count.get(k);
                        if (i == j && j == k) {
                            res += cnt * (cnt - 1) * (cnt - 1) / 6;  // pick 3 from cnt
                        } else if (i == j) {
                            res += cnt * (cnt - 1) / (cnt - 2) * cnt3;  // pick 2 from cnt1=2, and cnt3
                        } else if (i < j && j < k) {
                            res += cnt * cnt2 * cnt3;  // pick cnt * cnt2 * cnt3 different ways
                        }
                        res %= mod;
                    }
                }
            }
            return (int) res;
        }
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gcb5084357d_0_27
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int left, right, sum, N = nums.length;
        for (int i = 0; i < N - 3; i++) {
            if (i != 0 && nums[i] == nums[i - 1]) continue;  // 跟之前數字相同，直接跳過避免重複
            for (int j = i + 1; j < N - 2; j++) {
                if (j != i + 1 && nums[j] == nums[j - 1]) continue;  // 跟之前數字相同，直接跳過避免重複
                left = j + 1;
                right = nums.length - 1;
                while (left < right) {
                    sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left++], nums[right--]));
                        while (left < right && nums[left] == nums[left - 1]) left++;
                        while (left < right && nums[right] == nums[right + 1]) right--;
                    } else if (sum > target) right--;
                    else left++;
                }
            }
        }
        return res;
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gcb5084357d_0_35
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < D.length; j++) {
                int sum = C[i] + D[j];
                map.put(sum, 1 + map.getOrDefault(sum, 0));
            }
        }

        int res = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                res += map.getOrDefault(-(A[i] + B[j]), 0);
            }
        }

        return res;
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gcb5084357d_0_45
    // 只要求取k個數字和為target的數量，不要求內部具體是那些數字，我們就可以用背包問題，類似coin change,
    // 總錢數告訴我們後，我們看有多少種方法可以把target錢數正好分成硬幣的組合，
    // 這里硬幣使用總數必須為k，是一道DP的問題，只求count
    public int kSum(int[] A, int k, int target) {
        int N = A.length;
        int[][][] dp = new int[N + 1][k + 1][target + 1];  // dp[i][j][t]: 前i個取j個目標為t的結果數

        for (int i = 0; i <= N; i++) {
            dp[i][0][0] = 1;  // 全部硬幣都不拿，目標0，都只有一種方法
        }

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= k; j++) {
                for (int t = 1; t <= target; t++) {
                    dp[i][j][t] = 0;
                    if (t >= A[i - 1]) {
                        dp[i][j][t] = dp[i - 1][j - 1][t - A[i - 1]];  // 當前硬幣選擇拿，多重背包計算結果
                    }
                    dp[i][j][t] += dp[i - 1][j][t];  // 當前硬幣選擇不拿，直接複製i - 1的結果
                }
            }
        }

        return dp[N][k][target];
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gcb5084357d_0_53
    class KSumII {
        public List<List<Integer>> kSumII(int[] A, int k, int target) {
            List<List<Integer>> result = new ArrayList<>();
            Arrays.sort(A);
            dfs(A, 0, k, target, new ArrayList<Integer>(), result);
            return result;
        }

        private void dfs(int[] A, int startIndex, int remainCount, int remainTarget,
                         ArrayList<Integer> subset, List<List<Integer>> result) {
            if (remainTarget == 0 && remainCount == 0) {
                result.add(new ArrayList<>(subset));
                return;
            }
            if (remainCount == 0) return;

            for (int i = startIndex; i < A.length; i++) {
                if (A[startIndex] > remainTarget) break;
                subset.add(A[i]);
                dfs(A, i + 1, remainCount - 1, remainTarget - A[i], subset, result);
                subset.remove(subset.size() - 1);
            }
        }
    }


    // https://docs.google.com/presentation/d/1MCTF9_hPpqR9ZyiVyCYm_KQWFeow6W_GSzJRZyVVNaQ/edit#slide=id.gcb5084357d_0_67
    class KSumIII {
        public List<List<Integer>> fourSum(int[] nums, int target) {
            Arrays.sort(nums);
            return kSum(nums, target, 0, 4);
        }

        public List<List<Integer>> kSum(int[] nums, int target, int start, int k) {
            List<List<Integer>> res = new ArrayList<>();
            if (start == nums.length) return res;

            // 優化
            // 如果sort後的剩下數字，最小的*k還比target大，或者最大數字*k還比target小，我們就可以提前停止
            int avg = target / k;
            if (nums[start] > avg || avg > nums[nums.length - 1]) return res;


            if (k == 2) return twoSum(nums, target, start);
            for (int i = start; i < nums.length; i++) {
                if (i > start && nums[i] == nums[i - 1]) continue;
                for (List<Integer> subset : kSum(nums, target - nums[i], i + 1, k - 1)) {
                    subset.add(nums[i]);
                    res.add(subset);
                }
            }
            return res;
        }


        public List<List<Integer>> twoSum(int[] nums, int target, int start) {
            List<List<Integer>> res = new ArrayList<>();
            int i = start, j = nums.length - 1;
            while (i < j) {
                int sum = nums[i] + nums[j];
                if (sum < target) i++;
                else if (start > target) j--;
                else {
                    res.add(Arrays.asList(nums[i++], nums[j--]));
                    while (i < j && nums[i] == nums[i - 1]) i++;
                    while (i < j && nums[j] == nums[j + 1]) j--;
                }
            }
            return res;
        }
    }
}
