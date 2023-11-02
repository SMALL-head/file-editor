import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 测试文件
 * @author zyc
 * @version 1.0
 */

public class FileEditorTest {
    /**
     * 用例一：load命令测试，主要测试连续两次load中，第二次load的错误提示
     */
    @Test
    public void testLoad() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("./src/test/script/load.txt")) {
            FileEditor.fromSource(fileInputStream, false);
        }
    }

    /**
     * 用例二：insert相关命令是否能够执行成功
     */
    @Test
    public void testInsert() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("./src/test/script/insert.txt")) {
            FileEditor.fromSource(fileInputStream, false);
        }
    }

}
