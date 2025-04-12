package EndlessCheng.GenreMenu.BinarySearch.IndirectValue;

import java.util.Arrays;

public class MaxProfit {

    // https://leetcode.cn/problems/sell-diminishing-valued-colored-balls/solutions/2813962/1648-xiao-shou-jie-zhi-jian-shao-de-yan-xb14k/
    static final int MODULO = 1000000007;

    public int maxProfit(int[] inventory, int orders) {
        long profit = 0;

        // 每種顏色的球的剩余數目的最大值為非負整數，因此 low 的初始值等於 1；
        // 由於當沒有賣出任何球時，每種顏色的球的剩余數目的最大值為數組 inventory 中的最大值，
        // 因此 high 的初始值等於數組 inventory 中的最大值
        int low = 0, high = Arrays.stream(inventory).max().getAsInt();

        // 每次查找時，取 mid 為 low 和 high 的平均數向下取整，將 mid 作為每種顏色的球的剩余數目的最大值，
        // 判斷賣出的球的最小數目是否小於等於 orders，
        // 如果賣出的球的最小數目小於等於 orders，則 maxRemain≤mid，因此在 [low,mid] 中繼續查找。
        // 如果賣出的球的最小數目大於 orders，則 maxRemain>mid，因此在 [mid+1,high] 中繼續查找。
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (atMostOrders(inventory, orders, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        // 遍歷數組 inventory，對於每個大於 low 的元素 num，該元素對應賣出的球的數目是 num−low，
        // 對於最大總價值的貢獻值為 (num+low+1)×(num−low)÷2，將答案增加該貢獻值，將 orders 的值減少 num−low。
        for (int num : inventory) {
            if (num > low) {
                profit += (long) (num + low + 1) * (num - low) / 2;
                profit %= MODULO;
                orders -= num - low;
            }
        }

        // 遍歷結束之後，剩余需要賣 orders 個球，每個球的價值是 low，因此將答案增加 low×orders
        profit += (long) low * orders;
        profit %= MODULO;
        return (int) profit;
    }

    // 如果每種顏色的球的剩余數目的最大值為 bound，則對於數組 inventory 中的每個元素 num，
    // 該元素對應需要賣出的球的數目是 max(num−inventory,0)，遍歷數組 inventory 即可得到需要賣出的球的最小數目。
    public boolean atMostOrders(int[] inventory, int orders, int bound) {
        long total = 0;
        for (int num : inventory) {
            total += Math.max(num - bound, 0);
            if (total > orders) {
                return false;
            }
        }
        return true;
    }


}
