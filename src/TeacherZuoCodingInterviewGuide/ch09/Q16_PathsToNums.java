package TeacherZuoCodingInterviewGuide.ch09;

public class Q16_PathsToNums {
//    CD67 路徑數組變為統計數組
//    描述
//    給定一個路徑統計數組paths，表示一張圖。
//    paths[i]==j代表城市i連向城市j，如果paths[i]==i，則表示i城市是首都，
//    一張圖里只會有一個首都且圖中除首都指向自己之外不會有環。
//    例如，paths=[9,1,4,9,0,4,8,9,0,1]，代表的圖如圖9-14所示。
//    由數組表示的圖可以知道，城市1是首都，所以距離為0，離首都距離為1的城市只有城市9，
//    離首都距離為2的城市有城市0、3和7，離首都距離為3的城市有城市4和8，
//    離首都距離為4的城市有城市2、5和6。
//    所以距離為0的城市有1座，距離為1的城市有1座，距離為2的城市有3座，
//    距離為3的城市有2座，距離為4的城市有3座。那麽統計數組為nums=[1,1,3,2,3,0,0,0,0,0]
//            [要求]
//    時間覆雜度為O(n)，額外空間覆雜度為O(1)


    public static void pathsToNums(int[] paths) {
        if (paths == null || paths.length == 0) return;
        // citiesPath -> distancesArray
        pathsToDistans(paths);

        // distancesArray -> numArray
        distansToNums(paths);
    }


    // paths 數組改寫：
    //
    // 從左到右遍歷paths，先遍歷到位置0：
    // paths[0] == 9，首先令paths[0] == -1(這是為了標記起跳的城市)，因為城市0指向城市9，所以跳到城市9.
    // 跳到城市9之後，paths[9] == 1，說明下一個城市是1，因為城市9是由城市0跳過來的，所以先令paths[9] = 0，然後跳向城市1。
    // 跳到城市1之後，paths[1] == 1，說明城市1是首都。現在開始往回跳，城市1是由城市9跳過來的，所以跳回城市9。
    // 根據之前設置的paths[9] == 0，我們知道城市9是由城市0跳過來的，
    // 在回跳之前先設置paths[9] = -1，表示城市9到首都的距離為1，之後回跳到0。
    // 根據之前的設置paths[0] == -1，我們知道城市0是起跳位置，所以不再回跳，令paths[0] == -2，表示到首都的距離為2。
    // 以上在跳向首都的過程中，paths數組有一個路徑反指的過程，這是為了保證找到首都之後，能夠完全跳回來。
    // 在跳回來的過程中，設置好這一路所跳的城市 即可。
    // 對於其他位置，跳躍的過程同上，但是跳躍終止的條件可以不是跳到首都，
    // 當我們跳到下一個位置發現它的值是負數，說明這個位置已經計算出了到首都的距離，
    // 我們只要在這個基礎上來設置距離即可。
    // 首都我們單獨處理即可，找到首都的位置然後將它的值設為0，表示距離為0。
    public static void pathsToDistans(int[] paths) {
        int cap = 0;
        for (int i = 0; i != paths.length; i++) {
            if (paths[i] == i) {
                cap = i;
            } else if (paths[i] > -1) {
                int curI = paths[i];
                paths[i] = -1; // 起跳的城市先設為-1，原值curI保留，作為交換用參數
                int preI = i; // preI表示跳到當前位置的前一個位置，初始化當然是i
                while (paths[curI] != curI) {
                    if (paths[curI] > -1) {
                        int nextI = paths[curI]; // 保存paths[curI]原值，作為交換用參數
                        paths[curI] = preI; // paths[curI]是從preI位置來的，所以記錄下來
                        preI = curI; // preI位置移動到curI
                        curI = nextI; // curI位置是接下來要跳過去的位置
                    } else {
                        break;
                    }
                }

                // while結束的可能性有二：
                // 1. paths[curI] == curI的情況，根據題義就是首都，距離首都的距離當然是0
                // 2. paths[curI] <= -1，根據題義補可能有環，所以必定是之前已經求出答案的位置，就直接取paths[curI]值即可
                int value = paths[curI] == curI ? 0 : paths[curI];
                while (paths[preI] != -1) {
                    int lastPreI = paths[preI];
                    paths[preI] = --value; // 回跳過程中不斷減少value，用以表示到首都的距離
                    curI = preI;
                    preI = lastPreI;
                }
                paths[preI] = --value; // 找到原起跳位置，再減少value，用以表示到首都的距離
            }
        }
        paths[cap] = 0; // 找到首都的位置然後將它的值設為0，表示距離為0。
    }


    // paths 數組改寫後的迭代：
    //
    // 得到距離數組後，數組中的距離值都用負數表示。接下來我們根據距離數組來計算得到統計數組，該過程也是一個跳躍的過程。
    //
    // 從左到右遍歷數組，假設遍歷到為 i，paths[i] == -j，那麽我們可以知道城市 i 距離首都的距離是 j ，
    // 先令paths[i] == 0，表示此位置不再代表距離，然後跳到位置 j 處，
    // 如果位置 j 處的值為負數 -k，我們令 paths[j] = 1，表示我們已經找到一個距離為 j 的城市(城市 i )。
    // 然後根據 k 繼續往下跳。如果位置 j 處的值為正數，說明該位置已經是距離為 j 的城市的數量統計，直接加1即可。
    // 此時paths[j]不再代表距離，而是表示距離為 j 的城市數量
    public static void distansToNums(int[] disArr) {
        for (int i = 0; i != disArr.length; i++) {
            int index = disArr[i];

            // index如果大於等於0，表示該位置已經不再代表距離首都距離，直接跳過不處理
            // 統計的工作會交給其他loop中的while處理
            if (index < 0) {
                disArr[i] = 0; // important，表示從現在起此位置不再代表距離首都距離
                while (true) {
                    index = -index; // 準備跳到位置index處
                    if (disArr[index] > -1) {
                        // 如果位置index的值為正數，
                        // 說明該位置已經是距離為index的城市的數量統計，直接加1即可。
                        disArr[index]++;
                        break;
                    } else {
                        // 如果位置index處的值為負數 -k，令 paths[index] = 1，
                        // 表示我們已經找到一個離首都距離為index的城市(城市 i )。
                        int nextIndex = disArr[index];
                        disArr[index] = 1;
                        index = nextIndex;
                    }
                }
            }
        }
        disArr[0] = 1;
    }

    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i != arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] paths = {9, 1, 4, 9, 0, 4, 8, 9, 0, 1};
        printArray(paths);
        pathsToNums(paths);
        printArray(paths);

    }
}
