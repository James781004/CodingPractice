package FuckingAlgorithm.BinaryTree;

import java.util.LinkedList;
import java.util.List;

public class Q05_MergeSort {

    static class Merge {
        // 用於輔助合併有序數組
        private static int[] temp;

        public static void sort(int[] nums) {
            // 先給輔助數組開闢內存空間
            temp = new int[nums.length];
            // 排序整個數組（原地修改）
            sort(nums, 0, nums.length - 1);
        }

        // 定義：將子數組 nums[lo..hi] 進行排序
        private static void sort(int[] nums, int lo, int hi) {
            if (lo == hi) return; // 單個元素不用排序

            // 防止溢出，效果等同於 (hi + lo) / 2
            int mid = lo + (hi - lo) / 2;

            // 先對左半部分數組 nums[lo..mid] 排序
            sort(nums, lo, mid);

            // 再對右半部分數組 nums[mid+1..hi] 排序
            sort(nums, mid + 1, hi);

            // 將兩部分有序數組合併成一個有序數組
            merge(nums, lo, mid, hi);
        }

        // 將 nums[lo..mid] 和 nums[mid+1..hi] 這兩個有序數組合併成一個有序數組
        private static void merge(int[] nums, int lo, int mid, int hi) {
            // 先把 nums[lo..hi] 復制到輔助數組中
            // 以便合併後的結果能夠直接存入 nums
            for (int i = lo; i <= hi; i++) {
                temp[i] = nums[i];
            }

            // 數組雙指針技巧，合併兩個有序數組
            int i = lo, j = mid + 1;
            for (int p = lo; p <= hi; p++) {
                if (i == mid + 1) {
                    // 左半邊數組已全部被合併
                    nums[p] = temp[j++];
                } else if (j == hi + 1) {
                    // 右半邊數組已全部被合併
                    nums[p] = temp[i++];
                } else if (temp[i] > temp[j]) {
                    nums[p] = temp[j++];
                } else {
                    nums[p] = temp[i++];
                }
            }
        }
    }


//    https://leetcode.cn/problems/count-of-smaller-numbers-after-self/
//    315. 計算右側小於當前元素的個數
//    給你一個整數數組 nums ，按要求返回一個新數組 counts 。
//    數組 counts 有該性質： counts[i] 的值是  nums[i] 右側小於 nums[i] 的元素的數量。

    class countSmaller {
        private class Pair {
            int val, id;

            Pair(int val, int id) {
                // 記錄數組的元素值
                this.val = val;
                // 記錄元素在數組中的原始索引
                this.id = id;
            }
        }

        // 歸併排序所用的輔助數組
        private Pair[] temp;
        // 記錄每個元素後面比自己小的元素個數
        private int[] count;

        public List<Integer> countSmaller(int[] nums) {
            int n = nums.length;
            count = new int[n];
            temp = new Pair[n];
            Pair[] arr = new Pair[n];
            // 記錄元素原始的索引位置，以便在 count 數組中更新結果
            for (int i = 0; i < n; i++)
                arr[i] = new Pair(nums[i], i);

            // 執行歸併排序，本題結果被記錄在 count 數組中
            sort(arr, 0, n - 1);

            List<Integer> res = new LinkedList<>();
            for (int c : count) res.add(c);
            return res;
        }

        // 歸併排序
        private void sort(Pair[] arr, int lo, int hi) {
            if (lo == hi) return;
            int mid = lo + (hi - lo) / 2;
            sort(arr, lo, mid);
            sort(arr, mid + 1, hi);
            merge(arr, lo, mid, hi);
        }


        // 合併兩個有序數組
        private void merge(Pair[] arr, int lo, int mid, int hi) {
            for (int i = lo; i <= hi; i++) {
                temp[i] = arr[i];
            }

            int i = lo, j = mid + 1;
            for (int p = lo; p <= hi; p++) {
                if (i == mid + 1) {
                    arr[p] = temp[j++];
                } else if (j == hi + 1) {
                    arr[p] = temp[i++];
                    // 更新 count 數組
                    count[arr[p].id] += j - mid - 1;
                } else if (temp[i].val > temp[j].val) {
                    arr[p] = temp[j++];
                } else {
                    arr[p] = temp[i++];
                    // 更新 count 數組
                    count[arr[p].id] += j - mid - 1;
                }
            }
        }
    }


//    https://leetcode.cn/problems/reverse-pairs/
//    493. 翻轉對
//    給定一個數組 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我們就將 (i, j) 稱作一個重要翻轉對。
//
//    你需要返回給定數組中的重要翻轉對的數量。

    class reversePairs {
        // 記錄「翻轉對」的個數
        private int count = 0;

        private int[] temp;

        public void sort(int[] nums) {
            temp = new int[nums.length];
            sort(nums, 0, nums.length - 1);
        }

        // 歸併排序
        private void sort(int[] nums, int lo, int hi) {
            if (lo == hi) {
                return;
            }
            int mid = lo + (hi - lo) / 2;
            sort(nums, lo, mid);
            sort(nums, mid + 1, hi);
            merge(nums, lo, mid, hi);
        }


        private void merge(int[] nums, int lo, int mid, int hi) {
            for (int i = lo; i <= hi; i++) {
                temp[i] = nums[i];
            }

            // 進行效率優化，維護左閉右開區間 [mid+1, end) 中的元素乘 2 小於 nums[i]
            // 這樣可以保證初始區間 [mid+1, mid+1) 是一個空區間
            int end = mid + 1;
            for (int i = lo; i <= mid; i++) {
                // nums 中的元素可能較大，乘 2 可能溢出，所以轉化成 long
                while (end <= hi && (long) nums[i] > (long) nums[end] * 2) {
                    end++;
                }
                count += end - (mid + 1);
            }

            // 數組雙指針技巧，合併兩個有序數組
            int i = lo, j = mid + 1;
            for (int p = lo; p <= hi; p++) {
                if (i == mid + 1) {
                    nums[p] = temp[j++];
                } else if (j == hi + 1) {
                    nums[p] = temp[i++];
                } else if (temp[i] > temp[j]) {
                    nums[p] = temp[j++];
                } else {
                    nums[p] = temp[i++];
                }
            }
        }
    }

//    https://leetcode.cn/problems/count-of-range-sum/
//    327. 區間和的個數
//    給你一個整數數組 nums 以及兩個整數 lower 和 upper 。求數組中，值位於范圍 [lower, upper] （包含 lower 和 upper）之內的 區間和的個數 。
//
//    區間和 S(i, j) 表示在 nums 中，位置從 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。

    class countRangeSum {
        private int lower, upper;
        private int count = 0;
        private long[] temp;

        public int countRangeSum(int[] nums, int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
            // 構建前綴和數組，注意 int 可能溢出，用 long 存儲
            long[] preSum = new long[nums.length + 1];
            for (int i = 0; i < nums.length; i++) {
                preSum[i + 1] = (long) nums[i] + preSum[i];
            }
            // 對前綴和數組進行歸併排序
            sort(preSum);
            return count;
        }

        public void sort(long[] nums) {
            temp = new long[nums.length];
            sort(nums, 0, nums.length - 1);
        }

        private void sort(long[] nums, int lo, int hi) {
            if (lo == hi) {
                return;
            }
            int mid = lo + (hi - lo) / 2;
            sort(nums, lo, mid);
            sort(nums, mid + 1, hi);
            merge(nums, lo, mid, hi);
        }

        private void merge(long[] nums, int lo, int mid, int hi) {
            for (int i = lo; i <= hi; i++) {
                temp[i] = nums[i];
            }

            // 在合併有序數組之前加點私貨（這段代碼會超時）
            // for (int i = lo; i <= mid; i++) {
            //     for (int j = mid + 1; j <= hi; k++) {
            //         // 尋找符合條件的 nums[j]
            //         long delta = nums[j] - nums[i];
            //         if (delta <= upper && delta >= lower) {
            //             count++;
            //         }
            //     }
            // }

            // 進行效率優化
            // 維護左閉右開區間 [start, end) 中的元素和 nums[i] 的差在 [lower, upper] 中
            int start = mid + 1, end = mid + 1;
            for (int i = lo; i <= mid; i++) {
                // 如果 nums[i] 對應的區間是 [start, end)，
                // 那麼 nums[i+1] 對應的區間一定會整體右移，類似滑動窗口
                while (start <= hi && nums[start] - nums[i] < lower) {
                    start++;
                }
                while (end <= hi && nums[end] - nums[i] <= upper) {
                    end++;
                }
                count += end - start;
            }

            // 數組雙指針技巧，合併兩個有序數組
            int i = lo, j = mid + 1;
            for (int p = lo; p <= hi; p++) {
                if (i == mid + 1) {
                    nums[p] = temp[j++];
                } else if (j == hi + 1) {
                    nums[p] = temp[i++];
                } else if (temp[i] > temp[j]) {
                    nums[p] = temp[j++];
                } else {
                    nums[p] = temp[i++];
                }
            }
        }
    }
}
