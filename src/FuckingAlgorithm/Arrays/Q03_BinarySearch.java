package FuckingAlgorithm.Arrays;

import java.util.Random;

public class Q03_BinarySearch {

    //  二分查找框架
    // https://labuladong.github.io/algo/2/20/29/

    // 基本框架:
    // 因為我們初始化 right = nums.length - 1
    // 所以決定了我們的「搜索區間」是 [left, right]
    // 所以決定了 while (left <= right)
    // 同時也決定了 left = mid+1 和 right = mid-1
    // 因為我們只需找到一個 target 的索引即可
    // 所以當 nums[mid] == target 時可以立即返回
    int binarySearch(int[] nums, int target) {
        // 一左一右兩個指針相向而行
        int left = 0, right = nums.length - 1;

        // 初始化 right 的賦值是 nums.length - 1，即最後一個元素的索引，而不是 nums.length。
        // 這二者可能出現在不同功能的二分查找中，區別是：
        // 1. 前者相當於兩端都閉區間 [left, right]，
        // 2. 後者相當於左閉右開區間 [left, right)，因為索引大小為 nums.length 是越界的。
        while (left <= right) {
            int mid = (right + left) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                left = mid + 1;
            else if (nums[mid] > target)
                right = mid - 1;
        }

        return -1;
    }

    // 尋找左側邊界的二分搜索(左閉右開):
    // 因為我們初始化 right = nums.length
    // 所以決定了我們的「搜索區間」是 [left, right)
    // 所以決定了 while (left < right)
    // 同時也決定了 left = mid + 1 和 right = mid
    //
    // 因為我們需找到 target 的最左側索引
    // 所以當 nums[mid] == target 時不要立即返回
    // 而要收緊右側邊界以鎖定左側邊界
    int binarySearchLeftBound(int[] nums, int target) {
        int left = 0;
        int right = nums.length; // 注意

        // 因為 right = nums.length 而不是 nums.length - 1。
        // 因此每次循環的「搜索區間」是 [left, right) 左閉右開。
        // 當 nums[mid] 被檢測之後，下一步應該去 mid 的左側或者右側區間搜索，
        // 即 [left, mid) 或 [mid + 1, right)。
        while (left < right) { // 注意
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid; // 注意
            }
        }

        // 此時 target 比所有數都大，返回 -1
        if (left == nums.length) return -1;

        // 判斷一下 nums[left] 是不是 target
        return nums[left] == target ? left : -1;
//        return left;
    }


    // 尋找左側邊界的二分搜索(左閉右閉)
    int binarySearchLeftBound2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        // 搜索區間為 [left, right]
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                // 搜索區間變為 [mid+1, right]
                left = mid + 1;
            } else if (nums[mid] > target) {
                // 搜索區間變為 [left, mid-1]
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 收縮右側邊界
                right = mid - 1;
            }
        }
        // 判斷 target 是否存在於 nums 中
        // 此時 target 比所有數都大，返回 -1
        if (left == nums.length) return -1;
        // 判斷一下 nums[left] 是不是 target
        return nums[left] == target ? left : -1;
    }


    // 尋找右側邊界的二分查找(左閉右開)
    // 因為我們初始化 right = nums.length
    // 所以決定了我們的「搜索區間」是 [left, right)
    // 所以決定了 while (left < right)
    // 同時也決定了 left = mid + 1 和 right = mid
    //
    // 因為我們需找到 target 的最右側索引
    // 所以當 nums[mid] == target 時不要立即返回
    // 而要收緊左側邊界以鎖定右側邊界
    //
    // 又因為收緊左側邊界時必須 left = mid + 1
    // 所以最後無論返回 left 還是 right，必須減一
    int binarySearchRightBound(int[] nums, int target) {
        int left = 0, right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                left = mid + 1; // 注意
            } else if (nums[mid] < target) {
                left = mid + 1; // 收縮左側邊界
            } else if (nums[mid] > target) {
                right = mid;
            }
        }

        // 判斷 target 是否存在於 nums 中
        // 此時 left - 1 索引越界
        if (left - 1 < 0) return -1;

        // while 循環的終止條件是 left == right，所以 left 和 right 是一樣的，
        // 返回 right - 1 也可以。
        // 至於為什麼要減一，因為上面循環 left 的更新必須是 left = mid + 1，收縮左側邊界
        // 就是說 while 循環結束時，nums[left] 一定不等於 target 了，而 nums[left-1] 可能是 target。
        // 最後判斷一下 nums[left] 是不是 target
        return nums[left - 1] == target ? (left - 1) : -1;
    }

    // 尋找右側邊界的二分查找(左閉右閉)
    int binarySearchRightBound2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else if (nums[mid] == target) {
                // 這裡改成收縮左側邊界即可
                left = mid + 1;
            }
        }

        // 最後改成返回 left - 1
        // 由於 while 的結束條件為 right == left - 1，
        // left - 1改成 right 也沒有問題
        if (left - 1 < 0) return -1;
        return nums[left - 1] == target ? (left - 1) : -1;
    }

//    https://leetcode.cn/problems/binary-search/
//    704. 二分查找
//    給定一個 n 個元素有序的（升序）整型數組 nums 和一個目標值 target ，
//    寫一個函數搜索 nums 中的 target，如果目標值存在返回下標，否則返回 -1。

    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid + 1;
            }
        }

        return -1;
    }

//    https://leetcode.cn/problems/two-sum-ii-input-array-is-sorted/
//    167. 兩數之和 II - 輸入有序數組
//    給你一個下標從 1 開始的整數數組numbers，該數組已按非遞減順序排列，
//    請你從數組中找出滿足相加之和等於目標數target的兩個數。
//    如果設這兩個數分別是 numbers[index1] 和 numbers[index2] ，則 1 <= index1 < index2 <= numbers.length 。
//
//    以長度為 2 的整數數組 [index1, index2] 的形式返回這兩個整數的下標 index1 和 index2。
//
//    你可以假設每個輸入 只對應唯一的答案 ，而且你 不可以 重復使用相同的元素。
//
//    你所設計的解決方案必須只使用常量級的額外空間。


    // 只要數組有序，就應該想到雙指針技巧。
    // 這道題的解法有點類似二分查找，通過調節 left 和 right 就可以調整 sum 的大小：
    int[] twoSum(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int sum = nums[left] + nums[right];
            if (sum == target) {
                // 題目要求的索引是從 1 開始的
                return new int[]{left + 1, right + 1};
            } else if (sum < target) {
                left++; // 讓 sum 大一點
            } else if (sum > target) {
                right--; // 讓 sum 小一點
            }
        }

        return new int[]{-1, -1};
    }

//    https://labuladong.github.io/algo/2/20/30/
//    https://leetcode.cn/problems/random-pick-with-weight/
//    528. 按權重隨機選擇
//    給你一個 下標從 0 開始 的正整數數組 w ，其中 w[i] 代表第 i 個下標的權重。
//
//    請你實現一個函數 pickIndex ，它可以 隨機地 從范圍 [0, w.length - 1] 內（含 0 和 w.length - 1）
//    選出並返回一個下標。選取下標 i 的 概率 為 w[i] / sum(w) 。
//
//    例如，對於 w = [1, 3]，挑選下標 0 的概率為 1 / (1 + 3) = 0.25 （即，25%），
//    而選取下標 1 的概率為 3 / (1 + 3) = 0.75（即，75%）。

    class Selection {
        // 前綴和數組
        private int[] preSum;
        private Random rand = new Random();

        public Selection(int[] w) {
            int n = w.length;
            // 構建前綴和數組，偏移一位留給 preSum[0]
            preSum = new int[n + 1];
            preSum[0] = 0;
            // preSum[i] = sum(w[0..i-1])
            for (int i = 1; i <= n; i++) {
                preSum[i] = preSum[i - 1] + w[i - 1];
            }
        }

        public int pickIndex() {
            int n = preSum.length;
            // 在閉區間 [1, preSum[n - 1]] 中隨機選擇一個數字
            int target = rand.nextInt(preSum[n - 1]) + 1;
            // 獲取 target 在前綴和數組 preSum 中的索引
            // 別忘了前綴和數組 preSum 和原始數組 w 有一位索引偏移
            return left_bound(preSum, target) - 1;
        }

        // 搜索左側邊界的二分搜索
        int left_bound(int[] nums, int target) {
            if (nums.length == 0) return -1;
            int left = 0, right = nums.length;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) {
                    right = mid;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid;
                }
            }
            return left;
        }
    }

//    https://leetcode.cn/problems/capacity-to-ship-packages-within-d-days/
//    1011. 在 D 天內送達包裹的能力
//    傳送帶上的包裹必須在 days 天內從一個港口運送到另一個港口。
//
//    傳送帶上的第 i 個包裹的重量為 weights[i]。每一天，我們都會按給出重量（weights）的順序往傳送帶上裝載包裹。我們裝載的重量不會超過船的最大運載重量。
//
//    返回能在 days 天內將傳送帶上的所有包裹送達的船的最低運載能力。

    public int shipWithinDays(int[] weights, int days) {
        int left = 0, right = 0;
        for (int weight : weights) {
            // 最大運力保證剛好一次性運完所有包裹
            right += weight;
            // 包裹不能拆開運所以至少保證載重能承載任意一個包裹；
            left = Math.max(left, weight);
        }

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (weightSum(weights, mid) > days) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public int weightSum(int[] weights, int speed) {
        int sum = 0;
        int res = 0;
        for (int weight : weights) {
            sum += weight;
            if (sum > speed) {
                res += 1;
                sum = weight;
            }
        }
        // 加上最後一個不超載無法觸發sum > speed 要補1，如果超載則最後一個要單獨運輸一次還要補1；
        return res + 1;
    }


    //    https://leetcode.cn/problems/split-array-largest-sum/
//    410. 分割數組的最大值
//    給定一個非負整數數組 nums 和一個整數 m ，你需要將這個數組分成 m 個非空的連續子數組。
//
//    設計一個算法使得這 m 個子數組各自和的最大值最小。
    public int splitArray(int[] nums, int m) {
        int max = 0;
        int sum = 0;

        // 計算「子數組各自的和的最大值」的上下界
        for (int num : nums) {
            max = Math.max(max, num);
            sum += num;
        }

        // 使用「二分查找」確定一個恰當的「子數組各自的和的最大值」，
        // 使得它對應的「子數組的分割數」恰好等於 m
        int left = max;
        int right = sum;
        while (left < right) {
            int mid = left + (right - left) / 2;

            int splits = split(nums, mid);
            if (splits > m) {
                // 如果分割數太多，說明「子數組各自的和的最大值」太小，此時需要將「子數組各自的和的最大值」調大
                // 下一輪搜索的區間是 [mid + 1, right]
                left = mid + 1;
            } else {
                // 下一輪搜索的區間是上一輪的反面區間 [left, mid]
                right = mid;
            }
        }
        return left;
    }

    /***
     *
     * @param nums 原始數組
     * @param maxIntervalSum 子數組各自的和的最大值
     * @return 滿足不超過「子數組各自的和的最大值」的分割數
     */
    private int split(int[] nums, int maxIntervalSum) {
        // 至少是一個分割
        int splits = 1;
        // 當前區間的和
        int curIntervalSum = 0;
        for (int num : nums) {
            // 嘗試加上當前遍歷的這個數，如果加上去超過了「子數組各自的和的最大值」，就不加這個數，另起爐灶
            if (curIntervalSum + num > maxIntervalSum) {
                curIntervalSum = 0;
                splits++;
            }
            curIntervalSum += num;
        }
        return splits;
    }


//    https://leetcode.cn/problems/koko-eating-bananas/
//    875. 愛吃香蕉的珂珂
//    珂珂喜歡吃香蕉。這裡有 n 堆香蕉，第 i 堆中有 piles[i] 根香蕉。警衛已經離開了，將在 h 小時後回來。
//    珂珂可以決定她吃香蕉的速度 k （單位：根/小時）。每個小時，她將會選擇一堆香蕉，從中吃掉 k 根。
//    如果這堆香蕉少於 k 根，她將吃掉這堆的所有香蕉，然後這一小時內不會再吃更多的香蕉。
//    珂珂喜歡慢慢吃，但仍然想在警衛回來前吃掉所有的香蕉。
//    返回她可以在 h 小時內吃掉所有香蕉的最小速度 k（k 為整數）。

    public int minEatingSpeed(int[] piles, int H) {
        int maxVal = 1;
        for (int pile : piles) {
            maxVal = Math.max(maxVal, pile);
        }

        // 速度最小的時候，耗時最長
        int left = 1;
        // 速度最大的時候，耗時最短
        int right = maxVal;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (calculateSum(piles, mid) > H) {
                // 耗時太多，說明速度太慢了，下一輪搜索區間是 [mid + 1..right]
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    /**
     * 如果返回的小時數嚴格大於 H，就不符合題意
     *
     * @param piles
     * @param speed
     * @return 需要的小時數
     */
    private int calculateSum(int[] piles, int speed) {
        int sum = 0;
        for (int pile : piles) {
            // 上取整可以這樣寫
            sum += (pile + speed - 1) / speed;
            // 應該也可以
//            sum += Math.ceil(pile * 1.0 / speed);
        }
        return sum;
    }
}

