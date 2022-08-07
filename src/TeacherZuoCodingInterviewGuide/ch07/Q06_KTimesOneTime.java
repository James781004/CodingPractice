package TeacherZuoCodingInterviewGuide.ch07;

public class Q06_KTimesOneTime {

    public static int onceNum(int[] arr, int k) {
        int[] eO = new int[32];
        for (int i = 0; i != arr.length; i++) {
            setExclusiveOr(eO, arr[i], k);
        }
        int res = getNumFromKSysNum(eO, k);
        return res;
    }

    public static void setExclusiveOr(int[] eO, int value, int k) {
        // value先轉換成k進位置系統然後反過來的數列
        // 例如：153十進位轉換 -> [3, 5, 1]
        int[] curKSysNum = getKSysNumFromNum(value, k);

        // 每一位相加後%k即為無進位相加的結果
        for (int i = 0; i != eO.length; i++) {
            eO[i] = (eO[i] + curKSysNum[i]) % k;
        }
    }

    // value轉換成k進位置系統
    // 從左到右填入value % k的結果做頭，然後value每次循環都除掉k
    // 注意這邊res會是數字反過來寫的狀態
    public static int[] getKSysNumFromNum(int value, int k) {
        int[] res = new int[32];
        int index = 0;
        while (value != 0) {
            res[index++] = value % k;
            value = value / k;
        }
        return res;
    }

    // e0轉換回十進位置系統
    // e0是原數字反過來表示的數列，所以遍歷時也必須反過來處理
    // 而因為k個相同的k進制數無進位相加，結果一定是每一位上都是0的k進制數
    // 返回最後剩下的數就行
    public static int getNumFromKSysNum(int[] eO, int k) {
        int res = 0;
        for (int i = eO.length - 1; i != -1; i--) {
            res = res * k + eO[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int[] test1 = {1, 1, 1, 2, 6, 6, 2, 2, 10, 10, 10, 12, 12, 12, 6, 9};
        System.out.println(onceNum(test1, 3));

        int[] test2 = {-1, -1, -1, -1, -1, 2, 2, 2, 4, 2, 2};
        System.out.println(onceNum(test2, 5));

    }
}
