package GuChengAlgorithm.Ch01_BasicDataStructure.StackAndQueue;

public class Q04_CircularQueue {

    class MyCircularQueue {
        int[] q;
        int front, rear, size;

        public MyCircularQueue(int k) {
            front = 0;
            rear = -1;
            size = 0;
            q = new int[k];
        }

        public boolean enQueue(int value) {
            if (!isFull()) {
                rear = (rear + 1) % q.length;
                q[rear] = value;
                size++;
                return true;
            }

            return false;
        }

        public boolean deQueue() {
            if (!isEmpty()) {
                front = (front + 1) % q.length;
                size--;
                return true;
            }

            return false;
        }

        public int front() {
            return isEmpty() ? -1 : q[front];
        }

        public int rear() {
            return isEmpty() ? -1 : q[rear];
        }

        public boolean isFull() {
            return size == q.length;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }
}
