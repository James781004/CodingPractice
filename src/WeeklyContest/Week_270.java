package WeeklyContest;

import java.util.*;

class Week_270 {
    // https://leetcode.cn/problems/finding-3-digit-even-numbers/solution/zhao-chu-3-wei-ou-shu-by-leetcode-soluti-hptf/
    public int[] findEvenNumbers(int[] digits) {
        // 統計0 ~ 9各個數字的出現次數
        int[] count = new int[10];
        for (int digit : digits) {
            count[digit]++;
        }

        // 百位、十位、個位按各自規則不重不漏的進行數字選取
        // 各個位置選取之後統計結果減一，完成當前遍歷後恢復（統計結果再加一）
        List<Integer> list = new ArrayList<>();

        // 百位按1 ~ 9取存在的數字（保證了順序且不重復）
        for (int i = 1; i < 10; i++) {
            if (count[i] > 0) {
                count[i]--;
                // 十位取剩余0 ~ 9中存在的數字（保證了順序且不重復）
                for (int j = 0; j < 10; j++) {
                    if (count[j] > 0) {
                        count[j]--;
                        // 個位取剩余存在的偶數（保證了順序且不重復）
                        for (int k = 0; k < 10; k += 2) {
                            if (count[k] > 0) {
                                list.add(i * 100 + j * 10 + k);
                            }
                        }
                        count[j]++;
                    }
                }
                count[i]++;
            }
        }

        // 將有序的list按順序轉為int[]後返回
        int size = list.size();
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = list.get(i);
        }
        return ans;
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2095.Delete%20the%20Middle%20Node%20of%20a%20Linked%20List/README.md
    public ListNode deleteMiddle(ListNode head) {
        ListNode dummy = new ListNode(0, head);
        ListNode slow = dummy, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }


    // https://github.com/doocs/leetcode/blob/main/solution/2000-2099/2096.Step-By-Step%20Directions%20From%20a%20Binary%20Tree%20Node%20to%20Another/README.md
    // 先預處理父子節點的關系，然後 DFS 搜索即可。
    private Map<Integer, List<List<String>>> edges;
    private Set<Integer> visited;
    private String ans;

    public String getDirections(TreeNode root, int startValue, int destValue) {
        edges = new HashMap<>();
        visited = new HashSet<>();
        ans = null;
        traverse(root);
        dfs(startValue, destValue, new ArrayList<>());
        return ans;
    }

    private void traverse(TreeNode root) {
        if (root == null) {
            return;
        }

        // 建立父子樹關係
        if (root.left != null) {
            edges.computeIfAbsent(root.val, k -> new ArrayList<>())
                    .add(Arrays.asList(String.valueOf(root.left.val), "L"));
            edges.computeIfAbsent(root.left.val, k -> new ArrayList<>())
                    .add(Arrays.asList(String.valueOf(root.val), "U"));
        }
        if (root.right != null) {
            edges.computeIfAbsent(root.val, k -> new ArrayList<>())
                    .add(Arrays.asList(String.valueOf(root.right.val), "R"));
            edges.computeIfAbsent(root.right.val, k -> new ArrayList<>())
                    .add(Arrays.asList(String.valueOf(root.val), "U"));
        }
        traverse(root.left);
        traverse(root.right);
    }


    private void dfs(int start, int dest, List<String> t) {
        if (visited.contains(start)) { // 剪枝
            return;
        }

        if (start == dest) { // 抵達目標點，開始比大小並且組字串
            if (ans == null || ans.length() > t.size()) {
                ans = String.join("", t);
            }
            return;
        }

        visited.add(start);
        if (edges.containsKey(start)) {
            for (List<String> item : edges.get(start)) {
                t.add(item.get(1));  // List的1位置存放方向ULR
                dfs(Integer.parseInt(item.get(0)), dest, t);  // List的0位置存放節點值
                t.remove(t.size() - 1);
            }
        }
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }


    // https://leetcode.cn/problems/valid-arrangement-of-pairs/solution/he-fa-zhong-xin-pai-lie-shu-dui-by-leetc-h8rl/
    class ValidArrangement {
        LinkedList<int[]> ret;
        HashMap<Integer, LinkedList<Integer>> map;
        HashMap<Integer, Integer> inDegree;

        public int[][] validArrangement(int[][] pairs) {
            ret = new LinkedList<>();
            inDegree = new HashMap<>();
            map = new HashMap<>();
            for (int[] pair : pairs) { // 建圖並計算出入度
                int out = pair[0], in = pair[1];
                inDegree.put(in, inDegree.getOrDefault(in, 0) + 1);
                inDegree.put(out, inDegree.getOrDefault(out, 0) - 1);
                LinkedList<Integer> list = map.getOrDefault(in, new LinkedList<>());
                list.add(out);
                map.put(in, list);
            }
            int end = pairs[0][1];
            for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
                if (entry.getValue() == 1) {  // 半歐拉圖：存在出度比入度大 1 的點，那就把它當成起點
                    end = entry.getKey();
                    break;
                }
            }
            dfs(end);
            int[][] ans = new int[pairs.length][2];
            ret.toArray(ans);
            return ans;
        }

        void dfs(int end) {
            LinkedList<Integer> list2 = map.get(end);
            while (list2 != null && !list2.isEmpty()) {
                Integer pre = list2.pollLast();
                dfs(pre);
                ret.offer(new int[]{pre, end}); // 記錄歐拉路徑
            }
        }
    }
}

