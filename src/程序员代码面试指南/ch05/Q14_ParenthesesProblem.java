package 程序员代码面试指南.ch05;

public class Q14_ParenthesesProblem {
    public static boolean isValid(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        char[] chas = str.toCharArray();
        int status = 0;
        for (int i = 0; i < chas.length; i++) {
            if (chas[i] != ')' && chas[i] != '(') {
                return false;
            }
            if (chas[i] == ')' && --status < 0) {
                return false;
            }
            if (chas[i] == '(') {
                status++;
            }
        }
        return status == 0;
    }

    public static int maxLength(String str) {
        if (str == null || str.equals("")) {
            return 0;
        }
        char[] chas = str.toCharArray();
        int[] dp = new int[chas.length];
        int pre = 0;
        int res = 0;
        for (int i = 1; i < chas.length; i++) {
            if (chas[i] == ')') {
                pre = i - dp[i - 1] - 1; // 下標位置：當前位置減去前面一位累積長度之後，再往前一位
                if (pre >= 0 && chas[pre] == '(') {
                    // 除了前面一位累積長度+2，還需要考慮i - dp[i - 1] - 2位置還有沒有其他結果
                    // 例如：()(())假設目前找到最後一位)，那pre位置只能找到(())，前面的()就沒算進去
                    // 所以要多檢查pre-1位置有沒有更長的可能性
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }
            }
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    public static void main(String[] args) {
        String str1 = "((())())";
        System.out.println(isValid(str1));
        System.out.println(maxLength(str1));

        String str2 = "(())(()(()))";
        System.out.println(isValid(str2));
        System.out.println(maxLength(str2));

        String str3 = "()(()()(";
        System.out.println(isValid(str3));
        System.out.println(maxLength(str3));

    }
}
