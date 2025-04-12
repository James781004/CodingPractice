package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.Stack;

public class Find132pattern {

    // https://leetcode.cn/problems/132-pattern/solutions/677328/dan-diao-zhan-by-cyingenohalt-xbyl/
    public boolean find132pattern(int[] nums) {
        int len = nums.length;
        if (len < 3) return false;
        Stack<Integer> st = new Stack<>();
        int K = -1;

        // 假設當前元素 nums[J],要找在 J 之後比 nums[J] 小的數 nums[K],和在 J 之前比 nums[K] 小的數 nums[I]
        // 我們可以建立棧從後往前存儲數組的元素（或下標），在棧中找nums[K]，再繼續向前掃描找 nums[I]
        // 棧中要能夠保存比nums[J]下標更大並且值更小的元素，聯想到去維護一個單調棧
        // 從後往前掃描的過程中，一旦當前元素 nums[I] 比棧頂大，這個 nums[I] 或許就是我們要找的 nums[J]
        // 彈出棧中所有的比它小的元素，這些元素就是符合條件的 nums[K],最後一個彈出的就是符合條件的最大 nums[K]
        // 如果這個 num[K] 比繼續往前掃描到的 nums[I] 要大，則符合條件
        for (int I = len - 1; I >= 0; I--) {
            if (K > -1 && nums[K] > nums[I]) return true;
            while (!st.isEmpty() && nums[st.peek()] < nums[I]) {
                K = st.pop();
            }
            st.push(I);
        }
        return false;
    }


}
