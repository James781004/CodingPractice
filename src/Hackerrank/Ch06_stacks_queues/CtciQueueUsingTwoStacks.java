package Hackerrank.Ch06_stacks_queues;

import java.util.Stack;

public class CtciQueueUsingTwoStacks {
    // https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks/problem?h_l=interview&isFullScreen=true&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=stacks-queues
    class MyQueue<Y> {

        private Stack<Y> a = new Stack<Y>();
        private Stack<Y> b = new Stack<Y>();

        public void enqueue(Y element) {
            b.push(element);
        }

        public void dequeue() {
            if (a.isEmpty()) {
                while (!b.isEmpty()) {
                    a.push(b.pop());
                }
            }
            a.pop();
        }

        public Y peek() {
            if (a.isEmpty()) {
                while (!b.isEmpty()) {
                    a.push(b.pop());
                }
            }
            return a.peek();
        }
    }
}
