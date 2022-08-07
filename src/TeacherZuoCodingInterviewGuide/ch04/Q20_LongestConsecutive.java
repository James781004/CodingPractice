package TeacherZuoCodingInterviewGuide.ch04;

import java.util.HashMap;
import java.util.HashSet;

public class Q20_LongestConsecutive {
    //    題目
//    描述
//    給定無序數組arr，返回其中最長的連續序列的長度(要求值連續，位置可以不連續,例如 3,4,5,6為連續的自然數）
//     數據範圍：1≤n≤10 ，數組中的值滿足 1≤val≤10
//     要求：空間覆雜度 O(n)，時間覆雜度 O(nlogn)


    public static int longestConsecutive(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = 1;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (!map.containsKey(arr[i])) {
                map.put(arr[i], 1);
                if (map.containsKey(arr[i] - 1)) {
                    max = Math.max(max, merge(map, arr[i] - 1, arr[i]));
                }
                if (map.containsKey(arr[i] + 1)) {
                    max = Math.max(max, merge(map, arr[i], arr[i] + 1));
                }
            }
        }
        return max;
    }


    public static int merge(HashMap<Integer, Integer> map, int less, int more) {
        int left = less - map.get(less) + 1; // 找出目前已存在序列的最左位置
        int right = more + map.get(more) - 1; // 找出目前已存在序列的最右位置
        int len = right - left + 1;  // 最右位置和最左位置的距離
        map.put(left, len);
        map.put(right, len);
        return len;
    }

    // 用個哈希表來存儲數組中所有的數，然後遍歷數組，
    // 以每個數為連續數組的最後一個數，通過不斷自減檢查在不在哈希表里來更新連續子序列的長度。
    public static int longestConsecutive2(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        HashSet<Integer> set = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }

        int maxLen = 0;
        for (int num : arr) {
            int curLen = 0;
            if (!set.contains(num + 1)) { // 確保是連續子序列的最後一個數，避免重覆檢查同一個序列
                while (set.contains(num)) { // 往後找尋大到小序列的連續長度
                    num--;
                    curLen++;
                }
            }
            maxLen = Math.max(maxLen, curLen);
        }
        return maxLen;
    }


    public static void main(String[] args) {
        int[] arr = {100, 4, 200, 1, 3, 2};
        System.out.println(longestConsecutive(arr));
        System.out.println(longestConsecutive2(arr));

    }

}
