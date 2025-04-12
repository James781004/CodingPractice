package EndlessCheng.GenreMenu.Graph.DFS;

import java.util.*;

public class AccountsMerge {

    // https://leetcode.cn/problems/accounts-merge/solutions/2844186/ha-xi-biao-dfspythonjavacgojsrust-by-end-z9nh/
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        Map<String, List<Integer>> emailToIdx = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            for (int k = 1; k < accounts.get(i).size(); k++) {
                emailToIdx.computeIfAbsent(accounts.get(i).get(k), x -> new ArrayList<>()).add(i);
            }
        }

        List<List<String>> ans = new ArrayList<>();
        boolean[] vis = new boolean[accounts.size()];
        Set<String> emailSet = new HashSet<>(); // 用於收集 DFS 中訪問到的郵箱地址
        for (int i = 0; i < accounts.size(); i++) {
            if (vis[i]) {
                continue;
            }
            emailSet.clear();
            dfs(i, accounts, emailToIdx, vis, emailSet);

            List<String> res = new ArrayList<>(emailSet);
            Collections.sort(res);
            res.add(0, accounts.get(i).get(0));

            ans.add(res);
        }
        return ans;
    }

    private void dfs(int i, List<List<String>> accounts, Map<String, List<Integer>> emailToIdx, boolean[] vis, Set<String> emailSet) {
        vis[i] = true;
        for (int k = 1; k < accounts.get(i).size(); k++) { // 遍歷 i 的所有郵箱地址
            String email = accounts.get(i).get(k);
            if (emailSet.contains(email)) {
                continue;
            }
            emailSet.add(email);
            for (int j : emailToIdx.get(email)) { // 遍歷所有包含該郵箱地址的賬戶下標 j
                if (!vis[j]) { // j 沒有訪問過
                    dfs(j, accounts, emailToIdx, vis, emailSet);
                }
            }
        }
    }


}
