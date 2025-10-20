package EndlessCheng.GenreMenu.Backtracking.Backtracking.Other;

public class FindKthBit {

    // https://leetcode.cn/problems/find-kth-bit-in-nth-binary-string/solutions/1767760/by-boyka-cefr/
    /**
     * 首先很明顯的規律是 Sn = (Sn-1) * 2 + 1
     * 並且如果index 如果等於 (Sn + 1) >> 1 那一定是1
     * 如果小於中間，則等於 Sn-1 的同index
     * 如果大於中間，則等於  Sn-1 的逆序且反轉
     *
     * @param n
     * @param k
     * @return
     */

    int[] Sn;

    /**
     * 取反次數
     */
    int fCount = 0;

    public char findKthBit(int n, int k) {
        fCount = 0;
        Sn = new int[n];
        Sn[0] = 1;

        for (int i = 1; i < n; i++) {
            Sn[i] = Sn[i - 1] << 1 + 1;
        }

        int i = find(n, k);
        return (char) (i + '0');
    }

    public int find(int n, int k) {
        if (n == 1) {
            return 0;
        }

        int mid = (Sn[n - 1] + 1) >> 1;
        // 求的位置正好位於中間
        if (mid == k) {
            return 1;
        }
        // 左側，層數遞減
        if (mid > k) {
            return find(n - 1, k);
        } else {
            // 右側，k需要重新計算，並且記錄反轉個數
            fCount++;
            int i = find(n - 1, Sn[n - 1] - k + 1);
            if ((fCount & 1) == 1) {
                return i == 1 ? 0 : 1;
            } else {
                return i;
            }
        }
    }


}
