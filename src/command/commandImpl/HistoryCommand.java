package src.command.commandImpl;

import src.command.Operator;
import src.context.FileEditorContext;

/**
 * @author flk
 * @version 1.0
 */
public class HistoryCommand extends AbstractCommand{

    public HistoryCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
        subject.addObserver(FileEditorContext.getContext().getCommandLogger());
    }

    @Override
    public void execute() throws Exception {
        //一个参数
        if (originCommand.matches("history(\\s+\\d+)?")) {
            String[] parts = originCommand.split("\\s+");
            if(parts.length == 1) {
                ctx.getCommandLogger().logCommands();
            }else {
                ctx.getCommandLogger().logCommands(Integer.parseInt(parts[1]));
            }
        }
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

