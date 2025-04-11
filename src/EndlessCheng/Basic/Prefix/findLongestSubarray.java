package EndlessCheng.Basic.Prefix;

import java.util.HashMap;

public class findLongestSubarray {

    // https://leetcode.cn/problems/find-longest-subarray-lcci/solutions/2160308/tao-lu-qian-zhui-he-ha-xi-biao-xiao-chu-3mb11/
    public String[] findLongestSubarray(String[] array) {
        int n = array.length;
        var s = new int[n + 1]; // 前綴和，「元素和等於 0」等價於「兩個前綴和之差等於 0」，進而等價於「兩個前綴和相同」
        for (int i = 0; i < n; ++i) // 如果 array[i][0] 是字母，則把 array[i] 視作 1；如果是數字，則視作 −1
            s[i + 1] = s[i] + (array[i].charAt(0) >> 6 & 1) * 2 - 1; // 可以用庫函數處理字母和數字的統計

        int begin = 0, end = 0; // 符合要求的子數組 [begin,end)
        var first = new HashMap<Integer, Integer>();
        for (int i = 0; i <= n; i++) {
            int j = first.getOrDefault(s[i], -1);
            if (j < 0) // 首次遇到 s[i]，將它加入哈希表中
                first.put(s[i], i);
            else if (i - j > end - begin) { // 更長的子數組，更新數組長度
                begin = j;
                end = i;
            }
        }

        var sub = new String[end - begin]; // 最後得到了最長子數組的左端點和長度，因此可以根據這兩個值將最長子數組拷貝出來
        System.arraycopy(array, begin, sub, 0, sub.length);
        return sub;
    }


}
