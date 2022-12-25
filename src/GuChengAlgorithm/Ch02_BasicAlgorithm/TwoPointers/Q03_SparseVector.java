package GuChengAlgorithm.Ch02_BasicAlgorithm.TwoPointers;

import java.util.ArrayList;
import java.util.List;

public class Q03_SparseVector {
    // https://docs.google.com/presentation/d/1G_A3upxVNg_LHMpS3GeTRVQE9JZli_Kbt5gnvE6WhsU/edit#slide=id.g132d75113e7_0_53
    class SparseVector {
        List<int[]> list;

        public SparseVector(int[] nums) {
            list = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                list.add(new int[]{i, nums[i]});
            }
        }

        public int dotProduct(SparseVector vec) {
            if (vec.list.size() > list.size()) return vec.dotProduct(this);
            int dotProduct = 0;
            for (int i = 0; i < vec.list.size(); i++) {
                int index = vec.list.get(i)[0];
                int value = vec.list.get(i)[1];
                dotProduct += value * binarySearch(index, 0, list.size() - 1);
            }

            return dotProduct;
        }

        private int binarySearch(int index, int left, int right) {
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int midIndex = list.get(mid)[0];
                if (midIndex == index) return list.get(mid)[1];
                if (midIndex < index) left = mid + 1;
                else right = mid - 1;
            }
            return 0;
        }
    }
}
