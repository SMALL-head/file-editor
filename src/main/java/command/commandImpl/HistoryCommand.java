package command.commandImpl;

import context.FileEditorContext;

/**
 * @author flk
 * @version 1.0
 */
public class HistoryCommand extends AbstractCommand{

    public HistoryCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
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
        return false;
    }

    @Override
    public AbstractCommand reverseOperator() {
        return super.reverseOperator();
    }
}

