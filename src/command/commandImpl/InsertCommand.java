package src.command.commandImpl;

import src.command.Operator;
import src.context.FileEditorContext;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertCommand extends AbstractCommand{
    //FileEditorContext ctx;
    int fileLineNumber = 0;  // 文件行数
    int targetLineNum = 0;  // 目标行
    String targetText = null;  // 目标文本


    public InsertCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
        subject.addObserver(FileEditorContext.getContext().getCommandLogger());
    }

    @Override
    public void execute() throws Exception {
        fileLineNumber = getFileLines(ctx.getFile());
        parseInsertCommand();
        insertTargetLine(ctx.getFile(), targetLineNum, targetText);

        super.execute();
    }

    @Override
    public boolean isRecordable() {
        return true;
    }

    @Override
    public Operator reverseOperator() {
        return super.reverseOperator();
    }

    private void parseInsertCommand() {
        // 避免 insert 3 apples 歧义: 到底是插入 3 apples 呢，还是在第三行插入 apples 呢？
        // 可能设想：插入的文本必须以 "" 包裹
        // 目前先不管这个歧义，默认第二个是行号（如果是数字的话）
        // 1. 没有行数，比如 insert xxx
        Pattern pattern = Pattern.compile("insert\\s*(\\d*)?\\s*(.*)");
        Matcher matcher = pattern.matcher(originCommand);
        if (matcher.matches()){
            String lineNumberStr = matcher.group(1);
            targetText = matcher.group(2);

            if (lineNumberStr == null || lineNumberStr.equals("")) {
                targetLineNum = fileLineNumber + 1;
            } else {
                targetLineNum = Integer.parseInt(lineNumberStr);
                if (targetLineNum > fileLineNumber)
                    targetLineNum = fileLineNumber + 1;
            }
            System.out.println("插入的行号: " + targetLineNum);
            System.out.println("插入的内容: " + targetText);
        }

    }
    public static int getFileLines(RandomAccessFile file) {
        // 读完，指针就在文件尾了
        int lineCount = 0;
        try {
            file.seek(0);  // 一定要移动到文件最开始
            while (file.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    public static void insertTargetLine(RandomAccessFile file,int targetLine, String targetContext) throws IOException {
        file.seek(0);
        ArrayList<String> lineList = new ArrayList<>();
        String line;
        while ((line = file.readLine()) != null) {
            lineList.add(line);
        }
        // line 是从1开始的，但是index是从0开始的
        lineList.add(targetLine - 1, targetContext);
        writeLineList(file, lineList);

    }
    public static void writeLineList(RandomAccessFile file, ArrayList<String> lineList) throws IOException {
        String line;
        file.setLength(0); // 清空内容
        int size = lineList.size();
        for (int i = 0; i < size; i++) {
            line = lineList.get(i);
            file.writeBytes(line);
            if (i < size - 1) {
                file.writeBytes(System.lineSeparator());
            }
        }
    }
}
