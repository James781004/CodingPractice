package WeeklyContest;

import java.util.*;

class Week_283 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2194.Cells%20in%20a%20Range%20on%20an%20Excel%20Sheet/README.md
    public List<String> cellsInRange(String s) {
        List<String> ans = new ArrayList<>();
        for (char i = s.charAt(0); i <= s.charAt(3); i++) {
            for (char j = s.charAt(1); j <= s.charAt(4); j++) {
                ans.add(i + "" + j);
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/append-k-integers-with-minimal-sum/solution/pai-xu-bian-li-by-endlesscheng-81er/
    public long minimalKSum(int[] nums, int k) {
        Arrays.sort(nums);
        long ans = 0L;
        int[] arr = new int[nums.length + 2];
        for (int i = 0; i < nums.length; i++) {
            arr[i + 1] = nums[i];
        }
        arr[arr.length - 1] = Integer.MAX_VALUE;
        for (int i = 1; i < arr.length; i++) {
            // interval表示arr(i-1)至arr(i)中間元素個數
            long interval = arr[i] - arr[i - 1] - 1;
            if (interval <= 0) {
                continue;
            }
            if (interval >= k) {
                // arr[i - 1]+1 +...+(k個數)
                // sum = a[i - 1]+1 +....a[i - 1]+1 +(k-1)
                ans += (long) (arr[i - 1] + 1 + arr[i - 1] + k) * k / 2;
                return ans;
            }
            // arr[i-1]+1+...arr[i]-1
            // 乘積會導致越界，故interval采用long類型
            ans += (arr[i - 1] + arr[i]) * interval / 2;
            k -= interval;

        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2196.Create%20Binary%20Tree%20From%20Descriptions/README.md
    public TreeNode createBinaryTree(int[][] descriptions) {
        Map<Integer, TreeNode> m = new HashMap<>();
        Set<Integer> vis = new HashSet<>();

        for (int[] d : descriptions) {
            int p = d[0], c = d[1], isLeft = d[2];

            // map存放當前父子節點
            if (!m.containsKey(p)) {
                m.put(p, new TreeNode(p));
            }
            if (!m.containsKey(c)) {
                m.put(c, new TreeNode(c));
            }

            // 建立樹左右父子關係
            if (isLeft == 1) {
                m.get(p).left = m.get(c);
            } else {
                m.get(p).right = m.get(c);
            }

            // 遍歷過的子節點加入vis
            vis.add(c);
        }

        for (Map.Entry<Integer, TreeNode> entry : m.entrySet()) {
            if (!vis.contains(entry.getKey())) { // 遍歷過的子節點前面都已經加入vis
                return entry.getValue();  // 所以vis中不存在的點，就是根節點
            }
        }
        return null;
    }


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


    // https://leetcode.cn/problems/replace-non-coprime-numbers-in-array/solution/li-yong-zhan-mo-ni-gocpythonjava-by-endl-bnbv/
    public List<Integer> replaceNonCoprimes(int[] nums) {
        List<Integer> s = new ArrayList<>();
        for (int num : nums) {
            s.add(num);
            while (s.size() > 1) {
                // 每次從s尾部取出兩個元素，若其互質則退出循環
                int x = s.get(s.size() - 1);
                int y = s.get(s.size() - 2);
                int g = gcd(x, y);
                if (g == 1) break;

                // 否則將這兩個元素移除，並把他們的最小公倍數放進s尾部，繼續循環
                s.remove(s.size() - 1);
                s.set(s.size() - 1, x / g * y);
            }
        }
        return s;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}

