package TeacherZuoCodingInterviewGuide.ch05;

public class Q07_ModifyAndReplace {
//    字符串的調整
//    描述
//    1. 給定一個字符類型的數組chas[],chas右半區全是空字符，左半區不含有空字符。
//    現在想將左半區的空格字符串替換成“%20”，假設chas右半區足夠大，可以滿足替換需要的空間，請完成替換函數。

//    2. 給定一個字符串chas[],其中只含有字母字符和“*”字符，現在想把所有“*”全部挪到chas的左邊，字母字符移到chas的右邊。完成調整函數。

    public static void replace(char[] chas) {
        if (chas == null || chas.length == 0) {
            return;
        }
        int num = 0;
        int len = 0;
        for (len = 0; len < chas.length && chas[len] != 0; len++) {
            if (chas[len] == ' ') {
                num++; // 紀錄空格數
            }
        }

        // 空格變成%20之後，字串總長度變成len + 2 * num，下標位置再減去1
        int j = len + num * 2 - 1;

        // 逆向遍歷字串把結果放進chas
        for (int i = len - 1; i > -1; i--) {
            if (chas[i] != ' ') {
                chas[j--] = chas[i];
            } else {
                chas[j--] = '0';
                chas[j--] = '2';
                chas[j--] = '%';
            }
        }
    }

    public static void modify(char[] chas) {
        if (chas == null || chas.length == 0) {
            return;
        }

        // i, j 雙指針處理不同狀況
        // i從右到左負責找不是*的元素，找到後放入j位置然後j才右移
        // i走回頭部之後，j再把剩下位置填入*
        int j = chas.length - 1;
        for (int i = chas.length - 1; i > -1; i--) {
            if (chas[i] != '*') {
                chas[j--] = chas[i];
            }
        }
        for (; j > -1; ) {
            chas[j--] = '*';
        }
    }

    public static void main(String[] args) {
        char[] chas1 = {'a', ' ', 'b', ' ', ' ', 'c', 0, 0, 0, 0, 0, 0, 0, 0,};
        replace(chas1);
        System.out.println(String.valueOf(chas1));

        char[] chas2 = {'1', '2', '*', '*', '3', '4', '5'};
        modify(chas2);
        System.out.println(String.valueOf(chas2));

    }
}
