package LeetcodeMaster.MonoStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class Q02_NextGreaterElement {
//    496.下一個更大元素 I
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0496.%E4%B8%8B%E4%B8%80%E4%B8%AA%E6%9B%B4%E5%A4%A7%E5%85%83%E7%B4%A0I.md
//
//    給你兩個 沒有重覆元素 的數組 nums1 和 nums2 ，其中nums1 是 nums2 的子集。
//
//    請你找出 nums1 中每個元素在 nums2 中的下一個比其大的值。
//
//    nums1 中數字 x 的下一個更大元素是指 x 在 nums2 中對應位置的右邊的第一個比 x 大的元素。如果不存在，對應位置輸出 -1 。
//
//    示例 1:
//
//    輸入: nums1 = [4,1,2], nums2 = [1,3,4,2].
//    輸出: [-1,3,-1]
//    解釋:
//    對於 num1 中的數字 4 ，你無法在第二個數組中找到下一個更大的數字，因此輸出 -1 。
//    對於 num1 中的數字 1 ，第二個數組中數字1右邊的下一個較大數字是 3 。
//    對於 num1 中的數字 2 ，第二個數組中沒有下一個更大的數字，因此輸出 -1 。
//
//    示例 2:
//    輸入: nums1 = [2,4], nums2 = [1,2,3,4].
//    輸出: [3,-1]
//    解釋:
//    對於 num1 中的數字 2 ，第二個數組中的下一個較大數字是 3 。
//    對於 num1 中的數字 4 ，第二個數組中沒有下一個更大的數字，因此輸出-1 。
//
//    提示：
//
//            1 <= nums1.length <= nums2.length <= 1000
//            0 <= nums1[i], nums2[i] <= 10^4
//    nums1和nums2中所有整數 互不相同
//    nums1 中的所有整數同樣出現在 nums2 中


    // 棧頭到棧底的順序，要從小到大，也就是保持棧里的元素為遞增順序。
    // 只要保持遞增，才能找到右邊第一個比自己大的元素。
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> temp = new Stack<>();
        int[] res = new int[nums1.length];
        Arrays.fill(res, -1);
        HashMap<Integer, Integer> hashMap = new HashMap<>();

        //  nums1中的下標先放入map，之後比對用
        for (int i = 0; i < nums1.length; i++) {
            hashMap.put(nums1[i], i);  // key:下標元素，value：下標
        }
        temp.add(0);

        // 接下來就要分析如下三種情況:
        //
        // 情況一：當前遍歷的元素T[i]小於棧頂元素T[st.top()]的情況
        // 此時滿足遞增棧（棧頭到棧底的順序），所以直接入棧。
        //
        // 情況二：當前遍歷的元素T[i]等於棧頂元素T[st.top()]的情況
        // 如果相等的話，依然直接入棧，因為我們要求的是右邊第一個比自己大的元素，而不是大於等於！
        //
        // 情況三：當前遍歷的元素T[i]大於棧頂元素T[st.top()]的情況
        // 此時如果入棧就不滿足遞增棧了，這也是找到右邊第一個比自己大的元素的時候。
        for (int i = 1; i < nums2.length; i++) {
            if (nums2[i] <= nums2[temp.peek()]) { // 情況一情況二(這個部份其實可省略)
                temp.add(i);
            } else {
                while (!temp.isEmpty() && nums2[i] > nums2[temp.peek()]) { // 情況三(這個部份其實已經排除掉情況一情況二)
                    if (hashMap.containsKey(nums2[temp.peek()])) {  // 看map裡面是否存在這個當前nums2棧頂元素
                        Integer index = hashMap.get(nums2[temp.peek()]);  // 根據map找到nums2棧頂元素 在 nums1中的下標位置
                        res[index] = nums2[i]; // 在res的下標位置上賦值nums2[i]
                    }
                    temp.pop(); // 排出nums2棧頂元素，直到nums2棧頂值大於或等於當前遍歷元素nums2[i]為止
                }
                temp.add(i);
            }
        }
        return res;
    }
}
