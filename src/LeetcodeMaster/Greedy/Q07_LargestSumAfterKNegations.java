package LeetcodeMaster.Greedy;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Q07_LargestSumAfterKNegations {
//    1005.K次取反後最大化的數組和
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/1005.K%E6%AC%A1%E5%8F%96%E5%8F%8D%E5%90%8E%E6%9C%80%E5%A4%A7%E5%8C%96%E7%9A%84%E6%95%B0%E7%BB%84%E5%92%8C.md
//
//    給定一個整數數組 A，我們只能用以下方法修改該數組：我們選擇某個索引 i 並將 A[i] 替換為 -A[i]，然後總共重覆這個過程 K 次。（我們可以多次選擇同一個索引 i。）
//
//    以這種方式修改數組後，返回數組可能的最大和。
//
//    示例 1：
//
//    輸入：A = [4,2,3], K = 1
//    輸出：5
//    解釋：選擇索引 (1,) ，然後 A 變為 [4,-2,3]。
//    示例 2：
//
//    輸入：A = [3,-1,0,2], K = 3
//    輸出：6
//    解釋：選擇索引 (1, 2, 2) ，然後 A 變為 [3,1,0,2]。
//    示例 3：
//
//    輸入：A = [2,-3,-1,5,-4], K = 2
//    輸出：13
//    解釋：選擇索引 (1, 4) ，然後 A 變為 [2,3,-1,5,4]。
//    提示：
//
//            1 <= A.length <= 10000
//            1 <= K <= 10000
//            -100 <= A[i] <= 100


    public int largestSumAfterKNegations(int[] nums, int K) {
        // 將數組按照絕對值大小從大到小排序，注意要按照絕對值的大小
        nums = IntStream.of(nums)
                .boxed()
                .sorted((o1, o2) -> Math.abs(o2) - Math.abs(o1))
                .mapToInt(Integer::intValue).toArray();
        int len = nums.length;
        for (int i = 0; i < len; i++) {
            // 從前向後遍歷，遇到負數將其變為正數，同時K--
            if (nums[i] < 0 && K > 0) {
                nums[i] = -nums[i];
                K--;
            }
        }

        // 如果K還大於0，那麽反覆轉變數值最小的元素，將K用完
        if (K % 2 == 1) nums[len - 1] = -nums[len - 1];
        return Arrays.stream(nums).sum();
    }


    public int largestSumAfterKNegations2(int[] A, int K) {
        if (A.length == 1) return K % 2 == 0 ? A[0] : -A[0];
        Arrays.sort(A);
        int sum = 0;
        int idx = 0;
        for (int i = 0; i < K; i++) {
            if (i < A.length - 1 && A[idx] < 0) {
                A[idx] = -A[idx];
                if (A[idx] >= Math.abs(A[idx + 1])) idx++;
                continue;
            }
            A[idx] = -A[idx];
        }

        for (int i = 0; i < A.length; i++) {
            sum += A[i];
        }
        return sum;
    }
}
