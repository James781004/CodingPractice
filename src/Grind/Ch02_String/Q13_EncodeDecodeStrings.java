package Grind.Ch02_String;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q13_EncodeDecodeStrings {
    // https://www.twblogs.net/a/5b7fe57b2b717767c6b25d75
    // https://www.youtube.com/watch?v=ylzMfyycng8
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            char[] chArr = str.toCharArray();
            for (char ch : chArr) {
                switch (ch) {
                    case ',':
                        sb.append(",!");
                        break;
                    case '!':
                        sb.append("!!");
                        break;
                    default:
                        sb.append(ch);
                        break;
                }
            }
            sb.append(",");
        }

        return sb.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> result = new LinkedList<String>();
        StringBuilder sb = new StringBuilder();
        char[] chArr = s.toCharArray();
        for (int i = 0; i < chArr.length; i++) {
            if (chArr[i] == ',') {
                int cursor = i + 1;
                while (cursor < chArr.length && chArr[cursor] == '!') {
                    cursor++;
                }

                if ((cursor - i) % 2 == 1) {
                    result.add(sb.toString());
                    sb.delete(0, sb.length());
                } else {
                    sb.append(',');
                    i++;
                }

                for (int j = i + 1; j < cursor; j += 2) {
                    sb.append('!');
                }
                i = cursor - 1;
            } else if (chArr[i] == '!') {
                sb.append('!');
                i++;
            } else {
                sb.append(chArr[i]);
            }
        }

        return result;
    }


    // https://blog.51cto.com/u_15069482/3871744
    public class Codec {
        // Encodes a list of strings to a single string.
        public String encode(List<String> strs) {
            StringBuilder sb = new StringBuilder();
            for (String str : strs) {
                sb.append(str.length() + "/" + str);
            }

            return sb.toString();
        }

        // Decodes a single string to a list of strings.
        public List<String> decode(String s) {
            List<String> res = new ArrayList<>();
            int index = 0;

            while (index < s.length()) {
                int sep = s.indexOf("/", index);
                int len = Integer.valueOf(s.substring(index, sep));
                String curr = s.substring(sep + 1, sep + 1 + len);
                res.add(curr);
                index = sep + 1 + len;
            }

            return res;
        }
    }

}
