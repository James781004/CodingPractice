package WeeklyContest;

public class Week_367 {
    // https://leetcode.cn/problems/find-indices-with-index-and-value-difference-i/solutions/2483234/bao-li-mei-ju-jie-ti-by-lin-hos-moo6/
    // 暴力枚舉
    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (Math.abs(i - j) >= indexDifference && Math.abs(nums[i] - nums[j]) >= valueDifference)
                    return new int[]{i, j};
            }
        }
        return new int[]{-1, -1};
    }


    // https://leetcode.cn/problems/shortest-and-lexicographically-smallest-beautiful-string/solutions/2483144/mei-ju-pythonjavacgo-by-endlesscheng-5th8/
    public String shortestBeautifulSubstring(String s, int k) {
        // 1 的個數不足 k
        if (s.replaceAll("0", "").length() < k) {
            return "";
        }

        // 否則一定有解
        // 從 k 開始枚舉答案的長度 size，然後在 s 中枚舉所有長為 size 的子串，同時維護字典序最小的子串
        for (int size = k; ; size++) {
            String ans = "";
            for (int i = size; i <= s.length(); i++) {
                String t = s.substring(i - size, i);
                if ((ans.isEmpty() || t.compareTo(ans) < 0) && t.replaceAll("0", "").length() == k) {
                    ans = t;
                }
            }

            // 如果存在一個子串，其中 1 的個數等於 k，則返回字典序最小的子串
            if (!ans.isEmpty()) {
                return ans;
            }
        }
    }


    // 雙指針滑動窗口
    public String shortestBeautifulSubstring2(String s, int k) {
        String ans = "";
        char[] chars = s.toCharArray();
        int n = s.length();
        int count = 0;
        int shortest = Integer.MAX_VALUE, left = 0, right = 0;
        while (right < n) {
            while (count < k && right < n) {
                if (chars[right] == '1') count++;
                right++;
            }
            if (right == n && count < k) break;
            while (left < right && chars[left] == '0') {
                left++; // 如果左邊有多餘的 0，就縮減左窗口
            }

            // 找到了，記錄長度
            if (left < right && right - left <= shortest) {
                if (right - left == shortest) { // 如果當前長度相同，比較字典序
                    ans = ans.compareTo(s.substring(left, right)) > 0 ? s.substring(left, right) : ans;
                } else { // 如果當前長度更短，直接更新
                    shortest = right - left;
                    ans = s.substring(left, right);
                }
            }

            // 移動左指針，count 減1
            left++;
            count--;
        }
        return ans;
    }


    // https://leetcode.cn/problems/find-indices-with-index-and-value-difference-ii/solutions/2483143/shuang-zhi-zhen-wei-hu-zui-da-zui-xiao-p-h4bx/
    public int[] findIndicesII(int[] nums, int indexDifference, int valueDifference) {
        int maxIdx = 0, minIdx = 0;

        // 在枚舉 j 的同時，維護 nums[i] 的最大值 mx 和最小值 mn。
        for (int j = indexDifference; j < nums.length; j++) {
            int i = j - indexDifference; // 題目規定：abs(i - j) >= indexDifference
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i; // 維護最大值
            } else if (nums[i] < nums[minIdx]) {
                minIdx = i; // 維護最小值
            }

            // 題目規定：abs(nums[i] - nums[j]) >= valueDifference
            if (nums[maxIdx] - nums[j] >= valueDifference) {
                return new int[]{maxIdx, j};
            }
            if (nums[j] - nums[minIdx] >= valueDifference) {
                return new int[]{minIdx, j};
            }
        }
        return new int[]{-1, -1};
    }


    // https://leetcode.cn/problems/construct-product-matrix/solutions/2483137/zhou-sai-chang-kao-qian-hou-zhui-fen-jie-21hr/
    // 核心思想：把矩陣想象成一維的，我們需要算出每個數左邊所有數的乘積，以及右邊所有數的乘積，這都可以用遞推得到。
    public int[][] constructProductMatrix(int[][] grid) {
        final int MOD = 12345;
        int n = grid.length, m = grid[0].length;
        int[][] p = new int[n][m];

        long suf = 1; // 後綴乘積
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                p[i][j] = (int) suf; // p[i][j] 先初始化成後綴乘積
                suf = suf * grid[i][j] % MOD;
            }
        }

        long pre = 1; // 前綴乘積
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                p[i][j] = (int) (p[i][j] * pre % MOD); // 然後再乘上前綴乘積
                pre = pre * grid[i][j] % MOD;
            }
        }

        return p;
    }
}
