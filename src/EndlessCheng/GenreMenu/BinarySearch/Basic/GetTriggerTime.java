package EndlessCheng.GenreMenu.BinarySearch.Basic;

public class GetTriggerTime {

    // https://leetcode.cn/problems/ju-qing-hong-fa-shi-jian/solutions/209177/2bu-jie-jue-jian-dan-yi-dong-er-fen-cha-zhao-javab/
    public int[] getTriggerTime(int[][] increase, int[][] requirements) {
        int[] result = new int[requirements.length];

        //1. 累加屬性值，形成一個玩家每天屬性值的數組
        for (int i = 1; i < increase.length; i++) {
            increase[i][0] += increase[i - 1][0];
            increase[i][1] += increase[i - 1][1];
            increase[i][2] += increase[i - 1][2];
        }

        //2. 遍歷觸發條件，得到每個觸發條件對應的天數
        for (int i = 0; i < requirements.length; i++) {
            //2.1 特殊情況處理：如果觸發條件對應的屬性值為0，則觸發天數也是0
            if (requirements[i][0] == 0 && requirements[i][1] == 0 && requirements[i][2] == 0) {
                result[i] = 0;
            } else {
                //2.2 本題的核心思想，使用二分查找算法來尋找左側邊界，找到觸發劇情最小的天數。
                int left = 0;
                int right = increase.length - 1;

                while (left <= right) {
                    int middle = left + (right - left) / 2;
                    if (increase[middle][0] < requirements[i][0] || increase[middle][1] < requirements[i][1] || increase[middle][2] < requirements[i][2]) {
                        left = middle + 1;
                    } else {
                        right = middle - 1;
                    }
                }
                if (left < increase.length && increase[left][0] >= requirements[i][0] && increase[left][1] >= requirements[i][1] && increase[left][2] >= requirements[i][2]) {
                    result[i] = left + 1;
                } else {
                    result[i] = -1;
                }
            }
        }
        return result;
    }


}
