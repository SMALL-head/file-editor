package src.utils;

/**
 * @author zyc
 * @version 1.0
 */
public class StringUtils {
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static void printSpace(int num) {
        for (int i = 0; i < num; ++i) {
            System.out.print(" ");
        }
    }
}
