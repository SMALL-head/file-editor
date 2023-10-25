package src.command.commandImpl;

import src.context.FileEditorContext;
import src.exception.IllegalException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedList;

/**
 * Example： LoadCommand
 * 没有逆操作，因此没有实现reverseCommand方法。对于insert命令需要实现这个方法
 *
 * @author zyc
 * @version 1.0
 */
public class LoadCommand extends AbstractCommand {
    String filePath;
    //FileEditorContext ctx;

    public LoadCommand(FileEditorContext ctx, String filePath, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
        this.filePath = filePath;
        subject.addObserver(FileEditorContext.getContext().getCommandLogger());
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

        // 新增tmp文件，从file中复制内容到tmp文件中
        File tmpFile = new File(filePath + ".tmp");
        boolean createTmpFile = tmpFile.createNewFile();
        if (!createTmpFile) {
            System.out.println("无法编辑文件");
            throw new IOException();
        }
        Files.copy(Path.of(file.getPath()), Path.of(filePath + ".tmp"), StandardCopyOption.REPLACE_EXISTING);

        ctx.setActiveFile(filePath);
        ctx.setFile(new RandomAccessFile(tmpFile, "rw"));
        ctx.setExecuteStack(new LinkedList<>());

        System.out.println("打开文件: " + filePath);
        ctx.setDate(new Date()); // 设置文件当前打开时间
        super.execute();
    }

    @Override
    public boolean isRecordable() {
        return true;
    }

    private boolean isValidFilePath(String path) {
        // 此处可以添加更多的合法性检查
        // 这里的示例只是检查路径中是否包含了不允许的字符
        String illegalChars = "[\\\\/:*?\"<>|]";
        return !path.matches(".*" + illegalChars + ".*");
    }
}
