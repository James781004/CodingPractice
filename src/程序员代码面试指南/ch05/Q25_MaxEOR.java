package 程序员代码面试指南.ch05;

public class Q25_MaxEOR {

    public static int maxXorSubarray1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int[] eor = new int[arr.length];
        eor[0] = arr[0];

        // 生成eor數組，eor[i]代表arr[0..i]的異或和
        for (int i = 1; i < arr.length; i++) {
            eor[i] = eor[i - 1] ^ arr[i];
        }

        int max = Integer.MIN_VALUE;
        for (int j = 0; j < arr.length; j++) { // 以j位置結尾的情況下，每一個子數組最大的異或和
            for (int i = 0; i <= j; i++) { // 依次嘗試arr[0..j]、arr[1..j]..arr[i..j]..arr[j..j]
                max = Math.max(max, i == 0 ? eor[j] : eor[j] ^ eor[i - 1]);
            }
        }
        return max;
    }


    // 前綴樹的節點類型，每個節點向下只可能有走向0或1的路
    public static class Node {
        public Node[] nexts = new Node[2];
    }

    // 基於本題，定製前綴樹的實現
    public static class NumTrie {
        public Node head = new Node(); // 頭節點

        // 把某個數字newNum加入到這棵前綴樹里
        // num是一個32位的整數，所以加入的過程一共走32步
        public void add(int newNum) {
            Node cur = head;
            for (int move = 31; move >= 0; move--) {
                int path = ((newNum >> move) & 1);
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                cur = cur.nexts[path];
            }
        }

        // 給定一個eorj，eorj表示eor[j]，即以j位置結尾的情況下，arr[0..j]的異或和
        // 因為之前把eor[0]、eor[1]...eor[j-1]都加入過這棵前綴樹，所以可以選擇出一條最優路徑來
        // maxXor方法就是把最優路徑找到，並且返回eor[j]與最優路徑結合之後得到的最大異或和
        public int maxXor(int eorj) {
            Node cur = head;
            int res = 0;
            for (int move = 31; move >= 0; move--) {
                int path = ((eorj >> move) & 1);
                int best = move == 31 ? path : (path ^ 1);
                best = cur.nexts[best] != null ? best : (best ^ 1);
                res |= (path ^ best) << move;
                cur = cur.nexts[best];
            }
            return res;
        }
    }

    public static int maxXorSubarray2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int max = Integer.MIN_VALUE;
        int eor = 0;
        NumTrie numTrie = new NumTrie();
        numTrie.add(0);
        for (int j = 0; j < arr.length; j++) {
            eor ^= arr[j];
            max = Math.max(max, numTrie.maxXor(eor));
            numTrie.add(eor);
        }
        return max;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random())
                    - (int) (maxValue * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 30;
        int maxValue = 50;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int comp = maxXorSubarray1(arr);
            int res = maxXorSubarray2(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
        //
        // // int[] arr = generateRandomArray(6, maxValue);
        // int[] arr = { 3, -28, -29, 2};
        //
        // for (int i = 0; i < arr.length; i++) {
        // System.out.println(arr[i] + " ");
        // }
        // System.out.println("=========");
        // System.out.println(maxXorSubarray(arr));
        // System.out.println((int) (-28 ^ -29));

    }
}
