package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.*;

public class NextGreaterElement {


    // https://leetcode.cn/problems/next-greater-element-i/solutions/2820648/shi-pin-dan-diao-zhan-de-liang-chong-xie-ri0i/
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int n = nums1.length;
        Map<Integer, Integer> idx = new HashMap<>(n); // 預分配空間
        for (int i = 0; i < n; i++) {
            idx.put(nums1[i], i);
        }
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> st = new ArrayDeque<>();
        for (int i = nums2.length - 1; i >= 0; i--) {
            int x = nums2[i];
            while (!st.isEmpty() && x >= st.peek()) {
                // 由於 x 的出現，棧頂元素永遠不會是左邊元素的「下一個更大元素」
                st.pop();
            }
            if (!st.isEmpty() && idx.containsKey(x)) { // x 在 nums1 中
                ans[idx.get(x)] = st.peek(); // 記錄答案
            }
            st.push(x);
        }
        return ans;
    }


    public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
        int n = nums1.length;
        Map<Integer, Integer> idx = new HashMap<>(n); // 預分配空間
        for (int i = 0; i < n; i++) {
            idx.put(nums1[i], i);
        }
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Deque<Integer> st = new ArrayDeque<>();
        for (int x : nums2) {
            while (!st.isEmpty() && x > st.peek()) {
                // x 是棧頂的下一個更大元素
                // 既然棧頂已經算出答案，彈出
                ans[idx.get(st.pop())] = x; // 記錄答案
            }
            if (idx.containsKey(x)) { // x 在 nums1 中
                st.push(x); // 只需把在 nums1 中的元素入棧
            }
        }
        return ans;
    }


}
