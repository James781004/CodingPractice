package EndlessCheng.GenreMenu.DP.OtherLinear.OneDimention;

public class MaximumEnergy {

    // https://leetcode.cn/problems/taking-maximum-energy-from-the-mystic-dungeon/solutions/2774844/o1-kong-jian-zuo-fa-pythonjavacgo-by-end-8rad/
    public int maximumEnergy(int[] energy, int k) {
        int n = energy.length;
        int ans = Integer.MIN_VALUE;
        for (int i = n - k; i < n; i++) { // 枚舉終點 i
            int sufSum = 0;
            for (int j = i; j >= 0; j -= k) {
                sufSum += energy[j]; // 計算後綴和
                ans = Math.max(ans, sufSum);
            }
        }
        return ans;
    }


}
