package LeetcodeMaster.StackAndQueue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public class Q06_SlidingWindowMax {
//    239. 滑動窗口最大值
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0239.%E6%BB%91%E5%8A%A8%E7%AA%97%E5%8F%A3%E6%9C%80%E5%A4%A7%E5%80%BC.md
//
//    給定一個數組 nums，有一個大小為 k 的滑動窗口從數組的最左側移動到數組的最右側。
//    你只可以看到在滑動窗口內的 k 個數字。滑動窗口每次只向右移動一位。
//    返回滑動窗口中的最大值。
//
//    進階：
//
//    你能在線性時間覆雜度內解決此題嗎？


    // 解法一
    // 自定義數組
    class MyQueue {
        Deque<Integer> deque = new LinkedList<>();

        // 彈出元素時，比較當前要彈出的數值是否等於隊列出口的數值，如果相等則彈出
        // 同時判斷隊列當前是否為空
        void poll(int val) {
            if (!deque.isEmpty() && val == deque.peek()) {
                deque.poll();
            }
        }

        // 添加元素時，如果要添加的元素大於入口處的元素，就將入口元素彈出
        // 保證隊列元素單調遞減
        // 比如此時隊列元素3,1，2將要入隊，比1大，所以1彈出，此時隊列：3,2
        void add(int val) {
            if (!deque.isEmpty() && val > deque.peek()) {
                deque.removeLast();
            }
            deque.add(val);
        }

        // 隊列隊頂元素始終為最大值
        int peek() {
            return deque.peek();
        }
    }

    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums.length == 1) {
            return nums;
        }

        int len = nums.length - k + 1; // 最大窗口總數
        int[] res = new int[len]; // 存放結果元素的數組
        int num = 0; // res尾部下標位置
        MyQueue myQueue = new MyQueue(); // 自定義隊列

        // 先將前k的元素放入隊列
        for (int i = 0; i < k; i++) {
            myQueue.add(nums[i]);
        }

        res[num++] = myQueue.peek();

        // 從下標k位置開始依次進入隊列
        for (int i = k; i < nums.length; i++) {
            // 先判斷滑動窗口是否移除最前面的元素，移除是為了確保隊列頭節點需要在[i - k + 1, i]範圍內
            // 當前窗口範圍為[i - k + 1, i]，可知前一窗口位範圍為[i - 2k + 1, i - k]
            // 所以如果隊列頭位置的值與nums[i - k]位置相等，就表示它已經不可能出現在當前窗口範圍，必須在加入新元素前先排除
            myQueue.poll(nums[i - k]);
            // 滑動窗口加入最後面的元素
            myQueue.add(nums[i]);
            // 記錄對應的最大值
            res[num++] = myQueue.peek();
        }
        return res;
    }


    // 解法二
    // 利用雙端隊列手動實現單調隊列

    /**
     * 用一個單調隊列來存儲對應的下標，每當窗口滑動的時候，直接取隊列的頭部指針對應的值放入結果集即可
     * 單調隊列類似 （tail -->） 3 --> 2 --> 1 --> 0 (--> head) (右邊為頭結點，元素存的是下標)
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        ArrayDeque<Integer> deque = new ArrayDeque<>(); // 用來保存nums下標位置
        int n = nums.length;
        int[] res = new int[n - k + 1];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            // 根據題意，i為nums下標，是要在[i - k + 1, i] 中選到最大值，只需要保證兩點
            // 1.隊列頭節點需要在[i - k + 1, i]範圍內，不符合則要彈出
            while (!deque.isEmpty() && deque.peek() < i - k + 1) {
                deque.poll();
            }

            // 2.既然是單調，就要保證每次放進去的數字要比末尾的都大，否則也彈出
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offer(i);

            // 因為單調，當i增長到符合第一個k範圍（窗口形成）的時候，每滑動一步都將隊列頭節點放入結果就行了
            if (i >= k - 1) {
                res[idx++] = nums[deque.peek()];
            }
        }
        return res;
    }

}
