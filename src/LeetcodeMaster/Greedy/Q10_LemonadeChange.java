package LeetcodeMaster.Greedy;

public class Q10_LemonadeChange {
//    860.檸檬水找零
//    https://github.com/youngyangyang04/leetcode-master/blob/master/problems/0860.%E6%9F%A0%E6%AA%AC%E6%B0%B4%E6%89%BE%E9%9B%B6.md
//
//    在檸檬水攤上，每一杯檸檬水的售價為 5 美元。
//
//    顧客排隊購買你的產品，（按賬單 bills 支付的順序）一次購買一杯。
//
//    每位顧客只買一杯檸檬水，然後向你付 5 美元、10 美元或 20 美元。你必須給每個顧客正確找零，也就是說凈交易是每位顧客向你支付 5 美元。
//
//    注意，一開始你手頭沒有任何零錢。
//
//    如果你能給每位顧客正確找零，返回 true ，否則返回 false 。
//
//    示例 1：
//
//    輸入：[5,5,5,10,20]
//    輸出：true
//    解釋：
//    前 3 位顧客那里，我們按順序收取 3 張 5 美元的鈔票。
//    第 4 位顧客那里，我們收取一張 10 美元的鈔票，並返還 5 美元。
//    第 5 位顧客那里，我們找還一張 10 美元的鈔票和一張 5 美元的鈔票。
//    由於所有客戶都得到了正確的找零，所以我們輸出 true。
//    示例 2：
//
//    輸入：[5,5,10]
//    輸出：true
//    示例 3：
//
//    輸入：[10,10]
//    輸出：false
//    示例 4：
//
//    輸入：[5,5,10,10,20]
//    輸出：false
//    解釋：
//    前 2 位顧客那里，我們按順序收取 2 張 5 美元的鈔票。
//    對於接下來的 2 位顧客，我們收取一張 10 美元的鈔票，然後返還 5 美元。
//    對於最後一位顧客，我們無法退回 15 美元，因為我們現在只有兩張 10 美元的鈔票。
//    由於不是每位顧客都得到了正確的找零，所以答案是 false。
//    提示：
//
//            0 <= bills.length <= 10000
//    bills[i] 不是 5 就是 10 或是 20

    public boolean lemonadeChange(int[] bills) {
        int five = 0; // 實際上只有5元和10元會用來找零
        int ten = 0;

        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) { // 不用找零
                five++;
            }

            if (bills[i] == 10) { // 找5元
                five--;
                ten++;
            }

            if (bills[i] == 20) { // 找15元
                five--;
                ten--;
            }

            if (five < 0 || ten < 0) return false; // 無法找零就返回false
        }

        return true;
    }

}
