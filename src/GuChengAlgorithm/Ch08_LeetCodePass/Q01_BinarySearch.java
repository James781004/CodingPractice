package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q01_BinarySearch {
    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_0_109
    class VersionControl {
        public int firstBadVersion(int n) {
            int left = 1, right = n;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (!isBadVersion(mid)) left = mid + 1;
                else right = mid - 1;
            }
            return left;  // mid是最後一個good，next left is bad
        }


        public int firstBadVersion2(int n) {
            int left = 1, right = n;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (!isBadVersion(mid)) left = mid + 1;
                else right = mid;
            }
            return left;  // left == right
        }


        public int firstBadVersion3(int n) {
            if (n == 1) return isBadVersion(1) ? 1 : -1;
            int left = 1, right = n;
            while (left + 1 < right) {
                int mid = left + (right - left) / 2;
                if (!isBadVersion(mid)) left = mid;
                else right = mid;
            }
            if (!isBadVersion(left)) return left;
            if (!isBadVersion(right)) return right;
            return -1;
        }


        private boolean isBadVersion(int mid) {
            return mid == 666;
        }
    }


    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_0_159
    class SplitArrayLargestSum {
        public int splitArray(int[] nums, int m) {
            int sum = Arrays.stream(nums).sum();
            int max = Arrays.stream(nums).max().getAsInt();
            return binary(nums, m, sum, max);
        }

        private int binary(int[] nums, int m, int high, int low) {
            int mid = 0;
            while (low <= high) {
                mid = low + (high - low) / 2;
                if (valid(nums, m, mid)) high = mid - 1;
                else low = mid + 1;
            }
            return low;
        }

        private boolean valid(int[] nums, int m, int max) {
            int curSum = 0, count = 1;
            for (int num : nums) {
                curSum += num;
                if (curSum > max) {   // 如果超過了目前猜的數字max，就必須切割
                    curSum = num;  // num作為下一個切割起點元素
                    count++;  // 切割數++
                    if (count > m) return false;  // 最多分成m份，切割數超過m就不符合條件
                }
            }
            return true;
        }
    }


    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_1_5
    class LIS {
        public int lengthOfLIS(int[] nums) {
            if (nums.length == 0) return 0;
            int[] dp = new int[nums.length];
            Arrays.fill(dp, 1);
            int res = 1;
            for (int i = 1; i < nums.length; i++) {
                for (int j = 0; j < i; j++) {
                    if (nums[j] < nums[i]) dp[i] = Math.max(dp[j] + 1, dp[i]);
                    res = Math.max(res, dp[i]);
                }
            }
            return res;
        }


        public int lengthOfLIS2(int[] nums) {
            List<Integer> sub = new ArrayList<>();
            sub.add(nums[0]);
            for (int i = 1; i < nums.length; i++) {
                int num = nums[i];
                if (num > sub.get(sub.size() - 1)) {
                    sub.add(num);
                } else {
                    int j = 0;
                    while (num > sub.get(j)) j++;
                    sub.set(j, num);
                }
            }
            return sub.size();  // 這個構造的長度是答案，但是構造本身未必是最終正確結果
        }


        public int lengthOfLIS3(int[] nums) {
            List<Integer> sub = new ArrayList<>();
            sub.add(nums[0]);
            for (int i = 1; i < nums.length; i++) {
                int num = nums[i];
                if (num > sub.get(sub.size() - 1)) {
                    sub.add(num);
                } else {
                    int j = binarySearch(sub, num);  // binarySearch有機會更快找到位置
                    sub.set(j, num);
                }
            }
            return sub.size();
        }

        private int binarySearch(List<Integer> list, int target) {
            int left = 0, right = list.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (list.get(mid) >= target) right = mid - 1;
                else if (list.get(mid) < target) left = mid + 1;
                else return mid;
            }
            return left;
        }
    }
}
