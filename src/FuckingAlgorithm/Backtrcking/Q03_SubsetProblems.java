package FuckingAlgorithm.Backtrcking;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Q03_SubsetProblems {
//    https://labuladong.github.io/algo/4/31/106/

//    https://leetcode.cn/problems/subsets/
//    78. 子集
//    給你一個整數數組 nums ，數組中的元素 互不相同 。返回該數組所有可能的子集（冪集）。
//
//    解集 不能 包含重複的子集。你可以按 任意順序 返回解集。

    // 子集（元素無重不可復選）
    List<List<Integer>> res = new LinkedList<>();
    // 記錄回溯算法的遞歸路徑
    LinkedList<Integer> track = new LinkedList<>();

    // 主函數
    public List<List<Integer>> subsets(int[] nums) {
        backtrack(nums, 0);
        return res;
    }

    // 回溯算法核心函數，遍歷子集問題的回溯樹
    void backtrack(int[] nums, int start) {

        // 前序位置，每個節點的值都是一個子集
        res.add(new LinkedList<>(track));

        // 回溯算法標準框架
        for (int i = start; i < nums.length; i++) {
            // 做選擇
            track.addLast(nums[i]);
            // 通過 start 參數控制樹枝的遍歷，避免產生重複的子集
            backtrack(nums, i + 1);
            // 撤銷選擇
            track.removeLast();
        }
    }


//    https://leetcode.cn/problems/combinations/
//    77. 組合
//    給定兩個整數 n 和 k，返回范圍 [1, n] 中所有可能的 k 個數的組合。
//
//    你可以按 任何順序 返回答案。

    // 組合（元素無重不可復選）
    public List<List<Integer>> combine(int n, int k) {
        backtrack(1, n, k);
        return res;
    }

    void backtrack(int start, int n, int k) {
        // base case
        if (k == track.size()) {
            // 遍歷到了第 k 層，收集當前節點的值
            res.add(new LinkedList<>(track));
            return;
        }

        // 回溯算法標準框架
        for (int i = start; i <= n; i++) {
            // 選擇
            track.addLast(i);
            // 通過 start 參數控制樹枝的遍歷，避免產生重複的子集
            backtrack(i + 1, n, k);
            // 撤銷選擇
            track.removeLast();
        }
    }


//    https://leetcode.cn/problems/permutations/
//    46. 全排列
//    給定一個不含重複數字的數組 nums ，返回其 所有可能的全排列 。你可以 按任意順序 返回答案。


    // 排列（元素無重不可復選）
    // track 中的元素會被標記為 true
    boolean[] used;

    /* 主函數，輸入一組不重複的數字，返回它們的全排列 */
    public List<List<Integer>> permute(int[] nums) {
        used = new boolean[nums.length];
        backtrack(nums);
        return res;
    }

    // 回溯算法核心函數
    void backtrack(int[] nums) {
        // base case，到達葉子節點
        if (track.size() == nums.length) {
            // 收集葉子節點上的值
            res.add(new LinkedList(track));
            return;
        }

        // 回溯算法標準框架
        for (int i = 0; i < nums.length; i++) {
            // 已經存在 track 中的元素，不能重複選擇
            if (used[i]) {
                continue;
            }
            // 做選擇
            used[i] = true;
            track.addLast(nums[i]);
            // 進入下一層回溯樹
            backtrack(nums);
            // 取消選擇
            track.removeLast();
            used[i] = false;
        }
    }


//    https://leetcode.cn/problems/subsets-ii/
//    90. 子集 II
//    給你一個整數數組 nums ，其中可能包含重複元素，請你返回該數組所有可能的子集（冪集）。
//
//    解集 不能 包含重複的子集。返回的解集中，子集可以按 任意順序 排列。

    //    子集/組合（元素可重不可復選）
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // 先排序，讓相同的元素靠在一起
        Arrays.sort(nums);
        dfs(nums, 0);
        return res;
    }

    void dfs(int[] nums, int start) {
        // 前序位置，每個節點的值都是一個子集
        res.add(new LinkedList<>(track));

        for (int i = start; i < nums.length; i++) {
            // 剪枝邏輯，值相同的相鄰樹枝，只遍歷第一條
            // 相同的元素靠在一起，如果發現 nums[i] == nums[i-1]，則跳過
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            track.addLast(nums[i]);
            dfs(nums, i + 1);
            track.removeLast();
        }
    }


//    https://leetcode.cn/problems/combination-sum-ii/
//    40. 組合總和 II
//    給定一個候選人編號的集合 candidates 和一個目標數 target ，找出 candidates 中所有可以使數字和為 target 的組合。
//
//    candidates 中的每個數字在每個組合中只能使用 一次 。
//
//    注意：解集不能包含重複的組合。


    // 組合問題和子集問題是等價的
    // 只要額外用一個 trackSum 變量記錄回溯路徑上的元素和，然後將 base case 改一改即可解決這道題

    // 記錄 track 中的元素之和
    int trackSum = 0;

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates.length == 0) {
            return res;
        }
        // 先排序，讓相同的元素靠在一起
        Arrays.sort(candidates);
        backtrack(candidates, 0, target);
        return res;
    }

    // 回溯算法主函數
    void backtrack(int[] nums, int start, int target) {
        // base case，達到目標和，找到符合條件的組合
        if (trackSum == target) {
            res.add(new LinkedList<>(track));
            return;
        }
        // base case，超過目標和，直接結束
        if (trackSum > target) {
            return;
        }

        // 回溯算法標準框架
        for (int i = start; i < nums.length; i++) {
            // 剪枝邏輯，值相同的樹枝，只遍歷第一條
            if (i > start && nums[i] == nums[i - 1]) {
                continue;
            }
            // 做選擇
            track.add(nums[i]);
            trackSum += nums[i];
            // 遞歸遍歷下一層回溯樹
            backtrack(nums, i + 1, target);
            // 撤銷選擇
            track.removeLast();
            trackSum -= nums[i];
        }
    }


//    https://leetcode.cn/problems/permutations-ii/
//    47. 全排列 II
//    給定一個可包含重複數字的序列 nums ，按任意順序 返回所有不重複的全排列。

    // 排列（元素可重不可復選）
    public List<List<Integer>> permuteUnique(int[] nums) {
        // 先排序，讓相同的元素靠在一起
        Arrays.sort(nums);
        used = new boolean[nums.length];
        permuteUniqueHelper(nums);
        return res;
    }

    void permuteUniqueHelper(int[] nums) {
        if (track.size() == nums.length) {
            res.add(new LinkedList(track));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            // 新添加的剪枝邏輯，固定相同的元素在排列中的相對位置
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }
            track.add(nums[i]);
            used[i] = true;
            backtrack(nums);
            track.removeLast();
            used[i] = false;
        }
    }


//    https://leetcode.cn/problems/combination-sum/
//    39. 組合總和
//    給你一個 無重複元素 的整數數組 candidates 和一個目標整數 target ，
//    找出 candidates 中可以使數字和為目標數 target 的 所有 不同組合 ，並以列表形式返回。你可以按 任意順序 返回這些組合。
//
//    candidates 中的 同一個 數字可以 無限制重複被選取 。如果至少一個數字的被選數量不同，則兩種組合是不同的。
//
//    對於給定的輸入，保證和為 target 的不同組合數少於 150 個。

    // 子集/組合（元素無重可復選）
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates.length == 0) {
            return res;
        }
        combinationSumHelper(candidates, 0, target);
        return res;
    }

    // 回溯算法主函數
    void combinationSumHelper(int[] nums, int start, int target) {
        // base case，找到目標和，記錄結果
        if (trackSum == target) {
            res.add(new LinkedList<>(track));
            return;
        }
        // base case，超過目標和，停止向下遍歷
        if (trackSum > target) {
            return;
        }

        // 回溯算法標準框架
        for (int i = start; i < nums.length; i++) {
            // 選擇 nums[i]
            trackSum += nums[i];
            track.add(nums[i]);
            // 遞歸遍歷下一層回溯樹
            // 同一元素可重複使用，注意參數
            backtrack(nums, i, target);
            // 撤銷選擇 nums[i]
            trackSum -= nums[i];
            track.removeLast();
        }
    }


    // 排列（元素無重可復選）
    // nums 數組中的元素無重複且可復選的情況下，會有哪些排列？
    // 標準的全排列算法利用 used 數組進行剪枝，避免重複使用同一個元素。如果允許重複使用元素的話，直接放飛自我，去除所有 used 數組的剪枝邏輯就行了。
    public List<List<Integer>> permuteRepeat(int[] nums) {
        permuteRepeatHelper(nums);
        return res;
    }

    // 回溯算法核心函數
    void permuteRepeatHelper(int[] nums) {
        // base case，到達葉子節點
        if (track.size() == nums.length) {
            // 收集葉子節點上的值
            res.add(new LinkedList(track));
            return;
        }

        // 回溯算法標準框架
        for (int i = 0; i < nums.length; i++) {
            // 做選擇
            track.add(nums[i]);
            // 進入下一層回溯樹
            backtrack(nums);
            // 取消選擇
            track.removeLast();
        }
    }

}
