package src.main;

import src.command.commandImpl.AbstractCommand;
import src.command.factory.CommandFactory;
import src.utils.FileEditorConstants;

import java.util.Scanner;

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
        while (!(stringCommand = scanner.nextLine()).equals(FileEditorConstants.QUIT_STRING)) {
            // 1. 利用工厂模式生成对应的操作
            AbstractCommand abstractCommand = CommandFactory.generateCommand(stringCommand);
            try {
                abstractCommand.execute();
            } catch (Exception ex) {
                System.out.println(ex.getClass() +": " +  ex.getMessage());
            }
        }
    }
}
