package TeacherZuoCodingInterviewGuide.ch04;


import java.util.Arrays;
import java.util.Comparator;

public class Q08_EnvelopesProblem {

    //    信封嵌套問題
//    描述
//    給n個信封的長度和寬度。如果信封A的長和寬都小於信封B，那麽信封A可以放到信封B里，請求出信封最多可以嵌套多少層。

    public static class Envelope {
        public int len;
        public int wid;

        public Envelope(int weight, int hight) {
            len = weight;
            wid = hight;
        }
    }

    // 先把全部長度小到大排列，再把同長度範圍內的寬度大到小排列
    // 這題就可以轉換成最長遞增子序列問題
    public static class EnvelopeComparator implements Comparator<Envelope> {
        @Override
        public int compare(Envelope o1, Envelope o2) {
            return o1.len != o2.len ? o1.len - o2.len : o2.wid - o1.wid;
        }
    }

    public static Envelope[] getSortedEnvelopes(int[][] matrix) {
        Envelope[] res = new Envelope[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            res[i] = new Envelope(matrix[i][0], matrix[i][1]);
        }
        Arrays.sort(res, new EnvelopeComparator());
        return res;
    }

    public static int maxEnvelopes(int[][] matrix) {
        Envelope[] envelopes = getSortedEnvelopes(matrix);
        int[] ends = new int[matrix.length]; // 存放i+1長度子序列的最小結尾
        ends[0] = envelopes[0].wid;

        // 用來在end數組裡面目前有效區域的右邊界位置
        int right = 0;

        // 用來在end數組裡面二分搜尋的指針
        int l = 0;
        int r = 0;
        int m = 0;

        for (int i = 1; i < envelopes.length; i++) {
            l = 0;
            r = right;

            // 左閉右開二分搜尋，最後l會是我們要找的對象
            while (l <= r) {
                m = (l + r) / 2;
                if (envelopes[i].wid > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }

            // 更新有效區域的右邊界位置
            right = Math.max(right, l);

            // 更新有效區右邊界數值
            // ends[l]同位置每次更新，值只有可能愈來愈小
            // 因為更大的值可以出現在ends[l]後面位置形成更長的遞增子序列
            ends[l] = envelopes[i].wid;
        }
        return right + 1; // 這邊沒有存dp數組，直接取得右邊界位置+1就是答案
    }

    public static void main(String[] args) {
        int[][] test = {{3, 4}, {2, 3}, {4, 5}, {1, 3}, {2, 2},
                {3, 6}, {1, 2}, {3, 2}, {2, 4}};
        System.out.println(maxEnvelopes(test));
    }
}
