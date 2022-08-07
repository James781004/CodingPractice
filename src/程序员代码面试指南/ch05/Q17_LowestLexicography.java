package 程序员代码面试指南.ch05;

import java.util.Arrays;
import java.util.Comparator;

public class Q17_LowestLexicography {
    //    拼接所有的字符串产生字典序最小的字符串
//    描述
//    给定一个字符串的数组strs，请找到一种拼接顺序，使得所有的字符串拼接起来组成的字符串是所有可能性中字典序最小的，并返回这个字符串。

    public static class MyComparator implements Comparator<String> {

        @Override
        public int compare(String a, String b) {
            return (a + b).compareTo(b + a);
        }
    }

    public static String lowestString(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        // 貪心演算法，對字元串數組進行排序，
        // 如果某兩個字元串a和b滿足“a+b < b+a”，就將a排在b的前面，
        // 排序完後將字元串數組中的所有字元串連接起來就是要求的字典序最小的字元串。
        Arrays.sort(strs, new MyComparator());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String[] strs1 = {"jibw", "ji", "jp", "bw", "jibw"};
        System.out.println(lowestString(strs1));

        String[] strs2 = {"ba", "b"};
        System.out.println(lowestString(strs2));

    }
}
