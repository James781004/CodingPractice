package Hackerrank.Ch06_stacks_queues;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PoisonousPlants {
    // https://www.hackerrank.com/challenges/poisonous-plants/problem?h_l=interview&isFullScreen=true&playlist_slugs%5B%5D%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D%5B%5D=stacks-queues
    public static int poisonousPlants(List<Integer> p) {
        int max = 0;
        int day = 0;

        Stack<ArrayList<Integer>> stack = new Stack<>();

        for (int i = 0; i < p.size(); i++) {
            day = 0;
            int plant = p.get(i);
            //if(!stack.empty()){
            //System.out.println(stack.peek().get(0)+" "+plant);
            //}
            while (!stack.empty() && stack.peek().get(0) >= plant) {

                // System.out.println(stack);
                day = Math.max(day, stack.pop().get(1));

            }


            if (!stack.empty()) {
                day++;
            } else {
                day = 0;
            }
            max = Math.max(day, max);
            ArrayList<Integer> arr = new ArrayList<Integer>();
            arr.add(plant);
            arr.add(day);
            stack.push(arr);
            //System.out.println(stack);
            // System.out.println(arr);
        }
        return max;
    }
}
