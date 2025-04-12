package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.FixOrigin;

public class DuplicateZeros {

    // https://leetcode.cn/problems/duplicate-zeros/solutions/1607062/by-ac_oier-zivq/
    public void duplicateZeros(int[] arr) {
        // 整體思路是
        // 1.獲取到數組截取位置的坐標
        // 2.從尾到頭進行雙指針賦值

        // 1.獲取到數組截取位置的坐標 i
        int n = arr.length, i = 0, j = 0;
        while (j < n) {
            if (arr[i] == 0) j++;
            i++;
            j++;
        }

        // 這時候坐標i指向的是截取後一位，需要-1操作，j同理
        i--;
        j--;

        // 2.從尾到頭進行雙指針賦值 i : i->0   j : n->0
        while (i >= 0) {
            // j最後一步可能執行了+2操作,在此確保j的坐標小於n
            if (j < n) arr[j] = arr[i];
            
            // j的移動規則，在此確保j的坐標不小於0
            if (arr[i] == 0 && j >= 0) {
                j--;
                arr[j] = 0;
            }

            // i，j指針往前移動
            i--;
            j--;
        }
    }


}
