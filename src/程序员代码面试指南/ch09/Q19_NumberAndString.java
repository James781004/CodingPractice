package 程序员代码面试指南.ch09;

public class Q19_NumberAndString {
//    CD71 一種字符串和數字的對應關系
//    描述
//    一個char類型的數組chs，其中所有的字符都不同。
//    例如，chs = ['A', 'B', 'C', ... 'Z']，則字符串與整數的對應關系如下：
//    A,  B...Z, AA, AB... AZ, BA,BB...ZZ, AAA...ZZZ,    AAAA...
//            1,  2, .26,27,  28,... 52, 53. 54...702,703... 18278, 18279.
//    例如，chs=['A','B','C']，則字符串與整數的對應關系如下：
//    A, B, C, AA, AB...CC, AAA...CCC, AAAA....
//            1   2  3     4     5    12      13      39        40
//    給定一個數組chs, 實現根據對應關系完成字符串與整數相互轉換的兩個函數
//[要求]
//    數字轉字符串的覆雜度為O(logn)，字符串轉數字的覆雜度為O(字符串長度)


    // 從數字到字符串。以chs = [‘A’, ‘B’, ‘C’]，n = 72為例子描述該過程：
    //
    // chs的長度為3，所以這是一個3偽進制，從低位到高位依次為1,3,9,27,81…
    //
    // 從1開始減，72減1剩余71;
    // 71減3剩余68;
    // 68減9剩余59;
    // 59減27剩余32;
    // 32減81，不夠減。
    // 此時就知道想要表達十進制數的72，只需要使用3偽進制的前4位，也就是27,9,3,1。
    // 換句話說，既然k偽進制中每個位上的值都不能等於0，
    // 就從低位到高位把每個位置的都值都先減去1，看這個數到底需要前幾位。
    //
    // 剩餘的數是32，同時前四位的值各用了一遍。
    // 接下來看32最多可以用幾個27，最多用1個，加上之前使用的一個，一個要用兩個27，
    // 所以該位置上應該填 ‘B’。
    // 32%27的結果是5，然後看5能用幾個9，一個也用不了，所以該位置應該填 ‘A’。
    // 再看5能用幾個3，能用一個，所以該位置填 ‘B’。
    // 5%3的結果是2，再看2能用幾個1，能用兩個，所以該位置應該填 ‘C’。
    // 所以結果是 “BABC”
    //
    // 總結一下，就是先從低位到高位看一個數N最多能用幾個k偽進制數的位，時間覆雜度是 logkN 。
    // 然後從高位到低位反著回去看每個位上的值最多是多少，時間覆雜度是 logkN ，所以總的時間覆雜度是 logkN 。
    public static String getString(char[] chs, int n) {
        if (chs == null || chs.length == 0 || n < 1) {
            return "";
        }
        int cur = 1;
        int base = chs.length;
        int len = 0;

        // 低位到高位各減一次，目的是求出"當前位數(len)"
        while (n >= cur) {
            len++;
            n -= cur;
            cur *= base;
        }

        // 接下來把上一步的餘數n分解開來
        char[] res = new char[len];
        int index = 0;
        int nCur = 0;
        do {
            cur /= base; // 前面while結束時cur已經大於剩餘的n，所以這邊要除回一個base才是目前的最高位數
            nCur = n / cur;
            res[index++] = getKthCharAtChs(chs, nCur + 1); // 每一位的值已經各用了一遍所以nCur+1
            n %= cur; // n用掉了cur，取餘數
        } while (index != res.length);
        return String.valueOf(res);
    }

    public static char getKthCharAtChs(char[] chs, int k) {
        if (k < 1 || k > chs.length) {
            return 0;
        }
        return chs[k - 1];
    }


    // 從字符串到數字。以chs = [‘A’,’B’,’C’]，str = “ABBA”為例，
    // 可以知道這個字符串的含義是27有1個，9有2個，3有2個，1有1個，所以對應的數字是52。
    public static int getNum(char[] chs, String str) {
        if (chs == null || chs.length == 0) {
            return 0;
        }
        char[] strc = str.toCharArray();
        int base = chs.length;
        int cur = 1;
        int res = 0;
        for (int i = strc.length - 1; i != -1; i--) {
            res += getNthFromChar(chs, strc[i]) * cur;
            cur *= base;
        }
        return res;
    }

    public static int getNthFromChar(char[] chs, char ch) {
        int res = -1;
        for (int i = 0; i != chs.length; i++) {
            if (chs[i] == ch) {
                res = i + 1;
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        char[] chs = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};
        int len = 1;
        String res = "";
        for (int i = 1; i != 705; i++) {
            res = getString(chs, i);
            if (res.length() != len) {
                len = res.length();
                System.out.println("================");
            }
            System.out.print(res + " ");
            if (i % chs.length == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("========================");
        int testNum = 78128712;
        System.out.println(getNum(chs, getString(chs, testNum)));
        String testStr = "BZZA";
        System.out.println(getString(chs, getNum(chs, testStr)));

    }
}
