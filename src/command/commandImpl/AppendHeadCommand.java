package src.command.commandImpl;

import src.command.Operator;
import src.context.FileEditorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppendHeadCommand extends AbstractCommand{
    //FileEditorContext ctx;
    int fileLineNumber = 0;  // 文件行数
    String targetText = null;  // 目标文本
    public AppendHeadCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
        subject.addObserver(FileEditorContext.getContext().getCommandLogger());
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
        return true;
    }

    @Override
    public Operator reverseOperator() {
        return super.reverseOperator();
    }


}
