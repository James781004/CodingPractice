package PointToOffer.Search;

public class Q12_PathInMatrix {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param matrix string字符串
     * @param rows   int整型
     * @param cols   int整型
     * @param str    string字符串
     * @return bool布尔型
     */
    public boolean hasPath(String matrix, int rows, int cols, String str) {
        // write code here
        boolean[] visited = new boolean[rows * cols];
        char[] maxtrixs = matrix.toCharArray();
        char[] strs = str.toCharArray();
        //遍历寻找最开始的一个匹配
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (judge(maxtrixs, strs, visited, rows, cols, i, j, 0)) return true;
            }
        }
        return false;
    }

    //k代表当已经匹配到几个了
    public boolean judge(char[] matrixs, char[] strs, boolean[] visited, int rows, int cols, int i, int j, int k) {
        int is_visited_pos = i * cols + j;//计算将要访问的字母的下标
        if (i < 0 || i >= rows || j < 0 || j >= cols || visited[is_visited_pos] == true || matrixs[is_visited_pos] != strs[k]) {
            return false;
        }
        //如果第k个满足上面的条件，并且k等于要匹配的长度
        if (k == strs.length - 1) return true;
        //如果符合以上的条件即把当前位置标记为已经访问
        visited[is_visited_pos] = true;
        //上下左右搜寻
        if (judge(matrixs, strs, visited, rows, cols, i - 1, j, k + 1) ||
                judge(matrixs, strs, visited, rows, cols, i + 1, j, k + 1) ||
                judge(matrixs, strs, visited, rows, cols, i, j - 1, k + 1) ||
                judge(matrixs, strs, visited, rows, cols, i, j + 1, k + 1)
        ) return true;//通过is_visited_pos寻找到了
        //如果没有通过is_visited_pos寻找到，说明is_visited_pos不通，将其重新置为没有访问
        visited[is_visited_pos] = false;
        return false;
    }
}
