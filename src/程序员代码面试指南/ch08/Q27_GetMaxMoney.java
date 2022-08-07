package 程序员代码面试指南.ch08;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Q27_GetMaxMoney {
//    CD50 做項目的最大收益問題
//    描述
//    給定兩個整數W和K，W代表你擁有的初始資金，K代表你最多可以做K個項目。
//    再給定兩個長度為N的正數數組costs[]和profits[]，代表一共有N個項目，
//    costs[i]和profits[i]分別表示第i號項目的啟動資金與做完後的利潤(注意是利潤，
//    如果一個項目的啟動資金為10，利潤為4，代表該項目最終的收入為14)。
//    你不能並行只能串行地做項目，並且手里擁有的資金大於或等於某個項目的啟動資金時，你才能做這個項目。
//    該如何選擇做項目，能讓你最終的收益最大？返回最後能獲得的最大資金
//[要求]
//    時間覆雜度為O(klogn)，空間覆雜度為O(n)


    public static class Program {
        public int cost; // 項目的花費
        public int profit; // 項目的利潤

        public Program(int cost, int profit) {
            this.cost = cost;
            this.profit = profit;
        }
    }

    // 定義小根堆如何比較大小
    public static class CostMinComp implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }
    }

    // 定義大根堆如何比較大小
    public static class ProfitMaxComp implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }

    public static int getMaxMoney(int W, int K, int[] costs, int[] profits) {
        // 無效參數
        if (W < 1 || K < 0 || costs == null || profits == null || costs.length != profits.length) {
            return W;
        }

        // 項目花費小根堆，花費最少的項目在頂部
        PriorityQueue<Program> costMinHeap = new PriorityQueue<>(new CostMinComp());
        // 項目利潤大根堆，利潤最大的項目在頂部
        PriorityQueue<Program> profitMaxHeap = new PriorityQueue<>(new ProfitMaxComp());
        // 所有項目都進項目花費小根堆
        for (int i = 0; i < costs.length; i++) {
            costMinHeap.add(new Program(costs[i], profits[i]));
        }

        // 依次做K個項目
        for (int i = 1; i <= K; i++) {
            // 當前資金為W，在項目花費小根堆里所有花費小於等於W的項目，都可以考慮
            while (!costMinHeap.isEmpty() && costMinHeap.peek().cost <= W) {
                // 把可以考慮的項目，都放進項目利潤大根堆里
                profitMaxHeap.add(costMinHeap.poll());
            }
            // 如果此時項目利潤大根堆為空，說明可以考慮的項目為空
            // 說明當前資金W已經無法解鎖任何項目了，直接返回W
            if (profitMaxHeap.isEmpty()) {
                return W;
            }
            // 如果還可以做項目，從項目利潤大根堆拿出獲得利潤最多的項目完成
            W += profitMaxHeap.poll().profit;
        }
        return W;
    }
}
