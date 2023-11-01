package src.command.commandImpl;

import src.context.FileEditorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendTailCommand extends AbstractCommand{
    FileEditorContext ctx;
    int targetLineNum = 0;  // 目标行
    String targetText = null;  // 目标文本
    boolean isRecordable;

    public AppendTailCommand(FileEditorContext ctx, String originCommand, boolean isRecordable) {
        super(originCommand);
        this.ctx = ctx;
        this.isRecordable = isRecordable;
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
        System.out.println("添加到文件末尾的内容是: " + targetText);
        InsertCommand.insertTargetLine(ctx.getFile(), targetLineNum, targetText);
        super.execute();
    }

    @Override
    public boolean isRecordable() {
        return this.isRecordable;
    }

    @Override
    public AbstractCommand reverseOperator() {
        String reverseCommand = "delete " + this.targetLineNum;
        DeleteCommand deleteCommand = new DeleteCommand(this.ctx, reverseCommand, false);
        deleteCommand.setLoggable(false);
        return deleteCommand;
    }
}
