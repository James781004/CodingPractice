package FuckingAlgorithm.BinaryTree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class Q10_NestedInteger {
//    https://leetcode.cn/problems/flatten-nested-list-iterator/
//    341. 扁平化嵌套列表迭代器
//    給你一個嵌套的整數列表 nestedList 。每個元素要麼是一個整數，要麼是一個列表；該列表的元素也可能是整數或者是其他列表。
//    請你實現一個迭代器將其扁平化，使之能夠遍歷這個列表中的所有整數。
//
//    實現扁平迭代器類 NestedIterator ：
//
//    NestedIterator(List<NestedInteger> nestedList) 用嵌套列表 nestedList 初始化迭代器。
//    int next() 返回嵌套列表的下一個整數。
//    boolean hasNext() 如果仍然存在待迭代的整數，返回 true ；否則，返回 false 。

    public class NestedInteger {
        private Integer val;
        private List<NestedInteger> list;

        public NestedInteger(Integer val) {
            this.val = val;
            this.list = null;
        }

        public NestedInteger(List<NestedInteger> list) {
            this.list = list;
            this.val = null;
        }

        // 如果其中存的是一個整數，則返回 true，否則返回 false
        public boolean isInteger() {
            return val != null;
        }

        // 如果其中存的是一個整數，則返回這個整數，否則返回 null
        public Integer getInteger() {
            return this.val;
        }

        // 如果其中存的是一個列表，則返回這個列表，否則返回 null
        public List<NestedInteger> getList() {
            return this.list;
        }
    }

    public class NestedIterator implements Iterator<Integer> {

        Deque<Integer> queue = new ArrayDeque<>();

        // 初始化的時候進行處理。
        // 由於存在嵌套，比較簡單的做法是通過 DFS 進行處理，將元素都放至隊列。
        public NestedIterator(List<NestedInteger> nestedList) {
            dfs(nestedList);
        }

        @Override
        public Integer next() {
            return hasNext() ? queue.pollFirst() : -1;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        void dfs(List<NestedInteger> list) {
            for (NestedInteger item : list) {
                if (item.isInteger()) {
                    queue.addLast(item.getInteger());
                } else {
                    dfs(item.getList());
                }
            }
        }
    }


    public class NestedIterator2 implements Iterator<Integer> {

        Deque<NestedInteger> stack = new ArrayDeque<>();

        // 不對所有的元素進行預處理。
        // 而是先將所有的 NestedInteger 逆序放到棧中，當需要展開的時候才進行展開。
        public NestedIterator2(List<NestedInteger> list) {
            for (int i = list.size() - 1; i >= 0; i--) {
                NestedInteger item = list.get(i);
                stack.addLast(item);
            }
        }

        @Override
        public Integer next() {
            return hasNext() ? stack.pollLast().getInteger() : -1;
        }

        @Override
        public boolean hasNext() {
            if (stack.isEmpty()) {
                return false;
            } else {
                NestedInteger item = stack.peekLast();
                if (item.isInteger()) {
                    return true;
                } else {
                    item = stack.pollLast();
                    List<NestedInteger> list = item.getList();
                    for (int i = list.size() - 1; i >= 0; i--) {
                        stack.addLast(list.get(i));
                    }
                    return hasNext();
                }
            }
        }
    }

}
