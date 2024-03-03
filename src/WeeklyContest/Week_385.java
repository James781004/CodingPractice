package WeeklyContest;

import java.util.HashMap;
import java.util.Map;

public class Week_385 {
    // https://leetcode.cn/problems/count-prefix-and-suffix-pairs-i/solutions/2644200/tong-ji-qian-hou-zhui-xia-biao-dui-i-jav-md1l/
    // 兩層循環遍歷, 外層循環索引i小於內層索引j, 將所有可能的組合進行檢測。
    public int countPrefixSuffixPairs(String[] words) {
        int result = 0;
        for (int i = 0; i < words.length - 1; i++) {
            for (int j = i + 1; j < words.length; j++) {
                if (isPrefixAndSuffix(words[i], words[j])) {
                    result++;
                }
            }
        }
        return result;
    }

    public boolean isPrefixAndSuffix(String str1, String str2) {
        return str2.startsWith(str1) && str2.endsWith(str1);
    }


    // https://leetcode.cn/problems/find-the-length-of-the-longest-common-prefix/solutions/2644176/liang-chong-xie-fa-yong-zi-fu-chuan-bu-y-qwh8/


    // https://leetcode.cn/problems/most-frequent-prime/solutions/2644161/mei-ju-pythonjavacgo-by-endlesscheng-enj0/
    private static final int[][] DIRS = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};

    public int mostFrequentPrime(int[][] mat) {
        int m = mat.length;
        int n = mat[0].length;
        Map<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int[] d : DIRS) {
                    int x = i + d[0];
                    int y = j + d[1];
                    int v = mat[i][j];
                    while (x >= 0 && x < m && y >= 0 && y < n) {
                        v = v * 10 + mat[x][y];
                        if (isPrime(v)) {
                            cnt.merge(v, 1, Integer::sum);
                        }
                        x += d[0];
                        y += d[1];
                    }
                }
            }
        }

        int ans = -1;
        int maxCnt = 0;
        for (Map.Entry<Integer, Integer> e : cnt.entrySet()) {
            int v = e.getKey();
            int c = e.getValue();
            if (c > maxCnt) {
                ans = v;
                maxCnt = c;
            } else if (c == maxCnt) {
                ans = Math.max(ans, v);
            }
        }
        return ans;
    }

    private boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/count-prefix-and-suffix-pairs-ii/solutions/2644160/z-han-shu-zi-dian-shu-pythonjavacgo-by-e-5c2v/
    public long countPrefixSuffixPairs2(String[] words) {
        long ans = 0;
        Node root = new Node();
        for (String S : words) {
            char[] s = S.toCharArray();
            int n = s.length;
            Node cur = root;
            for (int i = 0; i < n; i++) {
                int p = (s[i] - 'a') << 5 | (s[n - 1 - i] - 'a');
                cur = cur.son.computeIfAbsent(p, k -> new Node());
                ans += cur.cnt;
            }
            cur.cnt++;
        }
        return ans;
    }

    static class Node {
        Map<Integer, Node> son = new HashMap<>();
        int cnt;
    }
}


