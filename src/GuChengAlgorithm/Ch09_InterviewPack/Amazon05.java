package GuChengAlgorithm.Ch09_InterviewPack;

import java.util.*;

public class Amazon05 {
    // https://docs.google.com/presentation/d/1H8kSg2JvEQm-8sq9QncDkMfkNhLGTz51xuSFth5pux0/edit#slide=id.g1493d0d0f25_0_62
    class AnalyzePattern {
        public List<String> mostVisitedPattern(String[] usernames, int[] timestamp, String[] websites) {
            Map<String, List<Pair>> map = new HashMap<>();
            int n = usernames.length;
            for (int i = 0; i < n; i++) {  // 每個用戶保存<用戶名, <時間, 網站>>
                map.computeIfAbsent(usernames[i], k -> new ArrayList<Pair>()).add(new Pair(timestamp[i], websites[i]));
            }

            Map<String, Integer> count = new HashMap<>();
            String res = "";
            for (String user : map.keySet()) {
                Set<String> set = new HashSet<>();  // 同一個用戶只增加pattern一次
                List<Pair> list = map.get(user);
                Collections.sort(list, (a, b) -> a.time - b.time);  // sort by time
                for (int i = 0; i < list.size(); i++) {
                    for (int j = i + 1; j < list.size(); j++) {
                        for (int k = j + 1; k < list.size(); k++) {
                            String str = list.get(i).web + " " + list.get(j).web + " " + list.get(k).web;
                            if (set.add(str)) count.put(str, count.getOrDefault(str, 0) + 1);
                            if (res.equals("") || count.get(res) < count.get(str) ||
                                    (count.get(res).equals(count.get(str)) && str.compareTo(res) < 0)) {
                                res = str;
                            }
                        }
                    }
                }
            }
            return Arrays.asList(res.split(" "));
        }

        class Pair {
            int time;
            String web;

            public Pair(int t, String w) {
                time = t;
                web = w;
            }
        }
    }


    // https://docs.google.com/presentation/d/1H8kSg2JvEQm-8sq9QncDkMfkNhLGTz51xuSFth5pux0/edit#slide=id.g1493d0d0f25_0_96
    class NextGreaterElement {
        // 推薦做法，從後往前，三步走，遞減棧
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            Map<Integer, Integer> map = new HashMap<>();
            Stack<Integer> stack = new Stack<>();
            for (int i = nums2.length - 1; i >= 0; i--) {
                while (!stack.isEmpty() && nums2[i] >= stack.peek()) stack.pop();  // 右出，比當前小的都排出
                map.put(nums2[i], stack.isEmpty() ? -1 : stack.peek());  // 取右邊比當前大的，如果沒有就放-1
                stack.push(nums2[i]); // 當前element進stack
            }
            int[] res = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                res[i] = map.get(nums1[i]);
            }
            return res;
        }

        // 第二種做法，鍛煉思維，一直加到比自己大就停，再慢慢吐出來處理之前的數字，也是遞減棧
        public int[] nextGreaterElement2(int[] nums1, int[] nums2) {
            Map<Integer, Integer> map = new HashMap<>();
            Stack<Integer> stack = new Stack<>();
            for (int num : nums2) {
                while (!stack.isEmpty() && stack.peek() < num) map.put(stack.pop(), num);
                stack.push(num);
            }

            for (int i = 0; i < nums1.length; i++) {
                nums1[i] = map.getOrDefault(nums1[i], -1);
            }

            return nums1;
        }
    }


    // https://docs.google.com/presentation/d/1H8kSg2JvEQm-8sq9QncDkMfkNhLGTz51xuSFth5pux0/edit#slide=id.g1493d0d0f25_0_170
    class HouseRob {
        public int rob(int[] num) {
            int[][] dp = new int[num.length + 1][2];  // 0不搶, 1搶
            for (int i = 1; i <= num.length; i++) {
                dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
                dp[i][1] = num[i - 1] + dp[i - 1][0];
            }
            return Math.max(dp[num.length][0], dp[num.length][1]);
        }

        public int rob2(int[] num) {
            if (num.length == 0) return 0;
            int[] dp = new int[num.length + 1];
            dp[0] = 0;
            dp[1] = num[0];
            for (int i = 2; i <= num.length; i++) {
                dp[i] = Math.max(dp[i - 1], dp[i - 2] + num[i - 1]);
            }
            return dp[num.length];
        }


        public int rob3(int[] num) {
            int prevNo = 0;
            int prevYes = 0;
            for (int n : num) {
                int temp = prevNo;
                prevNo = Math.max(prevNo, prevYes);
                prevYes = n + temp;
            }
            return Math.max(prevNo, prevYes);
        }

        int[] memo;

        public int robMemo(int[] num) {
            memo = new int[num.length + 1];
            Arrays.fill(memo, -1);
            return helper(num, num.length - 1);
        }

        private int helper(int[] num, int i) {
            if (i < 0) return 0;
            if (memo[i] >= 0) return memo[i];
            int res = Math.max(helper(num, i - 2) + num[1], helper(num, i - 1));
            return memo[i] = res;
        }
    }


    // https://docs.google.com/presentation/d/1H8kSg2JvEQm-8sq9QncDkMfkNhLGTz51xuSFth5pux0/edit#slide=id.g1493d0d0f25_0_242
    class HouseRobII {
        public int rob(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            if (nums.length == 1) return nums[0];
            int[] n1 = Arrays.copyOfRange(nums, 0, nums.length - 1);
            int[] n2 = Arrays.copyOfRange(nums, 1, nums.length);
            return Math.max(helper(n1), helper(n2));
        }

        private int helper(int[] nums) {
            if (nums == null || nums.length == 0) return 0;
            int pn = 0;
            int py = 0;
            for (int n : nums) {
                int temp = pn;
                pn = Math.max(pn, py);
                py = n + temp;
            }
            return Math.max(pn, py);
        }
    }


    // https://docs.google.com/presentation/d/1H8kSg2JvEQm-8sq9QncDkMfkNhLGTz51xuSFth5pux0/edit#slide=id.g1493d0d0f25_0_290
    class PartitionSubset {
        public boolean canPartition(int[] nums) {
            int totalSum = 0;
            for (int num : nums) totalSum += num;
            if (totalSum % 2 != 0) return false;
            int target = totalSum / 2;
            int n = nums.length;
            boolean[][] dp = new boolean[n + 1][target + 1];
            dp[0][0] = true;
            for (int i = 1; i <= n; i++) {
                int cur = nums[i - 1];
                for (int j = 0; j <= target; j++) {
                    if (j < cur) dp[i][j] = dp[i - 1][j];
                    else dp[i][j] = dp[i - 1][j] || dp[i - 1][j - cur];
                }
            }
            return dp[n][target];
        }


        public boolean canPartition2(int[] nums) {
            int totalSum = 0;
            for (int num : nums) totalSum += num;
            if (totalSum % 2 != 0) return false;
            int target = totalSum / 2;
            int n = nums.length;

            // 01背包
            boolean[] dp = new boolean[target];
            dp[0] = true;
            for (int i = 0; i < n; i++) {
                for (int j = target; j >= 0; j--) {
                    if (j >= nums[i]) dp[j] = dp[j] || dp[j - nums[i]];
                }
            }
            return dp[target];
        }
    }
}
