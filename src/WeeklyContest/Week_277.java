package WeeklyContest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Week_277 {
    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2148.Count%20Elements%20With%20Strictly%20Smaller%20and%20Greater%20Elements/README.md
    public int countElements(int[] nums) {
        int mi = 1000000, mx = -1000000;
        for (int num : nums) {
            mi = Math.min(mi, num);
            mx = Math.max(mx, num);
        }

        int ans = 0;
        for (int num : nums) {
            if (mi < num && num < mx) {
                ++ans;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2149.Rearrange%20Array%20Elements%20by%20Sign/README.md
    public int[] rearrangeArray(int[] nums) {
        int[] ans = new int[nums.length];
        int i = 0, j = 1;  // 雙指針處理正負下標，如果加入新元素就往後跳兩格
        for (int num : nums) {
            if (num > 0) {
                ans[i] = num;
                i += 2;
            } else {
                ans[j] = num;
                j += 2;
            }
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2150.Find%20All%20Lonely%20Numbers%20in%20the%20Array/README.md
    public List<Integer> findLonely(int[] nums) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int num : nums) {
            counter.put(num, counter.getOrDefault(num, 0) + 1);
        }
        List<Integer> ans = new ArrayList<>();
        counter.forEach((k, v) -> {
            if (v == 1 && !counter.containsKey(k - 1) && !counter.containsKey(k + 1)) {
                ans.add(k);
            }
        });
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2100-2199/2151.Maximum%20Good%20People%20Based%20on%20Statements/README.md
    // https://leetcode.cn/problems/maximum-good-people-based-on-statements/solution/er-jin-zhi-mei-ju-by-endlesscheng-ttix/
    public int maximumGood(int[][] statements) {
        int ans = 0;
        for (int mask = 1; mask < 1 << statements.length; ++mask) {
            ans = Math.max(ans, check(mask, statements));
        }
        return ans;
    }

    // 二進制枚舉好人的狀態 mask，由於“好人只說真話”，
    // 我們借此判斷 statements 與 mask 是否存在矛盾，
    // 不存在則獲取 mask 中好人的數量 cnt。
    // 迭代獲取最大的合法 cnt。
    private int check(int mask, int[][] statements) {
        int cnt = 0;
        int n = statements.length;
        for (int i = 0; i < n; ++i) {
            if (((mask >> i) & 1) == 1) {
                for (int j = 0; j < n; ++j) {
                    int v = statements[i][j];
                    if (v < 2 && ((mask >> j) & 1) != v) {
                        return 0;
                    }
                }
                ++cnt;
            }
        }
        return cnt;
    }


    /**
     * 二進制枚舉,,找到好人,,判斷好人說的話與事實是否相符,符合count+1,反之,跳到外層循環
     *
     * @param statements 陳述
     */
    public int maximumGood2(int[][] statements) {
        int res = 0;
        int n = statements.length;
        next:
        for (int i = 1; i < (1 << n); i++) {// 范圍為[0,2ⁿ-1],,這裡(1<<n)的意思是1轉化成二進制,並向左移動n位,,也就是`1*2*2*....*2=2ⁿ`
            int count = 0;// 枚舉的每個二進制數字i中好人的個數
            for (int j = 0; j < n; j++) {
                if (((i >> j) & 1) == 1) { // 找到好人(1 表示好人，0 表示壞人)
                    // 將二進制i有移動j位置,,並且`&` '00000.....1',意思就是取出i中二進制位置第j位的值,,如果為1,那麼就說明這個人是好人,
                    // 只有好人才會發生矛盾,,檢測好人的陳述和實際是否一致
                    for (int k = 0; k < n; k++) {
                        // 當好人陳述k為真,並且二進制右移動k位與陳述結果不一致,,說明發生矛盾
                        if (statements[j][k] < 2 && statements[j][k] != ((i >> k) & 1)) {
                            continue next;
                        }
                    }
                    count++; // 不發生矛盾,好人+1
                }
            }
            res = Math.max(res, count);
        }
        return res;
    }


    // 遞歸枚舉的版本
    int max = 0;

    public int maximumGood3(int[][] statements) {
        max = 0;
        int n = statements.length;
        int[] person = new int[n];
        dfs(0, person, n, statements);
        return max;
    }

    public void dfs(int now, int[] person, int n, int[][] statements) {
        if (now == n) {
            check(person, statements);
            return;
        }
        person[now] = 0;
        dfs(now + 1, person, n, statements);
        person[now] = 1;
        dfs(now + 1, person, n, statements);
    }

    /**
     * person數組：0表示壞人，1表示好人
     * 判斷好人的言論是否相互矛盾
     */
    public void check(int[] person, int[][] statements) {
        int n = person.length, truePerson = 0;
        for (int i = 0; i < n; i++) {
            if (person[i] == 0) {
                continue;
            }
            truePerson++;
            for (int j = 0; j < n; j++) {
                if (statements[i][j] == 0 && person[j] == 1) {
                    return;
                }
                if (statements[i][j] == 1 && person[j] == 0) {
                    return;
                }
            }
        }
        max = Math.max(max, truePerson);
    }

}

