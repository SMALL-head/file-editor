package src.command.commandImpl;

import src.context.FileEditorContext;
import src.exception.IllegalException;

import java.io.*;

/**
 * Example： LoadCommand
 * 没有逆操作，因此没有实现reverseCommand方法。对于insert命令需要实现这个方法
 *
 * @author zyc
 * @version 1.0
 */
public class LoadCommand extends AbstractCommand {
    String filePath;
    FileEditorContext ctx;

    public LoadCommand(FileEditorContext ctx, String filePath) {
        this.ctx = ctx;
        this.filePath = filePath;
    }

    @Override
    public void execute() throws Exception {
        // 校验文件路径是否合法
        if (!isValidFilePath(filePath)) {
            throw new IllegalException("文件路径不合法");
        }

        // 如果不存在文件，就创建一个新的文件
        File file = new File(filePath);
        if (!file.exists()) {
            boolean create = file.createNewFile();
            if (create) {
                System.out.println("创建新的文件: " + filePath);
            }
        }

        // 存储打开的文件
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        ctx.setActiveFile(filePath);
        ctx.setReader(reader);
        ctx.setWriter(writer);

        System.out.println("打开文件: " + filePath);
    }

    @Override
    public boolean isRecordable() {
        return false;
    }

    private boolean isValidFilePath(String path) {
        // 此处可以添加更多的合法性检查
        // 这里的示例只是检查路径中是否包含了不允许的字符
        String illegalChars = "[\\\\/:*?\"<>|]";
        return !path.matches(".*" + illegalChars + ".*");
    }
}
