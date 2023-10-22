package Grind.Ch08_HashTable;

import java.util.*;

public class Q02_InsertDeleteGetRandom {
    // https://leetcode.cn/problems/insert-delete-getrandom-o1/solutions/1416876/pythonjavajavascriptgo-by-himymben-nlrg/
    // 哈希表記錄加入和刪除的數，可以O(1)檢查是否出現過
    // 用數組維護所有數，方便隨機取一個數，數組後加入一個數也是O(1)，唯一難點在於刪除。
    // 用哈希表維護每個數加入時的坐標，在要刪除的數不是數組最後一個時，與最後一個交換
    // （因為是不在乎順序的，所以這種交換不影響任何東西），此時要刪除的數成為數組最後一個，可以O(1)刪除

    class RandomizedSet {
        private List<Integer> nums;
        private Map<Integer, Integer> idxMap;
        private Random random;

        public RandomizedSet() {
            nums = new ArrayList<>();
            idxMap = new HashMap<>();
            random = new Random();
        }

        public boolean insert(int val) {
            if (!idxMap.containsKey(val)) {
                idxMap.put(val, nums.size());
                nums.add(val);
                return true;
            }
            return false;
        }

        public boolean remove(int val) {
            if (idxMap.containsKey(val)) {
                int swapVal = nums.get(nums.size() - 1), idx = idxMap.get(val);
                idxMap.put(swapVal, idx);
                nums.set(idx, swapVal);
                idxMap.remove(val);
                nums.remove(nums.size() - 1);
                return true;
            }
            return false;
        }

        public int getRandom() {
            int randomIdx = random.nextInt(nums.size());
            return nums.get(randomIdx);
        }
    }
}
