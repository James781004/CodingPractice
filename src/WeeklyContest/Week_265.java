package WeeklyContest;

import java.util.*;

class Week_265 {
    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2057.Smallest%20Index%20With%20Equal%20Value/README.md
    public int smallestEqual(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (i % 10 == nums[i]) {
                return i;
            }
        }
        return -1;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2058.Find%20the%20Minimum%20and%20Maximum%20Number%20of%20Nodes%20Between%20Critical%20Points/README.md
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        ListNode prev = head;
        ListNode curr = head.next;
        int first = 0, last = 0;
        int i = 1;
        int[] ans = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};

        // 遍歷鏈表，維護第一個臨界點 first、最後一個臨界點 last，以及相鄰臨界點的最小距離
        while (curr.next != null) {
            // 找到臨界點 curr 時
            if (curr.val < Math.min(prev.val, curr.next.val)
                    || curr.val > Math.max(prev.val, curr.next.val)) {
                if (last == 0) { // 第一個臨界點的狀況
                    first = i;  // first初始化
                    last = i; // last更新為當前臨界點
                } else { // 第n個臨界點的狀況
                    ans[0] = Math.min(ans[0], i - last); // 更新最小值
                    ans[1] = i - first; // 最大值一定會隨著i前進而增加，所以更新
                    last = i; // last更新為當前臨界點
                }
            }
            i++;
            prev = curr;
            curr = curr.next;
        }
        return first == last ? new int[]{-1, -1} : ans;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    // https://leetcode.cn/problems/minimum-operations-to-convert-number/solution/gong-shui-san-xie-shuang-xiang-bfs-mo-ba-uckg/
    public int minimumOperations(int[] nums, int s, int t) {
        Deque<Integer> d = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();
        d.addLast(s);
        map.put(s, 0);
        while (!d.isEmpty()) {
            int cur = d.pollFirst();
            int step = map.get(cur);
            for (int i : nums) {
                int[] res = new int[]{cur + i, cur - i, cur ^ i};
                for (int next : res) {
                    if (next == t) return step + 1;
                    if (next < 0 || next > 1000) continue;
                    if (map.containsKey(next)) continue;
                    map.put(next, step + 1);
                    d.addLast(next);
                }
            }
        }
        return -1;
    }


    // 雙向 BFS
    int[] nums;

    public int minimumOperations2(int[] _nums, int s, int t) {
        nums = _nums;
        Deque<Long> d1 = new ArrayDeque<>(), d2 = new ArrayDeque<>();
        Map<Long, Integer> m1 = new HashMap<>(), m2 = new HashMap<>();
        d1.addLast((long) s);
        d2.addLast((long) t);
        m1.put((long) s, 0);
        m2.put((long) t, 0);
        while (!d1.isEmpty() && !d2.isEmpty()) {
            if (d1.size() < d2.size()) {
                int ans = update(d1, m1, d2, m2, true);
                if (ans != -1) return ans;
            } else {
                int ans = update(d2, m2, d1, m1, false);
                if (ans != -1) return ans;
            }
        }
        return -1;
    }

    int update(Deque<Long> d1, Map<Long, Integer> m1, Deque<Long> d2, Map<Long, Integer> m2, boolean flag) {
        int m = d1.size();
        while (m-- > 0) {
            long cur = d1.pollFirst();
            int step = m1.get(cur);
            for (int i : nums) {
                if (flag) {
                    // 正向搜索：進行出隊檢查，只有出隊元素符合條件，才能使用出隊元素往下拓展
                    if (0 <= cur && cur <= 1000) {
                        long[] result = new long[]{cur + i, cur - i, cur ^ i};
                        for (long next : result) {
                            if (m2.containsKey(next)) return step + 1 + m2.get(next);
                            if (!m1.containsKey(next)) {
                                d1.addLast(next);
                                m1.put(next, step + 1);
                            }
                        }
                    }
                } else {
                    // 反向搜索：進行入隊檢查，只有拓展元素符合條件，才能將拓展元素入隊
                    long[] result = new long[]{cur + i, cur - i, cur ^ i};
                    for (long next : result) {
                        if (0 <= next && next <= 1000) {
                            if (m2.containsKey(next)) return step + 1 + m2.get(next);
                            if (!m1.containsKey(next)) {
                                d1.addLast(next);
                                m1.put(next, step + 1);
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }


    // https://leetcode.cn/problems/check-if-an-original-string-exists-given-two-encoded-strings/solution/wei-rao-li-lun-ji-yi-hua-sou-suo-jiang-s-fdmo/
    int len1, len2;
    boolean[][][] visited;

    public boolean possiblyEquals(String s1, String s2) {
        visited = new boolean[41][41][2000];
        len1 = s1.length();
        len2 = s2.length();
        return dfs(s1, 0, s2, 0, 0);
    }

    public boolean dfs(String str1, int idx1, String str2, int idx2, int dis) {
        if (idx1 >= len1 && idx2 >= len2) {
            // 退出：兩個字符串展開的長度一樣
            if (dis == 0) {
                return true;
            }
            visited[idx1][idx2][dis + 1000] = true;
            return false;
        }
        if (visited[idx1][idx2][dis + 1000]) {
            return false;
        }
        visited[idx1][idx2][dis + 1000] = true;

        // 先處理s1
        // 我們優先展開數字 ｜ 把所有的展開可能性都試一下
        // 這邊定義 s1 操作時縮減 dis，遇到數字的情況就縮減數字的值 num1
        for (int temp1L = idx1; temp1L < len1 && Character.isDigit(str1.charAt(temp1L)); temp1L++) {
            int num1 = Integer.parseInt(str1.substring(idx1, temp1L + 1));
            if (dfs(str1, temp1L + 1, str2, idx2, dis - num1)) {
                return true;
            }
        }

        // 如果當前位置不是數字
        if (idx1 < len1 && !Character.isDigit(str1.charAt(idx1))) {
            // 我們只考慮s1展開比s2短的情況；如果是字符，將差距縮小
            // 如果s1已經比s2長了，我們先不展開字母；讓s2縮小一些差距先
            // dis > 0 表示 s2 目前比較長，所以 idx1 往後移動一位以加長 s1 (即dis - 1)
            if (dis > 0 && dfs(str1, idx1 + 1, str2, idx2, dis - 1)) return true;
        }

        // 對s2我們采用同樣的策略處理
        // 這邊定義 s2 操作時增加 dis，遇到數字的情況就縮減數字的值 num2
        for (int temp2L = idx2; temp2L < len2 && Character.isDigit(str2.charAt(temp2L)); temp2L++) {
            int num2 = Integer.parseInt(str2.substring(idx2, temp2L + 1));
            if (dfs(str1, idx1, str2, temp2L + 1, dis + num2)) {
                return true;
            }
        }

        // 如果當前位置不是數字
        if (idx2 < len2 && !Character.isDigit(str2.charAt(idx2))) {
            // 我們只考慮s1展開比s2短的情況；如果是字符，將差距縮小
            // 如果s1已經比s2長了，我們先不展開字母；讓s2縮小一些差距先
            // dis < 0 表示 s1 目前比較長，所以 idx2 往後移動一位以加長 s2 (即dis + 1)
            if (dis < 0 && dfs(str1, idx1, str2, idx2 + 1, dis + 1)) return true;
        }

        // 只有在兩者差距為0的時候且兩者都為字母，我們需要判斷一下是不是兩個字母相同；不同可以直接return false
        // 如果一個是數字，前面肯定已經處理了這種情況
        if (idx1 < len1 && idx2 < len2 && dis == 0 && !Character.isDigit(str1.charAt(idx1)) && !Character.isDigit(str2.charAt(idx2)) && str1.charAt(idx1) == str2.charAt(idx2)) {
            return dfs(str1, idx1 + 1, str2, idx2 + 1, 0);
        }
        return false;
    }


    // DP做法
    // https://leetcode.cn/problems/check-if-an-original-string-exists-given-two-encoded-strings/solution/dong-tai-gui-hua-ji-lu-ke-neng-de-chang-o87gp/
    public boolean possiblyEqualsDP(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        Set<Integer>[][] dp = new Set[m + 1][n + 1];  // dp[i][j]: s1的前i個字母和s2的前j個字母匹配且不發生沖突時，可能的長度差值
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = new HashSet<>();  // 用set保存枚舉兩字串長度差的所有可能結果
            }
        }

        char[] arr1 = s1.toCharArray(), arr2 = s2.toCharArray();

        dp[0][0].add(0);
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                for (int diff : dp[i][j]) {
                    // s1為數字
                    int num = 0;
                    for (int k = i; k < Math.min(i + 3, m) && isNum(arr1[k]); k++) {
                        num = num * 10 + arr1[k] - '0';
                        dp[k + 1][j].add(diff + num);
                    }

                    // s2為數字
                    num = 0;
                    for (int k = j; k < Math.min(j + 3, n) && isNum(arr2[k]); k++) {
                        num = num * 10 + arr2[k] - '0';
                        dp[i][k + 1].add(diff - num);
                    }

                    // 數字匹配s1字母
                    if (i < m && !isNum(arr1[i]) && diff < 0) {
                        dp[i + 1][j].add(diff + 1);
                    }

                    // 數字匹配s2字母
                    if (j < n && !isNum(arr2[j]) && diff > 0) {
                        dp[i][j + 1].add(diff - 1);
                    }

                    // 兩個字母匹配
                    if (i < m && j < n && !isNum(arr1[i]) && arr1[i] == arr2[j] && diff == 0) {
                        dp[i + 1][j + 1].add(diff);
                    }
                }
            }
        }

        return dp[m][n].contains(0);
    }

    public boolean isNum(char c) {
        return c >= '0' && c <= '9';
    }
}

