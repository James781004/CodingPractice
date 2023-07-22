package WeeklyContest;

import java.util.*;

class Week_258 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2000.Reverse%20Prefix%20of%20Word/README.md
    public String reversePrefix(String word, char ch) {
        int j = word.indexOf(ch);
        if (j == -1) {
            return word;
        }
        char[] cs = word.toCharArray();
        for (int i = 0; i < j; ++i, --j) {
            char t = cs[i];
            cs[i] = cs[j];
            cs[j] = t;
        }
        return String.valueOf(cs);
    }


    // https://github.com/doocs/leetcode/tree/main/solution/2000-2099/2001.Number%20of%20Pairs%20of%20Interchangeable%20Rectangles
    // 為了能夠唯一表示矩形，需要將矩形的寬高比化簡為最簡分數。
    // 因此，可以求出每個矩形的寬高比的最大公約數，然後將寬高比化簡為最簡分數。
    // 接下來，我們使用哈希表統計每個最簡分數的矩形數量，然後計算每個最簡分數的矩形數量的組合數，即可得到答案。
    public long interchangeableRectangles(int[][] rectangles) {
        long ans = 0;
        int n = rectangles.length + 1;
        Map<Long, Integer> cnt = new HashMap<>();
        for (int[] e : rectangles) {
            int w = e[0], h = e[1];
            int g = gcd(w, h);
            w /= g;
            h /= g;
            long x = (long) w * n + h;
            ans += cnt.getOrDefault(x, 0);
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // https://leetcode.cn/problems/maximum-product-of-the-length-of-two-palindromic-subsequences/solution/xyfs-zhuang-ya-zhi-hou-wo-ke-jiu-yao-bao-8sq7/
    public int maxProduct(String s) {
        int n = s.length(), m = 1 << n;
        List<int[]> list = new ArrayList<>();
        char[] str = s.toCharArray();

        // 記錄所有合法狀態的信息
        for (int i = 1; i < m; i++) {
            if (check(str, i)) {
                list.add(new int[]{i, Integer.bitCount(i)});
            }
        }

        int[][] arr = list.toArray(new int[0][0]);
        int res = 0, len = arr.length;
        // for-each 優化，j 不需要從 0 開始
        for (int i = 0; i < len; i++) {
            int x = arr[i][0], len_x = arr[i][1];
            for (int j = i + 1; j < len; j++) {
                int y = arr[j][0], len_y = arr[j][1];
                // 狀態之間沒有字符相交，滿足題意
                if ((x & y) == 0) {
                    res = Math.max(res, len_x * len_y);
                }
            }
        }

        return res;
    }

    private boolean check(char[] s, int state) {
        int left = 0, right = s.length - 1;

        // 以 state 為中心，檢查 state 對應的子序列是不是回文串
        while (left < right) {
            // 將 left 和 right 對應上 「狀態所對應的字符」 位置
            while (left < right && (state >> left & 1) == 0) {
                left++;
            }
            while (left < right && (state >> right & 1) == 0) {
                right--;
            }
            if (s[left] != s[right]) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }


    // https://leetcode.cn/problems/smallest-missing-genetic-value-in-each-subtree/solution/go-qi-fa-shi-he-bing-by-endlesscheng-kmff/
    // 整體思路，先找到1所對應的坐標idx，那麼從 idx到0，所有的輸出都會大於1，而其他的點必然都是1，因為每個節點的值具有唯一性，
    // 因此只需要著重找到從idx到0這條路的輸出即可
    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        int n = parents.length;
        int[] ans = new int[n];
        Arrays.fill(ans, 1); // ans 先全部填入1

        // 首先找到值為1的下標
        int idx = 0;
        for (; idx < n; idx++) {
            if (nums[idx] == 1) {
                break;
            }
        }

        // 如果找不到1， 那麼很簡單，所有下標對應的輸出都是1
        if (idx == n) {
            return ans;
        }

        // 構建通過父節點獲取子節點的 map
        // 記錄所有子節點
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (map.containsKey(parents[i])) {
                List<Integer> children = map.get(parents[i]);
                children.add(i);
                map.put(parents[i], children);
            } else {
                List<Integer> children = new ArrayList<>();
                children.add(i);
                map.put(parents[i], children);
            }
        }

        // 記錄訪問過的基因值
        Set<Integer> set = new HashSet<>();

        // 從 idx（1所對應的位置）開始，因為此時需要遍歷其所有子樹，因此from 設為-1
        dfs(idx, -1, map, set, nums);

        // 找到此時的最小缺省值
        int min = 1;
        while (set.contains(min)) {
            min++;
        }
        ans[idx] = min;

        // 開始向0遍歷，直到根節點0結束
        while (parents[idx] != -1) {
            dfs(parents[idx], idx, map, set, nums);
            idx = parents[idx];
            while (set.contains(min)) {
                min++;
            }
            ans[idx] = min;
        }
        return ans;
    }

    // 遍歷所有子節點，並把path加入set
    private void dfs(int root, int from, Map<Integer, List<Integer>> map, Set<Integer> set, int[] nums) {
        set.add(nums[root]);
        List<Integer> childrenList = map.get(root);
        if (childrenList == null || childrenList.size() == 0) {
            return;
        }
        for (int child : childrenList) {
            if (child != from) {
                dfs(child, from, map, set, nums);
            }
        }
    }
}

