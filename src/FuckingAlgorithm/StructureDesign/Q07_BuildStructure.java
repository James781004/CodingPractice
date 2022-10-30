package FuckingAlgorithm.StructureDesign;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Q07_BuildStructure {
//    https://leetcode.cn/problems/implement-queue-using-stacks/
//    232. 用棧實現隊列
//    請你僅使用兩個棧實現先入先出隊列。隊列應當支持一般隊列支持的所有操作（push、pop、peek、empty）：
//
//    實現 MyQueue 類：
//
//    void push(int x) 將元素 x 推到隊列的末尾
//    int pop() 從隊列的開頭移除並返回元素
//    int peek() 返回隊列開頭的元素
//    boolean empty() 如果隊列為空，返回 true ；否則，返回 false
//    說明：
//
//    你 只能 使用標准的棧操作 —— 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
//    你所使用的語言也許不支持棧。你可以使用 list 或者 deque（雙端隊列）來模擬一個棧，只要是標准的棧操作即可。

    class MyQueue {
        private Stack<Integer> s1, s2;

        public MyQueue() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        /**
         * 添加元素到隊尾
         */
        public void push(int x) {
            s1.push(x);
        }

        /**
         * 刪除隊頭的元素並返回
         */
        public int pop() {
            // 先調用 peek 保證 s2 非空
            peek();
            return s2.pop();
        }

        /**
         * 返回隊頭元素
         */
        public int peek() {
            if (s2.isEmpty())
                // 把 s1 元素壓入 s2
                while (!s1.isEmpty())
                    s2.push(s1.pop());
            return s2.peek();
        }

        /**
         * 判斷隊列是否為空
         */
        public boolean empty() {
            return s1.isEmpty() && s2.isEmpty();
        }
    }


    //    https://leetcode.cn/problems/implement-stack-using-queues/
//    225. 用隊列實現棧
//    請你僅使用兩個隊列實現一個後入先出（LIFO）的棧，並支持普通棧的全部四種操作（push、top、pop 和 empty）。
//
//    實現 MyStack 類：
//
//    void push(int x) 將元素 x 壓入棧頂。
//    int pop() 移除並返回棧頂元素。
//    int top() 返回棧頂元素。
//    boolean empty() 如果棧是空的，返回 true ；否則，返回 false 。
//
//
//    注意：
//
//    你只能使用隊列的基本操作 —— 也就是 push to back、peek/pop from front、size 和 is empty 這些操作。
//    你所使用的語言也許不支持隊列。 你可以使用 list （列表）或者 deque（雙端隊列）來模擬一個隊列 , 只要是標准的隊列操作即可。
    class MyStack {
        Queue<Integer> q = new LinkedList<>();
        int top_elem = 0;

        /**
         * 添加元素到棧頂
         */
        public void push(int x) {
            // x 是隊列的隊尾，是棧的棧頂
            q.offer(x);
            top_elem = x;
        }

        /**
         * 返回棧頂元素
         */
        public int top() {
            return top_elem;
        }

        /**
         * 刪除棧頂的元素並返回
         */
        public int pop() {
            int size = q.size();
            // 留下隊尾 2 個元素
            while (size > 2) {
                q.offer(q.poll());
                size--;
            }
            // 記錄新的隊尾元素
            top_elem = q.peek();
            q.offer(q.poll());
            // 刪除之前的隊尾元素
            return q.poll();
        }

        /**
         * 判斷棧是否為空
         */
        public boolean empty() {
            return q.isEmpty();
        }
    }


    // 最大二叉堆的Java實現
    // https://blog.csdn.net/wodong/article/details/78751778
    public class BinaryHeap<AnyType extends Comparable<? super AnyType>> {

        private static final int DEFAULT_CAPACITY = 10;

        private int currentSize;

        private AnyType[] array;

        public BinaryHeap() {
            currentSize = 0;
            array = (AnyType[]) new Comparable[DEFAULT_CAPACITY];
        }

        public BinaryHeap(int capacity) {
            currentSize = 0;
            array = (AnyType[]) new Comparable[capacity];
        }

        /**
         * 遞歸方式初始化
         *
         * @param items
         */
        public BinaryHeap(AnyType[] items) {
            this(items, true);
        }

        /**
         * 采用遞歸方式或者迭代（循環加動態步長）方式初始化
         *
         * @param items
         * @param useRecurse
         */
        public BinaryHeap(AnyType[] items, boolean useRecurse) {
            currentSize = items.length;
            array = (AnyType[]) new Comparable[items.length + DEFAULT_CAPACITY];
            int i = 1;
            for (AnyType item : items) {
                array[i++] = item;
            }
            if (useRecurse) {
                for (i = currentSize / 2; i > 0; i--) {
                    percolateDownByRecurse(i);
                }
            } else {
                for (i = currentSize / 2; i > 0; i--) {
                    percolateDown(i);
                }
            }
        }

        /**
         * 采用遞歸方式
         *
         * @param hole
         */
        private void percolateDownByRecurse(int hole) {
            int childLeft = hole * 2;
            if (childLeft > currentSize) {
                return;
            }

            // 先和左節點比，再和右節點比，一下實際上是取左節點，右節點和自身節點得最大值放入自身節點，左右節點再往下比
            // 更好的做法是現將左節點和右節點比較，然後選取較大的和自身節點比較。這樣只遞歸一個分支就可以了，可以用循環實現，見percolateDown的實現
            if (array[hole].compareTo(array[childLeft]) < 0) {
                AnyType temp = array[hole];
                array[hole] = array[childLeft];
                array[childLeft] = temp;
                percolateDownByRecurse(childLeft);
            }

            int childRight = childLeft + 1;
            if (childRight > currentSize) {
                return;
            }

            if (array[hole].compareTo(array[childRight]) < 0) {
                AnyType temp = array[hole];
                array[hole] = array[childRight];
                array[childRight] = temp;
                percolateDownByRecurse(childRight);
            }

        }

        /**
         * （最優解） 采用迭代（循環加動態步長）方式(下濾)
         *
         * @param hole
         */
        private void percolateDown(int hole) {
            while (hole * 2 <= currentSize) {
                int child = hole * 2;
                // 取左右節點的最大節點的索引
                if (child != currentSize && array[child + 1].compareTo(array[child]) > 0) {
                    child++;
                }
                // 過濾下沉方式確定每個元素位置
                if (array[hole].compareTo(array[child]) < 0) {
                    AnyType temp = array[hole];
                    array[hole] = array[child];
                    array[child] = temp;
                    hole = child;
                } else {
                    break;
                }
            }
        }

        public void insert(AnyType[] items) {
            for (AnyType item : items) {
                insert(item);
            }
        }

        /**
         * （最優解）采用迭代（循環加動態步長）的方式實現插入
         *
         * @param x
         */
        public void insert(AnyType x) {
            // 自動增長
            autoEnlargeArray();
            // 將新插入元素放入最後（這裡只是拿到新加入的最後一個位置，把這個位置想象成洞，並沒有實際放入該元素）
            int hole = ++currentSize;
            // 這個循環的邏輯是每次和父節點比較，如果比父節點大，將父節點放入洞中，將洞上浮到父節點位置，然後繼續循環，直到根節點退出
            // 這個循環的步長為動態的 hole/2
            while (hole > 1) {
                int parent = hole / 2;
                if (x.compareTo(array[parent]) > 0) {
                    array[hole] = array[parent];
                    hole = parent;
                } else {
                    break;
                }
            }
            // 最後將待插入元素插入選好的洞中
            array[hole] = x;
        }

        /**
         * 采用遞歸的形式實現插入
         *
         * @param x
         */
        public void insertByRecurse(AnyType x) {
            autoEnlargeArray();
            array[++currentSize] = x;
            percolateUp(currentSize);
        }

        /**
         * 將插入元素保持或上浮到合適位置(上濾)
         *
         * @param index
         */
        private void percolateUp(int index) {
            int parent = index / 2;
            if (parent == 0) {
                return;
            }
            if (array[index].compareTo(array[parent]) > 0) {
                AnyType temp = array[index];
                array[index] = array[parent];
                array[parent] = temp;
                percolateUp(parent);
            }
        }

        public AnyType findMax() {
            return array[1];
        }

        // extract max
        public AnyType deleteMax() {
            if (isEmpty()) {
                return null;
            }
            //提出根節點元素
            AnyType max = findMax();
            //將最後一個元素置於根節點，並將size減一
            array[1] = array[currentSize--];
            //
            percolateDown(1);
            return max;
        }

        public boolean isEmpty() {
            return currentSize == 0;
        }

        public void makeEmpty() {
            currentSize = 0;
            array = null;
        }

        private void autoEnlargeArray() {
            if (currentSize >= array.length + 1) {
                AnyType[] temp = (AnyType[]) new Comparable[array.length * 2 + 1];
                for (int i = 1; i < array.length; i++) {
                    temp[i] = array[i];
                }
                array = temp;
            }
        }

        /*
         * 降低某節點優先級
         * 簡單設計，僅供參考，當AnyType為Integer時有效
         */
        public void decreaseKey(int index, int value) {
            if (index > currentSize) {
                return;
            }
            if (array[index] instanceof Integer) {
                Integer current = (Integer) array[index];
                Integer now = current - value;
                array[index] = (AnyType) now;
            }
            percolateDown(index);
        }

        /*
         * 增加某節點優先級
         * 簡單設計，僅供參考，當AnyType為Integer時有效
         */
        public void increaseKey(int index, int value) {
            if (index > currentSize) {
                return;
            }
            if (array[index] instanceof Integer) {
                Integer current = (Integer) array[index];
                Integer now = current + value;
                array[index] = (AnyType) now;
            }
            percolateUp(index);
        }

        /**
         * 刪除某節點
         * 簡單設計，僅供參考，當AnyType為Integer時有效
         *
         * @param index
         */
        public void delete(int index) {
            increaseKey(index, Integer.MAX_VALUE / 2);
            deleteMax();
        }

        @Override
        public String toString() {
            String value = "[";
            for (int i = 1; i <= currentSize; i++) {
                if (i != 1) {
                    value += ", ";
                }
                value += array[i];
            }
            value += "]";
            return value;
        }

    }
}
