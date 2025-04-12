package EndlessCheng.GenreMenu.Graph.EulerianPath;

import java.util.Collections;
import java.util.HashSet;

public class CrackSafe {

    // https://leetcode.cn/problems/cracking-the-safe/solutions/1069334/po-jie-bao-xian-xiang-mi-ma-qiu-ha-mi-er-21mb/
    public String crackSafe(int n, int k) {
        // 總共可能的密碼數
        int size = (int) Math.pow(k, n);
        // 初始化第一個密碼
        // build為密碼拼接成的字符串，初始化為 n個0
        StringBuilder build = new StringBuilder(String.join("", Collections.nCopies(n, "0")));
        // set中存放已經遍歷過的結點
        HashSet<String> set = new HashSet<String>();
        set.add(build.toString());
        // 尋找路徑
        if (dfs(build, size, n, k, set)) {
            return build.toString();
        }
        // 找不到哈密爾頓路徑返回空字符串
        return "";
    }

    public boolean dfs(StringBuilder build, int size, int n, int k, HashSet set) {
        // set中的結點數等於保險箱總密碼的個數時，說明哈密爾頓路徑已經被找到，直接返回true
        if (size == set.size()) {
            return true;
        }
        // 取前一個密碼的後n-1位，是新密碼的前n-1位
        String t = build.substring(build.length() - (n - 1));
        // 每一個結點（密碼）有k條路徑可走，每一條路徑對應不同的新結點（新密碼）
        for (char i = '0'; i < '0' + k; i++) {
            String tmp = t + i;
            // tmp這個結點(密碼)是否在set中，即是否被標記過
            if (!set.contains(tmp)) {
                set.add(tmp);
                build.append(i);
                // 繼續往下尋找路徑
                if (dfs(build, size, n, k, set)) {
                    // 找到完整的哈密爾頓路徑後返回true
                    return true;
                }
                // 尋找路徑失敗，需要回溯，這個時候需要回到上一個結點
                set.remove(tmp);
                build.deleteCharAt(build.length() - 1);
            }
        }
        // 如果初始結點把所有的路徑走完都沒有找到哈密爾頓路徑，返回false
        return false;
    }


}
