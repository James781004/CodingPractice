package EndlessCheng.Basic.MonoStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class secondGreaterElement {

    // https://leetcode.cn/problems/next-greater-element-iv/solutions/1935877/by-endlesscheng-q6t5/
    public int[] secondGreaterElement(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        List<Integer> s = new ArrayList<>();
        List<Integer> t = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            while (!t.isEmpty() && nums[t.get(t.size() - 1)] < x) {
                ans[t.get(t.size() - 1)] = x; // t 棧頂的下下個更大元素是 x
                t.remove(t.size() - 1);
            }
            int j = s.size();
            while (j > 0 && nums[s.get(j - 1)] < x) {
                j--; // s 棧頂的下一個更大元素是 x
            }
            List<Integer> popped = s.subList(j, s.size());
            t.addAll(popped); // 把從 s 彈出的這一整段元素加到 t
            popped.clear(); // 彈出一整段元素
            s.add(i); // 當前元素（的下標）加到 s 棧頂
        }
        return ans;
    }


}
