package EndlessCheng.GenreMenu.SlidingWindow.SubArray.Shortest;

public class BeautifulBouquet {

    // https://leetcode.cn/problems/1GxJYY/solutions/1876005/s-by-endlesscheng-m0pg/
    // https://www.bilibili.com/video/BV1rT411P7NA/
    public int beautifulBouquet(int[] flowers, int cnt) {
        int sum = 0;
        int[] cnts = new int[1000001]; // // cnt[i]: 當前窗口內花 i 的數量

        for (int l = 0, r = 0; r < flowers.length; ) {
            cnts[flowers[r]]++;
            while (cnts[flowers[r]] > cnt)
                cnts[flowers[l++]]--;
            sum += ++r - l; //  // 說明[l, r]是美觀的, 為防止重復或漏掉, 每次只統計以r結尾的區間個數
        }

        return sum % 1000000007;
    }

}
