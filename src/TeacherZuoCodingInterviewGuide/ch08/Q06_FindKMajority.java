package TeacherZuoCodingInterviewGuide.ch08;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Q06_FindKMajority {
//    CD155 在數組中找到出現次數大於n/k的數
//    描述
//    給定一個整型數組arr，再給定一個整數k，打印所有出現次數大於n/k的數，如果沒有這樣的數,請打印”-1“。

    // 找大於一半的數
    public static void printHalfMajor(int[] arr) {
        int cand = 0;
        int times = 0;

        // 第一個遍歷把數字兩兩相消，看最後剩下誰
        for (int i = 0; i < arr.length; i++) {
            if (times == 0) {
                cand = arr[i];
                times = 1;
            } else if (arr[i] == cand) {
                times++;
            } else {
                times--;
            }
        }

        // 第二個遍歷把先前最後剩下的數字統計次數，如果超過一半就是答案
        times = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == cand) {
                times++;
            }
        }

        if (times > arr.length / 2) {
            System.out.println(cand);
        } else {
            System.out.println("no such number.");
        }
    }

    public static void printKMajor(int[] arr, int K) {
        if (K < 2) {
            System.out.println("the value of K is invalid.");
            return;
        }

        // 與上面方法一樣，只是這次第一次遍歷用map存放候選名單，遇見不同數字全部減1
        HashMap<Integer, Integer> cands = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            if (cands.containsKey(arr[i])) {
                cands.put(arr[i], cands.get(arr[i]) + 1);
            } else {
                if (cands.size() == K - 1) {
                    allCandsMinusOne(cands);
                } else {
                    cands.put(arr[i], 1);
                }
            }
        }

        // 統計候選名單中的實際出現次數，進入第二次遍歷，如果達到目標就打印
        HashMap<Integer, Integer> reals = getReals(arr, cands);
        boolean hasPrint = false;
        for (Map.Entry<Integer, Integer> set : cands.entrySet()) {
            Integer key = set.getKey();
            if (reals.get(key) > arr.length / K) {
                hasPrint = true;
                System.out.print(key + " ");
            }
        }
        System.out.println(hasPrint ? "" : "no such number.");
    }

    // map裡面全部減一，如果到0了就移除
    public static void allCandsMinusOne(HashMap<Integer, Integer> map) {
        List<Integer> removeList = new LinkedList<Integer>();
        for (Map.Entry<Integer, Integer> set : map.entrySet()) {
            Integer key = set.getKey();
            Integer value = set.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            map.put(key, value - 1);
        }
        for (Integer removeKey : removeList) {
            map.remove(removeKey);
        }
    }

    // 統計候選名單中的實際出現次數
    public static HashMap<Integer, Integer> getReals(int[] arr,
                                                     HashMap<Integer, Integer> cands) {
        HashMap<Integer, Integer> reals = new HashMap<Integer, Integer>();
        for (int i = 0; i < arr.length; i++) {
            int curNum = arr[i];
            if (cands.containsKey(curNum)) {
                if (reals.containsKey(curNum)) {
                    reals.put(curNum, reals.get(curNum) + 1);
                } else {
                    reals.put(curNum, 1);
                }
            }
        }
        return reals;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 1, 1, 2, 1};
        printHalfMajor(arr);
        int K = 4;
        printKMajor(arr, K);
    }
}
