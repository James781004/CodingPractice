package WeeklyContest;

import java.util.*;

public class Week_319 {
    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2469.Convert%20the%20Temperature/README.md
    public double[] convertTemperature(double celsius) {
        return new double[]{celsius + 273.15, celsius * 1.8 + 32};
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2400-2499/2470.Number%20of%20Subarrays%20With%20LCM%20Equal%20to%20K/README.md
    // 列舉每個數作為子陣列的第一個數，然後列舉每個數作為子陣列的最後一個數，
    // 計算這個子陣列的最小公倍數，如果最小公倍數等於 k，則答案加一。
    public int subarrayLCM(int[] nums, int k) {
        /*
            枚舉+遞推+剪枝:
            nums[i,j]的最小公倍數=nums[i,j-1]的最小公倍數*nums[j]/gcd(nums[i,j-1]的最小公倍,nums[j])
            即把nums[i,j-1]的最小公倍數看做一個整體
            f[i,j]=f[i,j-1]*nums[j]/gcd(f[i,j-1],nums[j])
         */
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int min = 1;    // min為nums[i,j]的最小公倍數
            for (int j = i; j < n; j++) {
                min = min * nums[j] / gcd(min, nums[j]);
                if (min > k) break; // 剪枝：前面超過了k，最小公倍數不可能為k
                if (min == k) res++;
            }
        }
        return res;
    }

    private int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://leetcode.cn/problems/minimum-number-of-operations-to-sort-a-binary-tree-by-level/solution/by-endlesscheng-97i9/
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

    public int minimumOperations(TreeNode root) {
        Queue<TreeNode> queue = new ArrayDeque<>();
        int ans = 0;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int[] arr = new int[size], temp = new int[size];
            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                arr[i] = temp[i] = cur.val;
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            Map<Integer, Integer> map = new HashMap<>();
            Arrays.sort(temp);  // 離散化，把元素映射到012345...，並保持小到大關係
            for (int i = 0; i < arr.length; i++) map.put(temp[i], i);
            for (int i = 0; i < arr.length; i++) {
                while (arr[i] != temp[i]) { // 置換環，從第一個數開始，把這個數字當成下標去訪問陣列，不斷循環直到回到這個數本身
                    int j = map.get(arr[i]); // 計算每個環內需要多少次交換
                    int t = arr[i];
                    arr[i] = arr[j];
                    arr[j] = t;
                    ans++;
                }
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/solution/zhong-xin-kuo-zhan-dppythonjavacgo-by-en-1yt1/
    public int maxPalindromes(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] f = new int[n + 1];  // s[0...i-1]
        for (int i = 0; i < 2 * n - 1; i++) {
            int l = i / 2, r = l + i % 2; // 中心擴展法，列舉所有奇數和偶數的中心點位置
            f[l + 1] = Math.max(f[l + 1], f[l]);  // 不選擇s[l]
            for (; l >= 0 && r < n && s[l] == s[r]; --l, ++r) {  // s[l...r]是回文串
                if (r - l + 1 >= k) {  // 貪心處理，f[l]是非遞減的，更小的f[l]也不會影響答案
                    f[r + 1] = Math.max(f[r + 1], f[l] + 1); // s[r]包含在回文串中，並且回文長度大於等於k
                    break;
                }
            }
        }
        return f[n];
    }


}
