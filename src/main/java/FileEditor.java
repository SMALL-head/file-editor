import command.commandImpl.AbstractCommand;
import command.factory.CommandFactory;
import utils.FileEditorConstants;
import utils.SoutUtils;

import static utils.TimeUtils.FormattedTime;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Scanner;



/**
 * 启动类
 * @author zyc
 * @version 1.0
 */
public class FileEditor {
    public static void main(String[] args) {
        // 关闭jvm的时候需要删除临时文件
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookThreadFunc()));

        // 从System.in中读取指令
        fromSource(System.in, true);

    }

    /**
     * main函数中主要的执行函数
     * @param source 指令源。例如可以是System.in或者来自于某个文件
     * @param printToStdout 是否将操作记录打印至stdout中。若为true，在指令执行后可以看到执行结果；若为false，则不会输出提示内容至stdout中。false的情况主要用于自动化测试
     */
    public static void fromSource(InputStream source, boolean printToStdout) {
        SoutUtils.setPrintToStdout(printToStdout);
        String stringCommand;
        Scanner scanner = new Scanner(source);

        try (FileWriter fileWriter = new FileWriter(".log", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println("session start at " + FormattedTime());
        } catch (IOException e) {
            SoutUtils.sout("无法打开日志文件：错误原因：" + e.getMessage());
        }
        SoutUtils.sout("欢迎使用FileEditor系统");
        while (!(stringCommand = scanner.nextLine()).equals(FileEditorConstants.QUIT_STRING)) {
            // 1. 利用工厂模式生成对应的操作
            AbstractCommand abstractCommand = CommandFactory.generateCommand(stringCommand);
            try {
                abstractCommand.execute();
            } catch (Exception ex) {
                SoutUtils.sout("指令执行错误：" + ex.getMessage());
            }
        }
    }
}
