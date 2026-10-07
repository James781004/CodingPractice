package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

import java.util.*;

public class TrafficCommand {

    // https://leetcode.cn/problems/Y1VbOX/solutions/1000120/cpython3java-1dfsji-yi-hua-by-hanxin_han-tvg4/
    String[] directions;
    Map<Integer, Character> ID_dir = new HashMap<>();
    int[][][][] memo = new int[21][21][21][21];

    public boolean check2(char[] car1, char[] car2) {
        char from1 = car1[0], to1 = car1[1];
        char from2 = car2[0], to2 = car2[1];
        //----目的地相同的情況
        if (to1 == to2) {
            return false;
        }
        //----檢驗是否有交叉（有重復討論的情況，重復就重復吧）
        if (from1 == 'N') {
            if (from2 == 'E') {
                if (to1 == 'E' && (to2 == 'W' || to2 == 'S')) return false;
                else if (to1 == 'S' && to2 == 'W') return false;
            } else if (from2 == 'S') {
                if (to1 == 'E' && to2 == 'N') return false;
                else if (to1 == 'S' && to2 == 'W') return false;
            } else if (from2 == 'W') {
                if (to1 == 'E' && to2 == 'N') return false;
                else if (to1 == 'S' && (to2 == 'N' || to2 == 'E')) return false;
            }
        } else if (from1 == 'E') {
            if (from2 == 'S') {
                if (to1 == 'W' && to2 == 'N') return false;
                else if (to1 == 'S' && (to2 == 'W' || to2 == 'N')) return false;
            } else if (from2 == 'W') {
                if (to1 == 'W' && to2 == 'N') return false;
                else if (to1 == 'S' && to2 == 'E') return false;
            } else if (from2 == 'N') {
                if (to1 == 'W' && (to2 == 'E' || to2 == 'S')) return false;
                else if (to1 == 'S' && to2 == 'E') return false;
            }
        } else if (from1 == 'S') {
            if (from2 == 'W') {
                if (to1 == 'N' && to2 == 'E') return false;
                else if (to1 == 'W' && (to2 == 'N' || to2 == 'E')) return false;
            } else if (from2 == 'N') {
                if (to1 == 'N' && to2 == 'E') return false;
                else if (to1 == 'W' && to2 == 'S') return false;
            } else if (from2 == 'E') {
                if (to1 == 'N' && (to2 == 'W' || to2 == 'S')) return false;
                else if (to1 == 'W' && to2 == 'S') return false;
            }
        } else if (from1 == 'W') {
            if (from2 == 'N') {
                if (to1 == 'N' && (to2 == 'S' || to2 == 'E')) return false;
                else if (to1 == 'E' && to2 == 'S') return false;
            } else if (from1 == 'E') {
                if (to1 == 'N' && to2 == 'W') return false;
                else if (to1 == 'E' && to2 == 'S') return false;
            } else if (from2 == 'S') {
                if (to1 == 'N' && to2 == 'W') return false;
                else if (to1 == 'E' && (to2 == 'W' || to2 == 'N')) return false;
            }
        }

        return true;
    }

    public boolean check(List<char[]> from_to) {
        int fn = from_to.size();
        for (int i = 0; i < fn; i++) {
            for (int j = i + 1; j < fn; j++) {
                if (check2(from_to.get(i), from_to.get(j)) == false)
                    return false;
            }
        }
        return true;
    }


    public int dfs(int a, int b, int c, int d) {
        if (memo[a][b][c][d] != -1)
            return memo[a][b][c][d];
        if (a == directions[0].length() && b == directions[1].length() && c == directions[2].length() && d == directions[3].length())
            return 0;
        int[] dir_now = new int[]{a, b, c, d};
        int res = Integer.MAX_VALUE - 10;

        List<char[]> from_to = new ArrayList<>();
        int[] go = new int[4];

        for (int state = 1; state < 16; state++) {
            boolean ok = true;
            from_to.clear();
            Arrays.fill(go, 0);

            //----解析state（從哪幾個方向出發）
            for (int di = 0; di < 4; di++) {
                if (((state >> di) & 1) == 1) {
                    if (dir_now[di] == directions[di].length()) {
                        ok = false;
                        break;
                    }
                    from_to.add(new char[]{ID_dir.get(di), directions[di].charAt(dir_now[di])});
                    go[di] = 1;
                }
            }
            if (ok == true && check(from_to) == true) {
                int tmp_res = dfs(a + go[0], b + go[1], c + go[2], d + go[3]);
                res = Math.min(res, tmp_res + 1);
            }
        }
        memo[a][b][c][d] = res;
        return res;
    }


    public int trafficCommand(String[] directions) {
        this.directions = directions;
        ID_dir.put(0, 'E');
        ID_dir.put(1, 'S');
        ID_dir.put(2, 'W');
        ID_dir.put(3, 'N');
        for (int i = 0; i < 21; i++)
            for (int j = 0; j < 21; j++)
                for (int k = 0; k < 21; k++)
                    Arrays.fill(memo[i][j][k], -1);
        return dfs(0, 0, 0, 0);
    }


}
