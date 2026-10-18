package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Basic;

public class NumIdenticalPairs {

    // https://leetcode.cn/problems/number-of-good-pairs/solutions/2974653/mei-ju-you-wei-hu-zuo-pythonjavaccgojsru-7u5v/
    public int numIdenticalPairs(int[] nums) {
        int ans = 0;
        int[] cnt = new int[101];
        for (int x : nums) { // x = nums[j]
            // 此時 cnt[x] 表示之前遍歷過的 x 的個數，加到 ans 中
            // 如果先執行 cnt[x]++，再執行 ans += cnt[x]，就把 i=j 這種情況也統計進來了，算出的答案會偏大
            ans += cnt[x];
            cnt[x]++;
        }
        return ans;
    }

}
