package LeetcodeMaster.Greedy;

public class Q09_GiveCandy {
//    135. 分發糖果
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0135.%E5%88%86%E5%8F%91%E7%B3%96%E6%9E%9C.md
//
//    老師想給孩子們分發糖果，有 N 個孩子站成了一條直線，老師會根據每個孩子的表現，預先給他們評分。
//
//    你需要按照以下要求，幫助老師給這些孩子分發糖果：
//
//    每個孩子至少分配到 1 個糖果。
//    相鄰的孩子中，評分高的孩子必須獲得更多的糖果。
//    那麼這樣下來，老師至少需要準備多少顆糖果呢？
//
//    示例 1:
//
//    輸入: [1,0,2]
//    輸出: 5
//    解釋: 你可以分別給這三個孩子分發 2、1、2 顆糖果。
//    示例 2:
//
//    輸入: [1,2,2]
//    輸出: 4
//    解釋: 你可以分別給這三個孩子分發 1、2、1 顆糖果。第三個孩子只得到 1 顆糖果，這已滿足上述兩個條件。


    /**
     * 分兩個階段
     * 1、起點下標1 從左往右，只要 右邊 比 左邊 大，右邊的糖果=左邊 + 1
     * 2、起點下標 ratings.length - 2 從右往左， 只要左邊 比 右邊 大，
     * 此時 左邊的糖果應該 取本身的糖果數（符合比它左邊大） 和 右邊糖果數 + 1 二者的最大值，這樣才符合 它比它左邊的大，也比它右邊大
     */
    public int candy(int[] ratings) {
        int[] candyVec = new int[ratings.length];
        candyVec[0] = 1;

        // 1、起點下標1 從左往右，只要 右邊 比 左邊 大，右邊的糖果=左邊 + 1
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                candyVec[i] = candyVec[i - 1] + 1;
            } else {
                candyVec[i] = 1;
            }
        }

        // 2、起點下標 ratings.length - 2 從右往左， 只要左邊 比 右邊 大，
        // 此時 左邊的糖果應該 取本身的糖果數（符合比它左邊大） 和 右邊糖果數 + 1 二者的最大值，
        // 這樣才符合 它比它左邊的大，也比它右邊大
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                candyVec[i] = Math.max(candyVec[i], candyVec[i + 1] + 1);
            }
        }

        int ans = 0;
        for (int s : candyVec) {
            ans += s;
        }
        return ans;
    }

}
