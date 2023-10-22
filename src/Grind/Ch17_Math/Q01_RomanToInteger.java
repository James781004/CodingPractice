package Grind.Ch17_Math;

public class Q01_RomanToInteger {
    // https://leetcode.cn/problems/roman-to-integer/solutions/39961/yong-shi-9993nei-cun-9873jian-dan-jie-fa-by-donesp/
    public int romanToInt(String s) {
        int sum = 0;
        int preNum = getValue(s.charAt(0));
        for (int i = 0; i < s.length(); i++) {
            int num = getValue(s.charAt(i));
            if (preNum < num) {
                sum -= preNum; // 當小值在大值的左邊，則減小值，如 IV=5-1=4
            } else {
                sum += preNum; // 當小值在大值的右邊，則加小值，如 VI=5+1=6
            }
            preNum = num; // 由上可知，右值永遠為正，因此最後一位必然為正
        }
        sum += preNum;
        return sum;
    }

    // 羅馬數字由 I,V,X,L,C,D,M 構成
    private int getValue(char ch) {
        switch (ch) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                return 0;
        }
    }


    public int romanToInt2(String s) {
        /*
        貪心+模擬
         */
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] nums = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        int res = 0;
        
        // 優先找大的數字
        for (int i = 0; i < 13; i++) {
            // 只要有對應開頭的就進行計算
            while (s.startsWith(roman[i])) {
                // 截斷前面已經計算的部分
                s = s.substring(roman[i].length());
                res += nums[i];
            }
        }
        return res;
    }
}
