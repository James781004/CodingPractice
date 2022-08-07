package TeacherZuoCodingInterviewGuide.ch08;

public class Q26_MaxGap {
    //    CD40 數組排序之後相鄰數的最大差值
//    描述
//    給定一個整形數組arr，返回排序後相鄰兩數的最大差值
//    arr = [9, 3, 1, 10]。如果排序，結果為[1, 3, 9, 10]，9和3的差為最大差值，故返回6。
//    arr = [5, 5, 5, 5]。返回0。
//            [要求]
//    時間覆雜度為O(n)，空間覆雜度為O(n)

    public static int maxGap(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        // 先找出最小最大值
        int len = nums.length;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }
        if (min == max) {
            return 0;
        }

        // 將nums裡面元素按照桶號放進桶，並且紀錄各桶最小最大值
        // 所謂桶號代表的是一個固定區間，按區間分組就是桶排序
        boolean[] hasNum = new boolean[len + 1];
        int[] maxs = new int[len + 1];
        int[] mins = new int[len + 1];
        int bid = 0;
        for (int i = 0; i < len; i++) {
            bid = bucket(nums[i], len, min, max); // 算出桶號
            mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
            maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
            hasNum[bid] = true;
        }

        // 根據題意：排序後相鄰兩數的最大差值
        // 最大差值只有可能來自某個非空桶的最小值減去前一個非空桶的最大值
        // 所以這邊遍歷數組求最大值mins[i] - lastMax
        // lastMax初始值先設定為maxs[0]，然後從i = 1開始遍歷
        int res = 0;
        int lastMax = maxs[0];
        int i = 1;
        for (; i <= len; i++) {
            if (hasNum[i]) {  // 找到第一個不為空的桶
                res = Math.max(res, mins[i] - lastMax);
                lastMax = maxs[i];
            }
        }
        return res;

    }

    // 返回一個位置，代表應該把val放入幾號桶中
    public static int bucket(long num, long len, long min, long max) {
        return (int) ((num - min) * len / (max - min));
    }

    public static void main(String[] args) {
        int[] arr = {11, 10, 9, 3, 1, 12};
        System.out.println(maxGap(arr));

    }
}
