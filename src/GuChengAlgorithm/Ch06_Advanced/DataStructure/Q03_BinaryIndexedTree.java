package GuChengAlgorithm.Ch06_Advanced.DataStructure;

public class Q03_BinaryIndexedTree {
    class BITBasic {
        int[] parent;

        public BITBasic(int N) {
            parent = new int[N];
        }


        public int sum(int x) {
            int sum = 0;
            for (x++; x > 0; x -= (x & -x)) {  // remove last bit
                sum += parent[x];
            }
            return sum;
        }

        public void add(int x, int val) {
            for (x++; x < parent.length; x += (x & -x)) {  // add last set bit
                parent[x] += val;
            }
        }
    }

    // https://docs.google.com/presentation/d/1yyuu0w2jJq9hLiyxAP4GiNZgOSCBb_8Ay5-usuZDi0E/edit#slide=id.gab10267339_0_71
    class NumArray {
        BIT bit;
        int[] nums;

        public NumArray(int[] nums) {
            int n = nums.length;
            this.nums = nums;
            this.bit = new BIT(nums);
        }

        public int sumRange(int i, int j) {
            return bit.sum(j) - bit.sum(i - 1);
        }

        public void update(int i, int val) {
            int diff = val - nums[i];
            nums[i] = val;
            bit.update(i, diff);
        }


        class BIT {
            int[] parent;

            public BIT(int[] input) {
                int N = input.length;
                parent = new int[N];
                for (int i = 0; i < N; i++) {
                    update(i, input[i]);
                }
            }


            public int sum(int x) {
                int sum = 0;
                for (x++; x > 0; x -= (x & -x)) {  // remove last bit
                    sum += parent[x];  // 從x向上找同分支元素(加起來求sum)
                }
                return sum;
            }

            public void add(int x, int val) {
                for (x++; x < parent.length; x += (x & -x)) {  // add last set bit
                    parent[x] += val;  // 從x開始更新同一層所有元素(加上val)
                }
            }

            public void update(int x, int val) {
                for (x++; x < parent.length; x += (x & -x)) parent[x] += val;
            }
        }
    }


    // https://docs.google.com/presentation/d/1yyuu0w2jJq9hLiyxAP4GiNZgOSCBb_8Ay5-usuZDi0E/edit#slide=id.gab10267339_0_78
    class sortArray {
        int MOD = (int) 1e9 + 7;

        public int createSortedArray(int[] instructions) {
            BIT bit = new BIT(1000000);
            int cost = 0;
            for (int num : instructions) {
                int left = bit.sum(num - 1);
                int right = bit.sum(100_000) - bit.sum(num);
                cost = (cost + Math.min(left, right)) % MOD;
                bit.add(num, 1);
            }
            return cost;
        }


        class BIT {
            int[] parent;

            public BIT(int N) {
                parent = new int[N];
            }


            public int sum(int x) {
                int sum = 0;
                for (x++; x > 0; x -= (x & -x)) {  // remove last bit
                    sum += parent[x];
                }
                return sum;
            }

            public void add(int x, int val) {
                for (x++; x < parent.length; x += (x & -x)) {  // add last set bit
                    parent[x] += val;
                }
            }
        }
    }

}
