package EndlessCheng.GenreMenu.Backtracking.Backtracking.Subset;

public class MaximumGood {

    // https://leetcode.cn/problems/maximum-good-people-based-on-statements/solutions/1227442/er-jin-zhi-mei-ju-by-endlesscheng-ttix/
    int max = 0;

    public int maximumGood(int[][] statements) {
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
