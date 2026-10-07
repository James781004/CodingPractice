package EndlessCheng.GenreMenu.DP.PreFixSuffix;

public class NumSplits {

    // https://leetcode.cn/problems/number-of-good-ways-to-split-a-string/solutions/348447/leetcode-5458-wo-gan-jio-jiang-de-ying-gai-xiang-x/
    public int numSplits(String s) {

        int size = 26;
        int[] leftCount = new int[size];
        int[] rightCount = new int[size];
        int leftVaild = 0;
        int rightVaild = 0;
        //先將整個字符串作為右半部分滑窗的內容
        for (char ch : s.toCharArray()) {
            int num = ch - 'a';
            if (rightCount[num] == 0) {
                rightVaild++;
            }
            rightCount[num]++;
        }

        int c = 0;
        //慢慢調整左半部分滑窗，從左往右遍歷，增大左半部分，縮減右半部分，進行滑窗
        for (char ch : s.toCharArray()) {
            int num = ch - 'a';
            //左邊沒有出現過這種字符，那麼左邊字符 +1
            if (leftCount[num] == 0) {
                leftVaild++;
            }
            //右邊這是最後一次出現該字符，那麼滑窗後右邊字符 -1，即這種字符不會再出現在右邊了
            if (rightCount[num] == 1) {
                rightVaild--;
            }
            leftCount[num]++;
            rightCount[num]--;
            if (leftVaild == rightVaild) {
                c++;
            }
        }
        return c;
    }


}
