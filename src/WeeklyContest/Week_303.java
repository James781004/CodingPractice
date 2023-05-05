package WeeklyContest;

import java.util.*;

public class Week_303 {
    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2351.First%20Letter%20to%20Appear%20Twice/README.md
    public char repeatedCharacter(String s) {
        int[] cnt = new int[26];
        for (int i = 0; ; i++) {
            char c = s.charAt(i);
            if (++cnt[c - 'a'] == 2) return c;
        }
    }

    // 用一個整數 mask 記錄每個字母是否出現過，其中 mask 的第 i 位表示第 i 個字母是否出現過
    public char repeatedCharacter2(String s) {
        int mask = 0;
        for (int i = 0; ; ++i) {
            char c = s.charAt(i);
            if ((mask >> (c - 'a') & 1) == 1) {
                return c;
            }
            mask |= 1 << (c - 'a');
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2300-2399/2352.Equal%20Row%20and%20Column%20Pairs/README.md
    public int equalPairs(int[][] grid) {
        int n = grid.length;
        int ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int ok = 1;
                for (int k = 0; k < n; k++) {
                    if (grid[i][k] != grid[k][j]) {
                        ok = 0;
                        break;
                    }
                }
                ans += ok;
            }
        }
        return ans;
    }


    // https://leetcode.cn/problems/design-a-food-rating-system/solution/ha-xi-biao-tao-ping-heng-shu-by-endlessc-hzct/
    class FoodRatings {
        class FoodWithRate {
            String food;
            int rate;
            String cuisine; // 保存烹飪方式的映射

            public FoodWithRate(String f, String cuisine, int rate) {
                food = f;
                this.cuisine = cuisine;
                this.rate = rate;
            }
        }

        Map<String, TreeSet<FoodWithRate>> map = new HashMap<>(); // 保存各個烹飪方式中所有food的排序結果
        Map<String, FoodWithRate> foodIndexMap = new HashMap<>(); // 對象索引

        public FoodRatings(String[] foods, String[] cuisines, int[] ratings) {
            int n = foods.length;
            for (int i = 0; i < n; i++) {
                String f = foods[i], c = cuisines[i];
                int rate = ratings[i];
                TreeSet<FoodWithRate> set = map.getOrDefault(c, new TreeSet<>((o1, o2) -> o1.rate != o2.rate ? o2.rate - o1.rate : o1.food.compareTo(o2.food)));
                FoodWithRate foodWithRate = new FoodWithRate(f, c, rate);
                foodIndexMap.put(f, foodWithRate); // 保存對象索引
                set.add(foodWithRate); // 加入新food對象, 由TreeSet比較器來排序
                map.put(c, set);
            }
        }

        public void changeRating(String food, int newRating) {
            FoodWithRate legacyFood = foodIndexMap.get(food);
            TreeSet<FoodWithRate> foodWithRates = map.get(legacyFood.cuisine); // 找烹飪方式
            foodWithRates.remove(legacyFood); // 根據索引刪除老節點
            FoodWithRate foodWithRate = new FoodWithRate(food, legacyFood.cuisine, newRating);
            foodIndexMap.put(food, foodWithRate);
            foodWithRates.add(foodWithRate); // 加入新節點,由TreeSet比較器來排序
        }

        public String highestRated(String c) {
            TreeSet<FoodWithRate> foodWithRates = map.get(c);
            return foodWithRates.first().food; // 找到最大值
        }
    }


    // 食物名字 -> 事物評分
    class Node implements Comparable<Node> {
        public String food;
        public int rate;

        Node(String food, int rate) {
            this.food = food;
            this.rate = rate;
        }

        @Override
        public int compareTo(Node o) {
            if (o.rate != this.rate)
                return o.rate - this.rate;
            return this.food.compareTo(o.food);
        }
    }


    // 3個Map解法
    class FoodRatings2 {
        // 烹飪方式 -> (1-n)食物名字 -> (1-1)食物評分
        List<TreeSet<Node>> cf = new ArrayList<>();
        // 食物 -> 烹飪方式
        Map<String, String> fc = new HashMap<>();
        // 食物 -> 評分
        Map<String, Integer> fr = new HashMap<>();
        // 將cuisines String 轉換為對應的數字編號 因為cuisines是不變的
        Map<String, Integer> cInt = new HashMap<>();

        // 初始化
        // 初始化系統
        private int n;

        public FoodRatings2(String[] foods, String[] cuisines, int[] ratings) {
            n = foods.length;
            int cs = 0;
            int idx;
            for (int i = 0; i < n; i++) {
                fc.put(foods[i], cuisines[i]);
                fr.put(foods[i], ratings[i]);
                if (cInt.get(cuisines[i]) == null) {
                    cInt.put(cuisines[i], cs++);
                    TreeSet<Node> ts = new TreeSet<>();
                    ts.add(new Node(foods[i], ratings[i]));
                    cf.add(ts);
                    continue;
                }
                idx = cInt.get(cuisines[i]);
                cf.get(idx).add(new Node(foods[i], ratings[i]));
            }
        }

        // 修改
        // 修改名字為 food 的食物的評分
        public void changeRating(String food, int newRating) {
            String cuisine = fc.get(food);
            int idx = cInt.get(cuisine);
            int oriRate = fr.get(food);
            cf.get(idx).remove(new Node(food, oriRate));
            cf.get(idx).add(new Node(food, newRating));

            //最後不要忘了將fr 中存儲rate值更新
            fr.put(food, newRating);
        }

        // 返回最大值
        // 返回指定烹飪方式 cuisine 下評分最高的食物的名字
        public String highestRated(String cuisine) {
            int idx = cInt.get(cuisine);
            return cf.get(idx).first().food;
        }
    }


    // https://leetcode.cn/problems/number-of-excellent-pairs/solution/deng-jie-zhuan-huan-pythonjavacgo-by-end-2qzs/
    // https://www.bilibili.com/video/BV14a411U7QZ/?t=11m
    public long countExcellentPairs(int[] nums, int k) {
        Set<Integer> vis = new HashSet<>();
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            if (vis.add(x)) {
                int c = Integer.bitCount(x);
                cnt.put(c, cnt.getOrDefault(c, 0) + 1);
            }
        }

        long ans = 0L;
        for (Map.Entry<Integer, Integer> x : cnt.entrySet()) {
            for (Map.Entry<Integer, Integer> y : cnt.entrySet()) {
                if (x.getKey() + y.getKey() >= k)
                    ans += (long) x.getValue() * y.getValue();
            }
        }

        return ans;
    }


    // 用前綴和（或者後綴和）來優化
    static final int U = 30;

    public long countExcellentPairs2(int[] nums, int k) {
        Set<Integer> vis = new HashSet<>();
        int[] cnt = new int[U];
        for (int x : nums) if (vis.add(x)) ++cnt[Integer.bitCount(x)];
        long ans = 0L;
        int s = 0;
        for (int i = k; i < U; ++i) s += cnt[i];
        for (int cx = 0; cx < U; ++cx) {
            ans += (long) cnt[cx] * s;
            int cy = k - 1 - cx;
            if (0 <= cy && cy < U) s += cnt[cy];
        }
        return ans;
    }

}
