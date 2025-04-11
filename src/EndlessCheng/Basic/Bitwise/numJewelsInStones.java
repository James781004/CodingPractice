package EndlessCheng.Basic.Bitwise;

public class numJewelsInStones {

    // https://leetcode.cn/problems/jewels-and-stones/solutions/2356253/ben-ti-zui-you-jie-xian-xing-shi-jian-ch-ddw3/
    public int numJewelsInStones(String jewels, String stones) {
        // 大寫字母二進制的低 6 位是從 000001 開始的（對應大寫字母 A），一直到 011010（對應大寫字母 Z）。
        // 小寫字母二進制的低 6 位是從 100001 開始的（對應小寫字母 a），一直到 111010（對應小寫字母 z），即十進制的 58
        // 所以取字符的的低 6 位，就可以把不同的大小寫字母映射到不同的數字上
        // 用一個 64 位整數 mask 來代替哈希表/布爾數組，壓縮存儲 jewels 中的字母。然後遍歷 stones，判斷每個字母是否在 mask 中
        // 把 jewels 轉換成字符集合 mask
        long mask = 0;
        for (char c : jewels.toCharArray())
            mask |= 1L << (c & 63);
        // 統計有多少 stones[i] 在集合 mask 中
        int ans = 0;
        for (char c : stones.toCharArray())
            ans += mask >> (c & 63) & 1;
        return ans;
    }


}
