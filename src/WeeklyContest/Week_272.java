package WeeklyContest;

import java.util.ArrayList;
import java.util.List;

class Week_272 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2108.Find%20First%20Palindromic%20String%20in%20the%20Array/README.md
    public String firstPalindrome(String[] words) {
        for (String w : words) {
            boolean ok = true;
            for (int i = 0, j = w.length() - 1; i < j && ok; ++i, --j) {
                if (w.charAt(i) != w.charAt(j)) {
                    ok = false;
                }
            }
            if (ok) {
                return w;
            }
        }
        return "";
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2109.Adding%20Spaces%20to%20a%20String/README.md
    public String addSpaces(String s, int[] spaces) {
        StringBuilder ans = new StringBuilder();
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (j < spaces.length && i == spaces[j]) {
                ans.append(' ');
                j++;
            }
            ans.append(s.charAt(i));
        }
        return ans.toString();
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2110.Number%20of%20Smooth%20Descent%20Periods%20of%20a%20Stock/README.md
    public long getDescentPeriods(int[] prices) {
        long ans = 0;
        int n = prices.length;

        // 使用雙指針 i 和 j，分別指向當前平滑下降階段的第一天和最後一天的下一天
        for (int i = 0, j = 0; i < n; i = j) {
            j = i + 1;
            while (j < n && prices[j - 1] - prices[j] == 1) {
                j++;
            }
            int cnt = j - i;  // 目前平滑下降的窗口範圍
            ans += (1L + cnt) * cnt / 2; // 1 ~ cnt 數列等差公式
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2111.Minimum%20Operations%20to%20Make%20the%20Array%20K-Increasing/README.md
    public int kIncreasing(int[] arr, int k) {
        int n = arr.length;
        int ans = 0;
        for (int i = 0; i < k; i++) {  // 分組收集目標元素
            List<Integer> t = new ArrayList<>();
            for (int j = i; j < n; j += k) {
                t.add(arr[j]);
            }
            ans += lis(t);  // 加上最長不降子序列(LIS)
        }
        return ans;
    }


    private int lis(List<Integer> arr) {
        List<Integer> t = new ArrayList<>();
        for (int x : arr) {
            // 二分判斷x的插入位置
            int idx = searchRight(t, x);
            if (idx == t.size()) {
                t.add(x);
            } else {
                t.set(idx, x);
            }
        }

        // t表示arr中存在的LIS
        // arr總長度減去LIS長度，就是需要修改的部份
        return arr.size() - t.size();
    }

    // 二分判斷LIS長度
    // left之前的數全部不大於x
    // 因此x應該放在arr中的left位置
    private int searchRight(List<Integer> arr, int x) {
        int left = 0, right = arr.size();
        while (left < right) {
            int mid = (left + right) >> 1;
            if (arr.get(mid) > x) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}

