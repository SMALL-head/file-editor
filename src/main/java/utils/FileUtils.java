package utils;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author zyc
 * @version 1.0
 */
public class FileUtils {
    public static void copyThenDelete(String src, String dst) throws IOException {
        Path srcPath = Path.of(src);
        Path dstPath = Path.of(dst);
        Files.copy(srcPath, dstPath, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(srcPath);
    }
}
