package src.command.commandImpl;

import src.context.FileEditorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendHeadCommand extends AbstractCommand{
    //FileEditorContext ctx;
    int fileLineNumber = 0;  // 文件行数
    String targetText = null;  // 目标文本
    boolean isRecordable;

    public AppendHeadCommand(FileEditorContext ctx, String originCommand, boolean isRecordable) {
        super(originCommand);
        this.ctx = ctx;
        this.isRecordable = isRecordable;
    }

    @Override
    public void execute() throws Exception {
        fileLineNumber = InsertCommand.getFileLines(ctx.getFile());
        parseAppendHeadCommand();
        System.out.println("添加到文件首部的内容是: " + targetText);
        InsertCommand.insertTargetLine(ctx.getFile(),1,targetText);
        super.execute();
    }

    private void parseAppendHeadCommand(){
        Pattern pattern = Pattern.compile("append-head\\s+(.*)");
        Matcher matcher = pattern.matcher(originCommand);
        if (matcher.matches()){
            targetText = matcher.group(1);
        }
    }

    @Override
    public boolean isRecordable() {
        return this.isRecordable;
    }

    @Override
    public AbstractCommand reverseOperator() {
        String reverseCommand = "delete 1";
        DeleteCommand deleteCommand = new DeleteCommand(this.ctx, reverseCommand, false);
        deleteCommand.setLoggable(false);
        return deleteCommand;
    }
}
