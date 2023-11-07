import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

/**
 * 测试文件
 *
 * @author zyc
 * @version 1.0
 */

public class FileEditorTest {
    @Before
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookThreadFunc()));
    }
    /**
     * 用例一：load命令测试，主要测试连续两次load中，第二次load的错误提示
     */
    @Test
    public void testLoad() throws IOException {
        String result = "./src/test/res/testcase1.txt";
        try (FileInputStream fileInputStream = new FileInputStream("./src/test/script/load.txt");
             PrintStream outStream = new PrintStream(new FileOutputStream(result))) {
            // 清空outStream中的内容
            outStream.write(new byte[0]);
            FileEditor.fromSource(fileInputStream, outStream);
        }
        // 保证两个文件的输入是正确的,并清空result文件内容，防止影响下次测试
        compareTwoFile(result, "./src/test/res/expect/testcase1.txt");
        clearFile(result);
    }

    /**
     * 用例二：insert相关命令是否能够执行成功
     */
    @Test
    public void testInsert() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("./src/test/script/insert.txt")) {
            FileEditor.fromSource(fileInputStream, null); // 无需检验打印内容
        }
        // 但需要校验testcase2.md中的内容是否正确，并清空文件内容
        String filename = "./testfile/testcase2.md";
        check(filename, "# title", "# tail", "## 二级标题");
        clearFile(filename);
    }

    /**
     * 用例三：显示功能测试
     */
    @Test
    public void testList() throws IOException {
        String result = "./src/test/res/testcase3.txt";
        try (FileInputStream fileInputStream = new FileInputStream("./src/test/script/list.txt");
             PrintStream outStream = new PrintStream(new FileOutputStream(result))) {
            // 清空outStream中的内容
            outStream.write(new byte[0]);
            FileEditor.fromSource(fileInputStream, outStream);
        }
        compareTwoFile(result, "./src/test/res/expect/testcase3.txt");
        clearFile("./testfile/list.md");
        clearFile(result);
    }

    @Test
    public void testRedoUndo() throws IOException {
        String result = "./src/test/res/testcase4.txt";
        try (FileInputStream fileInputStream = new FileInputStream("./src/test/script/redo_undo.txt");
             PrintStream outStream = new PrintStream(new FileOutputStream(result))) {
            // 清空outStream中的内容
            outStream.write(new byte[0]);
            FileEditor.fromSource(fileInputStream, outStream);
        }
        compareTwoFile(result, "./src/test/res/expect/testcase4.txt");
//        clearFile(result);
        String testcase4MD = "./testfile/testcase4.md";
        check(testcase4MD, "## level two title", "### level three title");
        clearFile(testcase4MD);
        clearFile(result);
    }

    /**
     * 校验目标md文件中内容是否正确
     *
     * @param filename 需要校验的文件名
     * @param target   expect的值。输入{@param filename}文件中的各行expect的值
     * @throws IOException 有可能导致各种io异常。
     */
    private void check(String filename, String... target) throws IOException {
        try (FileReader fr = new FileReader(filename, Charset.defaultCharset())) {
            BufferedReader bf = new BufferedReader(fr);
            for (String string : target) {
                String s = bf.readLine();
                assertEquals(string, s);
            }
        }
    }

    private void clearFile(String filename) throws IOException {
        File file = new File(filename);
        if (file.exists()) {
            try (FileWriter fw = new FileWriter(file)) {
                fw.write("");
            }
        }
    }

    private void compareTwoFile(String filename1, String filename2) throws IOException {
        try (FileReader fr1 = new FileReader(filename1, Charset.defaultCharset());
             FileReader fr2 = new FileReader(filename2, Charset.defaultCharset())) {
            BufferedReader bf1 = new BufferedReader(fr1);
            BufferedReader bf2 = new BufferedReader(fr2);
            String s1, s2;
            while ((s1 = bf1.readLine()) != null && (s2 = bf2.readLine()) != null) {
                assertEquals(s1, s2);
            }
        }
    }
}
