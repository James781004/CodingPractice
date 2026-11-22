package EndlessCheng.GenreMenu.DataStructure.PrefixSum.HashMap;

import java.util.Arrays;

public class FindLongestSubarray {

    // https://leetcode.cn/problems/find-longest-subarray-lcci/solutions/2160308/tao-lu-qian-zhui-he-ha-xi-biao-xiao-chu-3mb11/
    public String[] findLongestSubarray(String[] array) {
        int n = array.length, begin = 0, end = 0, s = n;
        var first = new int[n * 2 + 1];
        Arrays.fill(first, -1); // 注：去掉可以再快 1ms（需要 s 下标改从 1 开始）
        first[s] = 0; // s[0] = 0
        for (int i = 1; i <= n; ++i) {
            s += (array[i - 1].charAt(0) >> 6 & 1) * 2 - 1;
            int j = first[s];
            if (j < 0)
                first[s] = i;
            else if (i - j > end - begin) {
                begin = j;
                end = i;
            }
        }
        var sub = new String[end - begin];
        System.arraycopy(array, begin, sub, 0, sub.length);
        return sub;
    }


}
