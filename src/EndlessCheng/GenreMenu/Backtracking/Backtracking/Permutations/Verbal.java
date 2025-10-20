package EndlessCheng.GenreMenu.Backtracking.Backtracking.Permutations;

import java.util.Arrays;

public class Verbal {

    // https://leetcode.cn/problems/verbal-arithmetic-puzzle/solutions/772720/ying-he-hui-su-java-by-da-shi-shuo-3l7a/
    int[] c2N = new int[26]; //char to num
    int[] n2C = new int[10]; //num to char
    boolean[] not0 = new boolean[26]; //不為0的字母

    public boolean isSolvable(String[] words, String result) {
        int maxWord = 1;
        for (String word : words) {
            //記錄不能為前導零的字母
            if (word.length() > 1) {
                not0[word.charAt(0) - 'A'] = true;
            }
            maxWord = Math.max(maxWord, word.length());
            //如果存在word長度大於result長度，無解
            if (word.length() > result.length()) {
                return false;
            }
        }
        //最大word長度+1小於result長度，無解
        if (maxWord + 1 < result.length()) {
            return false;
        }
        //記錄不能為前導零的字母
        if (result.length() > 1) {
            not0[result.charAt(0) - 'A'] = true;
        }
        Arrays.fill(c2N, -1);
        Arrays.fill(n2C, -1);
        return dfs(words, result, 0, 0, 0);
    }

    public boolean dfs(String[] words, String result, int wordIdx, int pos, int x) { //wordIdx為當前遍歷到的word索引，pos為已經校驗和的倒數索引， x為進位
        //遍歷到result第一位之前，若進位為0，恰好找到解
        if (pos == result.length()) {
            return x == 0;
        }

        //遍歷完一輪words，
        if (wordIdx == words.length) {
            //所有word倒數pos位置的數字和
            int sum = x;
            for (String word : words) {
                //如果word長度不足則直接跳過
                if (word.length() > pos) {
                    sum += c2N[word.charAt(word.length() - 1 - pos) - 'A'];
                }
            }
            int num = sum % 10;
            char c = result.charAt(result.length() - 1 - pos);
            //result倒數pos位置的字母已經有映射，則必須等於num
            if (c2N[c - 'A'] != -1) {
                if (c2N[c - 'A'] == num) {
                    //wordidx回到0， pos向前進一個，進位更新為sum / 10
                    return dfs(words, result, 0, pos + 1, sum / 10);
                }
                return false;
            } else {
                //如果num已經被其他字母映射，無解
                if (n2C[num] != -1) {
                    return false;
                }
                //result倒數pos位置的字母映射為num
                c2N[c - 'A'] = num;
                n2C[num] = (int) c;
                boolean check = dfs(words, result, 0, pos + 1, sum / 10);
                if (check) {
                    return true;
                }
                n2C[num] = -1;
                c2N[c - 'A'] = -1;
                return false;
            }
        } else { //在某一輪words的遍歷中
            String word = words[wordIdx];
            //當前word長度不足，wordidx向前進一個
            if (word.length() <= pos) {
                return dfs(words, result, wordIdx + 1, pos, x);
            } else {
                char c = word.charAt(word.length() - 1 - pos);
                //word在倒數pos位置已經有映射
                if (c2N[c - 'A'] != -1) {
                    return dfs(words, result, wordIdx + 1, pos, x);
                } else {
                    //如果該位置字母不能為0，嘗試在1~9中為其分配
                    if (not0[c - 'A']) {
                        for (int i = 1; i < 10; i++) {
                            if (n2C[i] == -1) {
                                n2C[i] = c;
                                c2N[c - 'A'] = i;
                                //wordIdx向前進一個，pos不變
                                boolean check = dfs(words, result, wordIdx + 1, pos, x);
                                if (check) {
                                    return true;
                                }
                                c2N[c - 'A'] = -1;
                                n2C[i] = -1;
                            }
                        }
                    } else {
                        //在0-9中為其分配
                        for (int i = 0; i < 10; i++) {
                            if (n2C[i] == -1) {
                                n2C[i] = c;
                                c2N[c - 'A'] = i;
                                boolean check = dfs(words, result, wordIdx + 1, pos, x);
                                if (check) {
                                    return true;
                                }
                                c2N[c - 'A'] = -1;
                                n2C[i] = -1;
                            }
                        }
                    }
                }
            }
        }
        //所有方案都不滿足
        return false;
    }


}
