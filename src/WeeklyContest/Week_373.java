package WeeklyContest;

import java.util.*;

public class Week_373 {
    // https://leetcode.cn/problems/matrix-similarity-after-cyclic-shifts/solutions/2542365/javaban-ben-by-shao-chi-duo-shui-wo-bu-c-llqg/
    // 所有行列的值取模判斷mat[row][col]==mat[row][(col+k) % cols]是否相等
    // 如果不等直接返回false
    // 否則循環結束後返回true
    public boolean areSimilar(int[][] mat, int k) {
        int rowLen = mat.length, columnLen = mat[0].length;
        k = k % columnLen;
        for (int row = 0; row < rowLen; row++) {
            for (int column = 0; column < columnLen; column++) {
                if (row % 2 == 0) {
                    if (mat[row][column] != mat[row][(column + k) % columnLen]) {
                        return false;
                    }
                }
                if (row % 2 != 0) {
                    int index = column - k;
                    if (index < 0) {
                        index = columnLen + index;
                    }
                    if (mat[row][index] != mat[row][column]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    // https://leetcode.cn/problems/count-beautiful-substrings-i/solutions/2542332/qian-zhui-he-shuang-zhong-xun-huan-tong-pga4v/
    // 先用前綴和維護元音數量，再雙重循環統計
    public int beautifulSubstrings(String s, int k) {
        int n = s.length();
        int[] vowelCnt = new int[n + 1];

        for (int i = 1; i < n + 1; ++i) {
            vowelCnt[i] = vowelCnt[i - 1] + (isVowel(s.charAt(i - 1)) ? 1 : 0);
        }

        int res = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                int curVowelCnt = vowelCnt[j + 1] - vowelCnt[i];
                int curConsonantCnt = j + 1 - i - curVowelCnt;
                if (curVowelCnt == curConsonantCnt && curVowelCnt * curConsonantCnt % k == 0) {
                    ++res;
                }
            }
        }
        return res;
    }

    private static final char[] vowels = new char[]{'a', 'o', 'e', 'i', 'u'};

    private boolean isVowel(char c) {
        for (char vowel : vowels) {
            if (c == vowel) {
                return true;
            }
        }
        return false;
    }


    // https://leetcode.cn/problems/make-lexicographically-smallest-array-by-swapping-elements/solutions/2542284/pai-xu-fen-zu-by-angry-solomonrre-3uui/
    // 先排序，然後將可以交換的分成一組，這一組內的元素都是可以任意交換的，
    // 那麼我們只需要將這一組的下標和值分別排序，最後取按照下標的順序填入對應最小的值即可
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int[][] tmp = new int[nums.length][2];
        for (int i = 0; i < nums.length; i++) {
            tmp[i][0] = nums[i];
            tmp[i][1] = i;
        }
        Arrays.sort(tmp, (o1, o2) -> o1[0] == o2[0] ? (o1[1] - o2[1]) : (o1[0] - o2[0]));
        int[] res = new int[nums.length];
        int index = 0;
        while (index < tmp.length) {
            List<Integer> value = new ArrayList<>();
            List<Integer> indexList = new ArrayList<>();
            int max = tmp[index][0];
            while (index < tmp.length && tmp[index][0] <= max) {
                max = tmp[index][0] + limit;
                value.add(tmp[index][0]);
                indexList.add(tmp[index][1]);
                index++;
            }
            Collections.sort(value);
            Collections.sort(indexList);
            for (int i = 0; i < indexList.size(); i++) {
                res[indexList.get(i)] = value.get(i);
            }
        }
        return res;
    }


    // https://leetcode.cn/problems/count-beautiful-substrings-ii/solutions/2542387/javaqian-zhui-he-ha-xi-by-liu-jian-ping-l0pgx/
    public long beautifulSubstringsII(String s, int k) {
        int n = s.length();
        long res = 0;
        List<Integer> wList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (i * i % k == 0) wList.add(i);
        }
        //key: 數量差#cnt%k, value:數量
        Map<String, Integer> map = new HashMap<>();
        map.put(0 + "#" + 0, 1); //哨兵
        int cnt1 = 0; //元音數量
        int cnt2 = 0; //輔音數量
        for (int i = 1; i <= n; i++) {
            char c = s.charAt(i - 1);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') cnt1++;
            else cnt2++;
            int delta = cnt1 - cnt2;
            for (int w : wList) {
                int preMod = (cnt1 - w + k) % k;
                if (map.get(delta + "#" + preMod) != null) {
                    res += map.get(delta + "#" + preMod);
                }
            }
            String hash = delta + "#" + (cnt1 % k);
            map.put(hash, 1 + map.getOrDefault(hash, 0));
        }
        return res;
    }
}
