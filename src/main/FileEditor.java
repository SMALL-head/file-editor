package src.main;

import src.command.commandImpl.AbstractCommand;
import src.command.factory.CommandFactory;
import src.utils.FileEditorConstants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static src.utils.TimeUtils.FormattedTime;


/**
 * 启动类
 * @author zyc
 * @version 1.0
 */
public class FileEditor {
    public static void main(String[] args) throws Exception {
        // 关闭jvm的时候需要删除临时文件
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHookThreadFunc()));

        String stringCommand;
        Scanner scanner = new Scanner(System.in);

        try (FileWriter fileWriter = new FileWriter(".log", true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            printWriter.println("session start at " + FormattedTime());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            if (!(stringCommand = scanner.nextLine()).equals(FileEditorConstants.QUIT_STRING)) {
                // 1. 利用工厂模式生成对应的操作
                AbstractCommand abstractCommand = CommandFactory.generateCommand(stringCommand);
                try {
                    abstractCommand.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            else {
                AbstractCommand abstractCommand = CommandFactory.generateCommand("stats q");
                try {
                    abstractCommand.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            }
        }
    }
}
