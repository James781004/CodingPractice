package WeeklyContest;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Week_511 {

    // https://leetcode.cn/problems/even-number-of-knight-moves/solutions/3998549/pan-duan-qi-ou-xing-o1-yi-xing-pythonjav-if5q/
    public boolean canReach(int[] start, int[] target) {
        return (start[0] + start[1]) % 2 == (target[0] + target[1]) % 2;
    }


    // https://leetcode.cn/problems/count-dominant-nodes-in-a-binary-tree/solutions/3998552/zi-di-xiang-shang-dfspythonjavacgo-by-en-bpha/
    private int ans = 0;

    public int countDominantNodes(TreeNode root) {
        dfs(root);
        return ans;
    }

    // dfs(node) 返回 node 子樹中的最大節點值
    private int dfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int mx = Math.max(dfs(node.left), dfs(node.right));
        if (node.val >= mx) {
            // node.val 是 node 子樹中的最大節點值
            ans++;
            mx = node.val;
        }
        return mx;
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int x) {
            val = x;
        }
    }


    // https://leetcode.cn/problems/transform-binary-string-using-subsequence-sort/solutions/3998547/tan-xin-shuang-zhi-zhen-pythonjavacgo-by-01pn/
    public boolean[] transformStr(String S, String[] strs) {
        char[] s = S.toCharArray();
        int total0 = 0;
        for (char ch : s) {
            total0 += '1' - ch; // 統計 '0' 的個數
        }

        boolean[] ans = new boolean[strs.length];
        next:
        for (int idx = 0; idx < strs.length; idx++) {
            char[] t = strs[idx].toCharArray();
            int cnt0 = 0;
            int cntQ = 0;
            for (char ch : t) {
                if (ch == '0') {
                    cnt0++;
                } else if (ch == '?') {
                    cntQ++;
                }
            }

            // str 中的 '0' 的個數在閉區間 [cnt0, cnt0+cntQ] 中，total0 必須在這個范圍內
            if (total0 < cnt0 || total0 > cnt0 + cntQ) {
                continue;
            }

            // 判斷能否把 s 變成 t
            int i = 0;
            int j = 0;
            for (int k = 0; k < total0; k++) {
                // 找下一個 s[i] = '0'
                while (s[i] != '0') {
                    i++;
                }

                // 找下一個 t[j] = '0'
                while (t[j] == '1' || t[j] == '?' && cnt0 == total0) {
                    j++;
                }

                // s 中的 '0' 無法右移，所以無法把 s 變成 t
                if (i < j) {
                    continue next;
                }

                if (t[j] == '?') {
                    cnt0++;
                }

                i++;
                j++;
            }

            ans[idx] = true;
        }

        return ans;
    }


    // https://leetcode.cn/problems/minimum-number-of-string-groups-through-transformations/solutions/3998556/zui-xiao-biao-shi-fa-ha-xi-ji-he-qu-zhon-9tyu/
    public int minimumGroups(String[] words) {
        Set<String> st = new HashSet<>();

        for (String word : words) {
            char[] w = word.toCharArray();
            int m = w.length;
            char[] minS = new char[m];

            // 偶數下標
            char[] even = new char[(m + 1) / 2];
            for (int i = 0; i < even.length; i++) {
                even[i] = w[i * 2];
            }
            char[] s = smallestRepresentation(even);
            for (int j = 0; j < s.length; j++) {
                minS[j * 2] = s[j];
            }

            // 奇數下標
            char[] odd = new char[m / 2];
            for (int i = 0; i < odd.length; i++) {
                odd[i] = w[i * 2 + 1];
            }
            s = smallestRepresentation(odd);
            for (int j = 0; j < s.length; j++) {
                minS[j * 2 + 1] = s[j];
            }

            st.add(new String(minS));
        }

        return st.size();
    }

    // 返回 str 的字典序最小的循環同構串
    // 時間復雜度 O(|str|)，證明見代碼末尾的注釋
    private char[] smallestRepresentation(char[] str) {
        int n = str.length;
        char[] s = new char[n * 2]; // s = str + str
        System.arraycopy(str, 0, s, 0, n);
        System.arraycopy(str, 0, s, n, n);

        // 注：如果要返回一個和原串不同的字符串，初始化 i=1, j=2
        int i = 0;
        for (int j = 1; j < n; ) {
            // 暴力比較：是 i 開頭的字典序小，還是 j 開頭的字典序小？
            // 相同就繼續往後比，至多循環 n 次（如果循環 n 次，說明所有字母都相同，不用再比了）
            int k = 0;
            while (k < n && s[i + k] == s[j + k]) {
                k++;
            }
            if (k >= n) {
                break;
            }

            if (s[i + k] < s[j + k]) { // 注：如果求字典序最大，改成 >
                // 比如從 i 開始是 "aaab"，從 j 開始是 "aaac"
                // 從 i 開始比從 j 開始更小（排除 j）
                // 此外：
                // 從 i+1 開始比從 j+1 開始更小，所以從 j+1 開始不可能是答案，排除
                // 從 i+2 開始比從 j+2 開始更小，所以從 j+2 開始不可能是答案，排除
                // ……
                // 從 i+k 開始比從 j+k 開始更小，所以從 j+k 開始不可能是答案，排除
                // 所以下一個「可能是答案」的開始位置是 j+k+1
                j += k + 1;
            } else {
                // 從 j 開始比從 i 開始更小，更新 i=j（也意味著我們排除了 i）
                // 此外：
                // 從 j+1 開始比從 i+1 開始更小，所以從 i+1 開始不可能是答案，排除
                // 從 j+2 開始比從 i+2 開始更小，所以從 i+2 開始不可能是答案，排除
                // ……
                // 從 j+k 開始比從 i+k 開始更小，所以從 i+k 開始不可能是答案，排除
                // 所以把 j 跳到 i+k+1，不過這可能比 j+1 小，所以與 j+1 取 max
                // 綜上所述，下一個「可能是答案」的開始位置是 max(j+1, i+k+1)
                int tmp = j;
                j = Math.max(j, i + k) + 1;
                i = tmp;
            }

            // 每次要麼排除 k+1 個與 i 相關的位置（這樣的位置至多 n 個），要麼排除 k+1 個與 j 相關的位置（這樣的位置至多 n 個）
            // 所以上面關於 k 的循環，∑k <= 2n，所以二重循環的總循環次數是 O(n) 的
        }
        return Arrays.copyOfRange(s, i, i + n);
    }


}










