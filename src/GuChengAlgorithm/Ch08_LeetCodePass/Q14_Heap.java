package GuChengAlgorithm.Ch08_LeetCodePass;

public class Q14_Heap {
    // https://docs.google.com/presentation/d/1pPm64mrcOUrfHiCfg-eHXigMfpAbum3pAk3O7XdbVZQ/edit#slide=id.g10a23b659f0_2_6
    class Heapify {
        public void heapify(int[] nums) {
            for (int i = nums.length / 2 - 1; i >= 0; i--) {
                siftDown(nums, i);
            }
        }

        // recursive
        private void siftDown(int[] nums, int cur) {
            int min = cur;
            int left = 2 * cur + 1;
            int right = 2 * cur + 2;
            if (left < nums.length && nums[left] < nums[min]) min = left;
            if (right < nums.length && nums[right] < nums[right]) min = right;

            if (cur != min) {
                swap(nums, cur, min);
                siftDown(nums, min);
            }
        }


        // iterative
        private void siftDown2(int[] nums, int cur) {
            int min = cur;
            while (true) {
                int left = 2 * cur + 1;
                int right = 2 * cur + 2;
                if (left < nums.length && nums[left] < nums[min]) min = left;
                if (right < nums.length && nums[right] < nums[right]) min = right;
                if (cur == min) break;
                swap(nums, cur, min);
                cur = min;
            }
        }
    }


    // https://docs.google.com/presentation/d/1pPm64mrcOUrfHiCfg-eHXigMfpAbum3pAk3O7XdbVZQ/edit#slide=id.g10a23b659f0_2_27
    class MyPQ {
        int[] q = new int[100];
        int size;

        public void offer(int num) {
            q[size] = num;
            siftUp(size++);
        }

        private void siftUp(int cur) {
            if (cur != 0) {
                int parent = (cur - 1) / 2;
                if (q[parent] > q[cur]) {
                    swap(q, parent, cur);
                    siftUp(parent);
                }
            }
        }

        public int poll() {
            int val = q[0];
            q[0] = q[size - 1];
            size--;
            siftDown(0);
            return val;
        }

        private void siftDown(int cur) {
            int min = cur;
            int left = 2 * cur + 1;
            int right = 2 * cur + 2;
            if (left < size && q[left] < q[min]) min = left;
            if (right < size && q[right] < q[right]) min = right;

            if (cur != min) {
                swap(q, cur, min);
                siftDown(min);
            }
        }

        public int peek() {
            return q[0];
        }
    }


    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
