package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Basic;

public class Decrypt {

    // https://leetcode.cn/problems/defuse-the-bomb/solutions/2765762/on-ding-chang-hua-dong-chuang-kou-python-y2py/
    public int[] decrypt(int[] code, int k) {
        int n = code.length;
        int[] ans = new int[n];
        int r = k > 0 ? k + 1 : n;
        k = Math.abs(k);
        int s = 0;
        for (int i = r - k; i < r; i++) {
            s += code[i]; // 計算 ans[0]
        }
        for (int i = 0; i < n; i++) {
            ans[i] = s;
            s += code[r % n] - code[(r - k) % n];
            r++;
        }
        return ans;
    }


}
