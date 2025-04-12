package EndlessCheng.GenreMenu.BinarySearch.Other;

public class FindPeakGrid {

    // https://leetcode.cn/problems/find-a-peak-element-ii/solutions/2571587/tu-jie-li-yong-xing-zui-da-zhi-pan-duan-r4e0n/
    public int[] findPeakGrid(int[][] mat) {
        int left = -1;
        int right = mat.length - 1;
        while (left + 1 < right) {
            int i = (left + right) >>> 1;
            int j = indexOfMax(mat[i]);

            // 如果mid的元素小於其上一個元素，那麼在(left, mid)肯定會存在一個峰值
            // 否則mid的元素小於其下一個元素，那麼在(mid, right)會存在一個峰值
            if (mat[i][j] > mat[i + 1][j]) {
                right = i; // 峰頂行號 <= i
            } else {
                left = i; // 峰頂行號 > i
            }
        }
        return new int[]{right, indexOfMax(mat[right])};
    }

    // 找到當前行最大值的索引
    private int indexOfMax(int[] a) {
        int idx = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] > a[idx]) {
                idx = i;
            }
        }
        return idx;
    }


}
