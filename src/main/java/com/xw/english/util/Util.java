package com.xw.english.util;

public class Util {

    public static boolean isEnglishWord(String word) {
        String regex = "^[a-zA-Z]+([-.][a-zA-Z]+)*$";
        return word.matches(regex);
    }


    public static String correctWord(String word) {
        char[] chars = word.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (Character.isLetter(chars[i])) {
                return word.substring(0, i + 1);
            }
        }
        return "";
    }
}
