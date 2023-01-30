package GuChengAlgorithm.Ch07_ClassicInterviewQuestion;

public class Q04_SellStock {
    // https://docs.google.com/presentation/d/1C9ZUlpgpZ0hOMOTX0Rf9xHdWbywsU1-YTIoiZXMO1hY/edit#slide=id.g8d54549331_0_65
    // 最多一次买卖，K = 1
    // k不影响可以省略，因为当k=0不允许交易手上又是无股票的状态，一定为0。
    // 当我们把T[i-1][0][0]替换为0之后我们发现状态转移方程所有地方的k都是1了，所以k已经被remove掉
    class BestTime {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length == 0) return 0;
            int n = prices.length;
            int[][] dp = new int[n][2];
            dp[0][0] = 0;           // 股市剛開，手頭為空錢為0
            dp[0][1] = -prices[0];  // 股市剛開，買了不能賣

            for (int i = 1; i < n; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);  // 今天沒股票在手 = max(昨天沒股票, 昨天有股票今天賣掉)
                dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);                // 今天有股票在手 =  max(昨天有股票沒賣掉, 昨天沒股票今天買股票)
            }                                                                 // 因為最多只進行1次交易，昨天沒股票狀態一定是0

            return dp[n - 1][0];
        }


        public int maxProfit2(int[] prices) {
            if (prices == null || prices.length == 0) return 0;
            int min = Integer.MAX_VALUE, profit = 0;
            for (int p : prices) {
                min = Math.min(p, min);  // 找最低價格買入
                if (p > min) profit = Math.max(profit, p - min);  // 當前售價高於最低買入價格，比較價差並儲存
            }
            return profit;
        }
    }


    // https://docs.google.com/presentation/d/1C9ZUlpgpZ0hOMOTX0Rf9xHdWbywsU1-YTIoiZXMO1hY/edit#slide=id.g8d54549331_0_78
    class BestTimeII {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length == 0) return 0;
            int n = prices.length;
            int[][] dp = new int[n][2];
            dp[0][0] = 0;           // 股市剛開，手頭為空錢為0
            dp[0][1] = -prices[0];  // 股市剛開，買了不能賣

            for (int i = 1; i < n; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);  // 今天沒股票在手 = max(昨天沒股票, 昨天有股票今天賣掉)
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);  // 今天有股票在手 =  max(昨天有股票沒賣掉, 昨天沒股票今天買股票)
            }

            return dp[n - 1][0];
        }

        public int maxProfit2(int[] prices) {
            if (prices == null || prices.length == 0) return 0;
            int max = 0;
            for (int i = 1; i < prices.length; i++) {
                if (prices[i - 1] < prices[i]) {
                    max += prices[i] - prices[i - 1];  // 每次有價差就賣
                }
            }
            return max;
        }
    }


    // https://docs.google.com/presentation/d/1C9ZUlpgpZ0hOMOTX0Rf9xHdWbywsU1-YTIoiZXMO1hY/edit#slide=id.g8d54549331_0_127
    class BestTimeWithFee {
        public int maxProfit(int[] prices, int fee) {
            if (prices == null || prices.length == 0) return 0;
            int n = prices.length;
            int[][] dp = new int[n][2];
            dp[0][0] = 0;           // 股市剛開，手頭為空錢為0
            dp[0][1] = -prices[0];  // 股市剛開，買了不能賣

            for (int i = 1; i < n; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i] - fee);  // 今天沒股票在手 = max(昨天沒股票, 昨天有股票今天賣掉並計算手續費)
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);  // 今天有股票在手 =  max(昨天有股票沒賣掉, 昨天沒股票今天買股票)
            }

            return dp[n - 1][0];
        }

        public int maxProfit2(int[] prices, int fee) {
            int cash = 0, hold = -prices[0];
            for (int i = 1; i < prices.length; i++) {
                cash = Math.max(cash, hold + prices[i] - fee);
                hold = Math.max(hold, cash - prices[i]);
            }
            return cash;
        }
    }


    // https://docs.google.com/presentation/d/1C9ZUlpgpZ0hOMOTX0Rf9xHdWbywsU1-YTIoiZXMO1hY/edit#slide=id.g8d54549331_0_114
    class BestTimeWithCoolDown {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length == 0) return 0;
            int n = prices.length;
            int[][] dp = new int[n][2];

            dp[0][0] = 0;           // 股市剛開，手頭為空錢為0
            dp[0][1] = -prices[0];  // 股市剛開，買了不能賣
            dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[1]);  // 昨天沒股票, 今天也沒買股票
            dp[1][1] = Math.max(dp[0][1], -prices[1]);   // 今天有股票在手 =  max(昨天有股票沒賣掉, 昨天沒股票今天買股票)

            for (int i = 2; i < n; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);  // 今天沒股票在手 = max(昨天沒股票, 昨天有股票今天賣掉)
                dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);  // 今天有股票在手 =  max(昨天有股票沒賣掉, 前天沒股票今天買股票)
            }                                                                 // Cool Down 1天，所以前天沒股票今天才能買股票

            return dp[n - 1][0];
        }
    }


    // https://docs.google.com/presentation/d/1C9ZUlpgpZ0hOMOTX0Rf9xHdWbywsU1-YTIoiZXMO1hY/edit#slide=id.g8d54549331_0_90
    class BestTimeIII {
        public int maxProfit(int[] prices) {
            if (prices == null || prices.length == 0) return 0;
            int n = prices.length;
            int[][][] dp = new int[n][3][2];
            dp[0][1][0] = 0;
            dp[0][1][1] = -prices[0];
            dp[0][2][0] = 0;
            dp[0][2][1] = -prices[0];  // 初始化時，無論現在是第幾次交易，都只能買第一檔股票

            for (int i = 1; i < n; i++) {
                for (int j = 2; j >= 1; j--) {  // 交易次數，今天的第j次交易必須基於昨天第j-1次交易後的結果，所以這邊大到小遍歷

                    // 第i天沒有股票，j次交易，max(i - 1天也沒有股票，i - 1天有股票然後第i天賣了)，因為我們的賣的行為
                    // 是發生在第i天，所以還是第j次交易
                    dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);

                    // 注意：今天的第j次交易必須基於昨天第j-1次交易後的結果
                    // 第i天有股票，j次交易，max(i - 1天也有股票，i - 1天沒有股票然後第i天買了)，因為我們的賣的行為
                    // 肯定是發生在第i-1天或之前，要不然i - 1天不會沒有股票。
                    // 之前我們定義的是賣的時候作為一次完整交易，所以這裡要取的是dp[i - 1][j - 1][0]
                    dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
                }
            }

            return dp[n - 1][2][0];
        }
    }


    // https://docs.google.com/presentation/d/1C9ZUlpgpZ0hOMOTX0Rf9xHdWbywsU1-YTIoiZXMO1hY/edit#slide=id.g8d54549331_0_102
    class BestTimeIV {
        public int maxProfit(int K, int[] prices) {
            if (prices == null || prices.length == 0) return 0;
            int n = prices.length;
            int[][][] dp = new int[n][K + 1][2];
            for (int i = 0; i < n; i++) {
                for (int j = K; j >= 1; j--) {  // 交易次數
                    if (i == 0) {
                        dp[0][j][0] = 0;
                        dp[0][j][1] = -prices[0];
                        continue;
                    }
                    dp[i][j][0] = Math.max(dp[i - 1][j][0], dp[i - 1][j][1] + prices[i]);
                    dp[i][j][1] = Math.max(dp[i - 1][j][1], dp[i - 1][j - 1][0] - prices[i]);
                }
            }
            return dp[n - 1][K][0];
        }
    }
}
