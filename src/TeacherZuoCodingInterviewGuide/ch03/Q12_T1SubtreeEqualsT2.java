package TeacherZuoCodingInterviewGuide.ch03;

public class Q12_T1SubtreeEqualsT2 {
    // 題目
    // 給定彼此獨立的兩棵樹頭節點分別為 t1 和 t2，
    // 判斷 t1 中是否有與 t2 樹拓撲結構完全相同的子樹。

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    // 經典解法
    private static boolean isSubStructure(Node treeA, Node treeB) {
        if (treeA == null || treeB == null) return false;    // 空樹不是任何一棵樹的子樹
        // 樹A和樹B完全一樣，或樹B為樹A的左右子樹之一都可以
        return isSame(treeA, treeB) || isSubStructure(treeA.left, treeB) || isSubStructure(treeA.right, treeB);
    }

    private static boolean isSame(Node treeA, Node treeB) {
        if (treeB == null) return true;     // B樹到空，說明節點都檢查完了，返回true
        if (treeA == null || treeA.value != treeB.value) return false;    // A樹為空或節點值不同，返回false
        // 檢查兩棵樹的左右子樹是否都相同
        return isSame(treeA.left, treeB.left) && isSame(treeA.right, treeB.right);
    }

    // 進階解法：序列化後直接比較字串
    // 首先把 t1 樹和 t2 樹按照先序遍歷的方式序列化。
    // 以題目的例子來說，t1 樹序列化後的結果為“1!2!4!#!8!#!#!5!9!#!#!#!3!6!#!#!7!#!#!”，記為t1Str。
    // t2 樹序列化後的結果為“2!4!#!8!#!#!5!9!#!#!#!”，記為 t2Str。
    //
    // 接下來，只要驗證 t2Str 是否是 t1Str 的子串即可，這個用 KMP 算法可以在線性時間內解決。
    public static boolean isSubtree(Node t1, Node t2) {
        String s1 = serialByPre(t1);
        String s2 = serialByPre(t2);

//        return s1.contains(s2);
        return getIndexOf(s1, s2) != -1;
    }

    public static String serialByPre(Node head) {
        if (head == null) return "#!";
        String res = head.value + "!";
        res += serialByPre(head.left);
        res += serialByPre(head.right);
        return res;
    }

    // KMP
    public static int getIndexOf(String s, String m) {
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) return -1;
        char[] ss = s.toCharArray();
        char[] ms = m.toCharArray();
        int[] nextArr = getNextArray(ms);
        int index = 0;
        int mi = 0;
        while (index < ss.length && mi < ms.length) {
            if (ss[index] == ms[mi]) {
                index++;
                mi++;
            } else if (nextArr[mi] == -1) {
                index++;
            } else {
                mi = nextArr[mi];
            }
        }
        return mi == ms.length ? index - mi : -1;
    }

    public static int[] getNextArray(char[] ms) {
        if (ms.length == 1) {
            return new int[]{-1};
        }
        int[] nextArr = new int[ms.length];
        nextArr[0] = -1;
        nextArr[1] = 0;
        int pos = 2;
        int cn = 0;
        while (pos < nextArr.length) {
            if (ms[pos - 1] == ms[cn]) {
                nextArr[pos++] = ++cn;
            } else if (cn > 0) {
                cn = nextArr[cn];
            } else {
                nextArr[pos++] = 0;
            }
        }
        return nextArr;
    }


    public static void main(String[] args) {
        Node t1 = new Node(1);
        t1.left = new Node(2);
        t1.right = new Node(3);
        t1.left.left = new Node(4);
        t1.left.right = new Node(5);
        t1.right.left = new Node(6);
        t1.right.right = new Node(7);
        t1.left.left.right = new Node(8);
        t1.left.right.left = new Node(9);

        Node t2 = new Node(2);
        t2.left = new Node(4);
        t2.left.right = new Node(8);
        t2.right = new Node(5);
        t2.right.left = new Node(9);

        System.out.println(isSubtree(t1, t2));

    }
}
