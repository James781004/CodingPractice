package GuChengAlgorithm.ch05_DP;

import java.util.Map;

public class Q08_BitMask {
    // https://docs.google.com/presentation/d/1j_Z9nLLU7XySzqRexmavgbUfx5gxtA8XmYlYaH56pQk/edit#slide=id.g1181767250e_0_83
    class canIWin {
        Map<Integer, Boolean> memo;

        public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
            if (desiredTotal <= 0) return false;
            if (maxChoosableInteger * (maxChoosableInteger + 1) / 2 < desiredTotal) return false;
            return dfs(0, maxChoosableInteger, desiredTotal);
        }

        private boolean dfs(int state, int max, int target) {
            if (target <= 0) return false;
            if (memo.containsKey(state)) return memo.get(state);
            boolean win = false;
            for (int i = 0; i < max; i++) {
                if ((state & (1 << i)) == 0) {
                    win = win || !dfs(state ^ (1 << i), max, target - i - 1);
                }
            }
            memo.put(state, win);
            return win;
        }
    }


    // https://docs.google.com/presentation/d/1j_Z9nLLU7XySzqRexmavgbUfx5gxtA8XmYlYaH56pQk/edit#slide=id.g1181767250e_0_139
    class countArrangement {
        int N;
        Integer[] memo;

        public int countArrangement(int n) {
            this.N = n;
            this.memo = new Integer[1 << n];
            return dfs(n, 0);
        }

        private int dfs(int pos, int state) {
            if (pos == 1) return 1;
            if (memo[state] != null) return memo[state];
            int count = 0;
            for (int j = 1; j <= N; j++) {
                if ((state & (1 << (j - 1))) == 0 && (pos % j == 0 || j % pos == 0)) {
                    count += dfs(pos - 1, state | (1 << (j - 1)));
                }
            }
            return memo[state] = count;
        }
    }

}
