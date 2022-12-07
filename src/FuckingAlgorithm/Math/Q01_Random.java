package FuckingAlgorithm.Math;

import java.util.Arrays;
import java.util.Random;

public class Q01_Random {
    //    https://leetcode.cn/problems/linked-list-random-node/
//    382. 鏈表隨機節點
//    給你一個單鏈表，隨機選擇鏈表的一個節點，並返回相應的節點值。每個節點 被選中的概率一樣 。
//
//    實現 Solution 類：
//
//    Solution(ListNode head) 使用整數數組初始化對象。
//    int getRandom() 從鏈表中隨機選擇一個節點並返回該節點的值。鏈表中所有節點被選中的概率相等。
    class ListNode {
        int val;
        ListNode next;

        public ListNode(int v) {
            val = v;
        }
    }

    // 蓄水池抽樣
    class ReservoirSolution {
        ListNode head;
        Random r = new Random();

        public ReservoirSolution(ListNode head) {
            this.head = head;
        }

        /* 返回鏈表中一個隨機節點的值 */
        int getRandom() {
            int i = 0, res = 0;
            ListNode p = head;

            // while 循環遍歷鏈表
            while (p != null) {
                i++;
                // 生成一個 [0, i) 之間的整數
                // 這個整數等於 0 的概率就是 1/i
                if (0 == r.nextInt(i)) {
                    res = p.val;
                }
                p = p.next;
            }

            return res;
        }
    }


//    https://leetcode.cn/problems/shuffle-an-array/
//    384. 打亂數組
//    給你一個整數數組 nums ，設計算法來打亂一個沒有重復元素的數組。打亂後，數組的所有排列應該是 等可能 的。
//
//    實現 Solution class:
//
//    Solution(int[] nums) 使用整數數組 nums 初始化對象
//    int[] reset() 重設數組到它的初始狀態並返回
//    int[] shuffle() 返回數組隨機打亂後的結果


    // 分析洗牌算法正確性的准則：產生的結果必須有 n! 種可能。
    // 這個很好解釋，因為一個長度為 n 的數組的全排列就有 n! 種，也就是說打亂結果總共有 n! 種。
    // 算法必須能夠反映這個事實，才是正確的。
    // 有了這個原則再看代碼應該就容易理解了：
    //
    // 對於 nums[0]，我們把它隨機換到了索引 [0, n) 上，共有 n 種可能性；
    // 對於 nums[1]，我們把它隨機換到了索引 [1, n) 上，共有 n - 1 種可能性；
    // 對於 nums[2]，我們把它隨機換到了索引 [2, n) 上，共有 n - 2 種可能性；
    // 以此類推，該算法可以生成 n! 種可能的結果，所以這個算法是正確的，能夠保證隨機性。
    class ShuffleSolution {
        private int[] nums;
        private Random rand = new Random();

        public ShuffleSolution(int[] nums) {
            this.nums = nums;
        }

        public int[] reset() {
            return nums;
        }

        // 洗牌算法
        public int[] shuffle() {
            int n = nums.length;
            int[] copy = Arrays.copyOf(nums, n);
            for (int i = 0; i < n; i++) {
                // 生成一個 [i, n-1] 區間內的隨機數
                int r = i + rand.nextInt(n - i);
                // 交換 nums[i] 和 nums[r]
                swap(copy, i, r);
            }
            return copy;
        }

        private void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }


//    https://leetcode.cn/problems/random-pick-index/
//    398. 隨機數索引
//    給你一個可能含有 重復元素 的整數數組 nums ，請你隨機輸出給定的目標數字 target 的索引。你可以假設給定的數字一定存在於數組中。
//
//    實現 Solution 類：
//
//    Solution(int[] nums) 用數組 nums 初始化對象。
//    int pick(int target) 從 nums 中選出一個滿足 nums[i] == target 的隨機索引 i 。如果存在多個有效的索引，則每個索引的返回概率應當相等。

    class RandomIndexSolution {
        int[] nums;
        Random rand;

        public RandomIndexSolution(int[] nums) {
            this.nums = nums;
            this.rand = new Random();
        }

        // 時間換空間，每次 pick 都遍歷一遍 nums 數組，用水塘抽樣算法從中隨機選出一個索引
        // 當遇到第 i 個元素時，應該有 1/i 的概率選擇該元素，1 - 1/i 的概率保持原有的選擇
        public int pick(int target) {
            int count = 0, res = -1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != target) continue;
                count++;
                if (rand.nextInt(count) != 0) res = i;
            }

            return res;
        }
    }
}
