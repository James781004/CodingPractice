package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Longest;

public class NumberOfSubstrings {

    // https://leetcode.cn/problems/number-of-substrings-containing-all-three-characters/solutions/2805910/hua-dong-chuang-kou-javapythonti-jie-by-0l08h/
    public int numberOfSubstrings(String s) {
        int ans = 0;
        int l = 0;
        int[] cnt = new int[3];
        for (int r = 0; r < s.length(); r++) {
            cnt[s.charAt(r) - 'a']++;
            while (cnt[0] > 0 && cnt[1] > 0 && cnt[2] > 0) {
                cnt[s.charAt(l) - 'a']--;
                l++; //  一直移動左端點，直到不滿足條件
            }
            ans += l; // 以當前 r 為右端點的滿足條件的子字符串個數即為 l，遍歷右端點的時候進行累加即可


        }
        return ans;
    }


}
