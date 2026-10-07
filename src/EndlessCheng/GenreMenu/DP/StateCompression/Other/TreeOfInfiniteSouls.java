package EndlessCheng.GenreMenu.DP.StateCompression.Other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeOfInfiniteSouls {

    // https://leetcode.cn/problems/cnHoX6/solutions/3748723/mo-ling-zhi-shu-ti-jie-by-gpt-5-thinking-aplb/
    public int treeOfInfiniteSouls(int[] gem, int p, int target) {
        final int n = gem.length;
        // 預處理：位數、取模、10^位數
        int[] d = new int[n];
        int[] xi = new int[n];
        int totalDigits = 0;
        for (int i = 0; i < n; i++) {
            d[i] = digitLen(gem[i]);
            xi[i] = mod(gem[i], p);
            totalDigits += d[i];
        }

        // 10 的冪
        int maxExp = totalDigits + 4 * n + 10;
        int[] pow10 = new int[maxExp + 1];
        pow10[0] = 1 % p;
        for (int i = 1; i <= maxExp; i++) pow10[i] = (int) ((pow10[i - 1] * 10L) % p);
        int[] powDi = new int[n];
        for (int i = 0; i < n; i++) powDi[i] = pow10[d[i]];

        // 每個掩碼的位數和
        int FULL = (1 << n) - 1;
        int[] sumDigMask = new int[1 << n];
        for (int m = 1; m <= FULL; m++) {
            int lb = m & -m;
            int idx = Integer.numberOfTrailingZeros(lb);
            sumDigMask[m] = sumDigMask[m ^ lb] + d[idx];
        }

        // 生成所有樹形的“塊”（B0..Bn 的長度與值 mod p）
        @SuppressWarnings("unchecked")
        List<Blocks>[] memo = new ArrayList[n + 1];
        List<Blocks> shapes = buildBlocks(n, p, pow10, memo);

        int leftCnt = n / 2;
        int rightCnt = n - leftCnt;
        int T = mod(target, p);

        long answer = 0;

        // 逐形狀處理（一次兩側都做“枚舉排列→分桶→排序壓縮”）
        for (Blocks blk : shapes) {
            int[] L = blk.len;   // B0..Bn 的長度
            int[] B = blk.val;   // B0..Bn 的值 (mod p)
            int b0 = B[0];

            int sumLenLeftBlocks = 0;
            for (int i = 1; i <= leftCnt; i++) sumLenLeftBlocks += L[i];
            int sumLenRightBlocks = 0;
            for (int i = leftCnt + 1; i <= n; i++) sumLenRightBlocks += L[i];

            int[] powL = new int[n + 1];
            for (int i = 0; i <= n; i++) powL[i] = pow10[L[i]];

            // 右側（長度 rightCnt，起始塊位置 leftCnt+1）
            DPFinal rightFinal = enumerateSide(n, rightCnt, leftCnt + 1, powL, B, powDi, xi, p);
            // 左側（長度 leftCnt，起始塊位置 1）
            DPFinal leftFinal = enumerateSide(n, leftCnt, 1, powL, B, powDi, xi, p);

            // 按右掩碼配對其補集左掩碼
            for (int i = 0; i < rightFinal.maskCount; i++) {
                int rMask = rightFinal.masks[i];
                MapArr rArr = rightFinal.arrByMask[rMask];
                if (rArr == null) continue;

                int lMask = FULL ^ rMask;
                MapArr lArr = leftFinal.arrByMask[lMask];
                if (lArr == null) continue;

                int sumDigitsRight = sumDigMask[rMask];
                int sumDigitsLeft = totalDigits - sumDigitsRight;
                int M_right = pow10[sumDigitsRight + sumLenRightBlocks];
                int M_left = pow10[sumDigitsLeft + sumLenLeftBlocks];
                int base = mul(mul(b0, M_left, p), M_right, p);

                int[] lk = lArr.keys, lv = lArr.cnts;
                for (int t = 0; t < lk.length; t++) {
                    int aLeft = lk[t];
                    int cLeft = lv[t];
                    int need = mod(T - base - mul(aLeft, M_right, p), p);
                    int cRight = rArr.getCount(need);
                    if (cRight != 0) answer += (long) cLeft * cRight;
                }
            }
        }
        return (int) answer;
    }

    /* ----------------- “按側枚舉排列 → 分桶 → 排序壓縮” ----------------- */

    // 返回：所有 |mask|=k 的桶壓縮成 (有序 keys[], 對應 counts[])；以及這些 mask 列表
    private DPFinal enumerateSide(int n, int k, int posStart,
                                  int[] powL, int[] B, int[] powDi, int[] xi, int p) {
        int ALL = 1 << n;

        // 每個 mask 一個動態數組桶（延遲創建）；記錄達到該 mask 的所有 w
        IntList[] buckets = new IntList[ALL];
        int[] usedMasks = new int[130]; // C(9,4/5) ≤ 126
        IntHolder usedCntHolder = new IntHolder();

        // 深搜生成長度為 k 的有序無重復挑選；沿途維護 (mask, w)
        dfsEnumerate(n, k, posStart, powL, B, powDi, xi, p,
                0, 0, 0, buckets, usedMasks, usedCntHolder);

        int usedCnt = usedCntHolder.val;

        // 把每個桶排序並壓縮為 (key, count) 有序數組 —— 單趟掃描同時寫 keys/cnts
        MapArr[] arrByMask = new MapArr[ALL];
        for (int i = 0; i < usedCnt; i++) {
            int mask = usedMasks[i];
            IntList list = buckets[mask];
            int[] a = list.toArray();
            Arrays.sort(a);
            int m = a.length;
            int[] keysTmp = new int[m];
            int[] cntsTmp = new int[m];
            int uniq = 0;
            for (int t = 0; t < m; ) {
                int v = a[t];
                int j = t + 1;
                while (j < m && a[j] == v) j++;
                keysTmp[uniq] = v;
                cntsTmp[uniq] = j - t;
                uniq++;
                t = j;
            }
            int[] keys = Arrays.copyOf(keysTmp, uniq);
            int[] cnts = Arrays.copyOf(cntsTmp, uniq);
            arrByMask[mask] = new MapArr(keys, cnts);
        }
        return new DPFinal(arrByMask, Arrays.copyOf(usedMasks, usedCnt), usedCnt);
    }

    // 小結構：傳出 usedCnt
    private static class IntHolder {
        int val;
    }

    private void dfsEnumerate(int n, int k, int posStart,
                              int[] powL, int[] B, int[] powDi, int[] xi, int p,
                              int t, int mask, int w,
                              IntList[] buckets, int[] usedMasks, IntHolder usedCntHolder) {
        if (t == k) {
            IntList list = buckets[mask];
            if (list == null) {
                list = new IntList(16);
                buckets[mask] = list;
                usedMasks[usedCntHolder.val++] = mask;
            }
            list.add(w);
            return;
        }
        int pos = posStart + t;
        int powLj = powL[pos];
        int unused = ((1 << n) - 1) ^ mask;
        while (unused != 0) {
            int lb = unused & -unused;
            int gi = Integer.numberOfTrailingZeros(lb);
            unused ^= lb;

            int w2 = add(mul(add(mul(w, powDi[gi], p), xi[gi], p), powLj, p), B[pos], p);
            dfsEnumerate(n, k, posStart, powL, B, powDi, xi, p,
                    t + 1, mask | (1 << gi), w2,
                    buckets, usedMasks, usedCntHolder);
        }
    }

    /* ----------------- 形狀生成與“塊”拼接 ----------------- */

    static class Blocks {
        final int n;
        final int[] len; // B0..Bn 的長度
        final int[] val; // B0..Bn 的值 (mod p)

        Blocks(int n, int[] len, int[] val) {
            this.n = n;
            this.len = len;
            this.val = val;
        }
    }

    private List<Blocks> buildBlocks(int n, int p, int[] pow10, List<Blocks>[] memo) {
        if (memo[n] != null) return memo[n];
        List<Blocks> res = new ArrayList<>();
        if (n == 1) {
            res.add(new Blocks(1, new int[]{1, 1}, new int[]{1 % p, 9 % p}));
        } else {
            for (int k = 1; k <= n - 1; k++) {
                List<Blocks> left = buildBlocks(k, p, pow10, memo);
                List<Blocks> right = buildBlocks(n - k, p, pow10, memo);
                for (Blocks L : left)
                    for (Blocks R : right) {
                        int[] len = new int[n + 1];
                        int[] val = new int[n + 1];

                        // B0 = '1' + L.B0
                        concat(1, 1 % p, L.len[0], L.val[0], p, pow10, len, val, 0);
                        // B1..B_{k-1} = L 內部塊
                        for (int i = 1; i <= k - 1; i++) {
                            len[i] = L.len[i];
                            val[i] = L.val[i];
                        }
                        // Bk = L.Bk + R.B0
                        concat(L.len[k], L.val[k], R.len[0], R.val[0], p, pow10, len, val, k);
                        // 右內部塊
                        for (int j = 1; j <= R.n - 1; j++) {
                            len[k + j] = R.len[j];
                            val[k + j] = R.val[j];
                        }
                        // Bn = R.B_{R.n} + '9'
                        concat(R.len[R.n], R.val[R.n], 1, 9 % p, p, pow10, len, val, n);

                        res.add(new Blocks(n, len, val));
                    }
            }
        }
        memo[n] = res;
        return res;
    }

    private static void concat(int lenA, int valA, int lenB, int valB, int p, int[] pow10,
                               int[] outLen, int[] outVal, int idx) {
        outLen[idx] = lenA + lenB;
        outVal[idx] = (int) (((valA * 1L * pow10[lenB]) + valB) % p);
    }

    /* ----------------- 輕量結構與工具 ----------------- */

    // 動態 int 列表（避免 ArrayList<Integer> 裝箱開銷）
    static class IntList {
        int[] a;
        int sz;

        IntList(int cap) {
            a = new int[cap];
        }

        void add(int v) {
            if (sz == a.length) a = Arrays.copyOf(a, a.length << 1);
            a[sz++] = v;
        }

        int[] toArray() {
            return Arrays.copyOf(a, sz);
        }
    }

    // 壓縮後的 (key→count) 有序數組 + 二分查詢
    static class MapArr {
        final int[] keys;
        final int[] cnts;

        MapArr(int[] k, int[] c) {
            keys = k;
            cnts = c;
        }

        int getCount(int key) {
            int idx = Arrays.binarySearch(keys, key);
            return idx >= 0 ? cnts[idx] : 0;
        }
    }

    static class DPFinal {
        final MapArr[] arrByMask; // 索引為 mask（只有 |mask|=k 的若干處非空）
        final int[] masks;        // |mask|=k 的掩碼列表
        final int maskCount;

        DPFinal(MapArr[] arrByMask, int[] masks, int maskCount) {
            this.arrByMask = arrByMask;
            this.masks = masks;
            this.maskCount = maskCount;
        }
    }

    private static int digitLen(int x) {
        if (x == 0) return 1;
        int len = 0;
        long v = x >= 0 ? x : -(long) x;
        while (v > 0) {
            v /= 10;
            len++;
        }
        return len;
    }

    private static int mod(long x, int p) {
        long v = x % p;
        if (v < 0) v += p;
        return (int) v;
    }

    private static int add(int a, int b, int p) {
        int s = a + b;
        if (s >= p) s -= p;
        return s;
    }

    private static int mul(int a, int b, int p) {
        return (int) ((a * 1L * b) % p);
    }


}
