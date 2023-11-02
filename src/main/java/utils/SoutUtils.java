package utils;

/**
 * @author zyc
 * @version 1.0
 */
public class SoutUtils {
    static boolean printToStdout = true;

    public static void sout(String msg) {
        if (printToStdout) {
            System.out.println(msg);
        }
    }

    public static void setPrintToStdout(boolean printToStdout) {
        SoutUtils.printToStdout = printToStdout;
    }
}
