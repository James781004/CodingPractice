package GuChengAlgorithm.Ch02_BasicAlgorithm.Greedy;

public class Q03_Palindrome {
    // https://docs.google.com/presentation/d/18M3cuDjvBlaaMjZ2R5a6pK1pDI_ZCDbBmVniyW1PdEE/edit#slide=id.g1c33e7d9828_0_91
    public String breakPalindrome(String palindrome) {
        char[] s = palindrome.toCharArray();
        int n = s.length;
        for (int i = 0; i < n / 2; i++) {
            if (s[i] != 'a') {
                s[i] = 'a';
                return new String(s);
            }
        }
        s[n - 1] = 'b'; // if all 'a'
        return n < 2 ? "" : new String(s);
    }
}
