package EndlessCheng.GenreMenu.MonoStack.Basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondGreaterElementIV {

    // https://leetcode.cn/problems/next-greater-element-iv/solutions/1935877/by-endlesscheng-q6t5/
    public int[] secondGreaterElement(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        List<Integer> s = new ArrayList<>(); // 遞減單調棧 s 維護遍歷過的元素，如果當前元素 x 比棧頂大，那麼棧頂的下一個更大元素就是 x，並彈出棧頂
        List<Integer> t = new ArrayList<>(); // 遞減單調棧 t 記錄從 s 中彈出去的元素，如果又找到一個元素 y 比 t 的棧頂大，那麼棧頂的下下個更大元素就是 y，並彈出棧頂
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
            List<Integer> popped = s.subList(j, s.size()); // 此方法指向原 list 中的片段，如果進行修改，原 list也會被影響
            t.addAll(popped); // 把從 s 彈出的這一整段元素加到 t
            popped.clear(); // 彈出一整段元素，此方法會移除原 list 中的 popped 片段
            s.add(i); // 當前元素（的下標）加到 s 棧頂
        }
        return ans;
    }


}
