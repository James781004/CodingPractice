package EndlessCheng.GenreMenu.Backtracking.LinkedList.Pointers;

public class CircularArrayLoop {

    // https://leetcode.cn/problems/circular-array-loop/solutions/920839/tong-ge-lai-shua-ti-la-yi-ti-san-jie-shu-jfyn/
    public boolean circularArrayLoop(int[] nums) {
        // 2,-1,1,2,2
        // 0, 1,2,3,4
        // 0,2,3構成循環
        // 雙指針法：從每一個節點開始出發，看快慢指針是否能相遇，相遇的話就構成了環
        for (int i = 0; i < nums.length; i++) {
            int slow = i;
            int fast = next(nums, i);

            // 快慢指針方向是否一致 (同正或同負相乘結果大於0)
            // 快指針每次走兩步，所以，要判斷慢指針與快指針的下一次是否方向也一致
            while (nums[slow] * nums[fast] > 0 && nums[slow] * nums[next(nums, fast)] > 0) {
                if (slow == fast) {
                    if (slow != next(nums, slow)) {
                        return true;
                    } else {
                        break;
                    }
                }
                slow = next(nums, slow);
                fast = next(nums, next(nums, fast));
            }

            // 到這裡表示從i出發無法構成環，把數組對應下標位置的值改為0
            // 方向相同的才需要標記，不要把反方向的標記了
            int x = i;
            while (nums[x] * nums[next(nums, x)] > 0) {
                int tmp = next(nums, x);
                nums[x] = 0;
                x = tmp;
            }
        }

        return false;
    }

    private int next(int[] nums, int i) {
        int n = nums.length;
        // 考慮負值的情況，+n是把它轉正
        // 如果是正值的話，+n就超過n了，所以最後再%n一下
        // 這裡把正負情況考慮在一起了，當然也可以分開寫
        // 正值：(i + nums[i]) % n
        // 負值：(i + nums[i]) % n + n
        return ((i + nums[i]) % n + n) % n;
    }

}
