package FuckingAlgorithm.Math;

public class Q02_Bit {
//    https://leetcode.cn/problems/number-of-1-bits/
//    191. 位1的個數
//    編寫一個函數，輸入是一個無符號整數（以二進制串的形式），返回其二進制表達式中數字位數為 '1' 的個數（也被稱為漢明重量）。
//
//
//
//    提示：
//
//    請注意，在某些語言（如 Java）中，沒有無符號整數類型。在這種情況下，輸入和輸出都將被指定為有符號整數類型，並且不應影響您的實現，
//    因為無論整數是有符號的還是無符號的，其內部的二進制表示形式都是相同的。
//    在 Java 中，編譯器使用二進制補碼記法來表示有符號整數。因此，在上面的 示例 3 中，輸入表示有符號整數 -3。

    // you need to treat n as an unsigned value
    // n & (n-1) 這個操作是算法中常見的，作用是消除數字 n 的二進制表示中的最後一個 1
    public int hammingWeight(int n) {
        int res = 0;
        while (n != 0) {
            n = n & (n - 1);
            res++;
        }
        return res;
    }


    // https://leetcode.cn/problems/power-of-two/
//    231. 2 的冪
//    給你一個整數 n，請你判斷該整數是否是 2 的冪次方。如果是，返回 true ；否則，返回 false 。
//
//    如果存在一個整數 x 使得 n == 2x ，則認為 n 是 2 的冪次方。


    // 一個數如果是 2 的指數，那麼它的二進制表示一定只含有一個 1。
    // 位運算 n&(n-1) 在算法中挺常見的，作用是消除數字 n 的二進制表示中的最後一個 1，用這個技巧可以判斷 2 的指數。
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;
        return (n & (n - 1)) == 0;
    }


//    https://leetcode.cn/problems/single-number/
//    136. 只出現一次的數字
//    給定一個非空整數數組，除了某個元素只出現一次以外，其余每個元素均出現兩次。找出那個只出現了一次的元素。
//
//    說明：
//
//    你的算法應該具有線性時間復雜度。 你可以不使用額外空間來實現嗎？

    // 一個數和它本身做異或運算結果為 0，即 a ^ a = 0；
    // 一個數和 0 做異或運算的結果為它本身，即 a ^ 0 = a
    // 把所有數字進行異或，成對的數字就會變成 0，
    // 落單的數字和 0 做異或還是它本身，所以最後異或的結果就是只出現一次的元素。
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int n : nums) res ^= n;
        return res;
    }


//    https://leetcode.cn/problems/missing-number/
//    268. 丟失的數字
//    給定一個包含 [0, n] 中 n 個數的數組 nums ，找出 [0, n] 這個范圍內沒有出現在數組中的那個數。

    public int missingNumber(int[] nums) {
        int n = nums.length;
        // 雖然題目給的數據范圍不大，但嚴謹起見，用 long 類型防止整型溢出
        // 求和公式：(首項 + 末項) * 項數 / 2
        long expect = (0 + n) * (n + 1) / 2;
        long sum = 0;
        for (int x : nums) {
            sum += x;
        }
        return (int) (expect - sum);
    }


    // 異或運算的性質：一個數和它本身做異或運算結果為 0，一個數和 0 做異或運算還是它本身。
    // 而且異或運算滿足交換律和結合律，也就是說：2 ^ 3 ^ 2 = 3 ^ (2 ^ 2) = 3 ^ 0 = 3
    public int missingNumberBit(int[] nums) {
        int n = nums.length;
        int res = 0;
        // 先和新補的索引異或一下
        res ^= n;
        // 和其他的元素、索引做異或
        for (int i = 0; i < n; i++) res ^= i ^ nums[i];
        return res;
    }


    public int missingNumberBit2(int[] nums) {
        int n = nums.length;
        int ans = 0;
        for (int i = 0; i <= n; i++) ans ^= i;
        for (int i : nums) ans ^= i;
        return ans;
    }
}
