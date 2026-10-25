package EndlessCheng.GenreMenu.DataStructure.EnumerateRight.Advance;

public class CanArrange {

    // https://leetcode.cn/problems/check-if-array-pairs-are-divisible-by-k/solutions/585191/javadai-ma-tu-wen-xiang-jie-yi-ji-dai-ma-temo/
    public boolean canArrange(int[] arr, int k) {
        int[] mod = new int[k];
        //統計求余之後各余數的個數
        for (int num : arr)
            mod[(num % k + k) % k]++;
        for (int i = 1; i < k / 2; ++i)
            //如果對應的個數不匹配，直接返回false
            if (mod[i] != mod[k - i])
                return false;
        //余數中0的個數必須是偶數
        return mod[0] % 2 == 0;
    }

}
