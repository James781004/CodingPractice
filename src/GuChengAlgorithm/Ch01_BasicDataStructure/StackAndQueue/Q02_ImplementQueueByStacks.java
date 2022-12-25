package GuChengAlgorithm.Ch01_BasicDataStructure.StackAndQueue;

import java.util.Stack;

public class Q02_ImplementQueueByStacks {
    Stack<Integer> queue = new Stack<>();
    Stack<Integer> stack = new Stack<>();

    public void push(int x) {
        stack.push(x);
    }

    public int pop() {
        peek();
        return queue.pop();
    }

    public int peek() {
        if (queue.isEmpty()) {
            while (!stack.isEmpty()) queue.push(stack.pop());
        }
        return queue.peek();
    }

    public boolean empty() {
        return stack.isEmpty() && queue.isEmpty();
    }

}
