package FuckingAlgorithm.BinaryTree;

import java.util.PriorityQueue;
import java.util.Random;

public class Q09_QuickSort {

    static class QuickSort {
        public static void sort(int[] nums) {
            // 為了避免出現耗時的極端情況，先隨機打亂
            shuffle(nums);
            // 排序整個數組（原地修改）
            sort(nums, 0, nums.length - 1);
        }

        private static void sort(int[] nums, int lo, int hi) {
            if (lo >= hi) return;

            // 對 nums[lo..hi] 進行切分
            // 使得 nums[lo..p-1] <= nums[p] < nums[p+1..hi]
            int p = partition(nums, lo, hi);

            // 排序p的左右子數組
            sort(nums, lo, p - 1);
            sort(nums, p + 1, hi);
        }

        // 對 nums[lo..hi] 進行切分
        private static int partition(int[] nums, int lo, int hi) {
            int pivot = nums[lo];

            // 關於區間的邊界控制需格外小心，稍有不慎就會出錯
            // 我這裡把 i, j 定義為開區間，同時定義：
            // [lo, i) <= pivot；(j, hi] > pivot
            // 之後都要正確維護這個邊界區間的定義
            int i = lo + 1, j = hi;

            // 當 i > j 時結束循環，以保證區間 [lo, hi] 都被覆蓋
            while (i <= j) {
                while (i < hi && nums[i] <= pivot) {
                    i++;
                    // 此 while 結束時恰好 nums[i] > pivot
                }
                while (j > lo && nums[j] > pivot) {
                    j--;
                    // 此 while 結束時恰好 nums[j] <= pivot
                }
                // 此時 [lo, i) <= pivot && (j, hi] > pivot

                if (i >= j) {
                    break;
                }
                swap(nums, i, j);
            }
            // 將 pivot 放到合適的位置，即 pivot 左邊元素較小，右邊元素較大
            swap(nums, lo, j);
            return j;
        }

        // 洗牌算法，將輸入的數組隨機打亂
        private static void shuffle(int[] nums) {
            Random rand = new Random();
            int n = nums.length;
            for (int i = 0; i < n; i++) {
                // 生成 [i, n - 1] 的隨機數
                int r = i + rand.nextInt(n - i);
                swap(nums, i, r);
            }
        }

        // 原地交換數組中的兩個元素
        private static void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }


//    https://leetcode.cn/problems/sort-an-array/
//    912. 排序數組
//    給你一個整數數組 nums，請你將該數組升序排列。

    public int[] sortArray(int[] nums) {
        QuickSort.sort(nums);  // 歸並排序對數組進行原地排序
        return nums;
    }


//    https://leetcode.cn/problems/kth-largest-element-in-an-array/
//    215. 數組中的第K個最大元素
//    給定整數數組 nums 和整數 k，請返回數組中第 k 個最大的元素。
//
//    請注意，你需要找的是數組排序後的第 k 個最大的元素，而不是第 k 個不同的元素。
//
//    你必須設計並實現時間復雜度為 O(n) 的算法解決此問題。

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();  // 預設小頂堆，堆頂是最小元素
        for (int e : nums) {
            pq.offer(e);
            if (pq.size() > k) {
                pq.poll();  // 堆中元素多於 k 個時，刪除堆頂元素
            }
        }

        // pq 中剩下的是 nums 中 k 個最大元素，
        // 堆頂是最小的那個，即第 k 個最大元素
        return pq.peek();
    }

    public int findKthLargest2(int[] nums, int k) {
        // 首先隨機打亂數組
        shuffle(nums);
        int lo = 0, hi = nums.length - 1;
        // 轉化成「排名第 k 的元素」
        k = nums.length - k;
        while (lo <= hi) {
            // 在 nums[lo..hi] 中選一個分界點
            int p = partition(nums, lo, hi);
            if (p < k) {
                // 第 k 大的元素在 nums[p+1..hi] 中
                lo = p + 1;
            } else if (p > k) {
                // 第 k 大的元素在 nums[lo..p-1] 中
                hi = p - 1;
            } else {
                // 找到第 k 大元素
                return nums[p];
            }
        }
        return -1;


    }

    // 洗牌算法，將輸入的數組隨機打亂
    private void shuffle(int[] nums) {
        Random rand = new Random();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            // 生成 [i, n - 1] 的隨機數
            int r = i + rand.nextInt(n - i);
            swap(nums, i, r);
        }
    }

    // 原地交換數組中的兩個元素
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // 對 nums[lo..hi] 進行切分
    private int partition(int[] nums, int lo, int hi) {
        int pivot = nums[lo];

        // 關於區間的邊界控制需格外小心，稍有不慎就會出錯
        // 我這裡把 i, j 定義為開區間，同時定義：
        // [lo, i) <= pivot；(j, hi] > pivot
        // 之後都要正確維護這個邊界區間的定義
        int i = lo + 1, j = hi;

        // 當 i > j 時結束循環，以保證區間 [lo, hi] 都被覆蓋
        while (i <= j) {
            while (i < hi && nums[i] <= pivot) {
                i++;
                // 此 while 結束時恰好 nums[i] > pivot
            }
            while (j > lo && nums[j] > pivot) {
                j--;
                // 此 while 結束時恰好 nums[j] <= pivot
            }
            // 此時 [lo, i) <= pivot && (j, hi] > pivot

            if (i >= j) {
                break;
            }
            swap(nums, i, j);
        }
        // 將 pivot 放到合適的位置，即 pivot 左邊元素較小，右邊元素較大
        swap(nums, lo, j);
        return j;
    }


    // 荷蘭國旗問題
    public void arrPartition(int[] arr, int pivot) {
        int small = -1; // 小於區域的尾
        int big = arr.length;  // 大於區域的頭
        int index = 0;
        while (index != big) {
            if (arr[index] < pivot) {
                swap(arr, ++small, index++); // 把當前數追加在小於pivot區域的後面，當前位置處理完成，所以指針向後走
            } else if (arr[index] == pivot) {
                index++;  // 當前數與pivot相等，指針向後走
            } else {
                swap(arr, --big, index); // 把當前數插入到大於pivot區域的前面，但當前位置還沒確定是正解，所以指針不動
            }
        }
    }


}
