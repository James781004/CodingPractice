package EndlessCheng.GenreMenu.Backtracking.Backtracking.MeetMiddle;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CheckEqualPartitions {

    // https://leetcode.cn/problems/partition-array-into-two-equal-product-subsets/solutions/3690735/er-jin-zhi-mei-ju-pythonjavacgo-by-endle-w78j/
    public boolean checkEqualPartitions(int[] nums, long target) {
        BigInteger prodAll = Arrays.stream(nums)
                .mapToObj(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);
        if (!prodAll.equals(BigInteger.valueOf(target).pow(2))) {
            return false;
        }

        int m = nums.length / 2;
        int[] left = Arrays.copyOfRange(nums, 0, m);
        int[] right = Arrays.copyOfRange(nums, m, nums.length);
        Set<Pair> set1 = calc(left, target);
        Set<Pair> set2 = calc(right, target);

        for (Pair p : set1) {
            if (set2.contains(p)) {
                return true;
            }
        }
        return false;
    }

    private record Pair(long a, long b) {
    }

    private Set<Pair> calc(int[] nums, long target) {
        Set<Pair> st = new HashSet<>();
        dfs(0, 1, 1, nums, target, st);
        return st;
    }

    private void dfs(int i, long a, long b, int[] nums, long target, Set<Pair> st) {
        if (a > target || b > target) {
            return;
        }
        if (i == nums.length) {
            long g = gcd(a, b);
            st.add(new Pair(a / g, b / g)); // 最簡分數
            return;
        }
        dfs(i + 1, a * nums[i], b, nums, target, st);
        dfs(i + 1, a, b * nums[i], nums, target, st);
    }

    private long gcd(long a, long b) {
        while (a != 0) {
            long tmp = a;
            a = b % a;
            b = tmp;
        }
        return b;
    }


}
