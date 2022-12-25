package GuChengAlgorithm.Ch01_BasicDataStructure.StackAndQueue;

public class Q05_CircularDeque {

    class MyCircularDeque {
        int[] q;
        int front, rear, size, capacity;

        public MyCircularDeque(int k) {
            front = 0;
            rear = -1;
            size = 0;
            capacity = k;
            q = new int[k];
        }

        public boolean insertFront(int value) {
            if (isFull()) return false;
            if (--front < 0) front += capacity;
            q[front] = value;
            size++;
            if (size == 1) rear = front;
            return true;
        }

        public boolean insertLast(int value) {
            if (isFull()) return false;
            rear = (rear + 1) % capacity;
            q[rear] = value;
            size++;
            return true;
        }

        public boolean deleteFront() {
            if (!isEmpty()) {
                front = (front + 1) % capacity;
                size--;
                return true;
            }

            return false;
        }

        public boolean deleteLast() {
            if (!isEmpty()) {
                if (--rear < 0) rear += capacity;
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
