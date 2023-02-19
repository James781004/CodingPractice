package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Q03_SlidingWindow {
    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_1_335
    class MinimumSizeSubarraySum {
        public int minLen(int target, int[] A) {
            int left = 0, N = A.length, res = Integer.MAX_VALUE, sum = 0;
            for (int i = 0; i < N; i++) {
                sum += A[i];
                while (sum >= target) {
                    res = Math.min(res, i - left + 1);  // 計算窗口長度
                    sum -= A[left++];  // 左窗口向右縮減
                }
            }
            return res == Integer.MAX_VALUE ? 0 : res;
        }


        // Binary Search
        public int minLen2(int s, int[] nums) {
            int i = 1, j = nums.length, res = 0;
            while (i <= j) {
                int mid = i + (j - i) / 2;
                if (valid(mid, nums, s)) {
                    j = mid - 1;
                    res = mid;
                } else i = mid + 1;
            }
            return res;
        }

        private boolean valid(int size, int[] nums, int s) {
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                if (i - size >= 0) sum -= nums[i - size];
                if (sum >= s) return true;
            }
            return false;
        }
    }


    // https://docs.google.com/presentation/d/1BFx3vI52lICZBpdfEt1eL1hqrhn98vdKh2Ija42RHCA/edit#slide=id.g109cfd4a88b_1_278
    class LongestSubstringWithAtMostKDistinctCharacters {
        public int maxLen(String s, int k) {
            Map<Character, Integer> map = new HashMap<>();
            int left = 0, res = 0;
            for (int i = 0; i < s.length(); i++) {
                char cur = s.charAt(i);
                map.put(cur, map.getOrDefault(cur, 0) + 1);  // 1. 進：當前元素進入窗口
                while (map.size() > k) {  // 2. 出：窗口長度大於k需要開始縮減，這邊用if也行
                    char c = s.charAt(left);    // 左窗口向右縮減
                    map.put(c, map.get(c) - 1); // 左窗口元素c減少
                    if (map.get(c) == 0) map.remove(c);  // 已經沒有左窗口c，直接從map移除
                    left++;
                }
                res = Math.max(res, i - left + 1);  // 3. 算：計算窗口長度
            }
            return res;
        }


        public int maxLen2(String s, int k) {
            Map<Character, Integer> map = new LinkedHashMap<>();
            int maxLen = 0, j = 0;
            for (int i = 0; i < s.length(); i++) {
                char cur = s.charAt(i);
                map.remove(cur);  // 去掉cur前面一次出現的index
                map.put(cur, i);  // 1. 進：保存當前window每一個character最後一次出現的index(加在map最後方)
                if (map.size() > k) {  // 2. 出：窗口長度大於k需要開始縮減
                    char c = map.keySet().iterator().next();  // 取map最前面元素作為左窗口下標位置
                    int index = map.get(c);
                    j = index + 1;  // 在所有index中最左邊位置(左窗口下標位置)+1才是接下來valid window開始的下標位置
                    map.remove(c);  // 移除無效左窗口
                }
                maxLen = Math.max(maxLen, i - j + 1);  // 3. 算：計算窗口長度
            }
            return maxLen;
        }
    }

}
