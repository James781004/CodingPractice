package 程序员代码面试指南.ch09;

public class Q34_KMPAlgorithm {
//    CD87 KMP算法
//    描述
//    給定兩個字符串str和match，長度分別為N和M。實現一個算法，如果字符串str中含有子串match，則返回match在str中的開始位置，不含有則返回-1
//    若出現了多次，則按照升序輸出所有出現位置
//[要求]
//    時間覆雜度為O(n)

    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }
        char[] ss = s.toCharArray();
        char[] ms = m.toCharArray();
        int si = 0;
        int mi = 0;
        int[] next = getNextArray(ms);
        while (si < ss.length && mi < ms.length) {
            if (ss[si] == ms[mi]) {
                si++;
                mi++;
            } else if (next[mi] == -1) {
                si++;  // mi沒法匹配成功，就只能移si的指針了
            } else {
                mi = next[mi]; // 不相等且還能往前跳，則mi往前面跳
            }
        }
        return mi == ms.length ? si - mi : -1;
    }

    public static int[] getNextArray(char[] ms) {
        if (ms.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int pos = 2;
        int cn = 0;
        while (pos < next.length) {
            if (ms[pos - 1] == ms[cn]) {
                next[pos++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[pos++] = 0;
            }
        }
        return next;
    }

    public static void main(String[] args) {
        String str = "abcabcababaccc";
        String match = "ababa";
        System.out.println(getIndexOf(str, match));

    }


}
