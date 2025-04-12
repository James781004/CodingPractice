package EndlessCheng.GenreMenu.MonoStack.Contribution;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ValidSubarraySize {

    // https://leetcode.cn/problems/subarray-with-elements-greater-than-varying-threshold/solutions/1658410/by-endlesscheng-j6pp/
    int[] fa;

    public int validSubarraySize(int[] nums, int threshold) {
        var n = nums.length;
        fa = new int[n + 1];
        for (var i = 0; i <= n; i++) fa[i] = i;
        var sz = new int[n + 1];

        var ids = IntStream.range(0, n).boxed().toArray(Integer[]::new);
        Arrays.sort(ids, (i, j) -> nums[j] - nums[i]);
        for (var i : ids) {
            var j = find(i + 1);
            fa[i] = j; // 合並 i 和 i+1
            sz[j] += sz[i] + 1;
            if (nums[i] > threshold / sz[j]) return sz[j];
        }
        return -1;
    }

    int find(int x) {
        if (fa[x] != x) fa[x] = find(fa[x]);
        return fa[x];
    }


    public int validSubarraySize2(int[] nums, int threshold) {
        var n = nums.length;
        var left = new int[n]; // left[i] 為左側小於 nums[i] 的最近元素位置（不存在時為 -1）
        var st = new ArrayDeque<Integer>();
        for (var i = 0; i < n; i++) {
            while (!st.isEmpty() && nums[st.peek()] >= nums[i]) st.pop();
            left[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }

        var right = new int[n]; // right[i] 為右側小於 nums[i] 的最近元素位置（不存在時為 n）
        st = new ArrayDeque<>();
        for (var i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && nums[st.peek()] >= nums[i]) st.pop();
            right[i] = st.isEmpty() ? n : st.peek();
            st.push(i);
        }

        for (var i = 0; i < n; ++i) {
            var k = right[i] - left[i] - 1;
            if (nums[i] > threshold / k) return k;
        }
        return -1;
    }


}
