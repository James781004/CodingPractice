package EndlessCheng.GenreMenu.Backtracking.LinkedList.Comprehensive;

import java.util.Random;

public class DesignSkiplistNode {

    // https://leetcode.cn/problems/design-skiplist/solutions/3086158/she-ji-cong-ling-kai-shi-shou-xie-yi-bu-4memx/
    // 跳表節點類
    class SkiplistNode {
        int value;
        SkiplistNode[] next;

        // 構造函數
        public SkiplistNode(int value, int level) {
            this.value = value;
            this.next = new SkiplistNode[level + 1];  // 各層的指針
        }
    }

    // 跳表類
    class Skiplist {
        private static final int MAX_LEVEL = 15;  // 最大層數
        private static final double PROBABILITY = 0.5;  // 向上提升的概率
        private int level = 0;  // 當前跳表的最大層數
        private SkiplistNode head;  // 頭節點，值為 None
        private SkiplistNode tail;  // 尾節點，值為 None

        // 構造函數
        public Skiplist() {
            this.head = new SkiplistNode(Integer.MIN_VALUE, MAX_LEVEL);  // 初始化頭節點
            this.tail = new SkiplistNode(Integer.MAX_VALUE, MAX_LEVEL);  // 初始化尾節點
            // 初始化每層的頭節點->尾節點
            for (int i = 0; i <= MAX_LEVEL; i++) {
                head.next[i] = tail;
            }
        }

        // 隨機生成節點的最大層數
        private int randomLevel() {
            int level = 0;
            Random rand = new Random();
            // 注意，別超過最大層數MAX_LEVEL
            while (rand.nextDouble() < PROBABILITY && level < MAX_LEVEL) {
                level++;
            }
            return level;
        }

        // 查找
        public boolean search(int target) {
            SkiplistNode p = head;
            // 從最高層開始查找
            for (int i = level; i >= 0; i--) {
                // 每一層都跳到嚴格小於target的最後一個節點
                while (p.next[i] != tail && p.next[i].value < target) {
                    p = p.next[i];
                }
            }
            // 跳到最低層，下一個元素就是目標
            p = p.next[0];
            return p.value == target;
        }

        // 插入
        public void add(int num) {
            SkiplistNode[] update = new SkiplistNode[MAX_LEVEL + 1];  // 保存插入位置的前驅節點
            SkiplistNode p = head;

            // 從最高層開始查找
            for (int i = level; i >= 0; i--) {
                // 同理，找每層最後一個節點p
                while (p.next[i] != tail && p.next[i].value < num) {
                    p = p.next[i];
                }
                update[i] = p;  // 讓p指向num
            }

            // 根據隨機生成的層數決定是否提升
            int newLevel = randomLevel();

            // 如果生成的層數大於當前最大層數，更新跳表的最大層數
            if (newLevel > level) {
                for (int i = level + 1; i <= newLevel; i++) {
                    update[i] = head;
                }
                level = newLevel;
            }

            // 生成新節點
            SkiplistNode newNode = new SkiplistNode(num, newLevel);
            // 在每一層插入新節點
            for (int i = 0; i <= newLevel; i++) {
                newNode.next[i] = update[i].next[i];
                update[i].next[i] = newNode;
            }
        }

        // 刪除
        public boolean erase(int num) {
            SkiplistNode[] update = new SkiplistNode[MAX_LEVEL + 1];  // 保存插入位置的前驅節點
            SkiplistNode p = head;
            boolean found = false;

            // 從最高層開始查找
            for (int i = level; i >= 0; i--) {
                // 同理，找每層最後一個節點p
                while (p.next[i] != tail && p.next[i].value < num) {
                    p = p.next[i];
                }
                update[i] = p;  // 讓p指向num
            }

            // 跳到最低層，下一個元素可能就是目標
            p = p.next[0];
            // 找到
            if (p != tail && p.value == num) {
                found = true;
                // 在每一層刪除該節點
                for (int i = 0; i <= level; i++) {
                    if (update[i].next[i] != p) {
                        break;
                    }
                    update[i].next[i] = p.next[i];
                }
                // 可能需要刪除最上面的鏈表
                while (level > 0 && head.next[level] == tail) {
                    level--;
                }
            }
            return found;
        }
    }


}
