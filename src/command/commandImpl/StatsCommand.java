package src.command.commandImpl;

import src.context.FileEditorContext;

/**
 * @author wpy
 * @version 1.0
 */
public class StatsCommand extends AbstractCommand{

    public StatsCommand(FileEditorContext ctx, String originCommand) {
        super(originCommand);
        this.ctx = ctx;
    }

    @Override
    public void execute() throws Exception {
        if (originCommand.matches("stats((\\s+all)|(\\s+current)|(\\s+q))?")) {
            String[] parts = originCommand.split("\\s+");
            if(parts.length == 1) {
                ctx.getSessionStats().printSessionStats("current");//default: current
            }
            else if (parts[1].equals("current")) {
                ctx.getSessionStats().printSessionStats("current");//current
            }
            else if (parts[1].equals("all")) {
                ctx.getSessionStats().printSessionStats("all");//all
            }
            else {
                ctx.getSessionStats().printSessionStats("q");//q
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