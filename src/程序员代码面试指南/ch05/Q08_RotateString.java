package 程序员代码面试指南.ch05;

public class Q08_RotateString {
    //    翻轉字符串
//    描述
//    1. 給定字符類型的數組chas，請在單詞間做逆序調整。只要做到單詞的順序逆序即可，對空格的位置沒有要求。
//    2. 給一個字符類型的數組chas和一個整數size，請把大小為size的左半區整體右移到右半區，右半區整體移動到左邊。

    public static void rotateWord(char[] chas) {
        if (chas == null || chas.length == 0) {
            return;
        }

        // 1. 首先整體翻轉一次
        reverse(chas, 0, chas.length - 1);

        // 2. 之後每個單詞各自翻轉
        int l = -1;
        int r = -1;
        for (int i = 0; i < chas.length; i++) {
            if (chas[i] != ' ') {

                // 0位置或者前一位是空格，紀錄位置為左位
                l = i == 0 || chas[i - 1] == ' ' ? i : l;

                // 尾部位置或者後一位是空格，紀錄位置為右位
                r = i == chas.length - 1 || chas[i + 1] == ' ' ? i : r;
            }

            // 左右都不是-1時進行翻轉，之後重置左右位-1
            if (l != -1 && r != -1) {
                reverse(chas, l, r);
                l = -1;
                r = -1;
            }
        }
    }

    public static void reverse(char[] chas, int start, int end) {
        char tmp = 0;
        while (start < end) {
            tmp = chas[start];
            chas[start] = chas[end];
            chas[end] = tmp;
            start++;
            end--;
        }
    }

    public static void rotate1(char[] chas, int size) {
        if (chas == null || size <= 0 || size >= chas.length) {
            return;
        }

        // 1. 首先翻轉指定長度左半部
        reverse(chas, 0, size - 1);

        // 2. 再來翻轉剩餘長度右半部
        reverse(chas, size, chas.length - 1);

        // 3. 最後翻轉整體字串
        reverse(chas, 0, chas.length - 1);
    }

    public static void rotate2(char[] chas, int size) {
        if (chas == null || size <= 0 || size >= chas.length) {
            return;
        }
        int start = 0;
        int end = chas.length - 1;
        int lpart = size;
        int rpart = chas.length - size;
        int s = Math.min(lpart, rpart);
        int d = lpart - rpart;
        while (true) {
            exchange(chas, start, end, s);
            if (d == 0) {
                break;
            } else if (d > 0) {
                start += s;
                lpart = d;
            } else {
                end -= s;
                rpart = -d;
            }
            s = Math.min(lpart, rpart);
            d = lpart - rpart;
        }
    }

    public static void exchange(char[] chas, int start, int end, int size) {
        int i = end - size + 1;
        char tmp = 0;
        while (size-- != 0) {
            tmp = chas[start];
            chas[start] = chas[i];
            chas[i] = tmp;
            start++;
            i++;
        }
    }

    public static void main(String[] args) {
        char[] chas1 = {'d', 'o', 'g', ' ', 'l', 'o', 'v', 'e', 's', ' ', 'p',
                'i', 'g'};
        System.out.println(String.valueOf(chas1));
        rotateWord(chas1);
        System.out.println(String.valueOf(chas1));

        char[] chas2 = {'1', '2', '3', '4', '5', 'A', 'B', 'C'};
        System.out.println(String.valueOf(chas2));
        rotate1(chas2, 5);
        System.out.println(String.valueOf(chas2));
        rotate2(chas2, 3);
        System.out.println(String.valueOf(chas2));

    }
}
