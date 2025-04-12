package EndlessCheng.GenreMenu.SlidingWindow.FlexibleWindow.Shortest;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class SmallestRange {

    // https://leetcode.cn/problems/smallest-range-covering-elements-from-k-lists/solutions/356069/tan-xin-duo-zhi-zhen-you-xian-ji-dui-lie-by-girapa/
    public int[] smallestRange(List<List<Integer>> nums) {
        // 因為每次都要找最小元素，所以維護一個最小堆比較合適
        PriorityQueue<NumGroup> pq = new PriorityQueue<>(new Comparator<NumGroup>() {
            @Override
            public int compare(NumGroup n1, NumGroup n2) {
                return n1.num - n2.num;
            }
        });

        int end = -100001;
        
        // 記錄每個數組當前的指針位置，一開始都指向第0個元素，即每個區間的最小元素
        int[] index = new int[nums.size()];

        // 起始區間
        for (int i = 0; i < nums.size(); i++) {
            if (nums.get(i).get(0) > end) end = nums.get(i).get(0);
            NumGroup num = new NumGroup(nums.get(i).get(0), i);
            pq.offer(num);
        }

        int max = end;
        int start = pq.peek().num;
        int min;
        int len = end - start + 1;

        while (true) {
            // grp為當前最小元素的原數組號
            int grp = pq.poll().grp;
            // 如果當前最小元素已經是原數組最大元素了，則退出
            if (index[grp] + 1 == nums.get(grp).size()) break;

            // 索引++，並將當前最小元素的原數組中的下一個元素壓入優先級隊列
            index[grp]++;
            NumGroup n = new NumGroup(nums.get(grp).get(index[grp]), grp);
            pq.offer(n);
            // 當前最大值
            if (n.num > max) max = n.num;
            min = pq.peek().num;
            // 長度變小
            if (max - min + 1 < len) {
                start = min;
                end = max;
                len = max - min + 1;
            }
        }

        return new int[]{start, end};
    }


    class NumGroup {
        public NumGroup(int num, int grp) {
            this.num = num;
            this.grp = grp;
        }

        int num; // 數值
        int grp; // 組號
    }

}
