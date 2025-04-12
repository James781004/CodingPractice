package EndlessCheng.GenreMenu.BinarySearch.Basic;

public class NextGreatestLetter {

    // https://leetcode.cn/problems/find-smallest-letter-greater-than-target/solutions/2880990/can-kao-ling-shen-de-er-fen-kai-qu-jian-52qcx/
    public char nextGreatestLetter(char[] letters, char target) {
        if (target >= letters[letters.length - 1]) {
            return letters[0];
        }
        return binary(letters, (char) (target + 1));
    }

    private char binary(char[] letters, char target) {
        int left = -1, right = letters.length;
        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (letters[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return letters[right];
    }


}
