package utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author zyc
 * @version 1.0
 */
public class SoutUtils {
    static PrintStream printTo = System.out;

    public static void sout(String msg) {
        printTo.writeBytes(msg.getBytes(StandardCharsets.UTF_8));
        printTo.println();
    }
    public static void soutWithoutLn(String msg) {
        printTo.print(msg);
    }

    public static void setPrintToStdout(PrintStream printTo) {
        SoutUtils.printTo = printTo;
    }
}
