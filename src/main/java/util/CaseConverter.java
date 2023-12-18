package main.java.util;

/**
 * Utility-класс, предназгначенный для конвертации camel case в snake case и наоборот.
 */
public class CaseConverter {

    /**
     * Преобразует строку типа name_of_product в строку типа nameOfProduct
     */
    public static String convertToCamelCase(String s) {
        char[] chars = s.toCharArray();
        int changes = 0;
        for (int i = 0; i < chars.length; i++) {
            if (String.valueOf(chars[i]).equals("_")) {
                String begin = s.substring(0, i - changes);
                String end = s.substring(i + 2 - changes, s.length());
                ++changes;
                s = begin + String.valueOf(chars[i + 1]).toUpperCase() + end;
            }
        }
        return s;
    }

    /**
     * Преобразует строку типа nameOfProduct в строку типа name_of_product
     */
    public static String convertToSnakeCase(String s) {
        char[] chars = s.toCharArray();
        int changes = 0;
        for (int i = 0; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                String begin = s.substring(0, i + changes);
                ++changes;
                String end = s.substring(i + changes, s.length());
                s = begin + "_" + String.valueOf(chars[i]).toLowerCase() + end;
            }
        }
        return s;
    }
}
