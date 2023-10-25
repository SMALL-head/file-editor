package src.command.commandImpl;

import src.command.Operator;
import src.context.FileEditorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendTailCommand extends AbstractCommand{
    FileEditorContext ctx;
    int fileLineNumber = 0;  // 文件行数
    int targetLineNum = 0;  // 目标行
    String targetText = null;  // 目标文本
    public AppendTailCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
    }
    private void parseAppendTailCommand(){
        Pattern pattern = Pattern.compile("append-tail\\s+(.*)");
        Matcher matcher = pattern.matcher(originCommand);
        if (matcher.matches()){
            targetText = matcher.group(1);
        }
    }
    @Override
    public void execute() throws Exception {
        targetLineNum = InsertCommand.getFileLines(ctx.getFile()) + 1;
        parseAppendTailCommand();
        System.out.println("添加的行数: " + targetLineNum);
        System.out.println("添加的内容: " + targetText);
        InsertCommand.insertTargetLine(ctx.getFile(), targetLineNum, targetText);
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
}
