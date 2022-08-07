package TeacherZuoCodingInterviewGuide.ch04;

public class Q21_NQueens {
//    N皇後問題
//    描述
//    N皇後問題是指在N*N的棋盤上要擺N個皇後，要求任何兩個皇後不同行，不同列也不再同一條斜線上，求給一個整數n，返回n皇後的擺法。

    public static int num1(int n) {
        if (n < 1) return 0;
        int[] record = new int[n];
        return process1(0, record, n);
    }

    // 本題的搜索狀態完全不重複(具備後效性)，動態規劃無法減省任何時間。
    public static int process1(int i, int[] record, int n) {
        if (i == n) return 1; // 所有列都檢查完了，找到了一種合法的擺法

        // 嘗試每列的結果並記錄下來
        int res = 0;
        for (int j = 0; j < n; j++) {
            if (isValid(record, i, j)) {
                record[i] = j; // 紀錄本列存放位置
                res += process1(i + 1, record, n); // 帶著紀錄前往下一列做檢查
            }
        }
        return res;
    }

    public static boolean isValid(int[] record, int i, int j) {
        for (int k = 0; k < i; k++) {
            // 共列或共斜線
            if (j == record[k] || Math.abs(record[k] - j) == Math.abs(k - i)) {
                return false;
            }
        }
        return true;
    }

    public static int num2(int n) {
        if (n < 1 || n > 32) {
            return 0;
        }
        int upperLim = n == 32 ? -1 : (1 << n) - 1;
        return process2(upperLim, 0, 0, 0);
    }

    public static int process2(int upperLim, int colLim, int leftDiaLim,
                               int rightDiaLim) {
        if (colLim == upperLim) {
            return 1;
        }
        int pos = 0;
        int mostRightOne = 0;
        pos = upperLim & (~(colLim | leftDiaLim | rightDiaLim));
        int res = 0;
        while (pos != 0) {
            mostRightOne = pos & (~pos + 1);
            pos = pos - mostRightOne;
            res += process2(upperLim, colLim | mostRightOne,
                    (leftDiaLim | mostRightOne) << 1,
                    (rightDiaLim | mostRightOne) >>> 1);
        }
        return res;
    }

    public static void main(String[] args) {
        int n = 8;

        long start = System.currentTimeMillis();
        System.out.println(num2(n));
        long end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println(num1(n));
        end = System.currentTimeMillis();
        System.out.println("cost time: " + (end - start) + "ms");

    }
}
