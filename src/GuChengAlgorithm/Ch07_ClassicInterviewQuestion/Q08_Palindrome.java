package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

import java.util.ArrayList;
import java.util.List;

public class Q08_Palindrome {
    // https://docs.google.com/presentation/d/17MBk7UcDYcmUCtJsYj_m9SgnDPm-x3ByPxZ-ENqg6bk/edit#slide=id.g105d70bbe33_0_34
    class ValidPalindromeII {
        // delete at most one character
        public boolean validPalindrome(String s) {
            int i = 0, j = s.length() - 1;
            while (i < j && s.charAt(i) == s.charAt(j)) { // 跳過相同部份
                i++;
                j--;
            }
            if (i >= j) return true;  // 奇數的話(i == j)

            // 因為最多只能刪除一個，排除掉左右不是palindrome的位置後，內部剩下的部分把邊的兩個就是我們必須去處理的兩個點
            if (isPalin(s, i + i, j) || isPalin(s, i, j - 1)) return true;
            return false;
        }

        private boolean isPalin(String s, int i, int j) {
            while (i < j) {
                if (s.charAt(i) == s.charAt(j)) {
                    i++;
                    j--;
                } else return false;
            }
            return true;
        }
    }


    // https://docs.google.com/presentation/d/17MBk7UcDYcmUCtJsYj_m9SgnDPm-x3ByPxZ-ENqg6bk/edit#slide=id.g105d70bbe33_0_45
    class PalindromeNumber {
        public boolean isPalindrome(int x) {
            if (x < 0 || (x != 0 && x % 10 == 0)) return false;  // 非0整數結果是0必定不是迴文
            int reverse = 0;
            while (x > reverse) {
                reverse = reverse * 10 + x % 10;
                x /= 10;
            }
            return (x == reverse || x == reverse / 10);  // even digits and odd digits
        }


        public boolean isPalindrome2(int x) {
            int reverse = 0, origin = x;
            while (x > 0) {
                reverse = reverse * 10 + x % 10;
                x /= 10;
            }
            return reverse == origin;
        }
    }


    // https://docs.google.com/presentation/d/17MBk7UcDYcmUCtJsYj_m9SgnDPm-x3ByPxZ-ENqg6bk/edit#slide=id.g105d70bbe33_0_54
    class PalindromeLinkedList {
        public boolean isPalindrome(ListNode head) {
            ListNode firstHalfEnd = endOfFirstHalf(head);
            ListNode secondHalfStart = reverse(firstHalfEnd.next);
            ListNode p1 = head;
            ListNode p2 = secondHalfStart;
            boolean result = true;
            while (result && p2 != null) {
                if (p1.val != p2.val) result = false;
                p1 = p1.next;
                p2 = p2.next;
            }
            firstHalfEnd.next = reverse(secondHalfStart);
            return result;
        }

        private ListNode endOfFirstHalf(ListNode head) {
            ListNode fast = head;
            ListNode slow = head;
            while (fast.next != null && fast.next.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
            return slow;
        }

        private ListNode reverse(ListNode head) {
            ListNode pre = null;
            while (head != null) {
                ListNode next = head.next;
                head.next = pre;
                pre = head;
                head = next;
            }
            return pre;
        }
    }


    // https://docs.google.com/presentation/d/17MBk7UcDYcmUCtJsYj_m9SgnDPm-x3ByPxZ-ENqg6bk/edit#slide=id.g105d70bbe33_0_77
    class LPS {
        public String lps(String s) {
            int N = s.length();
            boolean[][] dp = new boolean[N][N];
            int start = 0, end = 0;
            for (int i = N - 1; i >= 0; i--) {
                for (int j = 1; j < N; j++) {
                    if ((s.charAt(i) == s.charAt(j)) && (j - i <= 2 || dp[i + i][j - 1])) {  // Palindrome子串形成條件
                        if (end - start < j - i) {  // 儲存最長子串起點終點
                            start = i;
                            end = j;
                        }
                        dp[i][j] = true;
                    }
                }
            }
            return s.substring(start, end + 1);
        }

        public String lps2(String s) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < s.length(); i++) {
                helper(s, i, 0, sb);  // 計算奇數字串
                helper(s, i, 1, sb);  // 計算偶數字串
            }
            return sb.toString();
        }

        private void helper(String s, int start, int offset, StringBuilder sb) {
            int left = start, right = start + offset;

            // 中心擴散法
            while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }

            // left, right中止loop時多走一步，這邊校正
            String cur = s.substring(left + 1, right);

            // 儲存最長子串
            if (cur.length() > sb.length()) {
                sb.setLength(0);
                sb.append(cur);
            }
        }
    }


    // https://docs.google.com/presentation/d/17MBk7UcDYcmUCtJsYj_m9SgnDPm-x3ByPxZ-ENqg6bk/edit#slide=id.g105d70bbe33_0_101
    class PalindromeSubstring {
        public int count(String s) {
            int N = s.length(), res = 0;
            boolean[][] dp = new boolean[N][N];
            for (int i = N - 1; i >= 0; i--) {
                for (int j = i; j < N; j++) {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && ((j - i <= 2) || dp[i + 1][j - 1]);
                    if (dp[i][j]) res++;
                }
            }
            return res;
        }


        int count = 1;

        public int count2(String s) {
            if (s.length() == 0) return 0;
            for (int i = 0; i < s.length() - 1; i++) {
                checkPalindrome(s, i, i);  // 奇數長度
                checkPalindrome(s, i, i + 1);  // 偶數長度
            }
            return count;
        }

        private void checkPalindrome(String s, int i, int j) {
            while (i >= 0 && j < s.length() && s.charAt(i) == s.charAt(j)) {
                count++;
                i++;
                j--;
            }
        }
    }


    // https://docs.google.com/presentation/d/17MBk7UcDYcmUCtJsYj_m9SgnDPm-x3ByPxZ-ENqg6bk/edit#slide=id.g105d70bbe33_0_115
    class LongestPalindromicSubsequence {
        public int longestPalindromicSubsequence(String s) {
            String ss = new StringBuilder(s).reverse().toString();
            return lcs(s, ss);
        }

        private int lcs(String s, String r) {
            int n = s.length();
            int[][] dp = new int[n + 1][n + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (s.charAt(i) == r.charAt(j)) {
                        dp[i + 1][j + 1] = dp[i][j] + 1;
                    } else {
                        dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                    }
                }
            }
            return dp[n][n];
        }


        public int longestPalindromicSubsequence2(String s) {
            int n = s.length();
            int[][] dp = new int[n][n];
            for (int i = n - 1; i >= 0; i--) {
                dp[i][i] = 1;  // 初始化
                for (int j = i; j < n; j++) {
                    if (s.charAt(i) == s.charAt(j)) dp[i][j] = dp[i + 1][j - 1] + 2;
                    else dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
            return dp[0][n - 1];
        }


        Integer[][] memo;

        public int longestPalindromicSubsequence3(String s) {
            int N = s.length();
            this.memo = new Integer[N][N];
            return dfs(s, 0, N - 1);
        }

        private int dfs(String s, int i, int j) {
            if (i > j) return 0;
            if (i == j) return 1;
            if (memo[i][j] != null) return memo[i][j];
            if (s.charAt(i) == s.charAt(j)) memo[i][j] = 2 + dfs(s, i + 1, j - 1);
            else memo[i][j] = Math.max(dfs(s, i + 1, j), dfs(s, i, j - 1));
            return memo[i][j];
        }
    }


    // https://docs.google.com/presentation/d/17MBk7UcDYcmUCtJsYj_m9SgnDPm-x3ByPxZ-ENqg6bk/edit#slide=id.g105d70bbe33_0_163
    class PalindromePartitioning {
        public List<List<String>> partition(String s) {
            List<List<String>> res = new ArrayList<>();
            dfs(s, 0, new ArrayList<String>(), res);
            return res;
        }

        private void dfs(String s, int start, ArrayList<String> level, List<List<String>> res) {
            if (start >= s.length()) {
                res.add(new ArrayList<>(level));
                return;
            }

            for (int i = start; i < s.length(); i++) {
                String sub = s.substring(start, i + 1);
                if (isPalindrome(sub)) {
                    level.add(sub);
                    dfs(s, i + 1, level, res);
                    level.remove(level.size() - 1);
                }
            }
        }

        private boolean isPalindrome(String s) {
            int i = 0, j = s.length() - 1;
            while (i < j) {
                if (s.charAt(i++) != s.charAt(j--)) return false;
            }
            return true;
        }


        public List<List<String>> partition2(String s) {
            List<List<String>> res = new ArrayList<>();

            // 預處理，將形成Palindrome子串的(i, j)位置設定為true
            boolean[][] dp = new boolean[s.length()][s.length()];
            for (int i = 0; i < s.length(); i++) {
                for (int j = 0; j <= i; j++) {
                    if (s.charAt(i) == s.charAt(j) && (i - j <= 2 || dp[j + 1][i - 1])) {
                        dp[i][j] = true;
                    }
                }
            }
            backtrack(res, new ArrayList<String>(), dp, s, 0);
            return res;
        }

        private void backtrack(List<List<String>> res, ArrayList<String> path, boolean[][] dp, String s, int pos) {
            if (pos == s.length()) {
                res.add(new ArrayList<>(path));
                return;
            }

            for (int i = pos; i < s.length(); i++) {
                if (dp[pos][i]) {  // 參考預處理的結果進行剪枝
                    path.add(s.substring(pos, i + 1));
                    backtrack(res, path, dp, s, i + 1);
                    path.remove(path.size() - 1);
                }
            }
        }
    }


    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }
}
