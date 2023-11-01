package src.command.commandImpl;

import src.context.FileEditorContext;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteCommand extends AbstractCommand{
    //FileEditorContext ctx;
    int fileLineNumber = 0;  // 文件行数
    int targetLineNum = 0;  // 删除前，要删除的目标行数
    String deleteInputText = null;  // 输入的要删除的文本
    String deleteFileText = null;  // 实际删除的整行内容
    boolean hasLineNumber = false;  // 是否是 "delete 行号" 指令，区分以内容删除的delete
    boolean isRecordable;

    public DeleteCommand(FileEditorContext ctx, String originCommand, boolean isRecordable) {
        super(originCommand);
        this.ctx = ctx;
        this.isRecordable = isRecordable;
    }

    @Override
    public void execute() throws Exception {
        fileLineNumber = InsertCommand.getFileLines(ctx.getFile());
        parseDeleteTailCommand();
        if (hasLineNumber) {
            deleteWithLineNumber();
        } else {
            deleteWithText();
        }
        System.out.println("删除的行号是：" + targetLineNum);
        System.out.println("删除的内容是：" + deleteFileText);

        super.execute();
    }

    private void parseDeleteTailCommand(){
        // 有歧义，delete 3是删除第三行还是删除内容为 3 的行
        // 这里的实现是行号优先
        Pattern pattern = Pattern.compile("delete\\s*(\\d*)?\\s*(.*)?");
        Matcher matcher = pattern.matcher(originCommand);
        if (matcher.matches()){
            String lineNumberStr = matcher.group(1);
            if (lineNumberStr == null || lineNumberStr.isEmpty()) {
                // 如果没有行数，则是用内容删除
                hasLineNumber = false;
                deleteInputText = matcher.group(2);
            } else {
                hasLineNumber = true;
                targetLineNum = Integer.parseInt(lineNumberStr);
                if (targetLineNum > fileLineNumber) {
                    // 如果要删除的行数大于文件总行数，认为删除最后一行
                    targetLineNum = fileLineNumber;
                }
            }
        }
    }

    private void deleteWithText() throws IOException {
        RandomAccessFile file = ctx.getFile();
        file.seek(0);
        ArrayList<String> lineList = new ArrayList<>();
        String line;
        int lineNumber = 1;
        while ((line = file.readLine()) != null) {
            // 删除逻辑，不知道是包含还是相等
            // if (line.contains(deleteInputText)) {
            if (line.equals(deleteInputText)) {
                targetLineNum = lineNumber;
                deleteFileText = line;
            } else {
                lineNumber++;
                lineList.add(line);
            }
        }
        InsertCommand.writeLineList(ctx.getFile(), lineList);
    }

    private void deleteWithLineNumber() throws IOException {
        RandomAccessFile file = ctx.getFile();
        file.seek(0);
        ArrayList<String> lineList = new ArrayList<>();
        String line;
        int lineNumber = 1;
        while ((line = file.readLine()) != null) {
            if (lineNumber == targetLineNum) {
                deleteFileText = line;
            } else {
                lineList.add(line);
            }
            lineNumber++;
        }
        InsertCommand.writeLineList(ctx.getFile(), lineList);
    }

    @Override
    public boolean isRecordable() {
        return this.isRecordable;
    }

    @Override
    public AbstractCommand reverseOperator() {
        String reverseCommand = "insert " + this.targetLineNum + " " + this.deleteFileText;
        InsertCommand insertCommand = new InsertCommand(this.ctx, reverseCommand, false);
        insertCommand.setLoggable(false);
        return insertCommand;
    }
}
