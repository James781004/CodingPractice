package EndlessCheng.GenreMenu.DP.OtherLinear.MultiDimension;

public class NumMusicPlaylists {

    // https://leetcode.cn/problems/number-of-music-playlists/solutions/711047/dong-tai-gui-hua-you-bu-hui-tui-zhi-hao-zgmxu/
    int mod = 1000000007;
    long[][][] dp;

    public int numMusicPlaylists(int N, int L, int K) {
        if (N == L) {
            return (int) helper(N, 0);
        }
        //按題目意思，連續的K + 1首歌是不能重復的
        dp = new long[L][N + 1][L - N + 1];
        long ans = helper(N, N - K - 1) * dfs(N, L, K, K + 1, K + 1, L - N);
        //先選取K + 1首不重復歌曲，再進行dfs
        ans %= mod;
        return (int) ans;
    }

    public long dfs(int N, int L, int K, int idx, int mark, int rep) {
        //idx當前播放了多少首，mark已播放歌曲種類的個數，rep代表剩余的可重復播放歌曲次數
        if (idx == L) {
            return 1;
        }
        if (dp[idx][mark][rep] != 0) {    //已計算過的值
            return dp[idx][mark][rep];
        }
        if (rep == 0) {   //可重復播放的次數為0，代表接下來都是沒播放過的歌曲，直接排列
            //可重復播放次數是由L - N獲得的，當L大於N時必須有歌曲重復L - N次，不能多不能少
            return helper(N - mark, 0);
        }
        long ans = 0;
        ans += (mark - K) * dfs(N, L, K, idx + 1, mark, rep - 1);
        //這一首播放重復的歌曲，有mark - K種選擇，同時可重復次數減1
        ans %= mod;
        if (mark != N) {
            ans += (N - mark) * dfs(N, L, K, idx + 1, mark + 1, rep);
            //這一首播放不重復歌曲，有N - mark種選擇，同時已播放歌曲種類加1
            ans %= mod;
        }
        dp[idx][mark][rep] = ans;   //記憶一下，其實這就是動態規劃了
        return ans;
    }

    public long helper(int num, int end) {
        //排列種類的計算
        long ans = 1;
        while (num != end) {
            ans *= num;
            ans %= mod;
            num--;
        }
        return ans;
    }

   
}
