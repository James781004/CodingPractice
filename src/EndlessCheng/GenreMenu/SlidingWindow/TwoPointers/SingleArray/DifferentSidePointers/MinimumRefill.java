package EndlessCheng.GenreMenu.SlidingWindow.TwoPointers.SingleArray.DifferentSidePointers;

public class MinimumRefill {

    // https://leetcode.cn/problems/watering-plants-ii/solutions/1153072/shuang-zhi-zhen-mo-ni-by-endlesscheng-9l76/
    public int minimumRefill(int[] plants, int capacityA, int capacityB) {
        int ans = 0;
        int a = capacityA;
        int b = capacityB;
        int i = 0;
        int j = plants.length - 1;
        while (i < j) {
            // Alice 給植物 i 澆水
            if (a < plants[i]) {
                // 沒有足夠的水，重新灌滿水罐
                ans++;
                a = capacityA;
            }
            a -= plants[i++];
            // Bob 給植物 j 澆水
            if (b < plants[j]) {
                // 沒有足夠的水，重新灌滿水罐
                ans++;
                b = capacityB;
            }
            b -= plants[j--];
        }
        // Alice 和 Bob 到達同一株植物，那麼當前水罐中水更多的人會給這株植物澆水
        if (i == j && Math.max(a, b) < plants[i]) {
            // 沒有足夠的水，重新灌滿水罐
            ans++;
        }
        return ans;
    }


}
