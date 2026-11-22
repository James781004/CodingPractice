package EndlessCheng.GenreMenu.DataStructure.PrefixSum.Basic;

public class CountOdds {

    // https://leetcode.cn/problems/count-odd-numbers-in-an-interval-range/solutions/3844307/o1-shu-xue-jie-pythonjavaccgojsrust-by-e-86dc/
    public int countOdds(int low, int high) {
        return (high + 1) / 2 - low / 2;
    }


}
