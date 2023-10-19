package src.main;

import src.command.commandImpl.AbstractCommand;
import src.command.factory.CommandFactory;
import src.context.FileEditorContext;

import java.util.Scanner;

/**
 * 启动类
 * @author zyc
 * @version 1.0
 */
public class FileEditor {
    public static void main(String[] args) throws Exception {
        String stringCommand = null;
        Scanner scanner = new Scanner(System.in);
        FileEditorContext ctx = FileEditorContext.getContext(); // 获取上下文单例
        while (!(stringCommand = scanner.nextLine()).equals("q")) {
            // 1. 利用工厂模式生成对应的操作
            AbstractCommand abstractCommand = CommandFactory.generateCommand(stringCommand);
            abstractCommand.execute();
        }
    }
}
