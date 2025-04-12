package EndlessCheng.GenreMenu.SlidingWindow.FixedWindow.Other;

public class DivisorSubstrings {

    // https://leetcode.cn/problems/find-the-k-beauty-of-a-number/solutions/1496449/mo-ni-by-endlesscheng-burh/
    // 數學做法
    // 不斷取最低的 k 位數字，判斷後，去掉末尾數字，不斷循環直到不足 k 位數字。
    public int divisorSubstrings(int num, int k) {
        int ans = 0;
        int x = num;
        int m = (int) Math.pow(10, k);
        while (x >= m / 10) {
            int tmp = x % m;
            if (tmp > 0 && num % tmp == 0) {
                ans++;
            }
            x /= 10; // 去掉最後一位
        }
        return ans;
    }


    public int divisorSubstrings2(int num, int k) {
        // 字符串-截取
        String numStr = Integer.toString(num);
        int count = 0;
        for (int i = 0; i <= numStr.length() - k; i++) {
            // 截取長度為k的子字符串
            String subStr = numStr.substring(i, i + k);
            // 將子字符串轉換為整數
            int subNum = Integer.parseInt(subStr);// 自動處理前導零，如Integer.parseInt("012")=12
            // 檢查子字符串是否能整除num，同時避免除以0
            if (subNum != 0 && num % subNum == 0) {
                count++;
            }
        }
        return count;
    }
}
