package GuChengAlgorithm.Ch02_BasicAlgorithm.Greedy;

public class Q04_JumpGame {
    // https://docs.google.com/presentation/d/18M3cuDjvBlaaMjZ2R5a6pK1pDI_ZCDbBmVniyW1PdEE/edit#slide=id.g1c33e7d9828_0_27
    Integer[] memo;

    public boolean canJumpMemo(int[] nums) {
        memo = new Integer[nums.length];
        memo[memo.length - 1] = 1;
        return canJumpFromPosition(0, nums) == 1;
    }

    private int canJumpFromPosition(int pos, int[] nums) {
        if (memo[pos] != null) return memo[pos];
        int furthest = Math.min(pos + nums[pos], nums.length - 1);  // 當下位置能到達最遠位置，注意不能超過終點
        for (int next = pos + 1; next <= furthest; next++) {  // 暴力嘗試這一步所有可能
            if (canJumpFromPosition(next, nums) == 1) return memo[pos] = 1;  // 到達終點用1表示
        }
        return memo[pos] = -1;  // 沒到達終點用-1表示
    }


    public boolean canJump(int[] nums) {
        int max = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (i <= max) {  // 當下位置還沒到達之前確定能到達最遠位置邊界
                max = Math.max(max, i + nums[i]);  // 擴展最遠位置邊界
            }
        }
        return max >= nums.length - 1;
    }
}
