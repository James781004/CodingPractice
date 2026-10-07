package EndlessCheng.GenreMenu.DP.Enhance.PrefixSum;

public class JumpGameVII {

    // https://leetcode.cn/problems/jump-game-vii/solutions/791314/qian-zhui-he-you-hua-dp-by-endlesscheng-k9d2/
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();
        int[] f = new int[n];
        int[] sum = new int[n + 1]; // f 的前綴和
        f[0] = sum[1] = 1;

        for (int j = 1; j < n; j++) {
            if (j >= minJump && s.charAt(j) == '0' && sum[j - minJump + 1] > sum[Math.max(j - maxJump, 0)]) {
                f[j] = 1;
            }
            sum[j + 1] = sum[j] + f[j];
        }

        return f[n - 1] == 1;
    }


    public boolean canReach2(String s, int minJump, int maxJump) {
        int n = s.length();
        int[] sum = new int[n + 1]; // f 的前綴和
        sum[1] = 1; // f[0] = true

        for (int j = 1; j < n; j++) {
            sum[j + 1] = sum[j];
            if (j >= minJump && s.charAt(j) == '0' && sum[j - minJump + 1] > sum[Math.max(j - maxJump, 0)]) {
                sum[j + 1]++; // f[j] = true
            }
        }

        return sum[n] > sum[n - 1]; // f[n-1] == true
    }

}
