package EndlessCheng.GenreMenu.DP.Enhance.WQS;

public class MaximumProfit {

    // https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-v/solutions/3981282/xiao-bai-si-lu-bi-ji-xi-lie-qian-xi-wqse-nhw1/
    // 輔助類別：用來記錄 (利潤 b, 交易次數 x)
    private static class Pair {
        long b; // 利潤 (profit)
        int x;  // 交易次數 (transaction count)

        Pair(long b, int x) {
            this.b = b;
            this.x = x;
        }
    }

    // 當求極大值時，負無窮大 (表示不可達狀態)
    // 為了防止加減運算溢位，設為 1e16 級別的數字
    private static final long INF = 1L << 50;

    public long maximumProfit(int[] prices, int k) {
        // 1. 先測試當手續費 fee = 0 時的情況
        Pair res = dp(prices, 0);
        // 如果手續費為 0 時的自然交易次數就小於等於 k，說明 k 限制不起作用，直接返回利潤
        if (res.x <= k) {
            return res.b;
        }

        // 2. WQS 二分查找合適的手續費 (fee)
        // 求 prices 總和作為二分上限
        long priceSum = 0;
        for (int p : prices) {
            priceSum += p;
        }

        long left = 0, right = priceSum + 1;
        long ans = 0;

        // 二分搜尋框架 (尋找使交易次數 <= k 的最小手續費)
        while (left + 1 < right) {
            long mid = left + (right - left) / 2;
            Pair currentRes = dp(prices, mid);

            if (currentRes.x <= k) {
                // 如果交易次數 <= k，記錄答案：當前利潤 + (k * 手續費)
                ans = k * mid + currentRes.b;
                right = mid; // 嘗試減小手續費以增加交易次數
            } else {
                left = mid;  // 交易次數 > k，說明手續費太低，需要增加手續費
            }
        }

        return ans;
    }

    // 核心 DP：給定手續費 fee，算出最佳利潤與交易次數
    private Pair dp(int[] prices, long fee) {
        // f0: 空倉（不持有任何股票）
        // f1: 持有買入的股票（多頭狀態）
        // f2: 持有做空的股票（空頭狀態，或另一種持倉形態）
        Pair f0 = new Pair(0, 0);
        Pair f1 = new Pair(-INF, 0);
        Pair f2 = new Pair(-INF, 0);

        for (int p : prices) {
            Pair nf0 = new Pair(f0.b, f0.x);
            Pair nf1 = new Pair(f1.b, f1.x);
            Pair nf2 = new Pair(f2.b, f2.x);

            // 1. 嘗試平倉賣出（完成一次交易，扣除手續費 fee，交易次數 +1）
            // 情況 A：從 f1（多頭）賣出
            Pair best1 = new Pair(f1.b + p - fee, f1.x + 1);
            // 情況 B：從 f2（空頭）平倉
            Pair best2 = new Pair(f2.b - p, f2.x); // 注意：如果是平空倉，此處視狀態轉移規則而定

            // 選擇轉移到 f0 (空倉) 的最優解
            if (less(best1, best2) && less(f0, best2)) {
                nf0 = best2;
            } else if (less(best2, best1) && less(f0, best1)) {
                nf0 = best1;
            }

            // 2. 嘗試建倉/轉移到 f1
            Pair best0ForF1 = new Pair(f0.b - p, f0.x);
            if (less(f1, best0ForF1)) {
                nf1 = best0ForF1;
            }

            // 3. 嘗試建倉/轉移到 f2
            Pair best0ForF2 = new Pair(f0.b + p - fee, f0.x + 1);
            if (less(f2, best0ForF2)) {
                nf2 = best0ForF2;
            }

            f0 = nf0;
            f1 = nf1;
            f2 = nf2;
        }

        return f0;
    }

    // 比較函數：判斷 p 是否「劣於」q
    // 我們追求的是「利潤最大」；若利潤相同，追求「交易次數儘量少」以符合 k 的限制
    private boolean less(Pair p, Pair q) {
        if (p.b < q.b) {
            return true;
        }
        return p.b == q.b && p.x > q.x;
    }

}
