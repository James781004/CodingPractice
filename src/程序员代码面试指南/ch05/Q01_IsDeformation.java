package 程序员代码面试指南.ch05;

public class Q01_IsDeformation {
    // 題目：判斷兩字串是否互為變形詞
    public static boolean isDeformation(String str1, String str2) {
        if (str1.length() != str2.length()) return false;
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int[] map = new int[256];
        for (int i = 0; i < chars1.length; i++) {
            map[chars1[i]]++;
        }

        for (int i = 0; i < chars2.length; i++) {
            if (map[chars2[i]]-- == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String A = "abcabcabc";
        String B = "bcacbaacb";
        System.out.println(isDeformation(A, B));

    }

}
