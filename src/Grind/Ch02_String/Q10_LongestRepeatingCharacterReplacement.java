package Grind.Ch02_String;

public class Q10_LongestRepeatingCharacterReplacement {
    // https://leetcode.com/problems/longest-repeating-character-replacement/
    // https://leetcode.cn/problems/longest-repeating-character-replacement/solutions/587389/dong-hua-mo-ni-yao-shi-kan-bu-dong-jiu-g-4rpi/
    public int characterReplacement(String s, int k) {
        int maxsame = 0;
        int len = s.length();
        int left = 0;

        // 模擬 hashmap
        int[] arr = new int[26];
        // 需要在這裡定義變量
        int right = 0;

        for (; right < len; right++) {
            // 得出索引，ASCII碼
            int index = s.charAt(right) - 'A';

            // 數目加1，因為要求出窗口內最多元素
            arr[index]++;

            // 得出最大 maxsame
            maxsame = Math.max(maxsame, arr[index]);

            // 不符合情況時 (移除操作最多可執行 k 次，執行後無法得到連續字串)，縮小窗口
            if (maxsame + k < right - left + 1) {
                arr[s.charAt(left) - 'A']--;
                left++;
            }
        }

        return right - left;
    }
}
