package GuChengAlgorithm.Ch02_BasicAlgorithm.TwoPointers;

public class Q01_VaildStrings {
    // https://docs.google.com/presentation/d/1G_A3upxVNg_LHMpS3GeTRVQE9JZli_Kbt5gnvE6WhsU/edit#slide=id.g132d75113e7_0_23
    public boolean validPalindrome(String s) {
        int i = 0, j = s.length() - 1;
        while (i < j && s.charAt(i) == s.charAt(j)) {
            i++;
            j--;
        }
        if (i >= j) return true;

        // try to delete one character
        if (isPalin(s, i + 1, j) || isPalin(s, i, j - 1)) return true;
        return false;
    }

    private boolean isPalin(String s, int i, int j) {
        while (i < j) {
            if (s.charAt(i) == s.charAt(j)) {
                i++;
                j--;
            } else return false;
        }
        return true;
    }


    // https://docs.google.com/presentation/d/1G_A3upxVNg_LHMpS3GeTRVQE9JZli_Kbt5gnvE6WhsU/edit#slide=id.g132d75113e7_0_58
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0, j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                i++;
                j++;
                continue;
            }

            // remove leading 0 cases
            if (abbr.charAt(j) <= '0' || abbr.charAt(j) > '9') return false;

            // digits sub string
            int start = j;
            while (j < abbr.length() && abbr.charAt(j) >= '0' && abbr.charAt(j) <= '9') {
                ++j;
            }

            int num = Integer.valueOf(abbr.substring(start, j));
            i += num;
        }
        return i == word.length() && j == abbr.length();
    }
}
