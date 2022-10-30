package FuckingAlgorithm.StructureDesign;

import java.util.HashMap;
import java.util.Stack;

public class Q05_MonoStack {

    // 單調棧模板
    // 把數組的元素想象成並列站立的人，元素大小想象成人的身高。
    // 這些人面對你站成一列，如何求元素「2」的下一個更大元素呢？
    // 很簡單，如果能夠看到元素「2」，那麼他後面可見的第一個人就是「2」的下一個更大元素，
    // 因為比「2」小的元素身高不夠，都被「2」擋住了，第一個露出來的就是答案。
    int[] nextGreaterElement(int[] nums) {
        int n = nums.length;
        // 存放答案的數組
        int[] res = new int[n];
        Stack<Integer> s = new Stack<>();
        // 倒著往棧裡放
        for (int i = n - 1; i >= 0; i--) {
            // 判定個子高矮
            while (!s.isEmpty() && s.peek() <= nums[i]) {
                // 矮個起開，反正也被擋著了。。。
                s.pop();
            }
            // nums[i] 身後的更大元素
            res[i] = s.isEmpty() ? -1 : s.peek();
            s.push(nums[i]);
        }
        return res;
    }


//    https://leetcode.cn/problems/next-greater-element-i/
//    496. 下一個更大元素 I
//    nums1 中數字 x 的 下一個更大元素 是指 x 在 nums2 中對應位置 右側 的 第一個 比 x 大的元素。
//
//    給你兩個 沒有重復元素 的數組 nums1 和 nums2 ，下標從 0 開始計數，其中nums1 是 nums2 的子集。
//
//    對於每個 0 <= i < nums1.length ，找出滿足 nums1[i] == nums2[j] 的下標 j ，並且在 nums2 確定 nums2[j] 的 下一個更大元素 。
//    如果不存在下一個更大元素，那麼本次查詢的答案是 -1 。
//
//    返回一個長度為 nums1.length 的數組 ans 作為答案，滿足 ans[i] 是如上所述的 下一個更大元素 。

    int[] nextGreaterElement1(int[] nums1, int[] nums2) {
        // 記錄 nums2 中每個元素的下一個更大元素
        int[] greater = nextGreaterElement(nums2);
        // 轉化成映射：元素 x -> x 的下一個最大元素
        HashMap<Integer, Integer> greaterMap = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            greaterMap.put(nums2[i], greater[i]);
        }
        // nums1 是 nums2 的子集，所以根據 greaterMap 可以得到結果
        int[] res = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            res[i] = greaterMap.get(nums1[i]);
        }
        return res;
    }


    //    https://leetcode.cn/problems/daily-temperatures/
//    739. 每日溫度
//    給定一個整數數組 temperatures ，表示每天的溫度，返回一個數組 answer ，其中 answer[i] 是指對於第 i 天，
//    下一個更高溫度出現在幾天後。如果氣溫在這之後都不會升高，請在該位置用 0 來代替。
    int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];

        // 這裡放元素索引，而不是元素
        Stack<Integer> s = new Stack<>();

        /* 單調棧模板 */
        for (int i = n - 1; i >= 0; i--) {
            while (!s.isEmpty() && temperatures[s.peek()] <= temperatures[i]) {
                s.pop();
            }
            // 得到索引間距
            res[i] = s.isEmpty() ? 0 : (s.peek() - i);
            // 將索引入棧，而不是元素
            s.push(i);
        }

        return res;
    }


//    https://leetcode.cn/problems/next-greater-element-ii/
//    503. 下一個更大元素 II
//    給定一個循環數組 nums （ nums[nums.length - 1] 的下一個元素是 nums[0] ），返回 nums 中每個元素的 下一個更大元素 。
//
//    數字 x 的 下一個更大的元素 是按數組遍歷順序，這個數字之後的第一個比它更大的數，
//    這意味著你應該循環地搜索它的下一個更大的數。如果不存在，則輸出 -1 。

    // 處理環形數組
    // 常用套路就是將數組長度翻倍
    int[] nextGreaterElements2(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> s = new Stack<>();

        // 數組長度加倍模擬環形數組
        for (int i = 2 * n - 1; i >= 0; i--) {
            // 索引 i 要求模，其他的和模板一樣
            while (!s.isEmpty() && s.peek() <= nums[i % n]) {
                s.pop();
            }
            res[i % n] = s.isEmpty() ? -1 : s.peek();
            s.push(nums[i % n]);
        }

        return res;
    }


}
