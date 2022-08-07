package TeacherZuoCodingInterviewGuide.ch05;

import java.util.HashMap;

public class Q06_FindStringInContainsNullArray {
//    在有序但是含有空的數組中查找字符串
//    描述
//    給定一個字符串數組strs[]，在strs中有些位置為null，但在不為null的位置上，其字符串是按照字典序由小到大出現的。
//    在給定一個字符串str，請返回str在strs中出現的最左位置，如果strs中不存在str請輸出“-1”。


    public static int getIndex(String[] strs, String str) {
        if (str == null || str.length() == 0 || strs == null) return -1;
        int res = -1;
        int left = 0;
        int right = str.length() - 1;
        int mid = 0;
        int i = 0;

        while (left <= right) {
            mid = (left + right) / 2;

            // mid非空并且命中，还要继续往左找找看
            if (strs[mid] != null && strs[mid].equals(str)) {
                res = mid;
                right = mid - 1;
            } else if (strs[mid] != null) {
                if (strs[mid].compareTo(str) < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            } else {
                // 簡單說就是strs[mid]為null的情況，指針i從mid位置開始左移
                // 重新找個不為null的元素來當作新的二分查詢中點
                i = mid;
                while (strs[i] == null && --i >= left)
                    ;
                // 如果i已經左移到超過左邊界，或者新找到的strs[i]字典序小於str
                // 說明左半部不會有答案，左邊界直接向右移動到mid+1
                if (i < left || strs[i].compareTo(str) < 0) {
                    left = mid + 1;
                } else {
                    // 否則右邊界直接向左移動i-1，並且看看res需不需要更新
                    res = strs[i].equals(str) ? i : res;
                    right = i - 1;
                }
            }
        }
        return res;
    }

    // 最快的方法是把每個字符串第一次出現的位置保存在哈希表里，然後進行查詢就行
    public static int getIndexByMap(String[] strs, String str) {
        if (str == null || str.length() == 0 || strs == null) return -1;
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            if (!map.containsKey(strs[i])) map.put(strs[i], i);
        }
        return map.getOrDefault(str, -1);
    }

    public static void main(String[] args) {
        String[] strs = new String[]{null, "a", null, "a", null, "b", null,
                null, null, "b", null, "c", null, "c", null, null, "d", null,
                null, null, null, null, "d", null, "e", null, null, "e", null,
                null, null, "f", null, "f", null};
        String str1 = "a";
        System.out.println(getIndex(strs, str1));
        String str2 = "b";
        System.out.println(getIndex(strs, str2));
        String str3 = "c";
        System.out.println(getIndex(strs, str3));
        String str4 = "d";
        System.out.println(getIndex(strs, str4));
        String str5 = "e";
        System.out.println(getIndex(strs, str5));
        String str6 = "f";
        System.out.println(getIndex(strs, str6));

    }
}
