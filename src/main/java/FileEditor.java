import command.commandImpl.AbstractCommand;
import command.commandImpl.StatsCommand;
import command.factory.CommandFactory;
import context.FileEditorContext;
import utils.FileEditorConstants;
import utils.SoutUtils;

import static utils.TimeUtils.FormattedTime;

import java.io.*;
import java.util.Scanner;

/**
 * 启动类
 * @author zyc
 * @version 1.0
 */
public class FileEditor {
    public static void main(String[] args) {
        // 创建钩子函数，在关闭jvm的时候删除临时文件
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookThreadFunc()));

        // 从System.in中读取指令,并打印执行情况至stdout
        fromStdinPrintStdout();

    }

    public static void fromStdinPrintStdout() {
        fromSource(System.in, System.out);
    }

    /**
     * main函数中主要的执行函数
     * @param source 指定输入源。例如可以是System.in或者来自于某个文件。
     * @param printTo 指令输出源。将操作记录打印至printTo流中。
     */
    public static void fromSource(InputStream source, PrintStream printTo) {
        SoutUtils.setPrintToStdout(printTo);
        String stringCommand;
        Scanner scanner = new Scanner(source);

        // 1. 首次运行的时候，记录session start at...的日志
        try (FileWriter fileWriter = new FileWriter(".log", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println("session start at " + FormattedTime());
        } catch (IOException e) {
            SoutUtils.sout("无法打开日志文件：错误原因：" + e.getMessage());
        }
        SoutUtils.sout("欢迎使用FileEditor系统");

        // 2. 从stdin中读取指令并执行。
        // 通过工厂模式生成对应的指令(command.factory.CommandFactory类)
        // 然后用命令模式来执行(command.commandImpl.AbstractCommand)
        // 执行的过程中用观察者模式记录日志(可以在AbstractCommand看到)
        while (true) {
            if (!(stringCommand = scanner.nextLine()).equals(FileEditorConstants.QUIT_STRING)) {
                // 1. 利用工厂模式生成对应的操作
                AbstractCommand abstractCommand = CommandFactory.generateCommand(stringCommand);
                try {
                    abstractCommand.execute();
                } catch (Exception ex) {
                    SoutUtils.sout("指令执行错误：" + ex.getMessage());
                }
            }
            else {
                AbstractCommand abstractCommand = new StatsCommand(FileEditorContext.getContext(), "stats q");
                try {
                    abstractCommand.execute();
                } catch (Exception ex) {
                    SoutUtils.sout("指令执行错误：" + ex.getMessage());
                }
                break;
            }
        }
    }
}
