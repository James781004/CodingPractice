package WeeklyContest;

import java.util.TreeMap;

public class Week_391 {
    // https://leetcode.cn/problems/harshad-number/solutions/2716854/bu-shi-yong-zi-fu-chuan-o1-kong-jian-xie-0kez/
    public int sumOfTheDigitsOfHarshadNumber(int x) {
        int s = 0;
        for (int v = x; v > 0; v /= 10) {
            s += v % 10;
        }
        return x % s > 0 ? -1 : s;
    }


    // https://leetcode.cn/problems/water-bottles-ii/solutions/2716852/100235-huan-shui-wen-ti-ii-zhi-jie-mo-ni-btdq/
    public int maxBottlesDrunk(int numBottles, int numExchange) {
        // 初始化已經喝掉的瓶數和空瓶數
        int totalDrunk = 0, emptyBottles = 0;

        // 當還可以交換或者有剩余酒瓶時繼續循環
        while (true) {
            totalDrunk += numBottles; // 增加已喝的瓶數並增加空瓶數
            emptyBottles += numBottles;
            numBottles = 0;// 重置當前擁有的酒瓶數為0，因為這些都已經計入了emptyBottles

            // 如果空瓶數足夠交換新的酒瓶
            if (emptyBottles >= numExchange) {
                emptyBottles -= numExchange; // 減去用於交換的空瓶數，獲得一個新的酒瓶
                numBottles++;
                numExchange++; // 每次交換後，所需的空瓶數增加
            } else {
                break;
            }
        }

        return totalDrunk;
    }


    // https://leetcode.cn/problems/count-alternating-subarrays/solutions/2716829/yun-xing-shi-jian-3mslei-jia-zi-shu-zu-b-fzk5/
    public long countAlternatingSubarrays(int[] nums) {
        long n = nums.length;
        long count = 1; // 初始為1，因為每個單獨的元素也是交替子數組
        long result = 1; // 最終結果

        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) {
                count++;
            } else {
                count = 1;
            }
            result += count;
        }

        return result;
    }


    // https://leetcode.cn/problems/minimize-manhattan-distances/solutions/2716755/tu-jie-man-ha-dun-ju-chi-heng-deng-shi-b-op84/
    public int minimumDistance(int[][] points) {
        TreeMap<Integer, Integer> xs = new TreeMap<>();
        TreeMap<Integer, Integer> ys = new TreeMap<>();
        for (int[] p : points) {
            xs.merge(p[0] + p[1], 1, Integer::sum);
            ys.merge(p[1] - p[0], 1, Integer::sum);
        }
        int ans = Integer.MAX_VALUE;
        for (int[] p : points) {
            int x = p[0] + p[1], y = p[1] - p[0];
            if (xs.get(x) == 1) xs.remove(x);
            else xs.merge(x, -1, Integer::sum);
            if (ys.get(y) == 1) ys.remove(y);
            else ys.merge(y, -1, Integer::sum);
            ans = Math.min(ans, Math.max(xs.lastKey() - xs.firstKey(), ys.lastKey() - ys.firstKey()));
            xs.merge(x, 1, Integer::sum);
            ys.merge(y, 1, Integer::sum);
        }
        return ans;
    }

}


