package GuChengAlgorithm.Ch08_LeetCodePass;

import java.util.*;

public class Q15_UnionFind {
    // https://docs.google.com/presentation/d/1pPm64mrcOUrfHiCfg-eHXigMfpAbum3pAk3O7XdbVZQ/edit#slide=id.g10a23b659f0_2_221
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        DSU dsu = new DSU();
        Map<String, String> emailToName = new HashMap<>();
        for (List<String> account : accounts) {
            String name = account.get(0);
            String primaryEmail = account.get(1);
            for (int i = 1; i < account.size(); i++) {
                String email = account.get(i);
                emailToName.put(email, name);
                dsu.union(primaryEmail, email);
            }
        }

        // key: primaryEmail, value: email list under the same account
        Map<String, List<String>> mergedAccount = new HashMap<>();
        for (String email : emailToName.keySet()) {
            String primaryEmail = dsu.find(email);
            mergedAccount.computeIfAbsent(primaryEmail, x -> new ArrayList<>()).add(email);
        }

        // sort email and name
        List<List<String>> res = new ArrayList<>();
        for (List<String> emails : mergedAccount.values()) {
            Collections.sort(emails);
            String name = emailToName.get(emails.get(0));
            List<String> account = new ArrayList<>();
            account.add(name);
            account.addAll(emails);
            res.add(account);
        }

        return res;
    }

    class DSU {
        Map<String, String> root = new HashMap<>();

        public String find(String x) {
            String parent = root.getOrDefault(x, x);
            if (!parent.equals(x)) parent = find(parent);
            root.put(x, parent);
            return root.get(x);
        }

        public void union(String x, String y) {
            root.put(find(x), find(y));
        }
    }
}
