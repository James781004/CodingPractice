package EndlessCheng.GenreMenu.MonoStack.Basic;

public class MctFromLeafValues {

    // https://leetcode.cn/problems/minimum-cost-tree-from-leaf-values/solutions/2291352/li-jie-yu-fa-xian-dan-diao-zhan-guo-chen-yuua/
    public int mctFromLeafValues(int[] arr) {
        final int len = arr.length;
        int[] deque = new int[len + 1];
        int res = 0, tail = 0, last;
        deque[0] = 1_000_000_007;

        for (int num : arr) {
            // 遇到一個節點比當前搜到的最小值大，
            // 證明之前的最小值可能是某個最優路徑的一部分，先處理那條路徑
            if (deque[tail] < num) {
                last = deque[tail--];

                // 逐個彈出來合並
                while (deque[tail] < num) {
                    res += deque[tail] * last;
                    last = deque[tail--];
                }
                res += last * num;
            }
            deque[++tail] = num;
        }

        // 棧中剩餘元素
        for (; tail > 1; tail--) {
            res += deque[tail] * deque[tail - 1];
        }

        return res;
    }


}
