package 劍指offer.Arrays;

public class Q4_ReplaceSpace {
    public String replaceSpace(String str) {
        if (str == null) return null;

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (String.valueOf(str.charAt(i)).equals(" ")) {
                sb.append("%20");
            } else {
                sb.append(str.charAt(i));
            }
        }

        return sb.toString();
    }
}
