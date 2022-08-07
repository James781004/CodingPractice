package TeacherZuoCodingInterviewGuide.ch09;

public class Q06_PaperFolding {
//    CD60 折紙問題
//    描述
//    請把一張紙條豎著放在桌子上，然後從紙條的下邊向上方對折1次，壓出折痕後展開。
//    此時折痕是凹下去的，即折痕突起的方向指向紙條的背面。
//    如果從紙條的下邊向上方連續對折2次，壓出折痕後展開，
//    此時有三條折痕，從上到下依次是下折痕、下折痕和上折痕。
//    給定一個輸入參數N，代表紙條都從下邊向上方連續對折N次，請從上到下打印所有折痕的方向。
//            [要求]
//    時間覆雜度為O(2^n)額外空間覆雜度為O(1)


    public static void printAllFolds(int N) {
        printProcess(1, N, true);
    }

    // 將對折幾次的結果寫出來看看就能發現規律。其實就是個根節點為down的滿二叉樹結構，
    // 所有的節點都滿足左孩子為down，右孩子為up，對折n次就是輸出n層樹的中序遍歷序列。
    public static void printProcess(int i, int N, boolean down) {
        if (i > N) return;
        printProcess(i + 1, N, true);
        System.out.println(down ? "down " : "up ");
        printProcess(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);

    }
}
