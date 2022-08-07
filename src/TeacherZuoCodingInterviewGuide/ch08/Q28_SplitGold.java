package TeacherZuoCodingInterviewGuide.ch08;

import java.util.PriorityQueue;

public class Q28_SplitGold {
//    CD51 分金條的最小花費
//    描述
//    給定一個正數數組arr，arr的累加和代表金條的總長度，arr的每個數代表金條要分成的長度。
//    規定長度為k的金條分成兩塊，費用為k個銅板。
//    返回把金條分出arr中的每個數字需要的最小代價。
//            [要求]
//    時間覆雜度為O(nlogn)，空間覆雜度為O(n)


    public static int getMinSplitCost(int[] arr) {
        if (arr == null || arr.length < 2) return 0;

        // 優先級隊列就是堆結構，而且默認是小根堆結構
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 0; i < arr.length; i++) {
            minHeap.add(arr[i]);
        }

        // 解法就是直接用貪心弄出來
        // 這里使用哈夫曼樹的思想,用一個小根堆。
        // 每次選兩個最小的數，合成一個數，把這個數放回原先的堆。
        // 再次選取兩個最小的數，相加後的數放回堆里，重復這個過程，直到還剩最後一個數
        int ans = 0;
        while (minHeap.size() != 1) {
            int sum = minHeap.poll() + minHeap.poll();
            ans += sum;
            minHeap.add(sum);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {3, 9, 5, 2, 4, 4};
        System.out.println(getMinSplitCost(arr));
    }
}
