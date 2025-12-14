package WeeklyContest;

import java.util.Arrays;

public class Week_480 {

    // https://leetcode.cn/problems/absolute-difference-between-maximum-and-minimum-k-elements/solutions/3856591/pai-xu-kuai-su-xuan-ze-pythonjavacgo-by-8vmzz/
    public int absDifference(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        return sum(nums, n - k, n) - sum(nums, 0, k);
    }

    private int sum(int[] a, int l, int r) {
        int s = 0;
        for (int i = l; i < r; i++) {
            s += a[i];
        }
        return s;
    }


    // https://leetcode.cn/problems/reverse-words-with-same-vowel-count/solutions/3856589/ku-han-shu-xie-fa-pythonjavacgo-by-endle-s27c/
    public String reverseWords(String s) {
        String[] a = s.split(" ");
        int cnt0 = countVowel(a[0]);
        for (int i = 1; i < a.length; i++) {
            if (countVowel(a[i]) == cnt0) {
                a[i] = new StringBuilder(a[i]).reverse().toString();
            }
        }
        return String.join(" ", a);
    }

    private int countVowel(String s) {
        int cnt = 0;
        for (char c : s.toCharArray()) {
            if ("aeiou".indexOf(c) >= 0) {
                cnt++;
            }
        }
        return cnt;
    }


    // https://leetcode.cn/problems/minimum-moves-to-balance-circular-array/solutions/3856585/tan-xin-cong-jin-dao-yuan-o1-kong-jian-x-3tpg/
    public long minMoves(int[] balance) {
        long total = 0;
        int negIdx = -1;
        for (int i = 0; i < balance.length; i++) {
            int x = balance[i];
            total += x;
            if (x < 0) {
                negIdx = i;
            }
        }

        if (total < 0) { // 總和必須非負
            return -1;
        }
        if (negIdx < 0) { // 沒有負數，無需操作
            return 0;
        }

        int n = balance.length;
        int need = -balance[negIdx];
        long ans = 0;
        for (int dis = 1; ; dis++) { // 把與 negIdx 相距 dis 的數移到 negIdx
            int s = balance[(negIdx - dis + n) % n] + balance[(negIdx + dis) % n];
            if (s >= need) {
                ans += (long) need * dis; // need 個 1 移動 dis 次
                return ans;
            }
            ans += (long) s * dis; // s 個 1 移動 dis 次
            need -= s;
        }
    }

    // https://leetcode.cn/problems/minimum-deletions-to-make-alternating-substring/solutions/3856595/xian-duan-shu-pythonjavacgo-by-endlessch-9579/
    public int[] minDeletions(String s, int[][] queries) {
        int size = 0;
        for (int[] q : queries) {
            size += q[0] - 1;
        }

        SegmentTree t = new SegmentTree(s);
        int[] ans = new int[size];
        int idx = 0;
        for (int[] q : queries) {
            if (q[0] == 1) {
                t.flip(q[1]);
            } else {
                ans[idx++] = t.query(q[1], q[2]);
            }
        }
        return ans;
    }


    class SegmentTree {
        private static class Data {
            byte lc; // 區間左端點字母
            byte rc; // 區間右端點字母
            int del = 0; // 區間刪除次數
        }

        private Data merge(Data l, Data r) {
            Data res = new Data();
            res.lc = l.lc;
            res.rc = r.rc;
            res.del = l.del + r.del + (l.rc == r.lc ? 1 : 0); // 端點相同時，合並後多刪一次
            return res;
        }

        private final int n;
        private final Data[] t;

        public SegmentTree(String s) {
            n = s.length();
            t = new Data[2 << (32 - Integer.numberOfLeadingZeros(n - 1))];
            build(s.toCharArray(), 1, 0, n - 1);
        }

        public void flip(int i) {
            flip(1, 0, n - 1, i);
        }

        public int query(int ql, int qr) {
            return query(1, 0, n - 1, ql, qr).del;
        }

        private void maintain(int node) {
            t[node] = merge(t[node * 2], t[node * 2 + 1]);
        }

        private void build(char[] a, int node, int l, int r) {
            t[node] = new Data();
            if (l == r) { // 葉子
                t[node].lc = t[node].rc = (byte) (a[l] - 'A');
                return;
            }
            int m = (l + r) / 2;
            build(a, node * 2, l, m); // 初始化左子樹
            build(a, node * 2 + 1, m + 1, r); // 初始化右子樹
            maintain(node);
        }

        private void flip(int node, int l, int r, int i) {
            if (l == r) { // 葉子（到達目標）
                t[node].lc ^= 1;
                t[node].rc ^= 1;
                return;
            }
            int m = (l + r) / 2;
            if (i <= m) { // i 在左子樹
                flip(node * 2, l, m, i);
            } else { // i 在右子樹
                flip(node * 2 + 1, m + 1, r, i);
            }
            maintain(node);
        }

        private Data query(int node, int l, int r, int ql, int qr) {
            if (ql <= l && r <= qr) { // 當前子樹完全在 [ql, qr] 內
                return t[node];
            }
            int m = (l + r) / 2;
            if (qr <= m) { // [ql, qr] 在左子樹
                return query(node * 2, l, m, ql, qr);
            }
            if (ql > m) { // [ql, qr] 在右子樹
                return query(node * 2 + 1, m + 1, r, ql, qr);
            }
            Data lRes = query(node * 2, l, m, ql, qr);
            Data rRes = query(node * 2 + 1, m + 1, r, ql, qr);
            return merge(lRes, rRes);
        }
    }


}









