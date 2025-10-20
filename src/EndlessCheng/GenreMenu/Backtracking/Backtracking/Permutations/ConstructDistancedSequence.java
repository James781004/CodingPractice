package EndlessCheng.GenreMenu.Backtracking.Backtracking.Permutations;

import java.util.Arrays;

public class ConstructDistancedSequence {

    // https://leetcode.cn/problems/construct-the-lexicographically-largest-valid-sequence/solutions/1365308/by-runningcarrot-5vv2/
    int[] ans;
    // 標記是否找到一個解了
    boolean flag = false;

    public int[] constructDistancedSequence(int n) {
        // 除了1出現1次，2到n出現兩次，那麼返回數組的長度為2n-1
        ans = new int[2 * n - 1];
        // 和ans等長，用於記錄當前遍歷的一個序列
        int[] arr = new int[2 * n - 1];
        // 初始為-1，表示這個位置沒有被用過
        Arrays.fill(arr, -1);
        // 記錄1到n各自是否已經填好了
        boolean[] visited = new boolean[n + 1];
        // 第二個參數0表示先填第一位，優先使用大數填
        dfs(n, 0, arr, visited);
        // 注意最後不能返回arr，arr元素最後都會被重置為-1，所以要多創建一個ans變量
        return ans;
    }

    // 當前在填arr[index]
    private void dfs(int n, int index, int[] arr, boolean[] visited) {
        // 找到一個解了，就不再遞歸，因為按我們的思路，第一個解一定是字典序最大的
        // 注意，這裡flag是必須的，不然會遍歷完所有狀態，ans就是字典序最小的了
        if (flag) {
            return;
        }
        // arr所有位置都填好了
        if (index == 2 * n - 1) {
            flag = true;
            // 將arr復制到ans，更新ans
            System.arraycopy(arr, 0, ans, 0, 2 * n - 1);
            return;
        }
        // 當前位置index已經填好了，那麼就看下一位置
        if (arr[index] != -1) {
            dfs(n, index + 1, arr, visited);
            return;
        }
        // 當前位置index還沒填，從n開始遞減地看能不能填
        for (int i = n; i >= 1; i--) {
            // i已經用過了，繼續看i-1
            if (visited[i]) {
                continue;
            }
            // 到這裡表示i沒用過，分情況討論i是不是1
            if (i == 1) {
                // 如果i是1，只需要將arr[index]賦值為i(即1)
                arr[index] = 1;
                visited[i] = true;
                dfs(n, index + 1, arr, visited);
                arr[index] = -1;
                visited[i] = false;
            } else {
                // 如果i不是1，需要將arr[index+i]也賦值為i
                // 當然，如果這裡arr[index + i] == -1不成立的話，說明i還是不能放在這裡，需要繼續循環看i-1
                if (index + i < 2 * n - 1 && arr[index + i] == -1) {
                    // 到了這裡說明i可以放在index和index+i處
                    arr[index] = i;
                    arr[index + i] = i;
                    // 標記i已經填好了
                    visited[i] = true;
                    // 遞歸
                    dfs(n, index + 1, arr, visited);
                    // 狀態重置
                    arr[index] = -1;
                    arr[index + i] = -1;
                    visited[i] = false;
                }
            }
        }
    }


}
