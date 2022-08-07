package 程序员代码面试指南.ch08;

public class Q30_GasStations {
    //    CD53 加油站良好出發點問題
//    描述
//    N個加油站組成一個環形，給定兩個長度都是N的非負數組oil和dis(N>1)，
//    oil[i]代表第i個加油站存的油可以跑多少千米，
//    dis[i]代表第i個加油站到環中下一個加油站相隔多少千米。
//    假設你有一輛油箱足夠大的車，初始時車里沒有油。
//    如果車從第i個加油站出發，最終可以回到這個加油站，那麽第i個加油站就算良好出發點，否則就不算。
//    請返回長度為N的boolean型數組res，res[i]代表第i個加油站是不是良好出發點
//    規定只能按照順時針走，也就是i只能走到i+1，N只能走到1
//[要求]
//    時間覆雜度為O(n)，空間覆雜度為O(1)

    public static boolean[] stations(int[] dis, int[] oil) {
        if (dis == null || oil == null || dis.length < 2
                || dis.length != oil.length) {
            return null;
        }

        // 先將dis初始化成「油耗數組」，用oil數組的相應元素減去dis數組的相應元素即可
        // 這時候dis數組的節點代表經過當前節點並準備前往下一節點的「剩餘油量」
        // 之後選定一個剩餘油量為正數的節點當成開頭
        int init = changeDisArrayGetInit(dis, oil);

        // 問題轉化成了：求符合“從該位置出發，累加一圈的過程中不出現負數”的所有位置。
        // 所以嘗試從init出發，不斷擴大「連通區域」來求解
        return init == -1 ? new boolean[dis.length] : enlargeArea(dis, init);
    }

    public static int changeDisArrayGetInit(int[] dis, int[] oil) {
        int init = -1;
        for (int i = 0; i < dis.length; i++) {
            dis[i] = oil[i] - dis[i];
            if (dis[i] >= 0) {
                init = i;
            }
        }
        return init;
    }

    public static boolean[] enlargeArea(int[] dis, int init) {
        boolean[] res = new boolean[dis.length];
        int start = init;
        int end = nextIndex(init, dis.length); // 左閉右開，end表示目前可從start抵達end之前的所有節點(連通區)
        int need = 0; // 表示積欠油量
        int rest = 0;
        do {
            // 當前來到的start已經在連通區域中，並且已經是終點的前一個節點
            // 可以確定後續的開始點一定無法轉完一圈
            if (start != init && start == lastIndex(end, dis.length)) {
                break;
            }
            // 當前來到的start不在連通區域中，就擴充連通區域
            if (dis[start] < need) {
                // 從當前start出發，無法到達init點，連通區先往前面方向開始擴
                // 因為從init之前的節點抵達連通區，其需求必須消耗掉need油量
                // 所以往前找尋新的start同時，必須算上當前start節點積欠的消耗油量dis[start]
                need -= dis[start];
            } else {
                // 如果當前start節點剩餘油量多過積欠的need油量
                // 表示這個start可以到達原本以init點開頭的連通區域
                // 剩餘油量抵消掉積欠的need油量，然後need歸零
                rest += dis[start] - need;
                need = 0;

                // 有剩餘的油就往後走，連通區end就可以往後面方向擴充
                while (rest >= 0 && end != start) {
                    rest += dis[end];
                    end = nextIndex(end, dis.length);
                }

                // 如果上面while結束後rest不是負數，表示連通區域已經可以覆蓋整個環
                // 而當前的start也確定是良好出發點，可以進入2階段
                if (rest >= 0) {
                    res[start] = true;

                    // 2階段找到所有能到達start的點，它們全部都是良好出發點
                    connectGood(dis, lastIndex(start, dis.length), init, res);
                    break;
                }
            }

            start = lastIndex(start, dis.length); // start往前面走，找尋其他可能性
        } while (start != init);
        return res;
    }


    // 已知start的next方向上有一個良好出發點
    // start如果可以達到這個良好出發點，那麼從start出發一定可以轉一圈
    public static void connectGood(int[] dis, int start, int init, boolean[] res) {
        int need = 0;
        while (start != init) {
            if (dis[start] < need) {
                need -= dis[start];  // 能量不夠，往前找尋新的start同時，必須算上當前start節點積欠的消耗油量dis[start]
            } else {
                res[start] = true;  // 能量夠，可以到達
                need = 0;
            }
            start = lastIndex(start, dis.length); // start往前面走，找尋其他可能性
        }
    }


    public static int lastIndex(int index, int size) {
        return index == 0 ? (size - 1) : index - 1;
    }

    public static int nextIndex(int index, int size) {
        return index == size - 1 ? 0 : (index + 1);
    }

    // for test
    public static boolean[] test(int[] dis, int[] oil) {
        if (dis == null || oil == null || dis.length < 2
                || dis.length != oil.length) {
            return null;
        }
        boolean[] res = new boolean[dis.length];
        for (int i = 0; i < dis.length; i++) {
            dis[i] = oil[i] - dis[i];
        }
        for (int i = 0; i < dis.length; i++) {
            res[i] = canWalkThrough(dis, i);
        }
        return res;
    }

    // for test
    public static boolean canWalkThrough(int[] arr, int index) {
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[index];
            if (sum < 0) {
                return false;
            }
            index = nextIndex(index, arr.length);
        }
        return true;
    }

    // for test
    public static void printArray(int[] dis, int[] oil) {
        for (int i = 0; i < dis.length; i++) {
            System.out.print(oil[i] - dis[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void printBooleanArray(boolean[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static int[] generateArray(int size, int max) {
        int[] res = new int[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = (int) (Math.random() * max);
        }
        return res;
    }

    // for test
    public static int[] copyArray(int[] arr) {
        int[] res = new int[arr.length];
        for (int i = 0; i < res.length; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    // for test
    public static boolean isEqual(boolean[] res1, boolean[] res2) {
        for (int i = 0; i < res1.length; i++) {
            if (res1[i] != res2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int max = 20;
        for (int i = 0; i < 5000000; i++) {
            int size = (int) (Math.random() * 20) + 2;
            int[] dis = generateArray(size, max);
            int[] oil = generateArray(size, max);
            int[] dis1 = copyArray(dis);
            int[] oil1 = copyArray(oil);
            int[] dis2 = copyArray(dis);
            int[] oil2 = copyArray(oil);
            boolean[] res1 = stations(dis1, oil1);
            boolean[] res2 = test(dis2, oil2);
            if (!isEqual(res1, res2)) {
                printArray(dis, oil);
                printBooleanArray(res1);
                printBooleanArray(res2);
                System.out.println("what a fucking day!");
                break;
            }
        }
    }
}
