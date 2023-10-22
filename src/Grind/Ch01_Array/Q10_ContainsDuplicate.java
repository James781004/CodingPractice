package Grind.Ch01_Array;

import java.util.HashSet;
import java.util.Set;

public class Q10_ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        if (nums.length < 2) return false;
        Set<Integer> set = new HashSet<>(nums.length);
        for (int x : nums) {
            if (set.contains(x)) return true;
            set.add(x);
        }
        return false;
    }
}
